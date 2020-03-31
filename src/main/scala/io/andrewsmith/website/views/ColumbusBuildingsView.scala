package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.{GoogleAnalytics, Head}
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags.{frag, _}

object ColumbusBuildingsView {
  private val tileServerUrl = sys.env.get("CBUS_BUILDING_TILE_SERVER_URL")
  val page: TypedTag[String] = html(
    lang := "en-US",
    Head.tag("The Age of Columbus Buildings", "A map displaying the year of construction for (almost) every building in Franklin County, Ohio.", frag(
      link(rel := "stylesheet", href := "https://api.mapbox.com/mapbox-gl-js/v1.8.1/mapbox-gl.css"),
      link(rel := "stylesheet", href := "/assets/css/columbusBuildings.css")
    )),
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
              " and ",
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
    script(src := "https://api.mapbox.com/mapbox-gl-js/v1.8.1/mapbox-gl.js"),
    script(s"let tileServerURL='${tileServerUrl.getOrElse("")}';"),
    script(src := "/assets/js/ColumbusBuildings.js"),
    GoogleAnalytics.scriptTag
  )
}
