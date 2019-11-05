package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import utils.Bogosorter

@Singleton
class BogoController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def currentArray: Action[AnyContent] = Action {
    Ok(Json.toJson(Bogosorter.array))
  }

  def numCompletions: Action[AnyContent] = Action {
    Ok(Json.toJson(Bogosorter.numCompletions))
  }
}
