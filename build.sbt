val http4sVersion = "0.21.6"

lazy val commonSettings = {
  organization := "io.andrewsmith"
  version := "1.2"
  scalaVersion := "2.13.3"
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
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.tpolecat" %% "doobie-core" % "0.9.0",
      "org.xerial" % "sqlite-jdbc" % "3.32.3",
      "com.amazonaws" % "aws-java-sdk-s3" % "1.11.831",
      "org.flywaydb" % "flyway-core" % "6.5.3",
      "org.specs2" %% "specs2-core" % "4.10.5" % "test",
      "org.specs2" %% "specs2-mock" % "4.10.5" % "test"
    )
  ).enablePlugins(FlywayPlugin)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen { s: State => "project server" :: s }
