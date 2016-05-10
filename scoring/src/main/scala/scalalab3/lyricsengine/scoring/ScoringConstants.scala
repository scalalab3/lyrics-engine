package scalalab3.lyricsengine.scoring

import com.typesafe.config.ConfigFactory


object ScoringConstants {
  val props = ConfigFactory.load()
  val SparkMaster = props.getString("spark.master")
  val LdaModel = props.getString("lda.model")
  val DatasetPath = props.getString("spark.datasetPaths")
  val VectorPaths = props.getString("spark.vectorPaths")
}
