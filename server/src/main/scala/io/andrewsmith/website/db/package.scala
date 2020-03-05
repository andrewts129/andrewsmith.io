package io.andrewsmith.website

import cats.effect.{ContextShift, IO}
import doobie.Transactor
import doobie.util.transactor.Transactor.Aux
import org.flywaydb.core.Flyway

import scala.concurrent.ExecutionContext

package object db {
  private val dbUrl = "jdbc:sqlite:.db/db.sqlite" // TODO use env var

  // Runs database migrations
  Flyway.configure()
    .locations("migrations")
    .dataSource(dbUrl, "", "")
    .load()
    .migrate()

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  val transactor: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.sqlite.JDBC", dbUrl
  )
}
