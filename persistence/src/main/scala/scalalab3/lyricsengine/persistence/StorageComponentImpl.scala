package scalalab3.lyricsengine.persistence

import com.mongodb.casbah.Imports._

import scalalab3.lyricsengine.domain._
import scalalab3.lyricsengine.persistence.mongo.MongoContext

trait StorageComponentImpl extends StorageComponent {

  override val storage: Storage

  class StorageImpl(implicit context: MongoContext) extends Storage {

    def addDataSet(dataSet: DataSet, version: Option[Int] = None) = {
      addWordsDefinition(dataSet.definition, version)
      addSongs(dataSet.songs, version)
    }

    def getDataSet(version: Option[Int] = None): DataSet = {
      val wd = findWordsDefinitions(version)
      val songs = findSongs(version)
      DataSet(wd, songs)
    }

    def getLastVersion: Int = {
      val query = MongoDBObject() // All documents
      val fields = MongoDBObject("version" -> 1) // Only return `version`
      val orderBy = MongoDBObject("version" -> -1) // Order by version descending
      val result = context.wdCollection.findOne(query, fields, orderBy)
      val defaultVersion = -1
      result match {
        case Some(res) => res.getAs[Int]("version").getOrElse(defaultVersion)
        case None => defaultVersion
      }
    }

    private def addSongs(songsToAdd: Seq[Song], version: Option[Int] = None) = {
      val builder = context.songsCollection.initializeOrderedBulkOperation //will automatically split the operation into batches
      for {
        song <- songsToAdd
      } builder.insert(songToMongoDBObj(song, version))
      val result = builder.execute()
    }

    private def addWordsDefinition(wd: WordsDefinition, version: Option[Int] = None) = {
      val builder = context.wdCollection.initializeOrderedBulkOperation
      builder.insert(wdToMongoDbObject(wd, version))
      val result = builder.execute()
    }

    private def findSongs(version: Option[Int] = None): Seq[Song] = {
      val query = MongoDBObject("version" -> version.getOrElse(getLastVersion))
      val result = context.songsCollection.find(query)
      val songsSet = for {
        song <- result
      } yield songFromMongoDBObj(song)
      songsSet.toSeq
    }

    private def findWordsDefinitions(version: Option[Int] = None): WordsDefinition = {
      val query = MongoDBObject("version" -> version.getOrElse(getLastVersion))
      val result = context.wdCollection.find(query)
      result.map(wdFromMongoDBObj(_)).next()
    }

    private def countSongs(): Int = context.songsCollection.find().count()

    private def countWD(): Int = context.wdCollection.find().count()

    private def songFromMongoDBObj(obj: MongoDBObject): Song = {
      val _msdTrackId = obj.as[String]("msdTrackId")
      val _mxmTrackId = obj.as[String]("mxmTrackId")
      val _words = obj.as[Map[Int, Int]]("words")
      Song(msdTrackId = _msdTrackId, mxmTrackId = _mxmTrackId, words = _words)
    }

    private def wdFromMongoDBObj(obj: MongoDBObject): Map[Int, String] = {
      obj.getAs[Map[String, String]]("wordsDefinitions").get.map { case (k, v) => (k.toInt, v.toString) }
    }

    private def songToMongoDBObj(song: Song, version: Option[Int]): MongoDBObject = {
      val songBuilder = MongoDBObject.newBuilder
      songBuilder +=("msdTrackId" -> song.msdTrackId,
        "mxmTrackId" -> song.mxmTrackId,
        "words" -> getWDefinitions(song),
        "version" -> version.getOrElse(getLastVersion)
        )
      songBuilder.result()
    }

    private def wdToMongoDbObject(wd: WordsDefinition, version: Option[Int]): MongoDBObject = {
      val wdBuilder = MongoDBObject.newBuilder
      wdBuilder +=(
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

    private def getWDefinitions(song: Song): MongoDBObject = {
      val words = MongoDBObject.newBuilder
      val songWords = song.words.map { case (k, v) => (k.toString, v) }
      words ++= songWords
      words.result()
    }
  }

}