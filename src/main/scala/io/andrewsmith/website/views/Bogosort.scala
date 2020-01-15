package io.andrewsmith.website.views

import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

// TODO
object Bogosort {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Bogosort"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      h1("Bogosort"),
      div(id := "bogo-container"),
      p(s"This program has been running for ",
        span(id := "total-duration"),
        " and has sorted the array ",
        span(id := "num-completions"), ".")
    ),
    script(`type` := "text/javascript", src := "https://d3js.org/d3.v5.min.js")
  )
}
