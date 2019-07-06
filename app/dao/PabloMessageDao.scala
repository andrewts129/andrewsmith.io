package dao

import java.sql.Timestamp

import javax.inject.Inject
import models.PabloMessage
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

class PabloMessageDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfig[JdbcProfile] {

    import profile.api._

    override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

    class PabloMessages(tag: Tag) extends Table[PabloMessage](tag, "messages") {
        def id = column[Int]("id")
        def text = column[String]("text")
        def createdAt = column[Timestamp]("createdAt")

        def * = (id, text, createdAt) <> ((PabloMessage.apply _).tupled, PabloMessage.unapply)
    }
}
