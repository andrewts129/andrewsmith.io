package models

import io.getquill.{PostgresJdbcContext, SnakeCase}

object BogoStats {
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
      import ctx._

      val q = quote {
        query[BogoStats].filter(stat => stat.key == "numCompletions").update(
          stat => stat.value -> (stat.value.toInt + lift(n)).toString
        )
      }

      ctx.run(q)
    }
  }
}
