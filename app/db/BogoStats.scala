package db

import swaydb._
import swaydb.serializers.Default._

object BogoStats {
  private val stats = swaydb.persistent.Map[String, Int, Nothing, IO.ApiIO](dir = ".swaydb/bogostats").get

  object NumCompletions {
    def get: Int = {
      stats.get("numCompletions").get.getOrElse(0)
    }

    def add(x: Int): Unit = {
      stats.put("numCompletions", NumCompletions.get + x)
    }
  }
}
