package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.{GoogleAnalytics, ScalaJs}
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.{nav, section, title}

object IndexView {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Andrew Smith"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png"),
      link(rel := "stylesheet", href := "/assets/css/index.css")
    ),
    body(
      canvas(id := "animationCanvas"),
      div(
        section(
          h1("Andrew Smith"),
          p("Computer Science & Engineering, The Ohio State University"),
        ),
        nav(
          ul(
            li(a("Links", href := "/links")),
            li(a("GitHub", href := "https://github.com/andrewts129")),
            li(a("Contact", href := "mailto:andrewts129@gmail.com"))
          )
        )
      )
    ),
    ScalaJs.function("IndexScript.main"),
    GoogleAnalytics.scriptTag,
  )
}
