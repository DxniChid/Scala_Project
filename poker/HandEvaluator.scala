package poker

import model.*

case class EvaluatedHand(
    rank: HandRank,
    values: List[Int],
    cards: List[Card]
)

object HandEvaluator:

  def evaluate(cards: List[Card]): EvaluatedHand =
    require(cards.size == 5, "Exactly 5 cards are required")

    val sortedCards =
      cards.sortBy(card => rankValue(card.rank)).reverse

    val values =
      sortedCards.map(card => rankValue(card.rank))

    val grouped =
      values
        .groupBy(identity)
        .view
        .mapValues(_.size)
        .toMap

    val counts =
      grouped.values.toList.sorted.reverse

    val flush =
      cards.map(_.suit).distinct.size == 1

    val straight =
      isStraight(values)

    val straightHigh =
      straightHighCard(values)

    if flush && straightHigh == 14 then
      EvaluatedHand(
        HandRank.RoyalFlush,
        List(14),
        sortedCards
      )

    else if flush && straight then
      EvaluatedHand(
        HandRank.StraightFlush,
        List(straightHigh),
        sortedCards
      )

    else if counts == List(4, 1) then
      val four =
        grouped.find(_._2 == 4).map(_._1).get

      val kicker =
        grouped.find(_._2 == 1).map(_._1).get

      EvaluatedHand(
        HandRank.FourOfAKind,
        List(four, kicker),
        sortedCards
      )

    else if counts == List(3, 2) then
      val three =
        grouped.find(_._2 == 3).map(_._1).get

      val pair =
        grouped.find(_._2 == 2).map(_._1).get

      EvaluatedHand(
        HandRank.FullHouse,
        List(three, pair),
        sortedCards
      )

    else if flush then
      EvaluatedHand(
        HandRank.Flush,
        values,
        sortedCards
      )

    else if straight then
      EvaluatedHand(
        HandRank.Straight,
        List(straightHigh),
        sortedCards
      )

    else if counts == List(3, 1, 1) then
      val three =
        grouped.find(_._2 == 3).map(_._1).get

      val kickers =
        grouped
          .filter(_._2 == 1)
          .keys
          .toList
          .sorted
          .reverse

      EvaluatedHand(
        HandRank.ThreeOfAKind,
        three :: kickers,
        sortedCards
      )

    else if counts == List(2, 2, 1) then
      val pairs =
        grouped
          .filter(_._2 == 2)
          .keys
          .toList
          .sorted
          .reverse

      val kicker =
        grouped
          .find(_._2 == 1)
          .map(_._1)
          .get

      EvaluatedHand(
        HandRank.TwoPair,
        pairs ++ List(kicker),
        sortedCards
      )

    else if counts == List(2, 1, 1, 1) then
      val pair =
        grouped.find(_._2 == 2).map(_._1).get

      val kickers =
        grouped
          .filter(_._2 == 1)
          .keys
          .toList
          .sorted
          .reverse

      EvaluatedHand(
        HandRank.OnePair,
        pair :: kickers,
        sortedCards
      )

    else
      EvaluatedHand(
        HandRank.HighCard,
        values,
        sortedCards
      )

  def bestHand(cards: List[Card]): EvaluatedHand =

    require(
      cards.size >= 5,
      "At least 5 cards are required"
    )

    val combinations =
      Combinations.choose(cards, 5)

    combinations
      .map(evaluate)
      .sortBy { hand =>
        val valueString =
          hand.values
            .map(value => f"$value%02d")
            .mkString

        (hand.rank.strength, valueString)
      }
      .last

  private def rankValue(rank: Rank): Int =
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

  private def isStraight(values: List[Int]): Boolean =
    val uniqueValues =
      values.distinct.sorted

    val normalStraight =
      uniqueValues.size == 5 &&
        uniqueValues.last - uniqueValues.head == 4

    val aceLowStraight =
      uniqueValues == List(2, 3, 4, 5, 14)

    normalStraight || aceLowStraight

  private def straightHighCard(values: List[Int]): Int =
    val uniqueValues =
      values.distinct.sorted

    if uniqueValues == List(2, 3, 4, 5, 14) then
      5
    else
      uniqueValues.last