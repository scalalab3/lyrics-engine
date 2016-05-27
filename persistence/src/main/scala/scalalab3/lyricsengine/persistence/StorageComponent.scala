package scalalab3.lyricsengine.persistence

import scalalab3.lyricsengine.domain._

/**
  * Created by annie on 5/17/16.
  */
trait StorageComponent {

  val storage: Storage

  trait Storage {
    def addDataSet(dataSet: DataSet, version: Option[Int] = None)

    def getDataSet(version: Option[Int] = None): DataSet

    def getLastVersion: Int
  }

}
