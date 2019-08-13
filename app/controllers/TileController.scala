package controllers

import javax.inject.{Inject, Singleton}
import play.api.cache.Cached
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents, EssentialAction}
import scalaj.http.{Http, HttpRequest}


@Singleton
class TileController @Inject()(cc: ControllerComponents, ws: WSClient, cached: Cached) extends AbstractController(cc) {
    private val baseUrl: String = "https://columbus-building-tileserver.herokuapp.com"

    def getStyle(name: String): EssentialAction = cached(s"style_$name") {
        Action {
            val url: String = s"$baseUrl/styles/$name/style.json"
            val request: HttpRequest = Http(url)
            Ok(request.asBytes.body)
        }
    }

    def getTile(source: String, z: String, y: String, x: String): EssentialAction = cached(s"tile_${source}_${z}_${y}_$x") {
        Action {
            val url: String = s"$baseUrl/data/$source/$z/$y/$x.pbf"
            val request: HttpRequest = Http(url)
            Ok(request.asBytes.body)
        }
    }
}
