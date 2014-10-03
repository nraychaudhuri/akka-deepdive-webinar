package actors

import java.util.Date

import akka.actor.{Props, Actor}
import models.City

object ProviderProxy {

  def props = Props(new ProviderProxy)

  case class FindFlight(from: City, to: City, date: Date)
  case class FlightDetails(from: City, to: City, date: Date, price: BigDecimal)

  //exception
  case class ProviderException(m: String) extends RuntimeException(m)

}

class ProviderProxy extends Actor {

  import ProviderProxy._

  override def receive = {
    case FindFlight(from, to, date) =>
      //search flight
      val price = findFlight()
      sender() ! FlightDetails(from, to, date, price)
  }

  private def findFlight(): BigDecimal = ???
}
