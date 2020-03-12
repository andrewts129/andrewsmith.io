package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

object HomeView {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("andrewsmith.io.net"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
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
