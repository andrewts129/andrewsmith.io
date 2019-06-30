package models

import java.sql.Timestamp

import play.api.libs.json._

case class PabloMessage(id: Int, text: String, creationTime: Timestamp)

object PabloMessage {

    implicit object PabloMessageFormat extends Format[PabloMessage] {

        // Not used, just put here so the code compiles. Otherwise useless
        override def reads(json: JsValue): JsResult[PabloMessage] = JsSuccess(PabloMessage(-1, "", null))

        override def writes(message: PabloMessage): JsValue = {
            JsObject(
                Seq("id" -> JsNumber(message.id),
                    "message" -> JsString(message.text),
                    "creation_time" -> JsString(message.creationTime.toString)
                )
            )
        }
    }

}
