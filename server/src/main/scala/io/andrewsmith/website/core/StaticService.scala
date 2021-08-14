package io.andrewsmith.website.core

import cats.data.OptionT
import cats.effect.{Blocker, ContextShift, IO}
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, Request, Response, StaticFile}

import java.io.File
import java.util.concurrent.Executors

object StaticService {
  private val blockingPool = Executors.newFixedThreadPool(4)
  private val blocker = Blocker.liftExecutorService(blockingPool)

  private val acceptedExtensions = Set("js", "css", "map", "html", "png", "ico", "txt", "pdf")

  def routes(implicit cs: ContextShift[IO]): HttpRoutes[IO] = HttpRoutes.of[IO] {
    case request @ GET -> Root => static("index.html", blocker, request)
    case request @ GET -> Root / file => extractExtension(file) match {
      case Some(ext) => if (acceptedExtensions(ext)) static(file, blocker, request) else NotFound()
      case None => static(s"$file.html", blocker, request)
    }
  }

  private def static(file: String, blocker: Blocker, request: Request[IO])(implicit cs: ContextShift[IO]): IO[Response[IO]] = {
    lazy val fromFile = staticFromFile(file, blocker, request)
    lazy val fromResource = staticFromResource(file, blocker, request)

    fromFile.getOrElseF(fromResource.getOrElseF(NotFound()))
  }

  private def staticFromFile(file: String, blocker: Blocker, request: Request[IO])(implicit cs: ContextShift[IO]): OptionT[IO, Response[IO]] = {
    val fileDir = sys.env.getOrElse("STATIC_FILE_DIR", "./static")
    StaticFile.fromFile(new File(s"$fileDir/$file"), blocker, Some(request))
  }

  private def staticFromResource(file: String, blocker: Blocker, request: Request[IO])(implicit cs: ContextShift[IO]): OptionT[IO, Response[IO]] = {
    StaticFile.fromResource(s"/$file", blocker, Some(request))
  }

  private def extractExtension(file: String): Option[String] = file.lastIndexOf('.') match {
    case -1 => None
    case i => Some(file.substring(i + 1))
  }
}
