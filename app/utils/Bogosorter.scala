package utils

import scala.util.Random
import swaydb._
import swaydb.serializers.Default._

import scala.concurrent.ExecutionContext

object Bogosorter {
  var array: Seq[Int] = initArray

  private val stats = swaydb.memory.Map[String, Int, Nothing, IO.ApiIO]().get
  stats.put("numCompletions", 0).get

  def bogoStep(): Unit = {
    if (isSorted(array)) {
      stats.put("numCompletions", stats.get("numCompletions").get.get + 1)
      array = initArray
    } else {
      array = randomSwap(array)
    }
  }

  def getNumCompletions: Int = {
    stats.get("numCompletions").get.get
  }

  private def initArray: Seq[Int] = {
    Random.shuffle((1 to 4).toVector)
  }

  private def randomSwap[T](a: Seq[T]): Seq[T] = {
    val i = Random.nextInt(a.size)
    val j = Random.nextInt(a.size - 1)
    swap(a, i, if (i <= j) j + 1 else j)
  }

  @scala.annotation.tailrec
  private def isSorted[T](a: Seq[T])(implicit ev: T => Ordered[T]): Boolean = {
    if (a.size < 2) {
      true
    } else {
      (a.head <= a.tail.head) && isSorted(a.tail)
    }
  }

  private def swap[T](a: Seq[T], i: Int, j: Int): Seq[T] = {
    a.updated(i, a(j)).updated(j, a(i))
  }
}