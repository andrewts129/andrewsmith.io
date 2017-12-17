name := """AndrewSmithDotIo"""
organization := "io.andrewsmith"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += ws
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"

pipelineStages in Assets:= Seq(uglify)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.andrewsmith.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.andrewsmith.binders._"
