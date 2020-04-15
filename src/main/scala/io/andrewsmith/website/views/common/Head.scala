package io.andrewsmith.website.views.common

import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title
import scalatags.Text.tags.frag

object Head {
  def tag(pageTitle: String, description: String, additionalTags: Frag, includeViewport: Boolean = true): TypedTag[String] = head(
    meta(charset := "UTF-8"),
    title(pageTitle),
    meta(name := "author", content := "Andrew Smith"),
    meta(name := "description", content := description),
    if (includeViewport) meta(name := "viewport", content := "width=device-width, initial-scale=1") else frag(),
    link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png"),
    additionalTags
  )
}
