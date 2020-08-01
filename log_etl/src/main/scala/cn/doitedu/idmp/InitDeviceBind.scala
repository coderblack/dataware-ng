package cn.doitedu.idmp

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Encoders, SparkSession}

case class Bind(uid:String,ts:Long,score:Int)
case class BindList(
                   did:String,
                   lst:List[Bind],
                   guid:String
                   )

object InitDeviceBind {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)

    val spark = SparkSession.builder()
      .appName("")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    val b = Bind("u1", 1L, 300)
    val df = spark.createDataset(Seq(BindList("d1", List(b), "u1")))
    df.show(10,false)
    df.printSchema()

    df.write.saveAsTable("dim.device_bind")

    spark.close()

  }

}
