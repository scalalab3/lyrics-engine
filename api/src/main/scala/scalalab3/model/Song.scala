package scalalab3.model


/**
  * Created by atomkevich on 5/1/16.
  */
case class Song(trackId: Option[Int] = None,
                artistName: String, songName: String = "",
                albumName: String = "", text: String = "")