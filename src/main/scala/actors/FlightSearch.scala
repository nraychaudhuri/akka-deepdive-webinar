package actors

import akka.actor.{Props, ActorRef, Actor}
import java.util.Date
import akka.routing.{ConsistentHashingPool, ConsistentHashingGroup}

import akka.routing.ConsistentHashingRouter.ConsistentHashMapping
import models.{City, Deal}
import scala.collection.immutable.Iterable

object FlightSearch {

  case class FindTrip(from: City,
                                to: City,
                                startDate: Date,
                                endDate: Date)
  def props = Props(new FlightSearch)
}


class FlightSearch extends Actor {

  import FlightSearch._

  val router = context.actorOf(ConsistentHashingPool(4,
    hashMapping = byCountry).props(SearchWorker.props),
    name="search-router")

  override def receive = {
    case FindTrip(from, to, startDate, endDate) =>
      router.forward(SearchWorker.FindCheapestFlight(from, to, startDate))
      router.forward(SearchWorker.FindCheapestFlight(to, from, endDate))
  }

  def byCountry: ConsistentHashMapping = {
    case f: SearchWorker.FindCheapestFlight => f.to.countryCode
  }

}
