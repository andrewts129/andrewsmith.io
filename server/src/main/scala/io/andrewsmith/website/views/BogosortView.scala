package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

object BogosortView {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Bogosort"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png"),
      link(rel := "stylesheet", href := "/assets/css/bogosort.css")
    ),
    body(
      div(
        h1("Bogosort"),
        div(id := "bogo-container"),
        p(s"This program has been running for ",
          span(id := "total-duration"),
          " and has sorted the array ",
          span(id := "num-completions"), ".")
      )
    ),
    script(src := "https://d3js.org/d3.v5.min.js"),
    script(src := "/assets/js/Bogosort.js"),
    GoogleAnalytics.scriptTag
  )
}
