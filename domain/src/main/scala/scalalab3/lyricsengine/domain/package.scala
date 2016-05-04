package scalalab3.lyricsengine

/**
  * @author Vlad Fefelov
  */
package object domain {

  type WordsDefinition = Map[Int, String]

  case class Song(msdTrackId: String, mxmTrackId: String, words: Map[Int, Int])

  case class DataSet(definition: WordsDefinition, songs: Seq[Song])
}
