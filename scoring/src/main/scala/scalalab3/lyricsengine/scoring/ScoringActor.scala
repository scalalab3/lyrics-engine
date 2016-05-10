package scalalab3.lyricsengine.scoring

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import org.apache.spark.mllib.clustering.LocalLDAModel
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scalalab3.lyricsengine.domain.{SongMeta, SongVector}
import scalalab3.lyricsengine.scoring.ScoringConstants.{DatasetPath, LdaModel}
import scalalab3.lyricsengine.scoring.Utils._

class ScoringActor extends Actor {
  val sc = createSparkContext()
  implicit val timeout = Timeout(5.seconds)
  var kdTreeActor: ActorRef = _
  var lyricsModel: LocalLDAModel = _
  var dictionary: RDD[(String, Int)]= _
  override def preStart() = {
    lyricsModel = LocalLDAModel.load(sc, LdaModel)
    kdTreeActor = context.actorOf(Props(new KDTreeActor(sc,lyricsModel.k)))
    dictionary = sc.textFile(DatasetPath).filter(_.startsWith("%")).flatMap(_.wordsWithIndex).cache()
  }

  override def receive = {
    case songMeta: SongMeta => {
      val wordCounts = sc.parallelize(songMeta.text.wordPairs).reduceByKey(_ + _)
      val dictionaryWordCount = dictionary.leftOuterJoin(wordCounts)
        .map { case (word, list) => list match {
          case (wordNumber, Some(count)) => (wordNumber, count.toDouble)
          case (wordNumber, None) => (wordNumber, 0.0d)
        }
        }
        .sortByKey()
        .values
        .collect()

      val rddPresentationTrack = sc.parallelize(
        List(songMeta.trackId.get -> Vectors.dense(dictionaryWordCount))).cache()
      val (id, probabilities) = lyricsModel.topicDistributions(rddPresentationTrack).first()
      //TODO: Store this vector to mongoDB for future execution.
      val recommendedSongs = ask(kdTreeActor, SongVector(id, probabilities)).mapTo[List[Long]]
      recommendedSongs pipeTo sender

    }
  }
}
