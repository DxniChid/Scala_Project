error id: file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala:
file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -Card#
	 -scala/Predef.Card#
offset: 56
uri: file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala
text:
```scala
package model

object Deck:

  val standard: Vector[@@Card] =
    for
      suit <- Suit.values
      rank <- Rank.values
    yield Card(rank, suit)

  def size: Int =
    standard.size
```


#### Short summary: 

empty definition using pc, found symbol in pc: 