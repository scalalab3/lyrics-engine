package scalalab3.lyricsengine.persistence.dao

import scalalab3.lyricsengine.domain._

/**
 * Created by annie on 5/17/16.
 */
trait StorageComponent {

  val storage: Storage

  trait Storage {

    def addSongs(songsToAdd: Seq[Song], version: Option[Int] = None)

    def addWordsDefinition(wd: WordsDefinition, version: Option[Int] = None)

    def findSongs(version: Option[Int] = None): Seq[Song]

    def findWordsDefinitions(version: Option[Int] = None): Seq[Map[Int, Int]]
  }

}
