package io.andrewsmith.website.bogosort

import cats.effect.{ContextShift, IO, Timer}
import doobie.util.transactor.Transactor
import fs2.Stream
import fs2.concurrent.Topic
import io.andrewsmith.website.bogosort.services.{BogoStream, BogosortService}
import org.http4s.HttpRoutes

object BogosortApp {
  def init(implicit cs: ContextShift[IO], transactor: Transactor[IO], timer: Timer[IO]): IO[BogosortApp] = {
    bogoStateTopic.map(new BogosortApp(_))
  }

  private def bogoStateTopic(implicit cs: ContextShift[IO]): IO[Topic[IO, Seq[Int]]] = {
    Topic[IO, Seq[Int]]((10 to 1).toVector)
  }
}

class BogosortApp(bogoStateTopic: Topic[IO, Seq[Int]])(implicit transactor: Transactor[IO], timer: Timer[IO]) {
  def routes: HttpRoutes[IO] = BogosortService.routes(bogoStateTopic)

  def backgroundStream: Stream[IO, Unit] = BogoStream.bogoStream.through(bogoStateTopic.publish)
}
