package io.andrewsmith.website.db

import doobie.implicits._
import cats.effect.IO

case class Message(id: Int, text: String, author: String, created: Long)

object Message {
  def add(text: String, author: String): IO[Message] = {
    val sql = for {
      _ <- sql"INSERT INTO Message (text, author, created) VALUES ($text, $author, -1);".update.run
      select <- sql"SELECT id, text, author, created FROM Message WHERE id = last_insert_rowid();".query[Message].unique
    } yield select

    sql.transact(transactor)
  }

  def pop(): IO[Message] = {
    val sql = for {
      select <- sql"SELECT id, text, author, created FROM Message WHERE id = (SELECT MIN(id) FROM Message);".query[Message].unique
      _ <- sql"DELETE FROM Message WHERE id = (SELECT MIN(id) FROM Message);".update.run
    } yield select

    sql.transact(transactor)
  }
}
