package scalalab3.lyricsengine

import akka.actor.ActorSystem
import org.specs2.mutable.Specification
import spray.http._
import spray.testkit.Specs2RouteTest

class LyricsServiceSpec extends Specification with Specs2RouteTest with LyricsService {
  implicit def actorRefFactory: ActorSystem = system
  
  "LyricsService" should {

    "return a 200 status for health check" in {
      Get("/health") ~> route ~> check {
        status === StatusCodes.OK
      }
    }

    "match specific lyrics" in {
      Post("/match", "the actual lyrics") ~> route ~> check {
        responseAs[String] === "the actual lyrics matched"
      }
    }
  }
}
