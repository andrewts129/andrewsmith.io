val http4sVersion = "0.21.1"

lazy val root = (project in file("."))
  .settings(
    name := "AndrewSmithDotIo",
    organization := "io.andrewsmith",
    version := "1.2",
    scalaVersion := "2.13.1",
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
  ).settings( // Flyway
    flywayUrl := s"jdbc:sqlite:server/${sys.env.getOrElse("SQLITE_DB_PATH", "sqlite.db")}",
    flywayLocations := Seq("migrations")
  ).enablePlugins(SbtWeb, Http4sWebPlugin, FlywayPlugin)
