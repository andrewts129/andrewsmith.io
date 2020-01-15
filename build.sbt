val http4sVersion = "0.20.15"

lazy val root = (project in file("."))
  .settings(
    name := "AndrewSmithDotIo",
    organization := "io.andrewsmith",
    version := "1.2",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-scalatags" % http4sVersion,
      "io.circe" %% "circe-generic" % "0.12.3",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.lihaoyi" %% "scalatags" % "0.8.3"
    ),
    scalacOptions ++= Seq("-Ypartial-unification")
  )
  .enablePlugins(SbtWeb, Http4sWebPlugin)
