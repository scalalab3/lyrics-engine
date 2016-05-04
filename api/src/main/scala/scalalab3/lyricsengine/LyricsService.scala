package scalalab3.lyricsengine

import akka.actor.Actor
import akka.util.Timeout
import spray.http.{MediaTypes, StatusCodes}
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService

import scala.concurrent.duration._
import scala.io.Source
import scala.language.postfixOps
import scalalab3.model.Song

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class LyricsServiceActor extends Actor with LyricsService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(route)
}


// this trait defines our service behavior independently from the service actor
trait LyricsService extends HttpService {
  implicit val timeout = Timeout(5 seconds)

  object TrackJsonSupport extends DefaultJsonProtocol {
    implicit val trackFormat = jsonFormat5(Song)
  }

  val songs = List(
    Song(Some(1), "artist1", "songName1", "album1", "text1"),
    Song(Some(2), "artist1", "songName1", "album1", "text1"),
    Song(Some(3), "artist1", "songName1", "album1", "text1"),
    Song(Some(4), "artist1", "songName1", "album1", "text1"),
    Song(Some(5), "artist1", "songName1", "album1", "text1"))
  val route = {
    import spray.httpx.SprayJsonSupport._
    import TrackJsonSupport._
    path("match") {
      post {
        entity(as[String]) { msg =>
          complete {
            s"$msg matched"
          }
        }
      }
    } ~
      path("health") {
        get {
          complete {
            StatusCodes.OK
          }
        }
      } ~
      path("home") {
        get {
          respondWithMediaType(MediaTypes.`text/html`) {
            val homePage = Source.fromURL(this.getClass.getResource("/index.html")).mkString
            complete(homePage)
          }
        }
      } ~
      pathPrefix("recommend") {
        get {
          respondWithMediaType(MediaTypes.`text/html`) {
            val recommendationPage = Source.fromURL(this.getClass.getResource("/recommendation.html")).mkString
            complete(recommendationPage)
          }
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
          path("recommend") {
            post {
              entity(as[Song]) { order =>
                complete(songs)
              }
            }
          }
      }
  }
}
