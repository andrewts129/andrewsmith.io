package io.andrewsmith.website.services

import cats.effect.IO
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.util.IOUtils
import org.http4s.{HttpRoutes, MediaType}
import org.http4s.dsl.io._
import org.http4s.headers.`Content-Type`

object FileService {
  // TODO do I really need AWS here? This is stupid
  private def downloadResume(): Array[Byte] = {
    val client: AmazonS3 = AmazonS3ClientBuilder
      .standard()
      .withRegion("us-east-1")
      .withCredentials(new EnvironmentVariableCredentialsProvider())
      .build()

    val resumeObject: S3Object = client.getObject("andrewsmithresume", "resume_b0.pdf")
    IOUtils.toByteArray(resumeObject.getObjectContent)
  }

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "robots.txt" => Ok("User-agent: *\nAllow: /")
    case GET -> Root / "AndrewSmithResume.pdf" => Ok(downloadResume(), `Content-Type`(MediaType.application.pdf))
  }
}
