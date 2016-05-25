package scalalab3.lyricsengine.persistence

import com.mongodb.casbah.Imports._

import scalalab3.lyricsengine.domain._
import scalalab3.lyricsengine.persistence.mongo.MongoContext

trait StorageComponentImpl extends StorageComponent {

  override val storage: Storage


  class StorageImpl(implicit context: MongoContext) extends Storage {

    def addDataSet(dataSet: DataSet, version: Option[Int] = None)={
     addWordsDefinition(dataSet.definition)
     addSongs(dataSet.songs)

    }
    // case class DataSet(definition: WordsDefinition, songs: Seq[Song])

    def addSongs(songsToAdd: Seq[Song], version: Option[Int] = None) = {
      val builder = context.songsCollection.initializeOrderedBulkOperation //will automatically split the operation into batches
      for {
        song <- songsToAdd
      }  builder.insert(songToMongoDBObj(song, version))
      val result = builder.execute()
    }

    def addWordsDefinition(wd: WordsDefinition, version: Option[Int] = None) = {
      val builder = context.wdCollection.initializeOrderedBulkOperation
      builder.insert(wdToMongoDbObject(wd, version))

      val result = builder.execute()
    }

    def findSongs(version: Option[Int] = None): Seq[Song] = {

      val query = version match {
        case Some(v) => MongoDBObject("version" -> v)
        case None => MongoDBObject("version" -> null)
      }
      val result = context.songsCollection.find(query)

      val songsSet = for {
        song <- result
      } yield songFromMongoDBObj(song)

      songsSet.toSeq
    }

    def findWordsDefinitions(version: Option[Int] = None): Seq[Map[Int, String]] = {
      val query = version match {
        case Some(v) => MongoDBObject("version" -> version.get)
        case None => MongoDBObject("version" -> null)
      }
      val result = context.wdCollection.find(query)

      val wdSet = for {
        wd <- result
      } yield wdFromMongoDBObj(wd)

      wdSet.toSeq
    }

    def countSongs(): Int = context.songsCollection.find().count()

    def countWD(): Int = context.wdCollection.find().count()

    private def songFromMongoDBObj(obj: MongoDBObject): Song = {
      val _msdTrackId = obj.getAs[String]("msdTrackId").get
      val _mxmTrackId = obj.getAs[String]("mxmTrackId").get

      val _words = obj.getAs[Map[Int, Int]]("words").get

      Song(msdTrackId = _msdTrackId, mxmTrackId = _mxmTrackId, words = _words)

    }

    private def wdFromMongoDBObj(obj: MongoDBObject): Map[Int, String] = {

      val version = obj.getAs[Int]("version").getOrElse(null)

      val words = obj.getAs[Map[String, String]]("wordsDefinitions").get.map { case (k, v) => (k.toInt, v.toString) }
      words
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
        "version" -> version.getOrElse(getLastVersion.getOrElse(null))
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

      def getLastVersion: Option[Int] = {
      val query = MongoDBObject() // All documents
      val fields = MongoDBObject("version" -> 1) // Only return `version`
      val orderBy = MongoDBObject("version" -> -1) // Order by version descending

      val result = context.wdCollection.findOne(query, fields, orderBy)

      result match {
        case Some(res) => res.getAs[Number]("version").map(_.intValue())
        case None => None
      }
    }

  }

}