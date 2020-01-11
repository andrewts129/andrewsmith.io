name := """AndrewSmithDotIo"""
organization := "io.andrewsmith"

version := "1.1"
maintainer := "andrewts129@gmail.com"

lazy val root = project.in(file(".")).enablePlugins(PlayScala, SbtWeb, JavaAppPackaging)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
    guice,
    ws,
    evolutions,
    jdbc,
    "com.amazonaws" % "aws-java-sdk-s3" % "1.11.699",
    "io.swaydb" %% "swaydb" % "0.11",
    "org.xerial" % "sqlite-jdbc" % "3.28.0",
    "io.getquill" %% "quill-jdbc" % "3.5.0"
)

pipelineStages in Assets := Seq()

javaOptions ++= Seq(
    "-Xmx128m",
    "-XX:+UnlockExperimentalVMOptions",
    "-XX:+UseCGroupMemoryLimitForHeap"
)
