package scalalab3.lyricsengine.reader

import scala.io.Source

object MillionSongDataSubSetReader {
  def extract(path: String): MillionSongDataSubSet = {
    val wordsSongsPair = readRawData(path)
    new MillionSongDataSubSet(extractWords(wordsSongsPair._1), extractSongs(wordsSongsPair._2))
  }

  private def readRawData(path: String) = {
    val lines = Source.fromFile(path).getLines().toSeq
    // 17 - Line that contains the words.
    val words = lines(17)
    // 18 - First line that contains the data by songs.
    val songs = lines.slice(18, lines.size)
    words -> songs
  }

  private def extractWords(words: String) = words.substring(1).split(",");

  private def extractSongs(songsData: Seq[String]) = songsData.map(
    songData => {
      val elements = songData.split(",")
      val trackId = elements(0)
      val mxmTrackId = elements(1)
      val words_map = extractSongWords(elements.slice(2, elements.size))
      new Song(trackId, mxmTrackId, words_map);
    }).toSeq

  private def extractSongWords(words: Array[String]) = words.toSeq.map(
    pairInStringForm => {
      val pair = pairInStringForm.split(":")
      val wordIndex = pair(0).toInt
      val count = pair(1).toInt
      wordIndex -> count
    }).toMap
}

/**
  * That class represent subset of data from Million Song Dataset (MSD).
  * http://labrosa.ee.columbia.edu/millionsong/sites/default/files/AdditionalFiles/mxm_dataset_train.txt.zip
  */
case class MillionSongDataSubSet(words: Seq[String], songs: Seq[Song])

case class Song(trackId: String, mxmTrackId: String, words: Map[Int, Int])



