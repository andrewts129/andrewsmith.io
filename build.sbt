name := """AndrewSmithDotIo"""
organization := "io.andrewsmith"

version := "1.1"
maintainer := "andrew@andrewsmith.io"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb, JavaAppPackaging)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
    guice,
    ws,
    evolutions,
    jdbc,
    "org.scalaj" %% "scalaj-http" % "2.4.1",
    "org.postgresql" % "postgresql" % "42.2.1",
)

pipelineStages in Assets:= Seq()

javaOptions += "-Xmx128m"
javaOptions += "-XX:+UnlockExperimentalVMOptions"
javaOptions += "-XX:+UseCGroupMemoryLimitForHeap"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.andrewsmith.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.andrewsmith.binders._"
