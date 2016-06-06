package scalalab3.lyricsengine.model.lda

import scopt.OptionParser

import scala.language.postfixOps
import scalalab3.lyricsengine.model.lda.LDAModelBuilder.execute

case class LDAParams(
                      dataFile: String = "/mxm_dataset_train.txt",
                      stemmedFile: String = "/stemmed/stemmed_words.txt",
                      stopWordsDirectory: String = "/stopwords/",
                      k: Int = 5,
                      maxIterations: Int = 10,
                      docConcentration: Double = -1,
                      topicConcentration: Double = -1,
                      algorithm: String = "online") extends AbstractParams[LDAParams]

object LDAModelStarter extends Serializable {
  def main(args: Array[String]): Unit = {
    val defaultParams = LDAParams()

    val parser = new OptionParser[LDAParams]("LDAJob") {
      head("lda")

      opt[String]("dataFile")
        .text(s"input path to data file."
          + s" default: ${defaultParams.dataFile}")
        .action((x, c) => c.copy(dataFile = x))

      opt[String]("stemmedFile")
        .text(s"input path to stemmed dictionary."
          + s" default: ${defaultParams.stemmedFile}")
        .action((x, c) => c.copy(stemmedFile = x))

      opt[String]("stopWordsDirectory")
        .text(s"stop words directory"
          + s" default: ${defaultParams.stopWordsDirectory}")
        .action((x, c) => c.copy(stopWordsDirectory = x))

      opt[Int]("k")
        .text(s"number of topics. default: ${defaultParams.k}")
        .action((x, c) => c.copy(k = x))

      opt[Int]("maxIterations")
        .text(s"number of iterations of learning. default: ${defaultParams.maxIterations}")
        .action((x, c) => c.copy(maxIterations = x))

      opt[Double]("docConcentration")
        .text(s"amount of topic smoothing to use (> 1.0) (-1=auto)." +
          s"  default: ${defaultParams.docConcentration}")
        .action((x, c) => c.copy(docConcentration = x))

      opt[Double]("topicConcentration")
        .text(s"amount of term (word) smoothing to use (> 1.0) (-1=auto)." +
          s"  default: ${defaultParams.topicConcentration}")
        .action((x, c) => c.copy(topicConcentration = x))

      opt[String]("algorithm")
        .text(s"inference algorithm to use. em and online are supported." +
          s" default: ${defaultParams.algorithm}")
        .action((x, c) => c.copy(algorithm = x))

      help("help") text "prints this usage text"
    }
    parser.parse(args, defaultParams) map execute getOrElse {
      parser.showUsageAsError
      sys.exit(1)
    }
  }
}