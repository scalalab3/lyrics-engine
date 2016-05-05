name := "lyrics-engine"

version := "0.1"

scalaVersion := "2.11.8"


lazy val domain = project in file("domain")
lazy val parser = project in file("parser") dependsOn domain
lazy val api = project in file("api")
lazy val client = project in file("client")
