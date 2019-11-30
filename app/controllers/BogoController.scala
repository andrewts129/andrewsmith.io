package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import utils.Bogosorter

@Singleton
class BogoController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def data: Action[AnyContent] = Action {
    Ok(Json.obj(
      "array" -> Bogosorter.array,
      "numCompletions" -> Bogosorter.numCompletions,
      )
    )
  }
}
