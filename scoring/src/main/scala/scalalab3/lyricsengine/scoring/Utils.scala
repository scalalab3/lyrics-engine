package scalalab3.lyricsengine.scoring

import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.{SparkConf, SparkContext}

import scala.language.implicitConversions
import scalalab3.lyricsengine.scoring.ScoringConstants._

/**
  * Created by atomkevich on 5/8/16.
  */
object Utils {
  implicit class RichString2(val s: String) extends AnyVal {
    def wordsWithIndex = s.split("\\W+").tail.zipWithIndex
    def wordPairs = s.split("\\W+").tail.map((_, 1))
  }

  implicit def vectorToStr(vector: Vector): Seq[Double] = vector.toArray.toSeq

   def createSparkContext(): SparkContext = {
    val conf = new SparkConf()
      .setAppName("Lyrics scoring job")
      .set("spark.driver.allowMultipleContexts", "true")
      .setMaster(SparkMaster)
    new SparkContext(conf)
  }
}
