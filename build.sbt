name := """AndrewSmithDotIo"""
organization := "io.andrewsmith"

version := "1.0-SNAPSHOT"
maintainer := "andrew@andrewsmith.io"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb, JavaAppPackaging, DockerPlugin)

scalaVersion := "2.12.7"

libraryDependencies += guice
//libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += ws
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.1"

pipelineStages in Assets:= Seq(uglify)

javaOptions += "-Xmx128m"
javaOptions += "-XX:+UnlockExperimentalVMOptions"
javaOptions += "-XX:+UseCGroupMemoryLimitForHeap"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.andrewsmith.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.andrewsmith.binders._"
