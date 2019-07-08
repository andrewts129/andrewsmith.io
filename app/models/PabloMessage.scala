package models

import java.sql.Timestamp
import java.util.Date

import play.api.libs.json.{Format, JsError, JsLookupResult, JsResult, JsSuccess, JsValue, Json}
import db.DbContext
import io.getquill.{MappedEncoding, PostgresAsyncContext, SnakeCase}

import scala.concurrent.ExecutionContext

case class PabloMessage(id: Int, text: String, creationTime: Timestamp)

object PabloMessage {

    trait Decoders {
        implicit val timestampDecoder = MappedEncoding[String, Timestamp](s => Timestamp.valueOf(s))
    }

    private val context = new PostgresAsyncContext(SnakeCase, "pablo.db") with Decoders

    import context._

    implicit val ec = ExecutionContext.global

    private val pabloMessages = quote(querySchema[PabloMessage]("messages"))

    def create(text: String) = {
        context.run(
            pabloMessages.insert(_.text -> lift(text)).returning(_.id)
        )
    }

    def getAll = null

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
