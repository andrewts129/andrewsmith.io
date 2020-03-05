package io.andrewsmith.website

import cats.effect._
import cats.implicits._
import io.andrewsmith.website.services._
import io.andrewsmith.website.utils.BogoStream
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._

object Main extends IOApp {
  private val app: HttpApp[IO] = Router(
    "/" -> (FileService.routes <+> ViewService.routes),
    "/api" -> (BogosortApiService.routes <+> MessagesApiService.routes),
    "/assets" -> ResourceService.routes
  ).orNotFound

  private val port = sys.env.getOrElse("PORT", "4000").toInt

  override def run(args: List[String]): IO[ExitCode] = BlazeServerBuilder[IO]
      .bindHttp(port, "0.0.0.0")
      .withHttpApp(app)
      .serve
      .concurrently(BogoStream.stateStream) // Run bogosort in the background
      .compile
      .drain
      .as(ExitCode.Success)
}
