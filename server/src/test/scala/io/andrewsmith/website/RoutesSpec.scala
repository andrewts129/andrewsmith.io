package io.andrewsmith.website

import cats.effect.IO
import fs2.concurrent.Topic
import org.http4s._
import org.http4s.implicits._
import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.specification.core.Fragment

class RoutesSpec extends Specification with Mockito {
  def responseOf(uri: Uri): Response[IO] = {
    val router = Main.router(mock[Topic[IO, Seq[Int]]], mock[Topic[IO, String]])
    router(Request[IO](Method.GET, uri)).unsafeRunSync()
  }

  def returnOk(uri: Uri): Fragment = {
    "return a status code of 200" in {
      responseOf(uri).status must beEqualTo(Status.Ok)
    }
  }

  def containText(uri: Uri, text: String): Fragment = {
    "return a status code of 200" in {
      val html = responseOf(uri).body.map(_.toChar).compile.toVector.unsafeRunSync().mkString
      html must contain(text)
    }
  }


  "Index" should {
    val uri = uri"/"

    returnOk(uri)
    containText(uri, "<h1>Andrew Smith</h1>")
  }

  "Bogosort" should {
    val uri = uri"/bogosort"

    returnOk(uri)
    containText(uri, "<h1>Bogosort</h1>")
  }

  "Columbus Buildings" should {
    val uri = uri"/columbus-buildings"

    returnOk(uri)
    containText(uri, "<h1>The Age of Columbus Buildings</h1>")
  }

  "Resume" should {
    val uri = uri"/resume"

    returnOk(uri)
    containText(uri, "redirect")
  }
}
