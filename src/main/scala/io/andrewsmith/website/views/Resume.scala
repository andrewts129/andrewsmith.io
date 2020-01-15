package io.andrewsmith.website.views

import io.andrewsmith.website.views.common.GoogleAnalytics
import scalatags.Text.TypedTag
import scalatags.Text.attrs._
import scalatags.Text.implicits._
import scalatags.Text.tags._
import scalatags.Text.tags2.title

object Resume {
  val page: TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      title("Resume"),
      link(rel := "icon", `type` := "image/png", href := "/assets/images/favicon.png"),
      script("function f(){window.location.replace('http://' + window.location.host + '/AndrewSmithResume.pdf')};setTimeout(f, 1500)"),
      script("(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');ga('create', 'UA-102560912-1', 'auto');ga('send', 'pageview', {'hitCallback': f});")
    ),
    body(
      a(href := "/AndrewSmithResume.pdf", "Please click here if your browser does not redirect you")
    )
  )
}
