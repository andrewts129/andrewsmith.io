package io.andrewsmith.website.core

import cats.data.OptionT
import cats.effect.IO
import fs2.io.file.Path
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, Request, Response, StaticFile}

object StaticService {
  private val acceptedExtensions = Set("js", "css", "map", "html", "png", "ico", "txt", "pdf")

  def routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case request @ GET -> Root => static("index.html", request)
    case request @ GET -> Root / file => extractExtension(file) match {
      case Some(ext) => if (acceptedExtensions(ext)) static(file, request) else NotFound()
      case None => static(s"$file.html", request)
    }
  }

  private def static(file: String, request: Request[IO]): IO[Response[IO]] = {
    lazy val fromFile = staticFromFile(file, request)
    lazy val fromResource = staticFromResource(file, request)

    fromFile.getOrElseF(fromResource.getOrElseF(NotFound()))
  }

  private def staticFromFile(file: String, request: Request[IO]): OptionT[IO, Response[IO]] = {
    val fileDir = sys.env.getOrElse("STATIC_FILE_DIR", "./static")
    StaticFile.fromPath(Path(s"$fileDir/$file"), Some(request))
  }

  private def staticFromResource(file: String, request: Request[IO]): OptionT[IO, Response[IO]] = {
    StaticFile.fromResource(s"/$file", Some(request))
  }

  private def extractExtension(file: String): Option[String] = file.lastIndexOf('.') match {
    case -1 => None
    case i => Some(file.substring(i + 1))
  }
}
