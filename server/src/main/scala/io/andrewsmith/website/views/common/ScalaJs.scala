package io.andrewsmith.website.views.common

import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.script

object ScalaJs {
  val scriptTag: TypedTag[String] = script(src := "assets/andrewsmithdotio-client-opt.js")
}
