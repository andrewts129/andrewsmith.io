package io.andrewsmith.website.utils

import cats.effect.{IO, Timer}
import doobie.util.transactor.Transactor
import fs2.Stream
import io.andrewsmith.website.db.BogoStat

import scala.concurrent.duration._
import scala.util.Random

object BogoStream {
  def bogoStream(implicit transactor: Transactor[IO], timer: Timer[IO]): Stream[IO, Seq[Int]] = {
    Stream.unfoldLoop(initArray)(
      array => (array, Some(nextState(array)))
    ).evalTap(
      array => if (isSorted(array)) BogoStat.incrementNumCompletions else IO()
    ).metered(1.second)
  }

  def numCompletionsStream(implicit transactor: Transactor[IO]): Stream[IO, Int] = {
    Stream.repeatEval(BogoStat.numCompletions)
  }

  private def nextState(a: Seq[Int]): Seq[Int] = {
    if (isSorted(a)) initArray else randomSwap(a)
  }

  private def initArray: Seq[Int] = Random.shuffle((1 to 10).toVector)

  private def randomSwap[T](a: Seq[T]): Seq[T] = {
    val i = Random.nextInt(a.size)
    val j = Random.nextInt(a.size - 1)
    swap(a, i, if (i <= j) j + 1 else j)
  }

  private def swap[T](a: Seq[T], i: Int, j: Int): Seq[T] = a.updated(i, a(j)).updated(j, a(i))

  @scala.annotation.tailrec
  private def isSorted[T](a: Seq[T])(implicit ev: T => Ordered[T]): Boolean = a match {
    case Seq() => true
    case _ +: Seq() => true
    case head +: tail => (head <= tail.head) && isSorted(tail)
  }
}
