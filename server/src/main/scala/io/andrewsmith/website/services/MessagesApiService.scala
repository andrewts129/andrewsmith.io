package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.db.Message
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityCodec._

object MessagesApiService {
  case class AddRequest(Body: String, From: String)

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case request @ POST -> Root / "messages" / "add" =>
      request.decode[AddRequest] { body => // TODO this is broken
        Message.add(body.Body, body.From).flatMap(m => Ok(m.asJson))
      }
    case POST -> Root / "messages" / "pop" =>
      Message.pop().flatMap {
        case Some(message) => Ok(message.asJson)
        case None => NoContent()
      }
  }
}
