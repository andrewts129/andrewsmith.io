package io.andrewsmith.website.utils

import fs2.Stream
import cats.effect.{IO, Timer}
import io.andrewsmith.markovs.Model

import java.nio.file.Path
import scala.concurrent.duration.DurationInt

object WordStream {
  def wordStream(implicit timer: Timer[IO]): Stream[IO, String] = {
    val schemaPath = Path.of(sys.env.getOrElse("WORDS_SCHEMA_PATH", ".db/default.schema"))
    Model.load(schemaPath).flatMap(Model.generateText(_).repeat).metered(3.seconds)
  }
}
