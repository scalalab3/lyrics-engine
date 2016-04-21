package scalalab3.lyricsengine.reader


/**
  * That class represent data from Million Song Dataset (MSD).
  */
case class MSD(words: Seq[String], songs: Seq[Song])

case class Song(trackId: String, mxmTrackId: String, words: Map[Int, Int])