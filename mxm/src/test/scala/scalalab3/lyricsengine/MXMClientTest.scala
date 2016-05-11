package scalalab3.lyricsengine

import com.typesafe.config.{ConfigFactory, Config}

import org.scalatest._

import scala.util.{Failure, Success}
import scalalab3.lyricsengine.mxm.MXMClient

class MXMClientTest extends FlatSpec with Matchers {
  implicit val conf: Config = ConfigFactory.load()
  val CorrectTrackId = 1015129
  val IncorrectTrackId = -1
  val CorrectTrackName = "My Love"
  val CorrectArtistName = "Little Texas"

  "A MXMClient" should "should return song meta information by correct trackId" in {
    MXMClient.getTrack(CorrectTrackId) match {
      case Success(songMeta) => {
        songMeta should not be (null)
        songMeta.artistName should not be (null)
        songMeta.songName should not be (null)
        songMeta.trackId should equal (Some(CorrectTrackId))
      }
    }
  }

  "A MXMClient" should "should return song meta information by correct trackName and artistName" in {
    MXMClient.getTrack(CorrectTrackName, CorrectArtistName) match {
      case Success(songMeta) => {
        songMeta should not be (null)
        songMeta.artistName should equal (Some(CorrectArtistName))
        songMeta.songName should equal (Some(CorrectTrackName))
        songMeta should not be (null)
      }
    }
  }

  "A MXMClient" should "should throw error by incorrect trackId" in {
    MXMClient.getTrack(IncorrectTrackId) match {
      case Failure(ex) => ex should not be (null)
    }
  }
  "A MXMClient" should "should throw error by incorrect trackName and artistName" in {
    MXMClient.getTrack("", "") match {
      case Failure(ex) => {
        ex should  not be (null)
        print(ex.getMessage)
        ex.getMessage should equal("The request had bad syntax or was inherently impossible to be satisfied")
      }
    }
  }
}