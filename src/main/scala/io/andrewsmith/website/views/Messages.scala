package io.andrewsmith.website.views

import scalatags.Text
import scalatags.Text.all._

// TODO implement
object Messages {
  val page: Text.TypedTag[String] = html(
    head(
      title := "TODO",
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      h1("TODO")
    )
  )
}
