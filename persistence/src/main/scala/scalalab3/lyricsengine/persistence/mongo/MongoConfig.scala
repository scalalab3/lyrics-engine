package scalalab3.lyricsengine.persistence.mongo

import com.typesafe.config.ConfigFactory

import constants.DefaultConfigValues._
import constants.DefaultConfigKeys._
import scala.util.Try

case class MongoConfig(host: String = defaultHost,
                       port: Int = defaultPort,
                       user: String = defaultUser,
                       password: String = defaultPassword,
                       dbName: String = defaultDbName,
                       songsCollection: String = defaultSongsCollection,
                       wdCollection: String  = defaultWDCollection)

object MongoConfig {
  private val config = ConfigFactory.load()

  def load(): MongoConfig =
    MongoConfig(
      getString(host, defaultHost),
      getInt(port, defaultPort),
      getString(user, defaultUser),
      getString(password, defaultPassword),
      getString(dbname, defaultDbName),
      getString(songsCollection, defaultSongsCollection),
      getString(wordsDefinitions, defaultWDCollection)
    )
  private def getString(key: String, defaultValue: String) = Try(config.getString(key)).getOrElse(defaultValue)
  private def getInt(key: String, defaultValue: Int) = Try(config.getInt(key)).getOrElse(defaultValue)
}
