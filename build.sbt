val http4sVersion = "0.21.18"

lazy val commonSettings = {
  organization := "io.andrewsmith"
  version := "1.2"
  scalaVersion := "2.13.7"
}

lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    name := "website-client",
    resourceDirectory in Compile := baseDirectory.value / "dist"
  )

lazy val server = (project in file("server"))
  .dependsOn(client)
  .settings(commonSettings)
  .settings(
    name := "website-server",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "io.circe" %% "circe-generic" % "0.13.0",
      "ch.qos.logback" % "logback-classic" % "1.2.6",
      "org.tpolecat" %% "doobie-core" % "0.10.0",
      "org.xerial" % "sqlite-jdbc" % "3.36.0.3",
      "org.flywaydb" % "flyway-core" % "8.0.4",
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
  ).enablePlugins(FlywayPlugin)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen { s: State => "project server" :: s }
