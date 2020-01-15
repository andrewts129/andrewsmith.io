package io.andrewsmith.website.views

import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.{title, section, nav}

// TODO add css
object Index {
  val page: TypedTag[String] = html(
    head(
      title("Andrew Smith"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      section(
        h1("Andrew Smith"),
        p("Computer Science & Engineering, The Ohio State University"),
      ),
      nav(
        ul(
          li(a("Projects", href := "/projects")),
          li(a("GitHub", href := "https://github.com/andrewts129")),
          li(a("Contact", href := "mailto:andrewts129@gmail.com"))
        )
      )
    )
  )
}
