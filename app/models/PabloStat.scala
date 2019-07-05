package models

import play.api.libs.json.{Format, JsError, JsLookupResult, JsResult, JsSuccess, JsValue, Json}

case class PabloStat(key: String, value: Int)

object PabloStat {

    implicit object PabloStatFormat extends Format[PabloStat] {

        override def reads(json: JsValue): JsResult[PabloStat] = {
            val key: JsLookupResult = json \ "key"
            val value: JsLookupResult = json \ "value"

            if (key.isDefined && value.isDefined) {
                JsSuccess(PabloStat(key.get.as[String], value.get.as[Int]))
            }
            else {
                JsError()
            }
        }

        override def writes(stat: PabloStat): JsValue = Json.obj("key" -> stat.key, "value" -> stat.value)
    }
}

