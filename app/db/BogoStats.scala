package db

import swaydb._
import swaydb.serializers.Default._

object BogoStats {
  private val stats = swaydb.persistent.Map[String, Int, Nothing, IO.ApiIO](dir = ".swaydb/bogostats").get

  object NumCompletions {
    if (!stats.contains("numCompletions").get) {
      stats.put("numCompletions", 0)
    }

    def get: Int = {
      stats.get("numCompletions").get.get
    }

    def add(x: Int): Unit = {
      stats.map {
        case ("numCompletions", value) => ("numCompletions", value + x)
        case (key, value) => (key, value)
      }.materialize.flatMap(stats.put).get
    }
  }
}
