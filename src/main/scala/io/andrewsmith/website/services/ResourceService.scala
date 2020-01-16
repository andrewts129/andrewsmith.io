package io.andrewsmith.website.services

import cats.effect.{ContextShift, IO}
import org.http4s.HttpRoutes
import org.http4s.server.staticcontent.ResourceService.Config
import org.http4s.server.staticcontent.resourceService

import scala.concurrent.ExecutionContext

object ResourceService {
  private implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  val routes: HttpRoutes[IO] = resourceService[IO](Config("/public", ExecutionContext.global))
}
