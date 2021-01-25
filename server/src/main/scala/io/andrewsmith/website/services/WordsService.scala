package io.andrewsmith.website.services

import cats.effect.IO
import fs2.concurrent.Topic
import io.andrewsmith.website.utils.BogoStream
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, ServerSentEvent}

object WordsService {
  def routes(wordsTopic: Topic[IO, String]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "stream" => Ok(
      wordsTopic.subscribe(10).map(ServerSentEvent(_))
    )
  }
}
