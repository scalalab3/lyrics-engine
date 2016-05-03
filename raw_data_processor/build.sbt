name := "raw_data_processor"

version := "1.0"

scalaVersion := "2.11.8"

// -------- -------- Dependencies: -------- --------

// -------- Config:
// https://github.com/typesafehub/config
libraryDependencies += "com.typesafe" % "config" % "1.3.0"

// -------- Logger:
// http://logback.qos.ch/
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"
// https://github.com/typesafehub/scala-logging
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"

// -------- Test:
// http://www.scalatest.org/
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6"

// -------- DB:
// mongo:
// http://mongodb.github.io/mongo-scala-driver/1.1/
libraryDependencies += "org.mongodb.scala" % "mongo-scala-driver_2.11" % "1.1.0"