package game

import model.*

object BettingRound:

  def applyAction(
      state: GameState,
      action: Action
  ): GameState =

    val player = state.currentPlayer
    val playerCurrentBet = state.playerBet(player.id)

    action match

      case Action.Fold =>
        val updatedPlayer = player.fold

        state
          .updatePlayer(updatedPlayer)
          .copy(
            currentPlayerIndex =
              nextPlayerIndex(state)
          )

      case Action.Check =>
        require(
          playerCurrentBet == state.currentBet,
          "Cannot check when there is a bet to call"
        )

        state.copy(
          currentPlayerIndex =
            nextPlayerIndex(state)
        )

      case Action.Call =>
        val amountToCall =
          calculateCallAmount(state)

        val actualAmount =
          amountToCall.min(player.chips)

        val updatedPlayer =
          player.bet(actualAmount)

        state
          .updatePlayer(updatedPlayer)
          .copy(
            pot = state.pot + actualAmount,
            playerBets =
              state.playerBets.updated(
                player.id,
                playerCurrentBet + actualAmount
              ),
            currentPlayerIndex =
              nextPlayerIndex(state)
          )

      case Action.Raise(amount) =>
        require(amount > 0, "Raise must be greater than zero")
        require(amount <= player.chips, "Not enough chips")

        val newPlayerBet =
          playerCurrentBet + amount

        require(
          newPlayerBet > state.currentBet,
          "Raise must increase the current bet"
        )

        val updatedPlayer =
          player.bet(amount)

        state
          .updatePlayer(updatedPlayer)
          .copy(
            pot = state.pot + amount,
            currentBet = newPlayerBet,
            playerBets =
              state.playerBets.updated(
                player.id,
                newPlayerBet
              ),
            currentPlayerIndex =
              nextPlayerIndex(state)
          )

      case Action.AllIn =>
        val amount = player.chips
        val newPlayerBet =
          playerCurrentBet + amount

        val updatedPlayer =
          player.bet(amount)

        state
          .updatePlayer(updatedPlayer)
          .copy(
            pot = state.pot + amount,
            currentBet =
              state.currentBet.max(newPlayerBet),
            playerBets =
              state.playerBets.updated(
                player.id,
                newPlayerBet
              ),
            currentPlayerIndex =
              nextPlayerIndex(state)
          )

  private def calculateCallAmount(
      state: GameState
  ): Int =

    val player = state.currentPlayer

    val playerCurrentBet =
      state.playerBet(player.id)

    (state.currentBet - playerCurrentBet).max(0)

  private def nextPlayerIndex(
      state: GameState
  ): Int =

    (state.currentPlayerIndex + 1) % state.players.size