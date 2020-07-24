import scala.collection.mutable.ListBuffer

object ListDemo {

  def main(args: Array[String]): Unit = {
    val lst = new ListBuffer[(String, String)]()
    lst +=(("a","b"))
    lst +=(("c","d"))
    println(lst)

    val lst2 = new ListBuffer[(String, String)]()
    lst2 +=(("aa","b"))
    lst2 +=(("cc","d"))

    val l1 = List(1,2,3)
    val l2 = List(4,5,6)
    val l = l1 ::: l2
    println(l)

  }
}
