package io.andrewsmith.website.services

import cats.effect.IO
import io.andrewsmith.website.db.BogoStats
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.io._

object BogosortApiService {
  case class BogosortState(array: Seq[Int], numCompletions: Int)

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "bogosort" / "data" =>
      val array = 1 to 10 // TODO
      BogoStats.numCompletions.flatMap(n =>
        Ok(BogosortState(array, n).asJson)
      )
  }
}
