package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.db.Message
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.{HttpRoutes, UrlForm}
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

object MessagesApiService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case request @ POST -> Root / "messages" / "add" =>
      request.decode[UrlForm] { formData =>
        val body = formData.get("Body").iterator.next()
        val from = formData.get("From").iterator.next()
        Message.add(body, from).flatMap(m => Ok(m.asJson))
      }
    case POST -> Root / "messages" / "pop" =>
      Message.pop().flatMap {
        case Some(message) => Ok(message.asJson)
        case None => NoContent()
      }
  }
}
