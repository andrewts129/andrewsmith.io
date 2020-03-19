package io.andrewsmith.website

import cats.effect._
import cats.implicits._
import fs2.Stream
import fs2.concurrent.Topic
import io.andrewsmith.website.services._
import io.andrewsmith.website.utils.BogoStream
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import org.http4s.server.middleware.GZip

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      bogoStateTopic <- Topic[IO, Seq[Int]]((10 to 1).toVector)
      exitCode <- {
        val bogoStream = BogoStream.bogoStream.through(bogoStateTopic.publish)

        val app: HttpApp[IO] = Router(
          "/" -> (FileService.routes <+> ViewService.routes),
          "/api" -> (BogosortApiService.routes(bogoStateTopic) <+> MessagesApiService.routes),
          "/assets" -> ResourceService.routes
        ).orNotFound

        val appWithMiddleware = GZip(app)

        val httpStream = BlazeServerBuilder[IO]
          .bindHttp(8000, "0.0.0.0")
          .withHttpApp(appWithMiddleware)
          .serve

        httpStream
          .merge(bogoStream)
          .compile
          .drain
          .as(ExitCode.Success)
      }
    } yield exitCode
  }
}
