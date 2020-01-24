package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.utils.BogoStream
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object BogosortApiService {
  // TODO add route to get current state (non-stream)
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "bogosort" / "state" => Ok(BogoStream.state)
  }
}
