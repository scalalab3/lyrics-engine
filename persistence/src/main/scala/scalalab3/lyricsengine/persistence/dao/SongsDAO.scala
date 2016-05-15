package scalalab3.lyricsengine.persistence.dao

import com.mongodb.casbah.Imports._
import com.typesafe.config._
import java.io.File
import scalalab3.lyricsengine.domain._

object SongsDAO extends App{

  val conf = ConfigFactory.defaultApplication()

  private val dbName = conf.getString("mongo.db")
  private val songsCollectionName = conf.getString("mongo.songscollection")
  private val wDefinitionsCollName = conf.getString("mongo.wordsdefinitions")

  private val songs: MongoCollection = MongoConnection()(dbName)(songsCollectionName)
  private val wordsDefinitions: MongoCollection = MongoConnection()(dbName)(wDefinitionsCollName)

  def addSongs(songsToAdd: Seq[Song], version: Option[Int] = None) = {
    val builder = songs.initializeOrderedBulkOperation //will automatically split the operation into batches
    for {
      song <- songsToAdd
    } builder.insert(songToMongoDBObj(song, version))

  }

  def addWordsDefinition(wd: WordsDefinition, version: Option[Int] = None) = {
    val builder = wordsDefinitions.initializeOrderedBulkOperation
    builder.insert(wdToMongoDbObject(wd, version))
  }

  def findSongs(version: Option[Int] = None): Seq[Song] = {
    val query = MongoDBObject("version" -> version.get)
    val result = songs.find(query)

    val songsSet = for {
      song <- result
    } yield songFromMongoDBObj(song)

    songsSet.toSeq
  }
  
  def findWordsDefinitions(version: Option[Int] = None): Seq[Map[Int,Int]] ={
    val query = MongoDBObject("version" -> version.get)
    val result = songs.find(query)

    val wdSet = for {
      wd <- result
    } yield wdFromMongoDBObj(wd)

    wdSet.toSeq
  }

  private def songFromMongoDBObj(obj: MongoDBObject):Song = {
    val _msdTrackId = obj.getAs[String]("msdTrackId").get
    val _mxmTrackId = obj.getAs[String]("mxmTrackId").get

    val _words = obj.getAs[Map[String, String]]("words").get.map {case (k,v) => (k.toInt, v.toInt)}

    Song(msdTrackId = _msdTrackId, mxmTrackId = _mxmTrackId, words = _words)

  }

  private def wdFromMongoDBObj(obj: MongoDBObject): Map[Int, Int] = {

    val version = obj.getAs[Int]("version").get

    val words = obj.getAs[Map[String, String]]("wordsDefinitions").get.map {case (k,v) => (k.toInt, v.toInt)}
    words
  }

  private def songToMongoDBObj(song: Song, version: Option[Int] = None): MongoDBObject = {
    val songBuilder = MongoDBObject.newBuilder
    songBuilder +=  ("msdTrackId" -> song.msdTrackId,
      "mxmTrackId" -> song.mxmTrackId,
      "words" -> getWDefinitions(song),
      "version" -> version.getOrElse(getLastVersion)
      )
    songBuilder.result()
  }

  private def wdToMongoDbObject(wd: WordsDefinition, version: Option[Int] = None): MongoDBObject = {
    val wdBuilder = MongoDBObject.newBuilder
    wdBuilder += (
      "wordsDefinitions" -> transformWordsDef(wd),
      "version" -> version.getOrElse(getLastVersion)
      )
    wdBuilder.result()
  }

  private def transformWordsDef(w: WordsDefinition): MongoDBObject = {
    val collection = MongoDBObject.newBuilder
    val transformed = w.map { case (k, v) => (k.toString, v) }
    collection ++= transformed
    collection.result()
  }

  private def getWDefinitions(song: Song):MongoDBObject = {
    val words = MongoDBObject.newBuilder
    val songWords = song.words.map { case (k, v) => (k.toString, v) }
    words ++= songWords
    words.result()
  }

  private def getLastVersion: Option[Int] = {
    val query = MongoDBObject() // All documents
    val fields = MongoDBObject("version" -> 1) // Only return `version`
    val orderBy = MongoDBObject("version" -> -1) // Order by version descending

    val result = wordsDefinitions.findOne(query, fields, orderBy)

    result.get.getAs[Int]("version")
  }

//  wordsDefinitions.find()
//  for { wd <- wordsDefinitions} yield wd
}