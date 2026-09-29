package model

enum Street:
  case PreFlop
  case Flop
  case Turn
  case River
  case Showdown

case class GameState(
    players: Vector[Player],
    deck: Vector[Card],
    communityCards: Vector[Card],
    pot: Int,
    currentPlayerIndex: Int,
    street: Street
):
  def currentPlayer: Player =
    players(currentPlayerIndex)

  def activePlayers: Vector[Player] =
    players.filterNot(_.folded)

  def updatePlayer(updatedPlayer: Player): GameState =
    copy(
      players =
        players.map { player =>
          if player.id == updatedPlayer.id then
            updatedPlayer
          else
            player
        }
    )