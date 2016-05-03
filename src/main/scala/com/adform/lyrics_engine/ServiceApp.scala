package com.adform.lyrics_engine


import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

/**
  * Created by atomkevich on 5/1/16.
  */


object ServiceApp extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  private val config = ConfigFactory.load()

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}