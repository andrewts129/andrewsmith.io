package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.views._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.scalatags._

// TODO add js and css
object ViewService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root => Ok(Index.page)
    case GET -> Root / "bogosort" => Ok(Bogosort.page)
    case GET -> Root / "columbus-buildings" => Ok(ColumbusBuildings.page)
    case GET -> Root / "home" => Ok(Home.page)
    case GET -> Root / "messages" => Ok(Messages.page)
    case GET -> Root / "projects" => Ok(Projects.page)
    case GET -> Root / "resume" => Ok(Resume.page)
    case GET -> Root / "whispering-pablo" => Ok(WhisperingPablo.page)
  }
}
