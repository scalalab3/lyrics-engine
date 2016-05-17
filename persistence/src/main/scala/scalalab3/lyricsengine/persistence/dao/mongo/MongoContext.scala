package scalalab3.lyricsengine.persistence.dao.mongo

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoClient

class MongoContext(val config: MongoConfig) {

  val mongoClient = MongoClient()
  val mongoDB = mongoClient(config.dbName)

  implicit val options: DBObject = BasicDBObjectBuilder.start().add("capped", true).add("size", 2000000000l).get()

  def songsCollection =
    if (mongoDB.collectionExists(config.songsCollection)) {
      mongoDB(config.songsCollection)
    } else {
      mongoDB.createCollection(config.songsCollection, options)
      mongoDB(config.songsCollection)
    }

  def wdCollection = {
    if (mongoDB.collectionExists(config.wdCollection)) {
      mongoDB(config.wdCollection)
    } else {
      mongoDB.createCollection(config.wdCollection, options)
      mongoDB(config.wdCollection)
    }
  }

  def drop = {
    mongoDB.dropDatabase()
  }



}
