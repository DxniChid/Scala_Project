package model

enum Suit:
  case Clubs
  case Diamonds
  case Hearts
  case Spades

enum Rank:
  case Two
  case Three
  case Four
  case Five
  case Six
  case Seven
  case Eight
  case Nine
  case Ten
  case Jack
  case Queen
  case King
  case Ace

case class Card(
    rank: Rank,
    suit: Suit
):
  override def toString: String =
    s"${rank.symbol}${suit.symbol}"

object Rank:

  extension (rank: Rank)
    def symbol: String =
      rank match
        case Rank.Two   => "2"
        case Rank.Three => "3"
        case Rank.Four  => "4"
        case Rank.Five  => "5"
        case Rank.Six   => "6"
        case Rank.Seven => "7"
        case Rank.Eight => "8"
        case Rank.Nine  => "9"
        case Rank.Ten   => "10"
        case Rank.Jack  => "J"
        case Rank.Queen => "Q"
        case Rank.King  => "K"
        case Rank.Ace   => "A"

object Suit:

  extension (suit: Suit)
    def symbol: String =
      suit match
        case Suit.Clubs    => "♣"
        case Suit.Diamonds => "♦"
        case Suit.Hearts   => "♥"
        case Suit.Spades   => "♠"