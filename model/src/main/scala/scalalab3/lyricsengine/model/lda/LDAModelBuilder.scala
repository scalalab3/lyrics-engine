package scalalab3.lyricsengine.model.lda

import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.clustering._
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object LDAModelBuilder {
  implicit def VectorToList(vector: Vector) = vector.toArray.toList

  def execute(params: LDAParams) {
    Logger.getRootLogger.setLevel(Level.INFO)
    val props = ConfigFactory.load()
    val conf = new SparkConf().setAppName(s"LDAJob with $params")
      .setMaster(props.getString("spark.master"))
    val sc = new SparkContext(conf)

    //load data
    val data = loadData(sc, params.dataFile)
    val terms = data.filter(str => str.startsWith("%"))
      .flatMap(str => str.split("\\W+").tail).collect()

    //load stemmed words
    val stemmedData = loadData(sc, params.stemmedFile).collect()
    val stemmedMap = stemmedData.map(_.trim.split("\\t")).map(x => (x(0), x(1))).toMap

    //load stopped words
    val stopWords = loadStopWords(sc, params.stopWordsDirectory)
    val stemmedStopWords = toStemmedList(stopWords, stemmedMap)
    val stopWordsIndexes = stemmedStopWords.map(terms.indexOf).filter(x => x != -1)

    //create term map
    val filteredTermsMap = terms.zipWithIndex.filter(x => !stopWordsIndexes.contains(x._2)).map{case (a,b)=> b -> a }.toMap

    // parse the data
    val (corpus, words) = dataPreprocessing(sc, data, filteredTermsMap)

    val ldaModel = new LDA()
      .setOptimizer(createOptimizer(params, corpus.count()))
      .setK(params.k)
      .setDocConcentration(params.docConcentration)
      .setTopicConcentration(params.topicConcentration)
      .setMaxIterations(params.maxIterations)
      .run(corpus)

    // save model
    ldaModel.save(sc, props.getString("lda.model"))
    ldaModel match {
      case model: LocalLDAModel =>
        model
          .topicDistributions(corpus)
          .map { case (id, vector) => (id :: vector).mkString(",") }
          .saveAsTextFile(props.getString("lda.vectors"))

      case _ =>
    }
  }

  def createOptimizer(params: LDAParams, actualCorpusSize: Long) = {
    params.algorithm.toLowerCase match {
      case "em" => new EMLDAOptimizer
      case "online" => new OnlineLDAOptimizer().setMiniBatchFraction(0.05 + 1.0 / actualCorpusSize)
      case _ => throw new IllegalArgumentException(
        s"Only em, online are supported but got ${params.algorithm}.")
    }
  }

  def dataPreprocessing(sc: SparkContext, data: RDD[String], termsMap: Map[Int, String]): (RDD[(Long, Vector)], Array[String]) = {
    val termsArray = termsMap.map { case (i, term) => term }.toArray

    val corpus = data.filter(s => !s.startsWith("#") && !s.startsWith("%"))
      .map(_.trim.split(',') match {
        case Array(id, mxmId, tail@_*) =>
          mxmId.toLong -> {
            val features = tail.map(_.split(":").filter(x => !termsMap.contains(x(0).toInt)) match {
              case Array(termNum, termCount) =>
                  termNum.toInt -> termCount.toDouble
            }).toMap
            Vectors.dense(termsArray.zipWithIndex map { case (term, i) => features.getOrElse(i + 1, 0.0) })
          }
      }).cache()
    (corpus, termsArray)
  }

  private def loadData(sc: SparkContext, path: String) =
    sc.textFile(getClass.getResource(path).getPath)

  private def loadStopWords(sc: SparkContext, path: String): Seq[String] =
    sc.wholeTextFiles(getClass.getResource(path).getPath).map(_._2).flatMap(_.toLowerCase.split("\\s")).collect()


  private def mergeTuples(first: Seq[(Int, Double)], second: Seq[(Int, Double)]) : Seq[(Int, Double)] =
    (first ++ second).groupBy(_._1).map{
      case (key,tuples) => (key, tuples.map(_._2).sum)
    }.toSeq

  private def toStemmedList(list: Seq[String], stemmedMap: Map[String, String]) : Seq[String] =
    list.filter(stemmedMap.contains).map(stemmedMap(_))
}