package io.andrewsmith.website.views

import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

// TODO implement
object ColumbusBuildings {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("TODO"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      h1("TODO")
    )
  )
}
