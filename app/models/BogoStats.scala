package models

import io.getquill.{PostgresJdbcContext, SnakeCase}

object BogoStats {
  // An RDBMS is definitely not the best solution here. But I already have one running on the server so... ‾\_(ツ)_/‾
  private case class BogoStats(key: String, value: String)

  lazy private val ctx = new PostgresJdbcContext(SnakeCase, "ctx")

  object NumCompletions {
    def apply(): Int = {
      import ctx._

      val q = quote {
        query[BogoStats].filter(stat => stat.key == "numCompletions").map(stat => stat.value).take(1)
      }

      ctx.run(q).head.toInt
    }

    def +=(n: Int): Unit = {
      // TODO figure out how to do this in one query... had trouble putting too much logic in the UPDATE query
      import ctx._

      val newValue: String = (NumCompletions() + n).toString

      val q = quote {
        query[BogoStats].filter(stat => stat.key == "numCompletions").update(
          stat => stat.value -> lift(newValue)
        )
      }

      ctx.run(q)
    }
  }
}
