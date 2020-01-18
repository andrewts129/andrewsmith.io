package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

// TODO
object WhisperingPablo {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Whispering Pablo"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      h1("Whispering Pablo"),
      p("Enter a message to submit: "),
      input(`type` := "text", id := "input-text-field", maxlength := 500),
      button(id := "submit-button", onclick := "submitMessage()", "Submit"),
      p(id := "connecting-label", "Connecting to Whispering Pablo, please wait..."),
      p(id := "connecting-sublabel", "(This can take some time; you get what you pay for with free hosting...)"),
      div(id := "feed-holder"),
      p("Number of whispers: ", span(id := "num-whispers"))
    ),
    GoogleAnalytics.scriptTag
  )
}
