package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.{GoogleAnalytics, Head}
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.{frag, _}

object HomeView {
  val page: TypedTag[String] = html(
    lang := "en-US",
    Head.tag("andrewsmith.io.net", "andrewsmith.io.net", frag(
      link(rel := "stylesheet", href := "/assets/css/index.css")
    )),
    body(
      h1("welcome to www.andrewsmith.io.net!"),
      form(method := "get", action := "https://www.google.com/search",
        label(`for` := "q",
          span("Google Search:"),
          input(`type` := "text", name := "q"),
          button(`type` := "submit", "Search")
        )
      )
    ),
    GoogleAnalytics.scriptTag
  )
}
