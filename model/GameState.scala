package model

enum GamePhase:
  case PreFlop
  case Flop
  case Turn
  case River
  case Showdown
  case Finished

case class GameState(
    players: Vector[Player],
    deck: Vector[Card],
    communityCards: Vector[Card],
    pot: Int,
    currentPlayer: Int,
    phase: GamePhase
):

  def currentPlayerData: Player =
    players(currentPlayer)

  def updatePlayer(updatedPlayer: Player): GameState =
    copy(
      players = players.updated(currentPlayer, updatedPlayer)
    )

  def addToPot(amount: Int): GameState =
    copy(
      pot = pot + amount
    )

  def addCommunityCard(card: Card): GameState =
    copy(
      deck = deck.tail,
      communityCards = communityCards :+ card
    )

  def nextPlayer: GameState =
    copy(
      currentPlayer =
        (currentPlayer + 1) % players.size
    )