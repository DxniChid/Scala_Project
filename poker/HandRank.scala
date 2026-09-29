package poker

enum HandRank(val strength: Int):

  case HighCard extends HandRank(1)
  case OnePair extends HandRank(2)
  case TwoPair extends HandRank(3)
  case ThreeOfAKind extends HandRank(4)
  case Straight extends HandRank(5)
  case Flush extends HandRank(6)
  case FullHouse extends HandRank(7)
  case FourOfAKind extends HandRank(8)
  case StraightFlush extends HandRank(9)
  case RoyalFlush extends HandRank(10)