package io.andrewsmith.website.views

import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

// TODO implement
object Messages {
  val page: TypedTag[String] = html(
    head(
      title("TODO"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      h1("TODO")
    )
  )
}
