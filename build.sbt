name := "LDA_test"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaV       = "2.4.4" // Akka HTTP not ciplied with Akka 2.4
  val akkaStreamV = "2.0.4"
  val scalaTestV  = "2.2.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamV,
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test",
    "io.spray" %% "spray-json" % "1.3.2"
  )
}
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.0"
