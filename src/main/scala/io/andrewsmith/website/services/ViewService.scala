package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.views.Index
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.scalatags._

object ViewService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root => Ok(Index.page)
  }
}
