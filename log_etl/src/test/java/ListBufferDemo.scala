import scala.collection.mutable.ListBuffer

object ListBufferDemo {

  def main(args: Array[String]): Unit = {
    val lst = new ListBuffer[(String, String)]()
    lst +=(("a","b"))
    lst +=(("c","d"))
    println(lst)
  }
}
