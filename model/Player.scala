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

    def value: Int =
      rank match
        case Rank.Two   => 2
        case Rank.Three => 3
        case Rank.Four  => 4
        case Rank.Five  => 5
        case Rank.Six   => 6
        case Rank.Seven => 7
        case Rank.Eight => 8
        case Rank.Nine  => 9
        case Rank.Ten   => 10
        case Rank.Jack  => 11
        case Rank.Queen => 12
        case Rank.King  => 13
        case Rank.Ace   => 14