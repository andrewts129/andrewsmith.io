package db

import java.time.Instant

import io.getquill.{SnakeCase, SqliteJdbcContext}

object MessageQueue {
  lazy val ctx = new SqliteJdbcContext(SnakeCase, "ctx")
  private case class Message(id: Int, text: String, author: String, created: Long)

  def add(text: String, author: String, created: Instant): Unit = {
    import ctx._

    val q = quote {
      query[Message].insert(_.text -> lift(text), _.author -> lift(author), _.created -> lift(created.getEpochSecond))
    }

    ctx.run(q)
  }

  def pop(): Option[(String, String, Instant)] = {
    import ctx._

    val select = quote {
      infix"""SELECT * FROM message WHERE id = (SELECT MIN(id) FROM message)""".as[Query[Message]]
    }

    val delete = quote {
      infix"""DELETE FROM message WHERE id = (SELECT MIN(id) FROM message)""".as[Delete[Message]]
    }

    val rows = ctx.run(select)
    ctx.run(delete)

    rows match {
      case Nil => None
      case _ => Some((rows.head.text, rows.head.author, Instant.ofEpochSecond(rows.head.created)))
    }
  }
}
