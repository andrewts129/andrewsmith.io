package io.andrewsmith.website.services

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object FileService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "robots.txt" => Ok("User-agent: *\nAllow: /")
    case GET -> Root / "AndrewSmithResume.pdf" => Ok("TODO resume")
  }
}
