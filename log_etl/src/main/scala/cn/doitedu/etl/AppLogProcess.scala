package cn.doitedu.etl

import org.apache.commons.lang.StringUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.sql.catalyst.ScalaReflection.Schema
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}


object AppLogProcess {
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME","hdfs")
    Logger.getLogger("org").setLevel(Level.WARN)
    val session = SparkSession.builder()
      .appName(this.getClass.getSimpleName)
      .master("local")
      .enableHiveSupport()
      .getOrCreate()


    import session.implicits._

    val schema = StructType(
      Seq(
        StructField("account", DataTypes.StringType),
        StructField("appId", DataTypes.StringType),
        StructField("appVersion", DataTypes.StringType),
        StructField("carrier", DataTypes.StringType),
        StructField("deviceId", DataTypes.StringType),
        StructField("deviceType", DataTypes.StringType),
        StructField("eventId", DataTypes.StringType),
        StructField("eventInfo", DataTypes.createMapType(DataTypes.StringType,DataTypes.StringType)),
        StructField("ip", DataTypes.StringType),
        StructField("latitude", DataTypes.StringType),
        StructField("longitude", DataTypes.StringType),
        StructField("netType", DataTypes.StringType),
        StructField("osName", DataTypes.StringType),
        StructField("osVersion", DataTypes.StringType),
        StructField("releaseChannel", DataTypes.StringType),
        StructField("resolution", DataTypes.StringType),
        StructField("sessionId", DataTypes.StringType),
        StructField("timeStamp", DataTypes.StringType)
      )
    )
    val df = session.read.schema(schema).json("hdfs://cdh02:8020/user/hive/warehouse/ods.db/app_log_json/dt=2020-07-12")
    df.show(10,false)

    // 取出今日的去重idmp映射数据
    val ids: DataFrame = df.select("deviceid","account").groupBy("deviceid","account").agg('deviceid,'account)
    ids.createTempView("ids")

    // 标记当天新老访客
    df.createTempView("df")
    val ok = session.sql(
      """
        |
        |select
        |a.account,
        | -- if(a.account is not null and trim(a.account)!='',a.account,if(b.account is not null and trim(b.account)!='',b.account,a.account)) as account,
        |appId       ,
        |appVersion  ,
        |carrier     ,
        |a.deviceId  ,
        |deviceType  ,
        |eventId     ,
        |eventInfo   ,
        |ip          ,
        |latitude    ,
        |longitude   ,
        |netType     ,
        |osName      ,
        |if(b.deviceid is null and b.account is null,1,0) as isnew,
        |'2020-07-12' as dt
        |from df a left join dim.idmp b
        |on a.deviceid = b.deviceid or a.account=b.account
        |
        |""".stripMargin)

    ok.show(10,false)
    //ok.write.partitionBy("dt").saveAsTable("dwd.app_log_dtl")

    // 合并两份idmp映射数据
    val newIdmp = session.sql(
      """
        |
        |select
        |if(a.deviceid is not null,a.deviceid,b.deviceid) as deviceid,
        |if(b.account is not null,b.account,a.account) as account
        |from dim.idmp a
        | full join
        | ids b
        |on a.deviceid=b.deviceid
        |
        |""".stripMargin)
    newIdmp.createTempView("newidmp")
    newIdmp.show(10,false)

    // 保存新的idmp结果
    /*session.sql(
      """
        |
        |insert into table dim.idmp partition(dt='2020-07-12')
        |select deviceid,account from newidmp
        |
        |""".stripMargin)*/

    session.close()
  }
}
