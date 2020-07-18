package cn.doitedu.etl

import java.util.Properties

import ch.hsr.geohash.GeoHash
import org.apache.commons.lang.StringUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.sql.catalyst.ScalaReflection.Schema
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}


object AppLogProcess {
  def main(args: Array[String]): Unit = {
    val dt = "20200714"
    val dt_1 = "20200713"


    System.setProperty("HADOOP_USER_NAME","root")
    Logger.getLogger("org").setLevel(Level.WARN)
    val session = SparkSession.builder()
      .appName(this.getClass.getSimpleName)
      .master("local")
      .config("hive.exec.dynamic.partition.mode","nonstrict")
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
        StructField("latitude", DataTypes.DoubleType),
        StructField("longitude", DataTypes.DoubleType),
        StructField("netType", DataTypes.StringType),
        StructField("osName", DataTypes.StringType),
        StructField("osVersion", DataTypes.StringType),
        StructField("releaseChannel", DataTypes.StringType),
        StructField("resolution", DataTypes.StringType),
        StructField("sessionId", DataTypes.StringType),
        StructField("timeStamp", DataTypes.LongType)
      )
    )
    val df = session.read
      .schema(schema).json(s"hdfs://h1:8020/user/hive/warehouse/ods.db/app_log_json/dt=${dt}")
      .withColumnRenamed("timeStamp","ts")
    df.cache();
    df.show(10,false)
    df.createTempView("df")

    // 取出今日的去重idmp映射数据
    val ids: DataFrame = df.select("deviceid","account").groupBy("deviceid","account").agg('deviceid,'account)
    ids.cache()
    ids.createTempView("ids")


    // 取出老的idmp映射数据
    val oldIdmp = session.read.table("dim.dim_idmp").where("dt='"+dt_1+"'")
    oldIdmp.cache()
    oldIdmp.createTempView("oldidmp")

    // 利用当日ids，回补当日account
    val huibu = session.sql(
      """
        |
        |select
        |if(a.account is not null and trim(a.account) != '',a.account,if(b.account is not null and trim(b.account) != '',b.account,null)) as account,
        |a.appId       ,
        |a.appVersion  ,
        |a.carrier     ,
        |a.deviceId  ,
        |a.deviceType  ,
        |a.eventId     ,
        |a.eventInfo   ,
        |a.ip          ,
        |a.latitude    ,
        |a.longitude   ,
        |a.netType     ,
        |a.osName      ,
        |a.osVersion   ,
        |a.releaseChannel ,
        |a.resolution  ,
        |a.sessionId   ,
        |a.ts
        |
        |from  df a join ids b on a.deviceid=b.deviceid
        |
        |""".stripMargin)
    huibu.cache()
    huibu.createTempView("huibu")

    // 利用T-1日idmp表，标记当天新老访客
    val tagNew = session.sql(
      s"""
        |
        |select
        |a.account         ,
        |a.appId           ,
        |a.appVersion      ,
        |a.carrier         ,
        |a.deviceId        ,
        |a.deviceType      ,
        |a.eventId         ,
        |a.eventInfo       ,
        |a.ip              ,
        |a.latitude        ,
        |a.longitude       ,
        |a.netType         ,
        |a.osName          ,
        |a.osVersion       ,
        |a.releaseChannel  ,
        |a.resolution      ,
        |a.sessionId       ,
        |a.ts              ,
        |if(b.deviceid is null and b.account is null,1,0) as isnew,
        |'${dt}' as dt
        |from huibu a left join oldidmp b
        |on a.deviceid = b.deviceid or a.account=b.account
        |
        |""".stripMargin)

    tagNew.show(10,false)
    tagNew.createTempView("tagnew")

    // 地理位置解析
    val gps2geo = (longitude:Double,latitude:Double)=>{
      GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 6)
    }
    session.udf.register("gps2geo",gps2geo)

    val props = new Properties()
    props.setProperty("user","root")
    props.setProperty("password","root")
    val geo = session.read.jdbc("jdbc:mysql://localhost:3306/realtimedw?useSSL=false", "area_dict", props)
    geo.createTempView("geodict")
    geo.cache()
    geo.show(10,false)


    // 关联地理位置，解析时间字段，生成明细表数据
    val dwd = session.sql(
      s"""
        |select
        |a.account          ,
        |a.isnew            ,
        |a.appId            ,
        |a.appVersion       ,
        |a.carrier          ,
        |a.deviceId         ,
        |a.deviceType       ,
        |a.eventId          ,
        |a.eventInfo        ,
        |a.ip               ,
        |a.latitude         ,
        |a.longitude        ,
        |a.netType          ,
        |a.osName           ,
        |a.osVersion        ,
        |a.releaseChannel   ,
        |a.resolution       ,
        |a.sessionId        ,
        |b.province         ,
        |b.city             ,
        |b.district         ,
        |ts                 ,
        |cast(to_date(from_unixtime(cast(a.ts/1000 as int))) as string) as datestr      ,
        |cast(year(to_date(from_unixtime(cast(a.ts/1000 as int)))) as string) as year   ,
        |cast(month(to_date(from_unixtime(cast(a.ts/1000 as int)))) as string) as month ,
        |cast(day(to_date(from_unixtime(cast(a.ts/1000 as int)))) as string) as day     ,
        |'${dt}' as dt
        |from
        |tagnew a left join geodict b
        |on gps2geo(a.longitude,a.latitude)=b.geo
        |
        |""".stripMargin)
    dwd.show(10,false)
    println("---即将保存dwd明细表数据------")
    dwd.write.format("Hive").mode(SaveMode.Append).partitionBy("dt").saveAsTable("dwd.app_log_dtl")

    // 合并两份idmp映射数据
    val newIdmp = session.sql(
      s"""
        |
        |select
        |if(a.deviceid is not null,a.deviceid,b.deviceid) as deviceid  ,
        |if(b.account is not null,b.account,a.account) as account
        |from oldidmp a full join ids b
        |on a.deviceid=b.deviceid
        |
        |""".stripMargin)
    newIdmp.show(10,false)
    newIdmp.createTempView("newidmp")

    // 保存新的idmp结果
    session.sql(
      s"""
        |
        |insert into table dim.dim_idmp partition(dt='${dt}')
        |select deviceid,account from newidmp
        |
        |""".stripMargin)

    session.close()
  }
}
