package io.andrewsmith.website

import cats.effect.{ContextShift, IO}
import doobie.Transactor
import doobie.util.transactor.Transactor.Aux

import scala.concurrent.ExecutionContext

// TODO add evolutions
package object db {
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  val transactor: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.sqlite.JDBC",
    "jdbc:sqlite:.db/db.sqlite"
  )
}
