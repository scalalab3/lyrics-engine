package scalalab3.lyricsengine.system

import com.typesafe.config.ConfigFactory


trait ProjectConfig {
  val conf = ConfigFactory.load()
  // Config variable:
  // ---- Million Song Dataset data:
  val PATH_TO_MSD_SUBSET_VAR_NAME = "msd.pathToMsd"
  // ---- MongoDB:
  val MONGODB_HOST_VAR_NAME = "mongoDb.host"
  val MONGODB_PORT_VAR_NAME = "mongoDb.port"
  val MONGODB_DB_NAME_VAR_NAME = "mongoDb.dbName"


  def getPathToData() = conf.getString(PATH_TO_MSD_SUBSET_VAR_NAME)

  def getMongoDbHost() = conf.getString(MONGODB_HOST_VAR_NAME)

  def getMongoDbPort() = conf.getInt(MONGODB_PORT_VAR_NAME)

  def getMongoDbName() = conf.getString(MONGODB_DB_NAME_VAR_NAME)
}
