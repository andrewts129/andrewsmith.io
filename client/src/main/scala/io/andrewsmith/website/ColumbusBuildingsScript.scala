package io.andrewsmith.website

import org.scalajs.dom.window.alert
import typings.mapboxGl.mapboxGlStrings.{fill, vector}
import typings.mapboxGl.mod._
import typings.mapboxGl.{mod => mapboxgl}

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("ColumbusBuildingsScript")
object ColumbusBuildingsScript {
  @JSExport
  def main(): Unit = {
    if (!mapboxgl.supported()) {
      alert("Sorry, your browser doesn't support the map on this page")
    } else {
      val tileServerUrl = "http://www.andrewsmith.io:81" // TODO

      val map = new mapboxgl.Map(MapboxOptions(
        container = "map",
        center = js.Tuple2(-82.9988, 39.9612),
        zoom = 13,
        minZoom = 11,
        maxZoom = 16,
        style = s"$tileServerUrl/styles/base/style.json",
        antialias = true
      ))

      map.on("load", () => {
        map.addSource("buildings", VectorSource(
          `type` = vector,
          tiles = js.Array(s"$tileServerUrl/data/buildings/{z}/{x}/{y}.pbf"),
          minzoom = 11,
          maxzoom = 15
        ))

        map.addSource("columbus", VectorSource(
          `type` = vector,
          tiles = js.Array(s"$tileServerUrl/data/columbus/{z}/{x}/{y}.pbf"),
        ))

        map.addControl(new FullscreenControl().asInstanceOf[Control])

        val colors = js.Array("#eaeae5", "#e41a1c", "#f24d0e", "#ff7f00", "#FFBF1A", "#ffff33", "#A6D73F", "#4daf4a", "#429781", "#377eb8", "#6866AE", "#984ea3")
        val stopYears = js.Array(0, 1800, 1825, 1850, 1875, 1900, 1925, 1950, 1975, 2000, 2025, 2050)
        val stops = stopYears.zip(colors).map(tup => js.Array(tup._1, tup._2))

        map.addLayer(Layer(
          id = "buildings",
          `type` = fill,
          source = "buildings",
          `source-layer` = "buildings",
          paint = FillPaint(
            `fill-color` = StyleFunction(
              property = "year_built",
              stops = stops.asInstanceOf[js.Array[js.Array[_]]]
            )
          ),
          layout = js.Dynamic.literal( // Sort so that older buildings are on top and undated buildings are at the back
            "fill-sort-key" -> js.Array("case",
              js.Array("==", js.Array("get", "year_built"), 0),
                -3000,
              // default
                js.Array("-", js.Array("to-number", js.Array("get", "year_built")))
            )
          ).asInstanceOf[FillLayout]
        ))
      })
    }
  }
}
