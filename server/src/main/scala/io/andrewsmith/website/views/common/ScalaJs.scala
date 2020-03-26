package io.andrewsmith.website.views.common

import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.script

object ScalaJs {
  private val filename = if (sys.env.get("SCALAJS_PROD").contains("true")) "andrewsmithdotio-client-opt-bundle.js" else "andrewsmithdotio-client-fastopt-bundle.js"

  def execute(obj: String, function: String, args: String*): Frag = frag(
    script(src := s"assets/$filename"),
    script(s"$obj.$function(${args.map(arg => "'" + arg + "'").mkString(",")});")
  )
}
