package io.andrewsmith.website.services

import cats.effect.IO
import fs2.concurrent.Topic
import io.andrewsmith.website.utils.BogoStream
import org.http4s.{HttpRoutes, ServerSentEvent}
import org.http4s.dsl.io._

object BogosortApiService {
  def routes(bogoStateTopic: Topic[IO, Seq[Int]]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "bogosort" / "state" => Ok(
      bogoStateTopic.subscribe(4)
        .zipWith(BogoStream.numCompletionsStream)((state, numCompletions) =>
          ServerSentEvent(s"${state.mkString(",")};$numCompletions")
    ))
  }
}
