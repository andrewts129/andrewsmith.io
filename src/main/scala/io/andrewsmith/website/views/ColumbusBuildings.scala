package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

// TODO
object ColumbusBuildings {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("The Age of Columbus Buildings"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png"),
      link(rel := "stylesheet", href := "https://api.mapbox.com/mapbox-gl-js/v1.5.0/mapbox-gl.css")
    ),
    body(
      div(id := "title-card",
        h1("The Age of Columbus Buildings")
      ),
      div(id := "map",
        div(id := "legend",
          div(
            p(id := "legend-description",
              "Year Built:"
            ),
            div(id := "legend-gradient",
              span("1800"),
              span(),
              span(),
              span(),
              span("1900"),
              span(),
              span(),
              span(),
              span("2000"),
              span()
            )
          ),
          div(id := "legend-source",
            p(
              "Source: ",
              a(href := "https://www.franklincountyauditor.com/", "Franklin Country Auditor"),
              "and ",
              a(href := "https://gismaps.osu.edu/OSUMaps/Default.html?", "The Ohio State University"),
              ". Some dates are estimates and are likely inaccurate."
            ),
            p(
              "Inspired by ",
              a(href := "https://www.bklynr.com/block-by-block-brooklyns-past-and-present/", "Block by Block, Brooklyn’s Past and Present"),
              " and ",
              a(href := "https://nathanrooy.github.io/pages/2017-02-15-the-age-of-cincinnati-full-screen.html", "The Age of Cincinnati"),
              "."
            ),
            p(
              "Source code for the ",
              a(href := "https://github.com/andrewts129/columbus-buildings", "data processing"),
              " and ",
              a(href := "https://github.com/andrewts129/andrew-smith-dot-io", "visualization"),
              " can be found on GitHub."
            )
          )
        )
      )
    ),
    script(`type` := "text/javascript", src := "https://api.mapbox.com/mapbox-gl-js/v1.5.0/mapbox-gl.js"),
    GoogleAnalytics.scriptTag
  )
}
