package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.db.Message
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.io._

object MessagesApiService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case POST -> Root / "messages" / "add" =>
      Message.add("text", "author").flatMap(m => Ok(m.asJson)) // TODO get request body
    case POST -> Root / "messages" / "pop" =>
      Message.pop().flatMap {
        case Some(message) => Ok(message.asJson)
        case None => NoContent()
      }
  }
}
