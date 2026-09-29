error id: file:///C:/Users/chidi/Desktop/Scala_Project/Main.scala:
file:///C:/Users/chidi/Desktop/Scala_Project/Main.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -model/println.
	 -model/println#
	 -model/println().
	 -println.
	 -println#
	 -println().
	 -scala/Predef.println.
	 -scala/Predef.println#
	 -scala/Predef.println().
offset: 377
uri: file:///C:/Users/chidi/Desktop/Scala_Project/Main.scala
text:
```scala
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

  Deck.shuffled.take(5).foreach(card => pri@@ntln(card))
```


#### Short summary: 

empty definition using pc, found symbol in pc: 