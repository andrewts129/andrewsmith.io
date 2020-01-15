package io.andrewsmith.website.services

import cats.effect.IO
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router

// TODO make robots.txt accessible at /robots.txt
object Routes {
  val httpApp: HttpApp[IO] = Router(
    "/" -> ViewService.routes,
    "/api" -> RestService.routes,
    "/assets" -> ResourceService.routes
  ).orNotFound
}
