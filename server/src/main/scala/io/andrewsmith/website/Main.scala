package io.andrewsmith.website

import scala.concurrent.ExecutionContext.global
import cats.effect._
import doobie.util.transactor.Transactor
import fs2.concurrent.Topic
import io.andrewsmith.website.bogosort.services.{BogoStream, BogosortService}
import io.andrewsmith.website.db.Database
import io.andrewsmith.website.services._
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import org.http4s.server.middleware.{GZip, RequestLogger}

object Main extends IOApp {
  implicit val transactor: Transactor[IO] = Database.transactor

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      bogoStateTopic <- BogosortService.topic
      messageTopic <- Topic[IO, String]("")

      exitCode <- {
        val app: HttpApp[IO] = Router(
          "/" -> StaticService.routes,
          "/bogosort" -> BogosortService.routes(bogoStateTopic),
          "/messages" -> MessagesService.routes(messageTopic)
        ).orNotFound

        val appWithMiddleware = RequestLogger.httpApp(logHeaders = true, logBody = true)(GZip(app))

        val httpStream = BlazeServerBuilder[IO](global)
          .bindHttp(8000, "0.0.0.0")
          .withHttpApp(appWithMiddleware)
          .serve

        val bogoStream = BogoStream.bogoStream.through(bogoStateTopic.publish)

        httpStream
          .merge(bogoStream)
          .compile
          .drain
          .as(ExitCode.Success)
      }
    } yield exitCode
  }
}
