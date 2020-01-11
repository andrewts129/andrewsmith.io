package controllers

import java.time.Instant

import db.MessageQueue
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, AnyContentAsFormUrlEncoded, AnyContentAsText, ControllerComponents}

@Singleton
class MessageQueueController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def add: Action[AnyContent] = Action { request =>
    val bodyAndAuthor: Option[(String, String)] = request.body match {
      case AnyContentAsText(text) => Some((text, "Unknown"))
      case AnyContentAsFormUrlEncoded(data) => try {
        Some((data("Body").head, data("From").head))
      } catch {
        case _: Throwable => None
      }
      case _ => None
    }

    bodyAndAuthor match {
      case Some(value) =>
        MessageQueue.add(value._1, value._2, Instant.now())
        Accepted
      case None => BadRequest
    }
  }

  def pop: Action[AnyContent] = Action {
    MessageQueue.pop() match {
      case Some(value) => Ok(Json.obj(
        "text" -> value._1,
        "author" -> value._2,
        "created" -> value._3
      ))
      case _ => NoContent
    }
  }
}
