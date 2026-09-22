error id: file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala:scala/Int#
file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala
empty definition using pc, found symbol in pc: scala/Int#
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -Int#
	 -scala/Predef.Int#
offset: 172
uri: file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala
text:
```scala
package model

object Deck:

  val standard: Vector[Card] =
    for
      suit <- Suit.values
      rank <- Rank.values
    yield Card(rank, suit)

  def size: In@@t =
    standard.size
```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/Int#