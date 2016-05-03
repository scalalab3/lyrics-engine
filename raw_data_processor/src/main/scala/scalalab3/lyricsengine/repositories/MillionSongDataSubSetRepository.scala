package scalalab3.lyricsengine.repositories


import scalalab3.lyricsengine.system.{ProjectConfig, ProjectLogger}

/**
  * Repository for mongoDB
  */
// TODO: move to API?
class MillionSongDataSubSetRepository extends ProjectConfig with ProjectLogger {
  def saveSongs(songs: Any) = ???

  def saveWords(songs: Any) = ???


  def findAll() = ???
}

