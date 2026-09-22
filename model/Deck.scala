package model

import scala.util.Random

object Deck:

  val standard: Vector[Card] =
    for
      suit <- Suit.values.toVector
      rank <- Rank.values.toVector
    yield Card(rank, suit)

  def size: Int =
    standard.size

  def shuffled: Vector[Card] =
    Random.shuffle(standard)