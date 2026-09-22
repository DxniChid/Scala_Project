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
  println("Shuffled Deck: First 5 cards:")

  Deck.shuffled.take(5).foreach(card => println(card))