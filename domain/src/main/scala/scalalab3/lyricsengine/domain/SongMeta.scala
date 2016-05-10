package scalalab3.lyricsengine.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
  * Created by atomkevich on 5/6/16.
  */
case class SongMeta(@JsonDeserialize(contentAs=classOf[java.lang.Long])trackId: Option[Long] = None,
                    artistName: Option[String], songName: Option[String],
                    albumName: Option[String], text: String)