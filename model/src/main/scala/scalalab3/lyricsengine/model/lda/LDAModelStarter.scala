package main.scala.scalalab3.lyricsengine.model.lda


import scopt.OptionParser

import scala.language.postfixOps
import scalalab3.lyricsengine.model.lda.AbstractParams
import scalalab3.lyricsengine.model.lda.LDAModelBuilder.execute

case class LDAParams(
                      input: String = "/mxm_dataset_test.txt",
                      k: Int = 20,
                      maxIterations: Int = 10,
                      vocabSize: Int = 100000,
                      algorithm: String = "online") extends AbstractParams[LDAParams]

object LDAModelStarter extends Serializable {
  def main(args: Array[String]): Unit = {
    val defaultParams = LDAParams()

    val parser = new OptionParser[LDAParams]("LDAJob") {
      head("lda")
      opt[Int]("k")
        .text(s"number of topics. default: ${
          defaultParams.k
        }")
        .action((x, c) => c.copy(k = x))
      opt[Int]("maxIterations")
        .text(s"number of iterations of learning. default: ${
          defaultParams.maxIterations
        }")
        .action((x, c) => c.copy(maxIterations = x))
      opt[Int]("vocabSize")
        .text(s"number of distinct word types to use, chosen by frequency. (-1=all)" +
          s"  default: ${
            defaultParams.vocabSize
          }")
        .action((x, c) => c.copy(vocabSize = x))
      opt[String]("algorithm")
        .text(s"inference algorithm to use. em and online are supported." +
          s" default: ${
            defaultParams.algorithm
          }")
        .action((x, c) => c.copy(algorithm = x))
      opt[String]("input")
        .text(s"input path (directories) to plain text corpora." +
          s" default: ${
            defaultParams.input
          }")
        .action((x, c) => c.copy(input = x))
      help("help") text("prints this usage text")
    }
    parser.parse(args, defaultParams) map execute getOrElse {
      parser.showUsageAsError
      sys.exit(1)
    }
  }


}