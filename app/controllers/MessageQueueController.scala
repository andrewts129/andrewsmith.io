package controllers

import db.MessageQueue
import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, AnyContentAsFormUrlEncoded, AnyContentAsText, ControllerComponents}

@Singleton
class MessageQueueController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def add: Action[AnyContent] = Action { request =>
    val body: String = request.body match {
      case AnyContentAsText(text) => text
      case AnyContentAsFormUrlEncoded(data) => data("message").head
    }
    MessageQueue.add(body)
    Created(body)
  }

  def pop: Action[AnyContent] = Action {
    MessageQueue.pop() match {
      case Some(value) => Ok(value)
      case _ => NoContent
    }
  }
}
