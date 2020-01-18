package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

// TODO
object Messages {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Add Message"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      form(name := "messageForm", method := "post", action := "/api/messages/add",
        label(`for` := "message", "Message: "),
        input(name := "Body", id := "message"),
        input(`type` := "hidden", name := "From", id := "Web Client"),
        button(`type` := "reset", onclick := "document.forms['messageForm'].submit();", "Submit")
      )
    ),
    GoogleAnalytics.scriptTag
  )
}
