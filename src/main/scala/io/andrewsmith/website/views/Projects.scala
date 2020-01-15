package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.{title, section}

// TODO
object Projects {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("My Projects"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png")
    ),
    body(
      h1("Projects"),
      ul(
        li(
          section(
            h2("Columbus Building Age Map"),
            p(
              "A map displaying the year of construction for (almost) every building in Franklin County, Ohio. The map can be viewed ",
              a(href := "/columbus-buildings", "here"),
              " and the source code for the data collection and vector tile server can be viewed on ",
              a(href := "https://github.com/andrewts129/columbus-buildings", "GitHub"),
              "."
            )
          ),
        ),
        li(
          section(
            h2("@DonaldTrumBot"),
            p(
              "A Twitter bot written in Python that uses Markov chains to create (mostly-nonsensical) tweets resembling those of Donald Trump. The account can be found ",
              a(href := "https://twitter.com/DonaldTrumBot", "here"),
              " and the source code is hosted on ",
              a(href := "https://github.com/andrewts129/donald-trump-bot", "GitHub"),
              "."
            )
          ),
        ),
        li(
          section(
            h2("Whispering Pablo"),
            p(
              "A \"social network\" demo with a live feed of messages that are submitted anonymously and vanish after one hour. Under the hood, it's a RESTful web service built with Scala, the Play Framework, and a PostgreSQL database hosted on Heroku. The actual site can be found ",
              a(href := "/whispering-pablo", "here"),
              " and the source code is hosted on ",
              a(href := "https://github.com/andrewts129/whispering-pablo", "GitHub"),
              "."
            )
          ),
        )
      )
    ),
    GoogleAnalytics.scriptTag
  )
}
