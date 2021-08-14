package io.andrewsmith.website.db

import cats.effect.{ContextShift, IO}
import doobie.Transactor
import org.flywaydb.core.Flyway

object Database {
  private val dbUrl = s"jdbc:sqlite:${sys.env.getOrElse("SQLITE_DB_PATH", "sqlite.db")}"

  Flyway.configure()
    .locations("migrations")
    .dataSource(dbUrl, "", "")
    .load()
    .migrate()

  def transactor(implicit cs: ContextShift[IO]): Transactor[IO] = {
    Transactor.fromDriverManager[IO]("org.sqlite.JDBC", dbUrl)
  }
}
