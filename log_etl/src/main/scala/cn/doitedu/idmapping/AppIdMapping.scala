package cn.doitedu.idmapping

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DataTypes, StructType}

object AppIdMapping {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.WARN)

    val spark = SparkSession.builder()
      .appName("")
      .master("local")
      .getOrCreate()
    import spark.implicits._
    val schema = new StructType().add("guid", DataTypes.StringType)
      .add("did", DataTypes.StringType)
      .add("ts", DataTypes.IntegerType)


    // 加载历史映射
    val pre: RDD[(String, (List[(String, Int, Int)], String))] = spark.sparkContext.makeRDD(Seq(
      ("d0", (List(("u0", 7, 100)), "u0")),
      ("d1", (List(("u1", 9, 100)), "u1")),
      ("d2", (List(("u2", 8, 100)), "u2")),
      ("d3", (List(("u2", 9, 90)), "u2")),
      ("d4", (Nil, "d4")),
      ("d5", (Nil, "d5"))
    )
    )

    // 加载当天数据
    val frame = spark.read.schema(schema).csv("testdata\\appidmp\\input\\day1")
    val rdd = frame.rdd.map(row => {
      val uid = row.getAs[String](0)
      val did = row.getAs[String](1)
      val ts = row.getAs[Int](2)
      (uid, did, ts)
    })

    // 去重留早  uid,did,ts
    val uidDistinct: RDD[(String, String, Int)] = rdd.groupBy(tp => (tp._1, tp._2)).map(tp => {
      val tuples: List[(String, String, Int)] = tp._2.toList.sortBy(_._3)
      tuples(0)
    })
    uidDistinct.take(10).foreach(println)


    // 按did分组，按ts排序，对uid打分
    val dev2uid: RDD[(String, List[(String, Int, Int)])] = uidDistinct.groupBy(_._2)
      .map(tp => {
        // 组内排序
        val tuples = tp._2.toList.sortBy(_._3).filter(_._1 != null)
        val uids = for (i <- 0 until tuples.size) yield (tuples(i)._1, tuples(i)._3, 100 - 10 * i)
        (tp._1, uids.toList)
      })

    println("------------------------")
    dev2uid.take(10).sortBy(_._1).foreach(println)

    println("------------------------")
    pre.take(10).sortBy(_._1).foreach(println)

    // 合并两个映射表
    println("------------------------")
    val joined = dev2uid.fullOuterJoin(pre)
    joined.take(10).foreach(println)
    val idmp = joined.map(tp => {
      val did = tp._1
      val left: List[(String, Int, Int)] = tp._2._1.get.toList
      val right: Option[(List[(String, Int, Int)], String)] = tp._2._2

      // 左边为空列表，右边有some
      if (left.size < 1 && right.isDefined) {
        (did, (right.get._1, right.get._2))
        // 左边为空列表，右边为None  --> 今日匿名用户历史上没有过
      } else if (left.size < 1 && right.isEmpty) {
        (did, (Nil, did))
        // 左边不为空，右边为None
      }else if(left.size > 0 && right.isDefined && right.get._1.size<1){
        (did, (left, right.get._2))
      }
      else if (left.size > 0 && right.isEmpty) {
        (did, (left, getBind(left)))
      } else {
        val merged = mergeLst(left, right.get._1)
        (did, (merged, getBind(merged)))
      }
    })
    println("------------------------")
    idmp.take(10).sortBy(_._1).foreach(println)


    spark.close()
  }

  def mergeLst(lst1: List[(String, Int, Int)], lst2: List[(String, Int, Int)]): List[(String, Int, Int)] = {
    val lst = lst1 ::: lst2
    val res: List[(String, Int, Int)] = lst.groupBy(tp => tp._1).map(tp => {
      val uid = tp._1
      val tmp = tp._2.reduce((x, y) => {
        (x._1, x._2, x._3 + y._3)
      })
      tmp
    }).toList
    res
  }

  def getBind(lst: List[(String, Int, Int)]): String = {

    val st: List[(String, Int, Int)] = lst.sortBy(tp => (-tp._3, tp._2))
    st(0)._1
  }


}
