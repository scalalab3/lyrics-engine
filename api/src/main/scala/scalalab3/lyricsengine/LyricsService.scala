package scalalab3.lyricsengine

import akka.actor.{Actor, Props}
import akka.pattern.ask
import akka.util.Timeout
import spray.http.StatusCodes
import spray.json.DefaultJsonProtocol
import spray.routing.{Directives, HttpService}

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success}
import scalalab3.lyricsengine.domain.SongMeta
import scalalab3.lyricsengine.scoring.ScoringActor

class LyricsServiceActor extends Actor with LyricsService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  implicit val system = context.system

  def receive = runRoute(route)


}

object SongMetaJsonSupport extends DefaultJsonProtocol {
  implicit val trackFormat = jsonFormat5(SongMeta)
}

// this trait defines our service behavior independently from the service actor
trait LyricsService extends HttpService with CORSSupport with Directives {
  implicit val timeout = Timeout(10 seconds)

  val scoringActor = actorRefFactory.actorOf(Props[ScoringActor])

  val songs = List(
    SongMeta(Some(1), Some("artist1"), Some("songName1"), Some("album1"), "text1"),
    SongMeta(Some(2), Some("artist1"), Some("songName1"), Some("album1"), "text1"),
    SongMeta(Some(3), Some("artist1"), Some("songName1"), Some("album1"), "text1"),
    SongMeta(Some(4), Some("artist1"), Some("songName1"), Some("album1"), "text1"),
    SongMeta(Some(5), Some("artist1"), Some("songName1"), Some("album1"), "text1"))
  val route = cors {
    import spray.httpx.SprayJsonSupport._

    import SongMetaJsonSupport._
    import scala.concurrent.ExecutionContext.Implicits.global
    // implicit def executionContext = actorRefFactory.dispatcher
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
      pathPrefix("api") {
        path("search") {
          get {
            complete(songs)
          }
        } ~
          path("recommend") {
            post {
              entity(as[SongMeta]) { song: SongMeta =>
                //TODO: Check if song has id. If not - song doesn't present in out system.
                // So using mxm client find full song meta with id or otherwise generate unique object id.
                // Then async save to mongoDB.
                //TODO: FIX TRACK ID
                val recommendedSongIds = ask(scoringActor, song.copy(trackId = Some(0))).mapTo[List[Long]]
                onComplete(recommendedSongIds) {
                  case Success(ids) =>  {
                    //TODO: find songs by ids in mongoDB
                    ids.foreach(println)
                    complete(songs)
                  }
                  case Failure(ex) => complete(s"An error occurred: ${ex.getMessage}")
                }
              }
            }
          }
      }
  }
}
