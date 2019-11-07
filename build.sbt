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
    "org.postgresql" % "postgresql" % "42.2.8",
    "io.getquill" %% "quill-jdbc" % "3.4.10"
)

pipelineStages in Assets:= Seq()

javaOptions ++= Seq(
    "-Xmx128m",
    "-XX:+UnlockExperimentalVMOptions",
    "-XX:+UseCGroupMemoryLimitForHeap"
)
