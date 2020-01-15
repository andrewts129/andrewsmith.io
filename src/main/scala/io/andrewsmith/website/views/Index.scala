package io.andrewsmith.website.views

import scalatags.Text
import scalatags.Text.all._

// TODO add css
object Index {
  val page: Text.TypedTag[String] = html(
    head(
      title := "Andrew Smith",
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      h1("Andrew Smith"),
      p("Computer Science & Engineering, The Ohio State University"),
      ul(
        li(a("Projects", href := "/projects")),
        li(a("GitHub", href := "https://github.com/andrewts129")),
        li(a("Contact", href := "mailto:andrewts129@gmail.com"))
      )
    )
  )
}
