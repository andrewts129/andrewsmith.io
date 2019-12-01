name := """AndrewSmithDotIo"""
organization := "io.andrewsmith"

version := "1.1"
maintainer := "andrew@andrewsmith.io"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb, JavaAppPackaging)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
    guice,
    ws,
    "com.amazonaws" % "aws-java-sdk-s3" % "1.11.592",
    "io.swaydb" %% "swaydb" % "0.10.9"
)

pipelineStages in Assets:= Seq()

javaOptions ++= Seq(
    "-Xmx128m",
    "-XX:+UnlockExperimentalVMOptions",
    "-XX:+UseCGroupMemoryLimitForHeap"
)
