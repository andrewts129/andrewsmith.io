package controllers

import io.getquill._
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import utils.Bogosorter

case class BogoStats(key: String, value: String)

@Singleton
class BogoController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def data: Action[AnyContent] = Action {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "ctx")
    import ctx._

    val q = quote {
      query[BogoStats].filter(stat => stat.key == "numCompletions").map(stat => stat.value).take(1)
    }

    val numCompletions: String = ctx.run(q).head

    Ok(Json.obj(
      "array" -> Bogosorter.array,
      "numCompletions" -> numCompletions.toInt,
      "lastCompletion" -> "todo")
    )
  }
}
