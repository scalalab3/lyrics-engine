import org.scalatest.{FlatSpec, Matchers}

import scalalab3.lyricsengine.domain.{DataSet, Song}
import scalalab3.lyricsengine.parser.DataSetParser

/**
  * @author Vlad Fefelov
  */
class DataSetParser$Test extends FlatSpec with Matchers {

  "A DataSetParser" should "parse data" in {

    val lines: Seq[String] = scala.io.Source.fromURL(getClass.getResource("/test_msd.txt")).getLines.toSeq

    val expected = buildExpectedData()
    val actual = DataSetParser.parse(lines)

    assert(expected == actual)  
  }

  "A test" should "fail" in {
    assert(true == false)
  }

  def buildExpectedData() = {
    val words = Map(1 -> "i", 2 -> "the", 3 -> "you", 4 -> "to", 5 -> "and")
    val firstSong = Song("TRZZZYV128F92E996D", "6849828", Map(1 -> 10, 2 -> 6, 3 -> 20, 5 -> 2, 7 -> 30))
    val secondSong = Song("TRZZZYX128F92D32C6", "681124", Map(1 -> 4, 2 -> 18, 4 -> 3, 5 -> 6, 6 -> 9))
    DataSet(words, Seq(firstSong, secondSong))
  }
}
