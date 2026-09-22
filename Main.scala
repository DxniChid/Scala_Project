import model.*

@main
def main(): Unit =
  println("====================================")
  println("        TEXAS HOLD'EM")
  println("        Poker Simulator")
  println("====================================")
  println()

  println(s"Deck size: ${Deck.size}")
  println()
  println("First 5 cards:")

  Deck.standard.take(5).foreach(card => println(card))