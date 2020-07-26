import java.io.RandomAccessFile

import org.lionsoul.ip2region.{DbConfig, DbSearcher}
import util.SparkUtil

object IpTest {
  def main(args: Array[String]): Unit = {

    /*val config = new DbConfig
    val searcher = new DbSearcher(config, "initdata/ip2region.db")

    val block = searcher.memorySearch("39.99.177.94")
    println(block)*/


   /*val rf = new RandomAccessFile("initdata/ip2region.db", "r")
    val ba = new Array[Byte](rf.length().toInt)
    rf.readFully(ba)

    val searcher = new DbSearcher(config,ba)
    val block1 = searcher.memorySearch("39.99.177.94")
    val block2 = searcher.memorySearch("1.2.5.50")
    println(block1)
    println(block2)*/



    val spark = SparkUtil.getSpark()
    val rf = new RandomAccessFile("initdata/ip2region.db", "r")
    val ba = new Array[Byte](rf.length().toInt)
    rf.readFully(ba)

    val bc = spark.sparkContext.broadcast(ba)
    val rdd = spark.sparkContext.makeRDD(Seq("39.99.177.94", "1.2.5.50", "1.2.153.128"))
    val res = rdd.map(ip=>{
      val binstr = bc.value
      val config = new DbConfig
      val searcher = new DbSearcher(config,binstr)
      val block = searcher.memorySearch(ip)
      block.toString

    })

    res.foreach(println)

    spark.close()
  }

}
