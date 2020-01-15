package io.andrewsmith.website.services

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object RestService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root => Ok("TODO JSON")
  }
}
