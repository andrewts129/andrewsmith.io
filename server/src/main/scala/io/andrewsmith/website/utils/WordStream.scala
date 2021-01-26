package io.andrewsmith.website.utils

import fs2.Stream
import cats.effect.{IO, Timer}
import io.andrewsmith.markovs.Model

import java.nio.file.Path
import scala.concurrent.duration._

object WordStream {
  def wordStream(schemaPath: Path)(implicit timer: Timer[IO]): Stream[IO, String] = {
    Model.load(schemaPath).flatMap(Model.generateText).repeat.metered(3.seconds)
  }
}
