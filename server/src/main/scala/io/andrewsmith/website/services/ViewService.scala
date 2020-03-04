package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.views._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.scalatags._

object ViewService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root => Ok(IndexView.page)
    case GET -> Root / "bogosort" => Ok(BogosortView.page)
    case GET -> Root / "columbus-buildings" => Ok(ColumbusBuildingsView.page)
    case GET -> Root / "home" => Ok(HomeView.page)
    case GET -> Root / "messages" => Ok(MessagesView.page)
    case GET -> Root / "links" => Ok(LinksView.page)
    case GET -> Root / "resume" => Ok(ResumeView.page)
    case GET -> Root / "whispering-pablo" => Ok(WhisperingPabloView.page)
  }
}
