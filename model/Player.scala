package model

case class Player(
    id: Int,
    name: String,
    chips: Int,
    hand: Vector[Card] = Vector.empty,
    folded: Boolean = false
):
  def receiveCards(cards: Vector[Card]): Player =
    copy(hand = cards)

  def bet(amount: Int): Player =
    require(amount >= 0, "Bet amount cannot be negative")
    require(amount <= chips, "Player cannot bet more chips than they have")

    copy(chips = chips - amount)

  def fold: Player =
    copy(folded = true)