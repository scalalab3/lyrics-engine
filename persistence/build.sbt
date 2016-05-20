import sbt.Keys._

name          := "persistence"

organization  := "scalalab3.lyricsengine"

version       := "0.1.0-SNAPSHOT"

scalaVersion  := "2.11.8"

resolvers += "releases"  at "https://oss.sonatype.org/content/groups/scala-tools"


libraryDependencies ++=
  Seq(
    "org.scalatest" % "scalatest_2.11" % "2.2.6",
    "org.mongodb" %% "casbah" % "3.1.1",
    "com.typesafe" % "config" % "1.3.0"
  )

