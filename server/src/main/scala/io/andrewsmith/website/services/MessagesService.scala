package io.andrewsmith.website.services

import cats.effect.IO
import fs2.concurrent.Topic
import io.circe.generic.auto._
import org.http4s.{HttpRoutes, ServerSentEvent, UrlForm}
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._

object MessagesService {
  def routes(messageTopic: Topic[IO, String]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "stream" => Ok(
      messageTopic.subscribe(10).map(ServerSentEvent(_))
    )
    case request @ POST -> Root / "add" =>
      request.decode[UrlForm] { formData =>
        val body = formData.get("Body").iterator.next()
        Ok(messageTopic.publish1(body))
      }
  }
}
