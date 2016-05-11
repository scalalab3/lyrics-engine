package scalalab3.lyricsengine.mxm

import com.typesafe.config.Config
import org.jmusixmatch.MusixMatch
import org.jmusixmatch.entity.track.TrackData

import scala.util.Try
import scalalab3.lyricsengine.domain.SongMeta


object MXMClient {
  implicit def trackConverter(trackData: TrackData): SongMeta = SongMeta(
    Some(trackData.getTrackId.toLong), Some(trackData.getArtistName),
    Some(trackData.getTrackName), Some(trackData.getAlbumName), "")

  def getTrack(trackName: String, artistName: String)(implicit conf: Config): Try[SongMeta] = {
    Try(new MusixMatch(conf.getString("mxm.apikey")) getMatchingTrack(trackName, artistName) getTrack)
  }

  def getTrack(trackID: Int)(implicit conf: Config): Try[SongMeta] = {
    Try(new MusixMatch(conf.getString("mxm.apikey")) getTrack trackID getTrack)
  }
}
