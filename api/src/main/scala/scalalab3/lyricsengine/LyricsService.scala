package scalalab3.lyricsengine

import akka.actor.Actor
import spray.http.StatusCodes
import spray.routing.HttpService

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

  val route =
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
    }

}
