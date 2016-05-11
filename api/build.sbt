organization  := "com.scalalab"

version       := "0.1"

scalaVersion  := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-httpx"   % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "io.spray" %% "spray-json" % "1.3.2",
    "org.scalacheck"             %% "scalacheck"     % "1.13.1" % "test",
    "org.scalatest"   %%   "scalatest"  % "2.2.0" % "test",
    "org.mongodb" % "bson" % "3.2.2"

  )
}

