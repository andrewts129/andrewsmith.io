package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

// TODO
object Resume {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Resume"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    GoogleAnalytics.scriptTag,
    body(
      a(href := "/AndrewSmithResume.pdf", "Please click here if your browser does not redirect you")
    )
  )
}
