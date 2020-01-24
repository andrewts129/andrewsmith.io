package io.andrewsmith.website.utils

import cats.effect.{IO, Timer}
import fs2.Stream
import io.andrewsmith.website.db.BogoStats
import org.http4s.ServerSentEvent

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.util.Random

// TODO both of these streams can be combined
object BogoStream {
  private def initArray: Seq[Int] = Random.shuffle((1 to 4).toVector)

  @scala.annotation.tailrec
  private def isSorted[T](a: Seq[T])(implicit ev: T => Ordered[T]): Boolean = a match {
    case Seq() => true
    case _ +: Seq() => true
    case head +: tail => (head <= tail.head) && isSorted(tail)
  }

  implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)
  val arrayStream: Stream[IO, ServerSentEvent] = Stream.awakeEvery[IO](1.seconds).map(_ =>
    ServerSentEvent({
      val newArray = initArray  // TODO do swaps instead
      if (isSorted(newArray)) {
        BogoStats.IncrementNumCompletions.unsafeRunSync()
      }
      newArray.mkString(",")
    })
  )

  val completionStream: Stream[IO, ServerSentEvent] = Stream.awakeEvery[IO](1.seconds).map(_ =>
    ServerSentEvent(BogoStats.numCompletions.unsafeRunSync().toString) // TODO do this better
  )
}
