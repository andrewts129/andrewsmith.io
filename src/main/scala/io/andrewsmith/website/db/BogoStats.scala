package io.andrewsmith.website.db

import cats.effect.IO
import doobie.implicits._

case class BogoStats(key: String, value: Int)

object BogoStats {
  def numCompletions: IO[Int] = {
    val sql = sql"SELECT value FROM BogoStats WHERE key = 'numCompletions';".query[Int].unique
    sql.transact(transactor)
  }

  def IncrementNumCompletions: IO[Int] = {
    val sql = for {
      _ <- sql"UPDATE BogoStats SET value = value + 1 WHERE key = 'numCompletions';".update.run
      select <- sql"SELECT value FROM BogoStats WHERE key = 'numCompletions';".query[Int].unique
    } yield select

    sql.transact(transactor)
  }
}
