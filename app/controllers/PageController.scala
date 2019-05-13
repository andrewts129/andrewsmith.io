package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

@Singleton
class PageController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
    def index: Action[AnyContent] = Action {
        Ok(views.html.index())
    }

    def oneHitWonders: Action[AnyContent] = Action {
        Ok(views.html.oneHitWonders())
    }

    def projects: Action[AnyContent] = Action {
        Ok(views.html.projects())
    }

    def resume: Action[AnyContent] = Action {
        Ok(views.html.resume())
    }

    def whisperingPablo: Action[AnyContent] = Action {
        Ok(views.html.whisperingPablo())
    }
}
