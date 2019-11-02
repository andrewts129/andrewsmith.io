package utils

import scala.util.Random

object Bogosorter {
  var array: Seq[Int] = Random.shuffle((1 to 10).toVector)

  @scala.annotation.tailrec
  def sort[T](a: Seq[T])(implicit ev: T => Ordered[T]): Seq[T] = {
    println(a)
    if (isSorted(a)) {
      a
    } else {
      sort(randomSwap(a))
    }
  }

  def randomSwap[T](a: Seq[T]): Seq[T] = {
    swap(a, Random.nextInt(a.size), Random.nextInt(a.size))
  }

  @scala.annotation.tailrec
  def isSorted[T](a: Seq[T])(implicit ev: T => Ordered[T]): Boolean = {
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