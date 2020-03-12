package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.{GoogleAnalytics, Head}
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.{frag, _}

object BogosortView {
  val page: TypedTag[String] = html(
    lang := "en-US",
    Head.tag("Bogosort", "A constantly running attempt to sort an array using the Bogosort algorithm", frag(
      link(rel := "stylesheet", href := "/assets/css/bogosort.css")
    )),
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
