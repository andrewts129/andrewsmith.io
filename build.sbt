val http4sVersion = "0.23.12"

lazy val commonSettings = {
  organization := "io.andrewsmith"
  version := "1.2"
  scalaVersion := "2.13.15"
}

lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    name := "website-client",
    Compile / resourceDirectory := baseDirectory.value / "dist"
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
      "io.circe" %% "circe-generic" % "0.14.3",
      "ch.qos.logback" % "logback-classic" % "1.4.4",
      "org.tpolecat" %% "doobie-core" % "1.0.0-RC2",
      "org.xerial" % "sqlite-jdbc" % "3.39.3.0",
      "org.flywaydb" % "flyway-core" % "9.7.0",
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    assembly / assemblyMergeStrategy := { // https://github.com/sbt/sbt-assembly/issues/391#issuecomment-981987422
      case f if f.endsWith("module-info.class") => MergeStrategy.discard
      case "NOTICE" => MergeStrategy.discard
      case PathList("META-INF", "LICENSE") => MergeStrategy.discard
      case PathList("META-INF", "INDEX.LIST") => MergeStrategy.discard
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case _ => MergeStrategy.deduplicate
    }
  ).enablePlugins(FlywayPlugin)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen { s: State => "project server" :: s }
