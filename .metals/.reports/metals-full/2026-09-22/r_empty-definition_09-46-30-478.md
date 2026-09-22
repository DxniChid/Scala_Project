error id: file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala:scala/package.Vector#
file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala
empty definition using pc, found symbol in pc: scala/package.Vector#
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -Vector#
	 -scala/Predef.Vector#
offset: 266
uri: file:///C:/Users/chidi/Desktop/Scala_Project/model/Deck.scala
text:
```scala
package model

import scala.util.Random

object Deck:

  val standard: Vector[Card] =
    for
      suit <- Suit.values.toVector
      rank <- Rank.values.toVector
    yield Card(rank, suit)

  def size: Int =
    standard.size

  def shuffled: Vector@@[Card] =
    Random.shuffle(standard)
```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/package.Vector#