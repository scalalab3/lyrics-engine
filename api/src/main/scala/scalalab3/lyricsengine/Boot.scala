package scalalab3.lyricsengine

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask
/**
  * @author Vlad Fefelov
  */
object Boot extends App {

  implicit val system = ActorSystem("song-recommendation-system")

  val service = system.actorOf(Props[LyricsServiceActor], "lyrics-service")
  val config = ConfigFactory.load();
  implicit val timeout = Timeout(5 seconds)
  IO(Http) ? Http.Bind(service, interface = config.getString("http.interface"), port = config.getInt("http.port"))
}
