package com.adform.lyrics_engine

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives
import akka.stream.Materializer
import com.adform.lyrics_engine.model.Song
import spray.json._

import scala.concurrent.ExecutionContextExecutor
import scala.io.Source

/**
  * Created by atomkevich on 5/1/16.
  */


object SongJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val songFormat = jsonFormat5(Song)
}

trait Service extends Directives {

  import SongJsonSupport._

  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer


  val songs = List(
    Song(Some(1), "artist1", "songName1", "album1", "text1"),
    Song(Some(2), "artist1", "songName1", "album1", "text1"),
    Song(Some(3), "artist1", "songName1", "album1", "text1"),
    Song(Some(4), "artist1", "songName1", "album1", "text1"),
    Song(Some(5), "artist1", "songName1", "album1", "text1"))

  val routes = {
    path("home") {
      get {
        val lyricssinglePage = Source.fromURL(getClass.getResource("/index.html")).mkString
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, lyricssinglePage))
      }
    } ~
      path("recommend") {
        get {
          val lyricssinglePage = Source.fromURL(getClass.getResource("/recommendation.html")).mkString
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, lyricssinglePage))
        }
      } ~
      pathPrefix("css") {
        get {
          getFromResourceDirectory("css")
        }
      } ~
      pathPrefix("js") {
        get {
          getFromResourceDirectory("js")
        }
      } ~
      pathPrefix("api") {
        path("search") {
          get {
            complete(songs)
          }
        } ~
        path("recommend" ) {
          post {
            entity(as[Song]) { order =>
              complete(songs)
            }
          }
        }
      }

  }
}