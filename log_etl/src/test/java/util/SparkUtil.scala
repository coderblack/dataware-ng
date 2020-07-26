package util

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object SparkUtil {
  def getSpark(): SparkSession = {
    Logger.getLogger("org").setLevel(Level.WARN)
    val spark = SparkSession.builder.appName("local").master("local").getOrCreate
    spark
  }
}
