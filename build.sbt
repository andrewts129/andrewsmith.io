name := """AndrewSmithDotIo"""
organization := "io.andrewsmith"

version := "1.1"
maintainer := "andrew@andrewsmith.io"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb, JavaAppPackaging)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
    guice,
    ws,
    "org.scalaj" %% "scalaj-http" % "2.4.1",
    "com.amazonaws" % "aws-java-sdk-s3" % "1.11.592",
    caffeine
)

pipelineStages in Assets:= Seq()

javaOptions += "-Xmx128m"
javaOptions += "-XX:+UnlockExperimentalVMOptions"
javaOptions += "-XX:+UseCGroupMemoryLimitForHeap"
