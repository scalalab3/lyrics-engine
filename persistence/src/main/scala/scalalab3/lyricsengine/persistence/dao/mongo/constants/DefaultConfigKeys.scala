package scalalab3.lyricsengine.persistence.dao.mongo.constants


object DefaultConfigKeys {
  val mongo = "mongo"
  val host = s"$mongo.host"
  val port = s"$mongo.port"
  val user = s"$mongo.user"
  val dbname = s"$mongo.dbname"
  val password = s"$mongo.password"
  val songsCollection = s"$mongo.songscollection"
  val wordsDefinitions = s"$mongo.wordsdefinitions"

}
