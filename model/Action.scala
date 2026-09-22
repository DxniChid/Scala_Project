package model

enum Action:
  case Fold
  case Check
  case Call
  case Bet(amount: Int)
  case Raise(amount: Int)