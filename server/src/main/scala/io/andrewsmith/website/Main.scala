package io.andrewsmith.website

import scala.concurrent.ExecutionContext.global
import cats.effect._
import doobie.util.transactor.Transactor
import io.andrewsmith.website.bogosort.BogosortApp
import io.andrewsmith.website.core.{Database, StaticService}
import io.andrewsmith.website.messages.MessagesApp
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import org.http4s.server.middleware.{GZip, RequestLogger}

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      implicit0(transactor: Transactor[IO]) <- Database.connect

      bogosortApp <- BogosortApp.init
      messagesApp <- MessagesApp.init

      exitCode <- {
        val app: HttpApp[IO] = Router(
          "/" -> StaticService.routes,
          "/bogosort" -> bogosortApp.routes,
          "/messages" -> messagesApp.routes
        ).orNotFound

        val appWithMiddleware = RequestLogger.httpApp(logHeaders = true, logBody = true)(GZip(app))

        val httpStream = BlazeServerBuilder[IO](global)
          .bindHttp(8000, "0.0.0.0")
          .withHttpApp(appWithMiddleware)
          .serve

        httpStream
          .merge(bogosortApp.backgroundStream)
          .compile
          .drain
          .as(ExitCode.Success)
      }
    } yield exitCode
  }
}
