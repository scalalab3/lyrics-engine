package scalalab3.lyricsengine.model.lda

import com.typesafe.config.ConfigFactory
import main.scala.scalalab3.lyricsengine.model.lda.LDAParams
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.clustering._
import org.apache.spark.mllib.linalg.{Vectors, Vector}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}


object LDAModelBuilder {
  implicit def Vector2List(vector: Vector) = vector.toArray.toList

   def execute(params: LDAParams) {
    Logger.getRootLogger.setLevel(Level.WARN)
    val props = ConfigFactory.load()
    val conf = new SparkConf().setAppName(s"LDAJob with $params")
      .setMaster(props.getString("spark.master"))
    val sc = new SparkContext(conf)


    // Load and parse the data
    val (corpus, terms) = dataPrePreprocessing(sc, params.input)

    val ldaModel = new LDA()
      .setOptimizer(createOptimizer(params, corpus.count()))
      .setK(params.k)
      .setMaxIterations(params.maxIterations)
      .run(corpus)

    // Save and load model.
    ldaModel.save(sc, props.getString("lda.model"))
     ldaModel match {
       case model: LocalLDAModel =>
         model
           .topicDistributions(corpus)
           .map{case(id, vector) => (id :: vector).mkString(",")}
           .saveAsTextFile(props.getString("lda.vectors"))

       case _ =>
     }
  }
  def createOptimizer(params: LDAParams, actualCorpusSize: Long) = {
    params.algorithm.toLowerCase match {
      case "em" => new EMLDAOptimizer
      // add (1.0 / actualCorpusSize) to MiniBatchFraction be more robust on tiny datasets.
      case "online" => new OnlineLDAOptimizer().setMiniBatchFraction(0.05 + 1.0 / actualCorpusSize)
      case _ => throw new IllegalArgumentException(
        s"Only em, online are supported but got ${params.algorithm}.")
    }
  }

  def dataPrePreprocessing(sc: SparkContext, paths: String):(RDD[(Long, Vector)], Array[String]) = {
    val data = sc.textFile(getClass.getResource(paths).getPath)
    val terms = data.filter(str => str.startsWith("%"))
      .flatMap(str => str.split("\\W+").tail).collect()

    val corpus = data.filter(s => !s.startsWith("#") && !s.startsWith("%"))
      .map(_.trim.split(',') match {
        case Array(id, mxmId, tail@ _*) =>
          mxmId.toLong -> {
            val features = tail.map(_.split(":") match {
              case Array(termNum, termCount) => termNum.toInt -> termCount.toDouble
            }).toMap
            Vectors.dense(terms.zipWithIndex map{case(term, i) => features.getOrElse(i + 1, 0.0)})
          }
      }).cache()
    (corpus, terms)
  }
}
