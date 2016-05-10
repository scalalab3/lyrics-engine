package main.scala.scalalab3.lyricsengine.model.lda

/**
  * Created by atomkevich on 5/6/16.
  */
import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.clustering._
import org.apache.spark.{SparkContext, SparkConf}
import scopt.OptionParser

import scala.language.postfixOps
import scalalab3.lyricsengine.model.lda.AbstractParams

case class LDAParams(
                      input: String = "/mxm_dataset_test.txt",
                      k: Int = 20,
                      maxIterations: Int = 10,
                      vocabSize: Int = 100000,
                      algorithm: String = "online") extends AbstractParams[LDAParams]

object LDAModelBuilder extends App with LDAService with LDAComponentImpl
  with TrackVectorRepositoryComponentImpl with TermRepositoryComponentImpl with Serializable {
  val defaultParams = LDAParams()

  val parser = new OptionParser[LDAParams]("LDAJob") {
    opt[Int]("k")
      .text(s"number of topics. default: ${defaultParams.k}")
      .action((x, c) => c.copy(k = x))
    opt[Int]("maxIterations")
      .text(s"number of iterations of learning. default: ${defaultParams.maxIterations}")
      .action((x, c) => c.copy(maxIterations = x))
    opt[Int]("vocabSize")
      .text(s"number of distinct word types to use, chosen by frequency. (-1=all)" +
        s"  default: ${defaultParams.vocabSize}")
      .action((x, c) => c.copy(vocabSize = x))
    opt[String]("algorithm")
      .text(s"inference algorithm to use. em and online are supported." +
        s" default: ${defaultParams.algorithm}")
      .action((x, c) => c.copy(algorithm = x))
    opt[String]("input")
      .text(s"input path (directories) to plain text corpora." +
        s" default: ${defaultParams.input}")
      .action((x, c) => c.copy(input = x))
  }

  parser.parse(args, defaultParams) map  execute getOrElse {
    parser.showUsageAsError
    sys.exit(1)
  }


  private def execute(params: LDAParams) {
    Logger.getRootLogger.setLevel(Level.WARN)
    val conf = new SparkConf().setAppName(s"LDAJob with $params").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val props = ConfigFactory.load()

    // Load and parse the data
    val (corpus, terms) = preprocess(sc, params.input)

    val ldaModel = new LDA()
      .setK(3)
      .setOptimizer(createOptimizer(params, corpus.count()))
      .setK(params.k)
      .setMaxIterations(params.maxIterations)
      .run(corpus)

    printLDAModel(ldaModel, terms, params.k)

    // Save and load model.
    ldaModel.save(sc, props.getString("lda.model"))
    if (ldaModel.isInstanceOf[LocalLDAModel]){
      (ldaModel.asInstanceOf[LocalLDAModel]).topicDistributions(corpus)
        .foreach({case(id, vector) => ldaService.saveTrackVector(id, vector)})
    }

    ldaService.saveTerms(terms)
  }
}