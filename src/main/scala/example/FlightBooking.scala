package example

import akka.actor.Actor.Receive
import akka.actor.{ActorSystem, ActorRef, Props, Actor}


trait FlightDetails
trait CreditCardDetails
trait Bookings


object FlightBooking {

  def props = Props(new FlightBooking)
  //message protocol
  case class MakeNewBooking(flightDetails: FlightDetails,
                            cardDetails: CreditCardDetails)
}


class FlightBooking extends Actor {

  import FlightBooking._


  override def receive: Receive = {
    case MakeNewBooking(flightDetails, cardDetails) =>
      makeBooking()
  }

  private def makeBooking() = {
    println(">>>> making new booking")
  }
}





object Main extends App {

  val system = ActorSystem("example")
  val ref: ActorRef = system.actorOf(FlightBooking.props, "flight-booking")

  ref ! FlightBooking.MakeNewBooking(new FlightDetails {}, new CreditCardDetails {})


  scala.io.StdIn.readLine()

  system.shutdown()

  system.awaitTermination()



}










