package io.andrewsmith.website.core

import cats.effect.IO
import doobie.Transactor
import org.flywaydb.core.Flyway

object Database {
  private val dbUrl = s"jdbc:sqlite:${sys.env.getOrElse("SQLITE_DB_PATH", "sqlite.db")}"

  def connect: IO[Transactor[IO]] = IO {
    Flyway.configure()
      .locations("migrations")
      .dataSource(dbUrl, "", "")
      .load()
      .migrate()

    Transactor.fromDriverManager[IO]("org.sqlite.JDBC", dbUrl)
  }
}
