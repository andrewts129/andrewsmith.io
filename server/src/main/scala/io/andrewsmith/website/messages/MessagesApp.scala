package io.andrewsmith.website.messages

import cats.effect.IO
import fs2.concurrent.Topic
import io.andrewsmith.website.messages.services.MessagesService
import org.http4s.HttpRoutes

object MessagesApp {
  def init: IO[MessagesApp] = {
    messageTopic.map(new MessagesApp(_))
  }

  private def messageTopic: IO[Topic[IO, String]] = {
    Topic[IO, String]
  }
}

class MessagesApp(messageTopic: Topic[IO, String]) {
  def routes: HttpRoutes[IO] = MessagesService.routes(messageTopic)
}
