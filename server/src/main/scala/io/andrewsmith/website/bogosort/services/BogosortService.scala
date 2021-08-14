package io.andrewsmith.website.bogosort.services

import cats.effect.{ContextShift, IO}
import doobie.util.transactor.Transactor
import fs2.concurrent.Topic
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, ServerSentEvent}

object BogosortService {
  def routes(bogoStateTopic: Topic[IO, Seq[Int]])(implicit transactor: Transactor[IO]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "state" => Ok(
      bogoStateTopic.subscribe(1)
        .zipWith(BogoStream.numCompletionsStream)((state, numCompletions) =>
          ServerSentEvent(s"${state.mkString(",")};$numCompletions")
    ))
  }

  def topic(implicit cs: ContextShift[IO]): IO[Topic[IO, Seq[Int]]] = {
    Topic[IO, Seq[Int]]((10 to 1).toVector)
  }
}
