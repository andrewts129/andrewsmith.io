package io.andrewsmith.website.services

import java.util.concurrent.Executors

import cats.effect.{Blocker, ContextShift, IO}
import org.http4s.HttpRoutes
import org.http4s.server.staticcontent.ResourceService.Config
import org.http4s.server.staticcontent.resourceService

import scala.concurrent.ExecutionContext

object ResourceService {
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
  private val blockingPool = Executors.newFixedThreadPool(4)
  private val blocker = Blocker.liftExecutorService(blockingPool)

  val routes: HttpRoutes[IO] = resourceService[IO](Config("dist/", blocker))
}
