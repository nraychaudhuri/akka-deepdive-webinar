package models



case class City(name: String, countryCode: String)

object Deal {

  def loadGermanDeals(): Set[Deal] = Set.empty[Deal]
  def loadUSDeals(): Set[Deal]  = Set.empty[Deal]
  def loadUkDeals(): Set[Deal]  = Set.empty[Deal]
  def noDeals(): Set[Deal]  = Set.empty[Deal]
}

class Deal
