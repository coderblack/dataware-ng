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
      .schema(schema).json("hdfs://h1:8020/user/hive/warehouse/ods.db/app_log_json/dt=2020-07-12")
      .withColumnRenamed("timeStamp","ts")
    df.show(10,false)

    // 取出今日的去重idmp映射数据
    val ids: DataFrame = df.select("deviceid","account").groupBy("deviceid","account").agg('deviceid,'account)
    ids.createTempView("ids")

    // 标记当天新老访客
    df.createTempView("df")
    val tagNew = session.sql(
      """
        |
        |select
        |a.account,
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
        |osVersion   ,
        |releaseChannel ,
        |resolution  ,
        |sessionId   ,
        |ts          ,
        |if(b.deviceid is null and b.account is null,1,0) as isnew,
        |'2020-07-12' as dt
        |from df a left join dim.dim_idmp b
        |on a.deviceid = b.deviceid or a.account=b.account
        |
        |""".stripMargin)

    tagNew.show(10,false)
    //ok.write.partitionBy("dt").saveAsTable("dwd.app_log_dtl")

    // 地理位置解析
    val gps2geo = (longitude:Double,latitude:Double)=>{
      GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 5)
    }
    session.udf.register("gps2geo",gps2geo)

    val props = new Properties()
    props.setProperty("user","root")
    props.setProperty("password","root")
    val geo = session.read.jdbc("jdbc:mysql://localhost:3306/dicts?useSSL=false", "area_dict", props)
    geo.createTempView("geodict")

    tagNew.createTempView("tagnew")

    val dwd = session.sql(
      """
        |select
        |a.account        ,
        |a.isnew          ,
        |a.appId          ,
        |a.appVersion     ,
        |a.carrier        ,
        |a.deviceId       ,
        |a.deviceType     ,
        |a.eventId        ,
        |a.eventInfo      ,
        |a.ip             ,
        |a.latitude       ,
        |a.longitude      ,
        |a.netType        ,
        |a.osName         ,
        |osVersion        ,
        |releaseChannel   ,
        |resolution       ,
        |sessionId        ,
        |b.province       ,
        |b.city           ,
        |b.district       ,
        |ts               ,
        |cast(to_date(from_unixtime(cast(a.ts/1000 as int))) as string)datestr         ,
        |cast(year(to_date(from_unixtime(cast(a.ts/1000 as int)))) as string) as year   ,
        |cast(month(to_date(from_unixtime(cast(a.ts/1000 as int)))) as string) as month ,
        |cast(day(to_date(from_unixtime(cast(a.ts/1000 as int)))) as string) as day     ,
        |'2020-07-12' as dt
        |from
        |tagnew a left join geodict b
        |on gps2geo(a.longitude,a.latitude)=b.geo
        |
        |""".stripMargin)
    dwd.show(10,false)
    dwd.write.format("Hive").mode(SaveMode.Append).partitionBy("dt").saveAsTable("dwd.app_log_dtl")

    // 合并两份idmp映射数据
    val newIdmp = session.sql(
      """
        |
        |select
        |if(a.deviceid is not null,a.deviceid,b.deviceid) as deviceid,
        |if(b.account is not null,b.account,a.account) as account
        |from dim.dim_idmp a
        | full join
        | ids b
        |on a.deviceid=b.deviceid
        |
        |""".stripMargin)
    newIdmp.createTempView("newidmp")
    newIdmp.show(10,false)

    // 保存新的idmp结果
    session.sql(
      """
        |
        |insert into table dim.dim_idmp partition(dt='2020-07-12')
        |select deviceid,account from newidmp
        |
        |""".stripMargin)

    session.close()
  }
}
