name          := "mxm"

organization  := "scalalab3.lyricsengine"

version       := "0.1"

scalaVersion  := "2.11.8"

libraryDependencies ++= {
  Seq(
    "com.sachinhandiekar" % "jMusixMatch" % "1.1.3",
    "com.typesafe" % "config" % "1.3.0",
    "org.scalatest" %% "scalatest" % "3.0.0-M15"
  )
}

