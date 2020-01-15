package io.andrewsmith.website.services

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object RestService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "bogosort" / "data" => Ok("BOGO JSON") // TODO
    case POST -> Root / "messages" / "add" => Ok("Message added!") // TODO
    case POST -> Root / "messages" / "pop" => Ok("Message popped!") // TODO
  }
}
