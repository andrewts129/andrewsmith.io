package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.utils.BogoStream
import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object BogosortApiService {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "bogosort" / "array" => Ok(BogoStream.arrayStream)
    case GET -> Root / "bogosort" / "completions" => Ok(BogoStream.completionStream)
  }
}
