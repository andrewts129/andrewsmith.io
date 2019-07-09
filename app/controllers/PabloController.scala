package controllers

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import models.PabloMessage
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.Future

@Singleton
class PabloController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
    def getFeed: Action[AnyContent] = Action {
        Ok(PabloMessage.getAll)
    }

    def getStats: Action[AnyContent] = Action {
        Ok("STATS")
    }

    def submitMessage(text: String): Action[AnyContent] = Action {
        Created(PabloMessage.create(text))
    }
}
