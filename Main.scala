import model.*
import game.BettingRound

@main
def main(): Unit =

  val player1 =
    Player(
      id = 1,
      name = "You",
      chips = 1000
    )

  val player2 =
    Player(
      id = 2,
      name = "Bot",
      chips = 1000
    )

  val state =
    GameState(
      players = Vector(player1, player2),
      deck = Deck.standard,
      communityCards = Vector.empty,
      pot = 0,
      currentPlayerIndex = 0,
      street = Street.PreFlop
    )

  println(s"Before: ${state.currentPlayer.name}")
  println(s"Chips: ${state.currentPlayer.chips}")
  println(s"Pot: ${state.pot}")

  val newState =
    BettingRound.applyAction(
      state,
      Action.Raise(100)
    )

  println()
  println(s"After:")
  println(s"Pot: ${newState.pot}")
  println(s"You's chips: ${newState.players.head.chips}")
  println(s"Next player: ${newState.currentPlayer.name}")