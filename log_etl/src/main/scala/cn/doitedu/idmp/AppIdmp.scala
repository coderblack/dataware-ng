package cn.doitedu.idmp

import java.util

import cn.doitedu.beans.Bean
import com.alibaba.fastjson.{JSON, JSONObject}
import com.google.gson.Gson
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

import scala.collection.{immutable, mutable}

case class Guid(deviceid:String,lst:List[Bind],guid:String)
object AppIdmp {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)

    val spark = SparkSession.builder()
      .appName("")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._

    // 1. 加载T日日志数据
    /*val odsEvent = spark.read.table("ods.app_event_log")
    odsEvent.show(10,false)*/

    val schema = new StructType().add("guid", DataTypes.StringType)
      .add("did", DataTypes.StringType)
      .add("ts", DataTypes.LongType)
    val frame = spark.read.schema(schema).csv("testdata\\appidmp\\input\\day1\\d1.csv")
    val odsEvent = frame.rdd.map(row => {
      val uid = row.getAs[String](0)
      val did = row.getAs[String](1)
      val ts = row.getAs[Long](2)
      (uid, did, ts)
    }).toDF("account", "deviceid", "timestamp")


    // 1.1 对日志数据中的(deviceid,account)组合去重，每种组合只取时间最早的一对
    val window = Window.partitionBy("account", "deviceid").orderBy("timestamp")
    val pairs = odsEvent.select("account", "deviceid", "timestamp").select('account, 'deviceid, 'timestamp, row_number() over (window) as "rn").where("rn=1").select('account, 'deviceid, 'timestamp)
    pairs.show(20, false)


    // 1.2 对(deviceid,account)组合按时间先后顺序打分
    val scored: RDD[(String, List[Bind])] = pairs.rdd.map(row => {
      val account = row.getAs[String]("account")
      val deviceid = row.getAs[String]("deviceid")
      val timestamp = row.getAs[Long]("timestamp")
      (deviceid, account, timestamp)
    })
      .groupBy(_._1)
      .map(tp => {
        val deviceid = tp._1
        val tuples: List[(String, Long)] = tp._2.map(tp => (tp._2, tp._3)).toList.sortBy(_._2)
        val sc = for (i <- 0 until tuples.size) yield (Bind(tuples(i)._1, tuples(i)._2, 100 - i * 10))
        (deviceid, sc.toList)
      })


    // 2.加载T-1日device_bind表
    val oldBind: Dataset[String] = spark.read.textFile("testdata\\appidmp\\idmpout\\day0\\d1.dat")

    val old = oldBind.rdd.map(s => {
      val nObject = JSON.parseObject(s)
      val deviceid = nObject.getString("deviceid")
      val arr = nObject.getJSONArray("lst")
      val guid = nObject.getString("guid")
      val lst = new mutable.ListBuffer[Bind]()
      for (i <- 0 until arr.size()) {
        val obj = arr.getJSONObject(i)
        val account = obj.getString("account")
        val timestamp = obj.getLong("timestamp")
        val score = obj.getInteger("score")
        lst += Bind(account, timestamp, score)
      }
      (deviceid, (lst.toList, guid))
    })

    scored.foreach(println)
    println("--------------------")
    old.foreach(println)

    println("--------------------")
    // 3.两表合并
    val tmp = scored.fullOuterJoin(old)
    tmp.foreach(println)

    println("--------------------")
    val res = tmp
      .map(tp => {
        var guid: String = null
        var lst: List[Bind] = List.empty[Bind]


        val deviceid = tp._1
        val left = tp._2._1
        val right = tp._2._2

        if (left.isDefined && right.isDefined) {
          val lst1 = left.get.filter(_.uid != null)
          val lst2 = right.get._1.filter(_.uid != null)
          if (lst1.size < 1) {
            lst = right.get._1
            guid = right.get._2
          } else if (lst2.size < 1) {
            lst = lst1
            guid = getGuid(lst)
          } else {
            lst = mergeLst(lst1, lst2)
            guid = getGuid(lst)
          }
        } else {
          if (left.isEmpty) {
            lst = right.get._1
            guid = right.get._2
          }

          if (right.isEmpty) {
            lst = left.get.filter(_.uid != null)
            guid = if (lst.size > 0) getGuid(lst) else deviceid
          }
        }
        (deviceid, lst, guid)
      })
    res.foreach(println)
    println("--------------------")
    val jsonRes = res.map(e=>{
      val deviceid = e._1
      val lst = e._2
      val guid = e._3
      val bean = new Bean()
      bean.setDeviceid(deviceid)
      bean.setGuid(guid)

      val lst2 = new util.ArrayList[util.HashMap[String, String]]()
      for (elem <- lst) {
        val m = new util.HashMap[String, String]()
        m.put("account",elem.uid)
        m.put("timestamp",elem.ts+"")
        m.put("score",elem.score+"")
        lst2.add(m)
      }
      bean.setLst(lst2)

      val gson = new Gson()
      gson.toJson(bean)
    })

    jsonRes.coalesce(1).saveAsTextFile("testdata\\appidmp\\idmpout\\day01")
    res.coalesce(1).map(tp=>tp._1+","+tp._3).saveAsTextFile("testdata\\appidmp\\guid\\day01")


    spark.close()
  }

  def mergeLst(lst1: List[Bind], lst2: List[Bind]): List[Bind] = {
    val lst = lst1 ::: lst2
    val res: List[Bind] = lst.groupBy(b => b.uid).map(tp => {
      val uid = tp._1
      val tmp = tp._2.reduce((x, y) => {
        Bind(x.uid, x.ts, x.score + y.score)
      })
      tmp
    }).toList
    res
  }

  def getGuid(lst: List[Bind]): String = {

    val st: List[Bind] = lst.sortBy(b => (-b.score, b.ts))
    st(0).uid
  }

}
