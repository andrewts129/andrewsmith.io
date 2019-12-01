package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.Environment
import play.api.Mode.Prod

@Singleton
class PageController @Inject()(cc: ControllerComponents, ws: WSClient, env: Environment) extends AbstractController(cc) {
    def index: Action[AnyContent] = Action {
        Ok(views.html.index(prod))
    }

    def oneHitWonders: Action[AnyContent] = Action {
        Ok(views.html.oneHitWonders(prod))
    }

    def projects: Action[AnyContent] = Action {
        Ok(views.html.projects(prod))
    }

    def resume: Action[AnyContent] = Action {
        Ok(views.html.resume(prod))
    }

    def whisperingPablo: Action[AnyContent] = Action {
        Ok(views.html.whisperingPablo(prod))
    }

    def columbusBuildings: Action[AnyContent] = Action {
        Ok(views.html.columbusBuildings(prod, tileServerUrl))
    }

    def bogosort: Action[AnyContent] = Action {
        Ok(views.html.bogosort(prod))
    }

    private def prod: Boolean = env.mode == Prod
    private def tileServerUrl: String = sys.env.getOrElse("TILE_SERVER_URL", "http://andrewsmith.io:81")
}
