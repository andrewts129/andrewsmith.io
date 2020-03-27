val http4sVersion = "0.21.1"

lazy val commonSettings = {
  organization := "io.andrewsmith"
  version := "1.2"
  scalaVersion := "2.13.1"
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
      "io.circe" %% "circe-generic" % "0.13.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.lihaoyi" %% "scalatags" % "0.8.5",
      "org.tpolecat" %% "doobie-core" % "0.8.8",
      "org.xerial" % "sqlite-jdbc" % "3.28.0",
      "com.amazonaws" % "aws-java-sdk-s3" % "1.11.735",
      "org.flywaydb"  %  "flyway-core" % "6.2.4",
    )
  ).settings( // ScalaJS
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    isDevMode in scalaJSPipeline := !sys.env.get("SCALAJS_PROD").contains("true")
  ).settings( // Flyway
    flywayUrl := s"jdbc:sqlite:server/${sys.env.getOrElse("SQLITE_DB_PATH", "sqlite.db")}",
    flywayLocations := Seq("migrations")
  ).enablePlugins(SbtWeb, Http4sWebPlugin, FlywayPlugin)

lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    name := "AndrewSmithDotIo-client",
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "1.0.0"
    )
  ).enablePlugins(ScalaJSPlugin, ScalaJSWeb)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}
