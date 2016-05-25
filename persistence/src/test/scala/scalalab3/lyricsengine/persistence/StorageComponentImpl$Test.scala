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

  "Mongo Test" >> {
    if (tryMongoContext.isSuccess) {
      implicit val s = tryMongoContext.get

      val wd = Map(1 -> "i", 2 -> "the", 3 -> "you", 4 -> "to", 5 -> "and")
      val firstSong = Song("TRZZZYV128F92E996D", "6849828", Map(1 -> 10, 2 -> 6, 3 -> 20, 5 -> 2, 7 -> 30))
      val secondSong = Song("TRZZZYX128F92D32C6", "681124", Map(1 -> 4, 2 -> 18, 4 -> 3, 5 -> 6, 6 -> 9))
      val seqSong = Seq(firstSong, secondSong)
      DataSet(wd, seqSong)
      val mongoStorage = new StorageComponentImpl {
        override val storage: Storage = new StorageImpl
      }.storage


      "add Song with version " in {
        mongoStorage.countSongs() must_== 0
        mongoStorage.addSongs(seqSong, Some(1))
        mongoStorage.countSongs() must_== 2

      }
      "add Song " in {
        mongoStorage.countSongs() must_== 2
        mongoStorage.addSongs(seqSong)
        mongoStorage.countSongs() must_== 4
      }

      "add Words Definitions " in {
        mongoStorage.countWD() must_== 0
        mongoStorage.addWordsDefinition(wd)
        mongoStorage.countWD() must_== 1
      }

      "add Words Definitions with version" in {
        mongoStorage.countWD() must_== 1
        mongoStorage.addWordsDefinition(wd, Some(1))
        mongoStorage.countWD() must_== 2
      }

      "find Songs without version" in {
        mongoStorage.findSongs() must have size 2
      }

      "find Songs with version" in {
        mongoStorage.findSongs(Some(1)) must have size 2
      }

      "find WordsDefinitions without version" in {
        mongoStorage.findWordsDefinitions() must have size 1
      }

      "find WordsDefinitions with version" in {
        mongoStorage.findWordsDefinitions(Some(1)) must have size 1
      }

      "get Last Version" in {
        mongoStorage.getLastVersion() must_==Some(1)
      }


    } else "Skipped Test" >> skipped("Mongo context is not available in ")
  }

  def uuid() = Some(UUID.randomUUID())

  def drop() = for (m <- tryMongoContext) m.drop

  override def beforeAll(): Unit = drop()
  override def afterAll(): Unit = drop()
}
