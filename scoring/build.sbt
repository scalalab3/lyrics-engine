import sbt.Keys._

name := "scoring"

version := "0.1"

scalaVersion := "2.11.8"


libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
    "org.apache.spark" % "spark-core_2.11" % "1.6.1",
    "com.github.scopt" % "scopt_2.11" % "3.4.0",
    "org.apache.spark" % "spark-mllib_2.11" % "1.6.1",
    "com.thesamet" %% "kdtree" % "1.0.4"
  )
}

