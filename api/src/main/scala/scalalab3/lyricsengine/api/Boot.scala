package scalalab3.lyricsengine.api

import akka.actor._
import akka.io.IO
import akka.util.Timeout
import spray.can.Http

import scala.concurrent.duration._
import scala.util.Properties

/**
  * @author Vlad Fefelov
  */
object Boot extends App {

  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[LyricsServiceActor], "http-service")

  implicit val timeout = Timeout(5 seconds)

  val port = Properties.envOrElse("PORT", "8080").toInt

  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = port)
}
