package io.andrewsmith.website.db

import cats.effect.IO
import doobie.implicits._

case class BogoStat(key: String, value: Int)

object BogoStat {
  def numCompletions: IO[Int] = {
    val sql = sql"SELECT value FROM BogoStat WHERE key = 'numCompletions';".query[Int].unique
    sql.transact(transactor)
  }

  def IncrementNumCompletions: IO[Int] = {
    val sql = for {
      _ <- sql"UPDATE BogoStat SET value = value + 1 WHERE key = 'numCompletions';".update.run
      select <- sql"SELECT value FROM BogoStat WHERE key = 'numCompletions';".query[Int].unique
    } yield select

    sql.transact(transactor)
  }
}
