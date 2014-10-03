package actors

import java.util.Date

import akka.actor.SupervisorStrategy.{Decider, Resume}
import akka.actor._
import akka.routing.{FromConfig, BroadcastGroup, BroadcastPool}

import models._

object SearchWorker {

  case class FindCheapestFlight(from: City, to: City, date: Date)

  def props = Props(new SearchWorker)
}

class SearchWorker extends Actor {

  import SearchWorker._

  var noOfDealsByCountry: Int = 0

  var cachedDeals = ???

  var paths: List[String] = List.empty

  val proxies = context.actorOf(BroadcastGroup(paths).props(), "provider-proxies")


  val customDecider: Decider = {
    case p: ProviderProxy.ProviderException => Resume
    case t: Throwable => SupervisorStrategy.defaultDecider(t)
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy()(customDecider)

  override def receive = {
    case FindCheapestFlight(from, to, date) =>
      val cheapestDealFinder = context.actorOf(FindCheapestDeal.props(sender(), 10))
      proxies.tell(ProviderProxy.FindFlight(from, to, date), cheapestDealFinder)
  }
}



object FindCheapestDeal {

  def props(replyTo: ActorRef, count: Int) = Props(new FindCheapestDeal(replyTo, count))
}

class FindCheapestDeal(replyTo: ActorRef, count: Int) extends Actor {

  override def receive: Receive = {
    case ProviderProxy.FlightDetails(from, to, date, price) =>
  }
}
