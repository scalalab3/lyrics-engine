package scalalab3.lyricsengine.system

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait ProjectLogger {
  val logger = Logger(LoggerFactory.getLogger("common"))
}
