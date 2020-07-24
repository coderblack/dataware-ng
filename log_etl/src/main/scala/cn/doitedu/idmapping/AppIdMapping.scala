package cn.doitedu.idmapping

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SparkSession, types}
import org.apache.spark.sql.types.{DataTypes, StructType}

import scala.collection.mutable.ListBuffer

object AppIdMapping {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)

    val spark = SparkSession.builder()
      .appName("")
      .master("local")
      .getOrCreate()

    val schema = new StructType().add("uid",DataTypes.StringType)
      .add("deviceid",DataTypes.StringType)
      .add("ts",DataTypes.IntegerType)

    val d1 = spark.read.schema(schema).csv("testdata\\appidmp\\input\\day1")
    d1.show(10,false)
    val d1rdd = d1.rdd.map(row=>{
      val uid = row.getAs[String](0)
      val deviceid = row.getAs[String](1)
      val ts = row.getAs[Int](2)
      (uid,deviceid,ts,0)
    })

    // 挑出没有uid的
    val nouid = d1rdd.filter(tp => tp._1 == null)
    val nouids = nouid.map(_._2).distinct()
    nouids.take(10).foreach(println)

    // 过滤出有uid的
    val haveuid = d1rdd.filter(tp => tp._1 != null)
    // uid,device相同的记录，去重留一条
    val uids = haveuid.groupBy(tp=>(tp._1,tp._2)).map(tp=>{
      val tuples = tp._2.toArray.sortBy(tp => tp._3)
      tuples(0)
    })
    uids.take(10).foreach(println)

    // 按相同uid分组，形成每个user的设备id列表
    val lst = uids.groupBy(_._1).map(tp=>{
      val uid = tp._1
      val arr = tp._2.toArray.sortBy(_._3)
      val lb = for(i <- 0 until(arr.size)) yield {
        val tp = arr(i)
        (tp._2,tp._3,tp._4+(1000-i*100))
      }
      (uid,lb.toList)
    })
    //lst.take(10).foreach(println)

    import spark.implicits._
    lst.toDF()



    spark.close()
  }
}
