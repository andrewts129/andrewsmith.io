package models

import java.sql.{Connection, PreparedStatement, ResultSet, Timestamp}
import java.util.Date

import play.api.db.{Database, Databases}
import play.api.libs.json.{Format, JsError, JsLookupResult, JsResult, JsSuccess, JsValue, Json}

import scala.concurrent.ExecutionContext

case class PabloMessage(id: Int, text: String, creation: Timestamp)

object PabloMessage {
    val db: Database = Databases(
        driver = "org.postgresql.Driver",
        url = "postgres://dev:123456@127.0.0.1:5432#/pabloDB"
    )

    def create(text: String): PabloMessage = {
        var result: PabloMessage = null
        val connection: Connection = db.getConnection()

        try {
            val statement: PreparedStatement = connection.prepareStatement(
                "INSERT INTO messages (text) VALUES (?) RETURNING *;"
            )
            statement.setString(1, text)

            val resultSet: ResultSet = statement.executeQuery()
            resultSet.first()

            result = PabloMessage(resultSet.getInt("id"), resultSet.getString("text"), resultSet.getTimestamp("creation"))
        }
        finally {
            connection.close()
        }

        result
    }

    def getAll: List[PabloMessage] = {
        var feed: List[PabloMessage] = List()

        val connection = db.getConnection()

        try {
            val statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM messages ORDER BY creation;")

            while (resultSet.next()) {
                feed = PabloMessage(
                    resultSet.getInt("id"),
                    resultSet.getString("text"),
                    resultSet.getTimestamp("creation_time")
                ) :: feed
            }

        }
        finally {
            connection.close()
        }

        feed
    }

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
            Json.obj("id" -> message.id, "message" -> message.text, "creation_time" -> message.creation.toString)
        }
    }
}
