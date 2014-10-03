package com.example

import actors.FlightSearch
import akka.actor.ActorSystem
import models.City

object Hello {
  def main1(args: Array[String]): Unit = {
    val system = ActorSystem("webinar")

    val search = system.actorOf(FlightSearch.props)

    search ! FlightSearch.FindTrip(City("berlin", "DE"), City("london", "UK"),
      new java.util.Date, new java.util.Date)


    Console.readLine()

    system.shutdown()
    system.awaitTermination()
  }
}
