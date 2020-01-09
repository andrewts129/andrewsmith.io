package controllers

import db.MessageQueue
import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, AnyContentAsFormUrlEncoded, AnyContentAsText, ControllerComponents}

@Singleton
class MessageQueueController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def add: Action[AnyContent] = Action { request =>
    val body: Option[String] = request.body match {
      case AnyContentAsText(text) => Some(text)
      case AnyContentAsFormUrlEncoded(data) => data.get("Body") match {
        case Some(value) => Some(value.head)
        case _ => None
      }
      case _ => None
    }

    body match {
      case Some(value) =>
        MessageQueue.add(value)
        Accepted
      case None => BadRequest
    }
  }

  def pop: Action[AnyContent] = Action {
    MessageQueue.pop() match {
      case Some(value) => Ok(value)
      case _ => NoContent
    }
  }
}
