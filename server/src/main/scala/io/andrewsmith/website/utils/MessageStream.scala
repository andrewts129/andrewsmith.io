package io.andrewsmith.website.utils

import scala.concurrent.duration._
import cats.effect.{IO, Timer}
import fs2.Stream


object MessageStream {
  def messageStream(implicit timer: Timer[IO]): Stream[IO, String] = Stream.eval(IO("Hello world")).repeat.metered(5.seconds)
}
