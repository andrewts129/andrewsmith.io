package controllers

import db.BogoStats
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import utils.BogoSorter

@Singleton
class BogoController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def data: Action[AnyContent] = Action {
    Ok(Json.obj(
      "array" -> BogoSorter.array,
      "numCompletions" -> BogoStats.NumCompletions.get)
    )
  }
}
