package com.adform.lyrics_engine.model

import org.apache.commons.lang3.StringUtils.EMPTY
/**
  * Created by atomkevich on 5/1/16.
  */
case class Song(trackId: Option[Int] = None, artistName: String, songName: String = EMPTY, albumName: String = EMPTY, text: String = EMPTY)