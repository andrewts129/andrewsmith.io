package io.andrewsmith.website.utils

import cats.effect.{IO, Timer}
import fs2.Stream
import io.andrewsmith.website.db.BogoStat
import org.http4s.ServerSentEvent

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Random

// TODO this doesn't really work
object BogoStream {
  implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)

  val stateStream: Stream[IO, Seq[Int]] = Stream.unfoldLoop(initArray)(
    a => (a, Some(nextState(a)))
  ).metered(1.second)

  val numCompletionsStream: Stream[IO, Int] = Stream.repeatEval(BogoStat.numCompletions)

  val sseStream: Stream[IO, ServerSentEvent] = stateStream.zipWith(numCompletionsStream)((state, numCompletions) => {
    ServerSentEvent(s"${state.mkString(",")};$numCompletions")
  })

  private def nextState(a: Seq[Int]): Seq[Int] = {
    if (isSorted(a)) {
      BogoStat.IncrementNumCompletions.unsafeRunSync() // TODO do this without unsafeRunSync?
      initArray
    } else randomSwap(a)
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
