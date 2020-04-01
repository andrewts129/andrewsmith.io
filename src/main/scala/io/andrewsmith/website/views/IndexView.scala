package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.{GoogleAnalytics, Head}
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.{frag, _}
import scalatags.Text.tags2.{nav, section}

object IndexView {
  val page: TypedTag[String] = html(
    lang := "en-US",
    Head.tag("Andrew Smith", "Andrew Smith's homepage", frag(
      script(src := "/assets/js/Index.js", defer := true),
      link(rel := "stylesheet", href := "/assets/css/index.css")
    )),
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
    GoogleAnalytics.scriptTag,
  )
}
