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
    def getResume(b: Option[String]): Action[AnyContent] = Action {
        def downloadResume(branch: String): Array[Byte] = {
            val url: String = "https://raw.githubusercontent.com/andrewts129/resume/" + branch + "/AndrewSmithResume.pdf"
            val authToken: String = sys.env("GITHUB_ACCESS_TOKEN")

            val request: HttpRequest = Http(url).header("Authorization", "token " + authToken)
            val response: HttpResponse[Array[Byte]] = request.asBytes

            response.body
        }

        val branches: Map[String, String] = Map("0" -> "master", "1" -> "trump").withDefaultValue("trump")
        val resumeBytes: Array[Byte] = downloadResume(branches(b.getOrElse("1")))

        Result(
            header = ResponseHeader(200, Map.empty),
            body = HttpEntity.Strict(ByteString(resumeBytes), Some("application/pdf"))
        )
    }
}
