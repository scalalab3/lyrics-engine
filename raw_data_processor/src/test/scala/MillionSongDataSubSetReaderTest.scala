import org.scalatest.{FlatSpec, Matchers}

import scalalab3.lyricsengine.reader.{MillionSongDataSubSet, MillionSongDataSubSetReader, Song}

class MillionSongDataSubSetReaderTest extends FlatSpec with Matchers {

  "A MillionSongDataSubSetReader" should "correct load all data" in {
    // --- Prepare expected data:
    val expectedData: MillionSongDataSubSet = prepareTestData();

    // --- Computation actual data:
    val path = getClass.getResource("/test_msd.txt").getPath
    val actualData: MillionSongDataSubSet = MillionSongDataSubSetReader.extract(path)

    // --- Checking data:
    assert(expectedData.words == actualData.words)
    assert(expectedData.songs.size == actualData.songs.size)
    actualData.songs(0) == expectedData.songs(0)
    actualData.songs(1) == expectedData.songs(1)
    //Just in case.
    assert(expectedData.songs == actualData.songs)
  }

  def prepareTestData() = {
    val words = List("i", "the", "you", "to", "and")
    val firstSong = new Song("TRZZZYV128F92E996D", "6849828", Map(1 -> 10, 2 -> 6, 3 -> 20, 5 -> 2, 7 -> 30))
    val secondSong = new Song("TRZZZYX128F92D32C6", "681124", Map(1 -> 4, 2 -> 18, 4 -> 3, 5 -> 6, 6 -> 9))
    new MillionSongDataSubSet(words, Seq(firstSong, secondSong))
  }
}
