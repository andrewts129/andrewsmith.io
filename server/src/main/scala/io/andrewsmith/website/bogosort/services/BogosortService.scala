package io.andrewsmith.website.bogosort.services

import cats.effect.IO
import cats.implicits._
import doobie.util.transactor.Transactor
import fs2.concurrent.Topic
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, ServerSentEvent}

object BogosortService {
  def routes(bogoStateTopic: Topic[IO, Seq[Int]])(implicit transactor: Transactor[IO]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "state" => Ok(
      bogoStateTopic.subscribe(1)
        .zipWith(BogoStream.numCompletionsStream)((state, numCompletions) =>
          ServerSentEvent(s"${state.mkString(",")};$numCompletions".some)
    ))
  }
}
