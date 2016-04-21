name := "lyrics-engine"

version := "1.0"

scalaVersion := "2.11.8"


lazy val raw_data_processor = project in file("raw_data_processor")
lazy val data_analyzer = project in file("data_analyzer")
lazy val http_api = project in file("http_api")
