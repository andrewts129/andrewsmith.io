package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scalaj.http.{Http, HttpRequest}


@Singleton
class TileController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
    private val baseUrl: String = "https://columbus-building-tileserver.herokuapp.com"

    def getStyle(name: String): Action[AnyContent] = Action {
        val url: String = s"$baseUrl/styles/$name/style.json"
        val request: HttpRequest = Http(url)
        Ok(request.asBytes.body)
    }

    def getTile(source: String, z: String, y: String, x: String): Action[AnyContent] = Action {
        val url: String = s"$baseUrl/data/$source/$z/$y/$x.pbf"
        val request: HttpRequest = Http(url)
        Ok(request.asBytes.body)
    }
}
