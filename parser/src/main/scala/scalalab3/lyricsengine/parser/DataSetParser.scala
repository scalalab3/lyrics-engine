package scalalab3.lyricsengine.parser

import scalalab3.lyricsengine.domain.{DataSet, Song, WordsDefinition}

/**
  * @author Vlad Fefelov
  */
object DataSetParser {

  def parse(source: Seq[String]): DataSet = DataSet(definitions(source), songs(source))

  private def definitions(source: Seq[String]): WordsDefinition = source
    .filterNot(_.startsWith("#"))
    .filter(_.startsWith("%")).map(_.tail)
    .flatMap(_.split(","))
    .zipWithIndex
    .map(pair => (pair._2 + 1, pair._1))
    .toMap

  private def songs(source: Seq[String]): Seq[Song] = source
    .filterNot(_.startsWith("#"))
    .filterNot(_.startsWith("%"))
    .map(_.split(","))
    .map(line => Song(line(0), line(1), wordFrequencies(line.slice(2, line.length))))

  private def wordFrequencies(source: Seq[String]): Map[Int, Int] = source
    .map(_.split(":").toIndexedSeq)
    .map(pair => (pair(0).toInt, pair(1).toInt))
    .toMap
}
