package io.andrewsmith.website

import cats.effect._
import cats.implicits._
import io.andrewsmith.website.services.Routes
import org.http4s.server.blaze._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = BlazeServerBuilder[IO]
    .bindHttp(4000)
    .withHttpApp(Routes.httpApp)
    .serve
    .compile
    .drain
    .as(ExitCode.Success)
}
