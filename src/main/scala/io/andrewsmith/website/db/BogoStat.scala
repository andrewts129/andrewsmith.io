package io.andrewsmith.website.db

import cats.effect.IO
import doobie.implicits._

case class BogoStat(key: String, value: Int)

object BogoStat {
  // So we don't need to hit the database every second for a number that changes once every few months
  private var numCompletionsCache: Option[IO[Int]] = None

  def numCompletions: IO[Int] = {
    numCompletionsCache match {
      case Some(value) => value
      case None =>
        val sql = sql"SELECT value FROM BogoStat WHERE key = 'numCompletions';".query[Int].unique
        val value = sql.transact(transactor)
        numCompletionsCache = Some(value) // Cache this value
        value
    }
  }

  def IncrementNumCompletions: IO[Int] = {
    numCompletionsCache = None // Invalidate cache

    val sql = for {
      _ <- sql"UPDATE BogoStat SET value = value + 1 WHERE key = 'numCompletions';".update.run
      select <- sql"SELECT value FROM BogoStat WHERE key = 'numCompletions';".query[Int].unique
    } yield select

    sql.transact(transactor)
  }
}
