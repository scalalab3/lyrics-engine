import sbt.Keys._

name          := "persistence"

organization  := "scalalab3.lyricsengine"

version       := "0.1.0-SNAPSHOT"

scalaVersion  := "2.11.8"

resolvers += "releases"  at "https://oss.sonatype.org/content/groups/scala-tools"

val specsV = "3.7.2"
val scalaTestV = "2.2.6"

libraryDependencies ++=
  Seq(
    "org.scalatest" %% "scalatest" % scalaTestV,
    "org.specs2" %% "specs2-core" % specsV,
    "org.specs2" %% "specs2-matcher-extra" % specsV,
    "org.mongodb" %% "casbah" % "3.1.1",
    "com.typesafe" % "config" % "1.3.0"
  )

