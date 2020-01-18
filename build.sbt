val http4sVersion = "0.20.15"

lazy val commonSettings = {
  organization := "io.andrewsmith"
  version := "1.2"
  scalaVersion := "2.12.10"
}

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    name := "AndrewSmithDotIo-server",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-scalatags" % http4sVersion,
      "io.circe" %% "circe-generic" % "0.12.3",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.lihaoyi" %% "scalatags" % "0.8.3",
      "org.tpolecat" %% "doobie-core" % "0.8.8",
      "org.xerial" % "sqlite-jdbc" % "3.28.0"
    ),
    scalacOptions ++= Seq("-Ypartial-unification"),
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value
  )
  .enablePlugins(SbtWeb, Http4sWebPlugin)

lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    name := "AndrewSmithDotIo-client",
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.7"
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}
