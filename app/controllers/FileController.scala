package controllers

import javax.inject._

import akka.util.ByteString
import play.api.http.HttpEntity
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
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
