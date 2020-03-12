package io.andrewsmith.website.views.common

import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.script

object ScalaJs {
  private val filename = if (sys.env.get("SCALAJS_PROD").contains("true")) "andrewsmithdotio-client-opt-bundle.js" else "andrewsmithdotio-client-fastopt-bundle.js"

  def function(functionName: String): Frag = frag(
    script(src := s"assets/$filename"),
    script(s"$functionName();")
  )
}
