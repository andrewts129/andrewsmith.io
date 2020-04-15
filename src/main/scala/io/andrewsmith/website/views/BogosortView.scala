package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.{GoogleAnalytics, Head}
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.{frag, _}
import scalatags.Text.tags2.noscript

object BogosortView {
  val page: TypedTag[String] = html(
    lang := "en-US",
    Head.tag("Bogosort", "A constantly running attempt to sort an array using the Bogosort algorithm", frag(
      script(src := "/assets/js/Bogosort.js", defer := true),
      link(rel := "stylesheet", href := "/assets/css/bogosort.css")
    ), includeViewport = false),
    body(
      div(
        h1("Bogosort"),
        noscript(
          p("This animation requires JavaScript to view.")
        ),
        div(id := "bogo-container"),
        p(s"This program has been running for ",
          span(id := "total-duration"),
          " and has sorted the array ",
          span(id := "num-completions"), ".")
      )
    ),
    GoogleAnalytics.scriptTag
  )
}
