
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Encoders, SparkSession}
import org.codehaus.jackson.map.ObjectMapper
import org.json4s._
import org.json4s.jackson.JsonMethods._
import util.SparkUtil

object JsonDemo {
  def main(args: Array[String]): Unit = {
    val json = "{\"account\":\"BowImPnD\",\"carrier\":\"中国电信\",\"deviceId\":\"8IUsjEUZmiST\",\"deviceType\":\"HUAWEI-RY-10\",\"eventId\":\"productView\",\"ip\":\"110.127.102.188\",\"latitude\":29.26816003265482,\"longitude\":88.95606277351817,\"netType\":\"WIFI\",\"osName\":\"android\",\"osVersion\":\"7.8\",\"properties\":{\"needAccount\":false,\"pageId\":\"669\",\"productId\":\"319\",\"refType\":\"1\",\"refUrl\":\"799\",\"title\":\"EFl raD AVh\",\"url\":\"ZPo/lOR\",\"utm_campain\":\"12\",\"utm_loctype\":2,\"utm_source\":\"5\"},\"resolution\":\"1024*768\",\"sessionId\":\"dv1bMNkiYBF\",\"timeStamp\":1595658653948}"
    val eventLog: EventLog = JSON.parseObject(json, classOf[EventLog])
    System.out.println(eventLog)
    implicit val formats = DefaultFormats
    val eventCase = parse(json).extract[EventCase]
    println(eventCase)


    val spark = SparkUtil.getSpark()

    val rdd = spark.sparkContext.makeRDD(Seq(json))

    val beanRdd: RDD[EventLog] = rdd.map(s=>JSON.parseObject(s,classOf[EventLog]))

    beanRdd.foreach(println)

    import spark.implicits._
    val df = spark.createDataFrame(beanRdd, classOf[EventLog])
    //val df = spark.createDataFrame(beanRdd, classOf[EventCase])
    df.printSchema()
    df.show(10,false)

    spark.close()

  }

}
