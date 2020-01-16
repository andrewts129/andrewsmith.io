package io.andrewsmith.website.services

import cats.Foldable
import cats.effect.IO
import io.andrewsmith.website.db.Message
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.io._

object RestService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "bogosort" / "data" => Ok("BOGO JSON") // TODO
    case POST -> Root / "messages" / "add" =>
      Message.add("text", "author").flatMap(m => Ok(m.asJson)) // TODO get request body
    case POST -> Root / "messages" / "pop" =>
      Message.pop().flatMap(m => Ok(m.asJson)) // TODO add not found
  }
}
