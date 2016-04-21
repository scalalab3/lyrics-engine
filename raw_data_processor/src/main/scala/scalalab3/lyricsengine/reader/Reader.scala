package scalalab3.lyricsengine.reader

import com.typesafe.config.ConfigFactory

import scala.io.Source

object Reader {
  val conf = ConfigFactory.load();

  def main(args: Array[String]) {
    print(extract())
  }

  def extract(): MSD = {
    val path = conf.getString("path_to_msd")

    val pair = read(path)

    val words = extractWords(pair._1)
    val songs = extractSongs(pair._2)

    new MSD(words, songs)
  }

  private def read(path: String) = {
    val lines = Source.fromFile(path).getLines().toSeq
    val words = lines(17)
    val songs = lines.slice(18, lines.size)
    (words, songs)
  }

  private def extractWords(words: String) = words.substring(1).split(",");

  private def extractSongs(songsData: Seq[String]) = songsData.map(songData => {
    val elements = songData.split(",")
    val trackId = elements(0)
    val mxmTrackId = elements(1)
    val words_map = extractSongWords(elements.slice(2, elements.size))
    new Song(trackId, mxmTrackId, words_map);
  })

  private def extractSongWords(words: Array[String]): Map[Int, Int] = words.toSeq.map(source_pair => {
    val pair = source_pair.split(":")
    assert(pair.size == 2)
    pair(0).toInt -> pair(1).toInt
  }).toMap;
}


