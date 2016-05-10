package scalalab3.lyricsengine.scoring

import akka.actor.{Actor, ActorLogging}
import com.thesamet.spatial.{DimensionalOrdering, KDTreeMap}
import org.apache.spark.SparkContext

import scalalab3.lyricsengine.domain.SongVector
import scalalab3.lyricsengine.scoring.ScoringConstants.VectorPaths


class KDTreeActor(sc: SparkContext, dimensionalSize: Int) extends Actor with ActorLogging with Serializable {
  var kdTree: KDTreeMap[Seq[Double], Long] = _

  override def preStart() = {
    val trainVectors = sc.textFile(VectorPaths).map(line => line.split(",") match {
      case Array(id, vector@_*) => vector.map(_.toDouble) -> id.toLong
    }).collect()
    kdTree = KDTreeMap.fromSeq(trainVectors)(DimensionalOrdering.dimensionalOrderingForSeq[Seq[Double], Double](dimensionalSize))
  }

  override def receive: Receive = {
    case SongVector(id, probabilities) => {
      val nearest = kdTree.findNearest(probabilities, 4).map(_._2)
      sender ! nearest
    }
  }
}
