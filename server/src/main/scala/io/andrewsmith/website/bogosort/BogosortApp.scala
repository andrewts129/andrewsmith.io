package io.andrewsmith.website.bogosort

import cats.effect.IO
import doobie.util.transactor.Transactor
import fs2.Stream
import fs2.concurrent.Topic
import io.andrewsmith.website.bogosort.services.{BogoStream, BogosortService}
import org.http4s.HttpRoutes

object BogosortApp {
  def init(implicit transactor: Transactor[IO]): IO[BogosortApp] = {
    bogoStateTopic.map(new BogosortApp(_))
  }

  private def bogoStateTopic: IO[Topic[IO, Seq[Int]]] = {
    Topic[IO, Seq[Int]].flatTap {
      topic => topic.publish1((10 to 1).toVector)
    }
  }
}

class BogosortApp(bogoStateTopic: Topic[IO, Seq[Int]])(implicit transactor: Transactor[IO]) {
  def routes: HttpRoutes[IO] = BogosortService.routes(bogoStateTopic)

  def backgroundStream: Stream[IO, Unit] = BogoStream.bogoStream.through(bogoStateTopic.publish)
}
