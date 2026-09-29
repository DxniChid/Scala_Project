package model

enum Action:
  case Fold
  case Check
  case Call
  case Raise(amount: Int)
  case AllIn