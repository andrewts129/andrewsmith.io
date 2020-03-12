package io.andrewsmith.website.db

import cats.effect.IO
import doobie.implicits._

case class Message(id: Int, text: String, author: String, created: Option[Long])

object Message {
  def add(text: String, author: String): IO[Message] = {
    val sql = for {
      _ <- sql"INSERT INTO Message (text, author) VALUES ($text, $author);".update.run
      select <- sql"SELECT id, text, author, created FROM Message WHERE id = last_insert_rowid();".query[Message].unique
    } yield select

    sql.transact(transactor)
  }

  def pop(): IO[Option[Message]] = {
    val sql = for {
      select <- sql"SELECT id, text, author, created FROM Message WHERE id = (SELECT MIN(id) FROM Message);".query[Message].option
      _ <- sql"DELETE FROM Message WHERE id = (SELECT MIN(id) FROM Message);".update.run
    } yield select

    sql.transact(transactor)
  }
}
