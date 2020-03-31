package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.{GoogleAnalytics, Head}
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.{frag, _}

object MessagesView {
  val page: TypedTag[String] = html(
    lang := "en-US",
    Head.tag("Add Message", "Form to submit a message that will be spoken through my Raspberry Pi's speakers", frag()),
    body(
      form(name := "messageForm", method := "post", action := "/api/messages/add",
        label(`for` := "message", "Message: "),
        input(name := "Body", id := "message"),
        input(`type` := "hidden", name := "From", value := "Web Client"),
        button(`type` := "reset", onclick := "document.forms['messageForm'].submit();", "Submit")
      )
    ),
    GoogleAnalytics.scriptTag
  )
}
