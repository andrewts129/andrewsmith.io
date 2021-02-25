package io.andrewsmith.website.utils

import fs2.Stream
import cats.effect.IO
import io.andrewsmith.markovs.Model

import java.nio.file.Path

object WordStream {
  private lazy val model = Model.load(Path.of(sys.env.getOrElse("WORDS_SCHEMA_PATH", ".db/default.schema")))

  def wordStream: Stream[IO, String] = {
    model.flatMap(Model.generateText(_).repeat)
  }
}
