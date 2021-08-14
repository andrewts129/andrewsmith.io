package io.andrewsmith.website.db

import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor

case class BogoStat(key: String, value: Int)

object BogoStat {
  def numCompletions(implicit transactor: Transactor[IO]): IO[Int] = {
    val sql = sql"SELECT value FROM BogoStat WHERE key = 'numCompletions';".query[Int].unique
    sql.transact(transactor)
  }

  def incrementNumCompletions(implicit transactor: Transactor[IO]): IO[Int] = {
    val sql = for {
      _ <- sql"UPDATE BogoStat SET value = value + 1 WHERE key = 'numCompletions';".update.run
      select <- sql"SELECT value FROM BogoStat WHERE key = 'numCompletions';".query[Int].unique
    } yield select

    sql.transact(transactor)
  }
}
