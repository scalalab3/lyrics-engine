package scalalab3.lyricsengine.persistence

import java.util.UUID
import org.specs2.mutable.Specification
import org.specs2.specification._
import scala.util.Try
import scalalab3.lyricsengine.domain.{DataSet, Song}
import scalalab3.lyricsengine.persistence.mongo.{MongoConfig, MongoContext}

class StorageComponentImpl$Test extends Specification with BeforeAfterAll {
  sequential

  val tryMongoContext = Try(new MongoContext(MongoConfig.load()))

  "MongoDB Test" >> {
    if (tryMongoContext.isSuccess) {
      implicit val s = tryMongoContext.get
      val wd = Map(1 -> "i", 2 -> "the", 3 -> "you", 4 -> "to", 5 -> "and")
      val firstSong = Song("TRZZZYV128F92E996D", "6849828", Map(1 -> 10, 2 -> 6, 3 -> 20, 5 -> 2, 7 -> 30))
      val secondSong = Song("TRZZZYX128F92D32C6", "681124", Map(1 -> 4, 2 -> 18, 4 -> 3, 5 -> 6, 6 -> 9))
      val seqSong = Seq(firstSong, secondSong)
      val MdataSet=DataSet(wd, seqSong)
      val mongoStorage = new StorageComponentImpl {
        override val storage: Storage = new StorageImpl
      }.storage

      "add DataSet " in {
        mongoStorage.countWD() must_== 0
        mongoStorage.addDataSet(MdataSet)
        mongoStorage.countWD() must_== 1
      }

      "add DataSet with version " in {
        mongoStorage.countWD() must_== 1
        mongoStorage.addDataSet(MdataSet, Some(1))
        mongoStorage.countWD() must_== 2
      }

      "get DataSet " in {
        mongoStorage.getDataSet()
        mongoStorage.findSongs() must have size 2
      }

      "get DataSet with version" in {
        mongoStorage.getDataSet(Some(1))
        mongoStorage.findSongs() must have size 2
      }

      "get LastVersion" in {
        mongoStorage.getLastVersion == Some(1)
      }

    } else "Skipped Test" >> skipped("Mongo context is not available in ")
  }

  def uuid() = Some(UUID.randomUUID())

  def drop() = for (m <- tryMongoContext) m.drop

  override def beforeAll(): Unit = drop()
  override def afterAll(): Unit = drop()
}
