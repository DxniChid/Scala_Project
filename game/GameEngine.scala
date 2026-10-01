package game

import model.*

object GameEngine:

  def createGame(
      playerNames: Vector[String],
      startingChips: Int
  ): GameState =

    require(playerNames.nonEmpty, "At least one player is required")
    require(startingChips > 0, "Starting chips must be greater than zero")

    val players =
      playerNames.zipWithIndex.map { case (name, index) =>
        Player(
          id = index,
          name = name,
          chips = startingChips
        )
      }

    GameState(
      players = players,
      deck = Deck.shuffled,
      communityCards = Vector.empty,
      pot = 0,
      currentPlayerIndex = 0,
      street = Street.PreFlop
    )

  def dealHoleCards(
      state: GameState
  ): GameState =

    val (players, remainingDeck) =
      Dealer.dealHoleCards(
        state.players,
        state.deck
      )

    state.copy(
      players = players,
      deck = remainingDeck
    )

  def dealFlop(
      state: GameState
  ): GameState =

    val (flop, remainingDeck) =
      Dealer.dealFlop(state.deck)

    state.copy(
      deck = remainingDeck,
      communityCards =
        state.communityCards ++ flop,
      street = Street.Flop
    )

  def dealTurn(
      state: GameState
  ): GameState =

    val (card, remainingDeck) =
      Dealer.dealTurn(state.deck)

    state.copy(
      deck = remainingDeck,
      communityCards =
        state.communityCards :+ card,
      street = Street.Turn
    )

  def dealRiver(
      state: GameState
  ): GameState =

    val (card, remainingDeck) =
      Dealer.dealRiver(state.deck)

    state.copy(
      deck = remainingDeck,
      communityCards =
        state.communityCards :+ card,
      street = Street.River
    )

  def finishRound(
      state: GameState
  ): GameState =

    state.copy(
      street = Street.Showdown
    )