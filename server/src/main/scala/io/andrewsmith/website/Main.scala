package io.andrewsmith.website

import scala.concurrent.ExecutionContext.global
import cats.effect._
import fs2.concurrent.Topic
import io.andrewsmith.website.services._
import io.andrewsmith.website.utils.{BogoStream, WordStream}
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import org.http4s.server.middleware.{GZip, RequestLogger}

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      bogoStateTopic <- Topic[IO, Seq[Int]]((10 to 1).toVector)
      messageTopic <- Topic[IO, String]("")
      wordsTopic <- Topic[IO, String]("")

      exitCode <- {
        val bogoStream = BogoStream.bogoStream.through(bogoStateTopic.publish)
        val wordStream = WordStream.wordStream.through(wordsTopic.publish)

        val app: HttpApp[IO] = Router(
          "/" -> StaticService.routes,
          "/bogosort" -> BogosortService.routes(bogoStateTopic),
          "/messages" -> MessagesService.routes(messageTopic),
          "/words" -> WordsService.routes(wordsTopic)
        ).orNotFound

        val appWithMiddleware = RequestLogger.httpApp(logHeaders = true, logBody = true)(GZip(app))

        val httpStream = BlazeServerBuilder[IO](global)
          .bindHttp(8000, "0.0.0.0")
          .withHttpApp(appWithMiddleware)
          .serve

        httpStream
          .merge(bogoStream)
          .merge(wordStream)
          .compile
          .drain
          .as(ExitCode.Success)
      }
    } yield exitCode
  }
}
