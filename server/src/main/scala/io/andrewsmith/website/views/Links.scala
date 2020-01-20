package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.{section, title, `var`, q}

// TODO
object Links {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Stuff I've Made"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png"),
      link(rel := "stylesheet", href := "/assets/css/links.css")
    ),
    body(
      div(
        h1("Stuff I've Made"),
        ul(
          li(
            section(
              h2("Columbus Building Age Map"),
              ul(
                li("A map displaying the year of construction for (almost) every building in Franklin County, Ohio."),
                li(a(href := "/columbus-buildings", "Link")),
                li(a(href := "https://github.com/andrewts129/columbus-buildings", "Source Code"))
              )
            ),
          ),
          li(
            section(
              h2("Bogosort"),
              ul(
                li("This server is constantly attempting to sort an array of ",
                  `var`("n"), " = 10 using the famous sorting algorithm ",
                  a(href := "https://en.wikipedia.org/wiki/Bogosort", "Bogosort"),
                  ". It's not going well."),
                li(a(href := "/columbus-buildings", "Link")),
                li(a(href := "https://github.com/andrewts129/columbus-buildings", "Source Code"))
              )
            ),
          ),
          li(
            section(
              h2("@DonaldTrumBot"),
              ul(
                li("A Twitter bot written in Python that uses Markov chains to create (mostly-nonsensical) tweets resembling those of Donald Trump."),
                li(a(href := "https://twitter.com/DonaldTrumBot", "Link")),
                li(a(href := "https://github.com/andrewts129/donald-trump-bot", "Source Code"))
              )
            ),
          ),
          li(
            section(
              h2("Whispering Pablo"),
              ul(
                li(q("Shitty Yik-Yak 2.0"), " - Matt Cohen"),
                li(a(href := "/whispering-pablo", "Link")),
                li(a(href := "https://github.com/andrewts129/whispering-pablo", "Source Code"))
              )
            ),
          ),
          li(
            section(
              h2("Yell at me"),
              ul(
                li("Submit something through this form and the Raspberry Pi in my apartment will loudly speak it in a Scottish accent! (WIP, there may be a delay)"),
                li(a(href := "/messages", "Link")),
              )
            ),
          )
        )
      )
    ),
    GoogleAnalytics.scriptTag
  )
}
