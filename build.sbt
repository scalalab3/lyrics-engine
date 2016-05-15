import _root_.sbt.Keys._

name := "lyrics-engine"

version := "1.0-SNAPSHOT"

name := "lyrics-engine"

lazy val commonSettings = Seq(
  organization := "scalalab3.lyricsengine",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
)

mainClass in Compile := Some("scalalab3.lyricsengine.api.Boot")

lazy val domain = (project in file("domain"))
  .settings(commonSettings: _*)
lazy val parser = (project in file("parser"))
  .settings(commonSettings: _*)
  .dependsOn(domain)
lazy val api = (project in file("api"))
  .settings(commonSettings: _*)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .dependsOn(api)
  .aggregate(domain, parser, api)
  .enablePlugins(UniversalPlugin, JavaAppPackaging)

lazy val domain = project in file("domain")
lazy val parser = project in file("parser") dependsOn domain
lazy val persistence = project in file("persistence") dependsOn domain
lazy val api = project in file("api")

