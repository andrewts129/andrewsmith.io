package controllers

import java.io.{ByteArrayInputStream, File}
import java.net.URL
import javax.inject._

import akka.util.{ByteString, Helpers}
import play.api._
import play.api.http.HttpEntity
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scalaj.http.{Http, HttpRequest, HttpResponse}

@Singleton
class FileController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {

  def getResume() = Action {
    def downloadResume(): Array[Byte] = {
      val url: String = "https://raw.githubusercontent.com/andrewts129/resume/master/AndrewSmithResume.pdf"
      val authToken: String = sys.env("GITHUB_ACCESS_TOKEN")

      val request: HttpRequest = Http(url).header("Authorization", "token " + authToken)
      val response: HttpResponse[Array[Byte]] = request.asBytes

      response.body
    }

    val resumeBytes: Array[Byte] = downloadResume()

    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString(resumeBytes), Some("application/pdf"))
    )
  }
}
