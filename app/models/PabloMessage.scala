package models

import java.sql.Timestamp

import play.api.libs.json.{Format, JsError, JsLookupResult, JsResult, JsSuccess, JsValue, Json}

case class PabloMessage(id: Int, text: String, creationTime: Timestamp)

object PabloMessage {

    implicit object PabloMessageFormat extends Format[PabloMessage] {

        override def reads(json: JsValue): JsResult[PabloMessage] = {
            val id: JsLookupResult = json \ "id"
            val text: JsLookupResult = json \ "text"
            val creationTime: JsLookupResult = json \ "creation_time"

            if (id.isDefined && text.isDefined && creationTime.isDefined) {
                JsSuccess(
                    PabloMessage(id.get.as[Int], text.get.as[String], Timestamp.valueOf(creationTime.get.as[String]))
                )
            }
            else {
                JsError()
            }
        }

        override def writes(message: PabloMessage): JsValue = {
            Json.obj("id" -> message.id, "message" -> message.text, "creation_time" -> message.creationTime.toString)
        }
    }
}
