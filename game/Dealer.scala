package game

import model.*

object Dealer:

  def dealHoleCards(
      players: Vector[Player],
      deck: Vector[Card]
  ): (Vector[Player], Vector[Card]) =

    val (updatedPlayers, remainingDeck) =
      players.foldLeft((Vector.empty[Player], deck)) {
        case ((currentPlayers, currentDeck), player) =>

          val holeCards = currentDeck.take(2)
          val restOfDeck = currentDeck.drop(2)

          val updatedPlayer =
            player.receiveCards(holeCards)

          (currentPlayers :+ updatedPlayer, restOfDeck)
      }

    (updatedPlayers, remainingDeck)

  def dealFlop(
      deck: Vector[Card]
  ): (Vector[Card], Vector[Card]) =

    val cards = deck.take(3)
    val remainingDeck = deck.drop(3)

    (cards, remainingDeck)

  def dealTurn(
      deck: Vector[Card]
  ): (Card, Vector[Card]) =

    val card = deck.head
    val remainingDeck = deck.tail

    (card, remainingDeck)

  def dealRiver(
      deck: Vector[Card]
  ): (Card, Vector[Card]) =

    val card = deck.head
    val remainingDeck = deck.tail

    (card, remainingDeck)