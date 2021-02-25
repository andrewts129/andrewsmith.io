package io.andrewsmith.website.services

import cats.effect.{IO, Timer}
import io.andrewsmith.website.utils.WordStream.wordStream
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, ServerSentEvent}

import scala.concurrent.duration.DurationInt

object WordsService {
  def routes(implicit timer: Timer[IO]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "stream" => Ok(
      wordStream.metered(3.seconds).map(ServerSentEvent(_))
    )
  }
}
