package io.andrewsmith.website

import cats.effect._
import cats.implicits._
import io.andrewsmith.website.services.{FileService, ResourceService, RestService, ViewService}
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._


object Main extends IOApp {
  private val app: HttpApp[IO] = Router(
    "/" -> FileService.routes.combineK(ViewService.routes),
    "/api" -> RestService.routes,
    "/assets" -> ResourceService.routes
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = BlazeServerBuilder[IO]
    .bindHttp(4000) // TODO make configurable
    .withHttpApp(app)
    .serve
    .compile
    .drain
    .as(ExitCode.Success)
}
