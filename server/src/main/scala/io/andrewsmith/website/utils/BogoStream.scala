package io.andrewsmith.website.utils

import cats.effect.{IO, Timer}
import fs2.Stream
import io.andrewsmith.website.db.BogoStats
import org.http4s.ServerSentEvent

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Random

object BogoStream {
  implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)

  val state: Stream[IO, ServerSentEvent] = Stream.unfoldLoop(initArray)(
    a => (a, if (isSorted(a)) Some(initArray) else Some(randomSwap(a)))
  )
    .metered(1.second)
    .map(a => ServerSentEvent({
      // TODO is there a way to do this without .unsafeRunSync()?
      if (isSorted(a)) {
        BogoStats.IncrementNumCompletions.unsafeRunSync()
      }

      val completions = BogoStats.numCompletions.unsafeRunSync()

      s"${a.mkString(",")};$completions"
    }))

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
