package ui

import game.*
import model.*
import poker.*

import scala.util.Random

object ConsoleUI:

  def start(): Unit =

    println("================================")
    println("       TEXAS HOLD'EM")
    println("================================")

    val playerCount =
      readInt("Number of players (2-4): ", 2, 4)

    val names =
      Vector("You") ++
        (2 to playerCount).map(i => s"Player $i")

    var chips = 1000
    var playAgain = true

    while playAgain && chips > 0 do

      println()
      println("================================")
      println("          NEW ROUND")
      println("================================")

      var state =
        GameEngine.createGame(
          names,
          chips
        )

      state =
        GameEngine.dealHoleCards(state)

      println()
      println("Your cards:")
      println(
        state.players.head.hand.mkString(" ")
      )

      println()
      println("=== PRE-FLOP ===")

      state =
        playBettingRound(state)

      if state.activePlayers.size > 1 then

        state =
          GameEngine.dealFlop(state)

        println()
        println("=== FLOP ===")
        println(
          s"Community cards: ${state.communityCards.mkString(" ")}"
        )

        state =
          playBettingRound(state)

      if state.activePlayers.size > 1 then

        state =
          GameEngine.dealTurn(state)

        println()
        println("=== TURN ===")
        println(
          s"Community cards: ${state.communityCards.mkString(" ")}"
        )

        state =
          playBettingRound(state)

      if state.activePlayers.size > 1 then

        state =
          GameEngine.dealRiver(state)

        println()
        println("=== RIVER ===")
        println(
          s"Community cards: ${state.communityCards.mkString(" ")}"
        )

        state =
          playBettingRound(state)

      println()
      println("=== SHOWDOWN ===")

      val finalState =
        awardPot(state)

      showResults(finalState)

      chips =
        finalState.players.head.chips

      println()
      println(s"You have $chips Chips left.")

      if chips <= 0 then

        println(
          "Uh-Oh! You dont have anymore Chips, you lost!"
        )

        playAgain = false

      else if finalState.players.forall(
          player =>
            player.name == "You" ||
              player.chips == 0
        ) then

        println()
        println(
          "YOU WON! The other players have no Chips."
        )

        playAgain = false

      else

        playAgain =
          readYesNo(
            "Do you want to play again? (y/n): "
          )

        if !playAgain then
          println(
            "Thanks for playing, visit us again soon!"
          )

    println()
    println("Game over.")


  private def playBettingRound(
      state: GameState
  ): GameState =

    var currentState = state
    var actedPlayers = Set.empty[Int]

    while !bettingRoundFinished(
        currentState,
        actedPlayers
      ) do

      val player =
        currentState.currentPlayer

      if player.folded || player.chips == 0 then

        actedPlayers =
          actedPlayers + player.id

        currentState =
          nextPlayer(currentState)

      else if player.name == "You" then

        println()
        println(
          s"Pot: ${currentState.pot} | Chips: ${player.chips}"
        )

        println("Choose action:")
        println("1 = Check")
        println("2 = Call")
        println("3 = Raise")
        println("4 = Fold")
        println("5 = All-In")

        val choice =
          readInt("Your action: ", 1, 5)

        val oldBet =
          currentState.currentBet

        choice match

          case 1 =>

            if currentState.playerBet(player.id) ==
                currentState.currentBet then

              currentState =
                BettingRound.applyAction(
                  currentState,
                  Action.Check
                )

              actedPlayers =
                actedPlayers + player.id

            else

              println(
                "Cannot check. You must Call or Raise."
              )

          case 2 =>

            currentState =
              BettingRound.applyAction(
                currentState,
                Action.Call
              )

            actedPlayers =
              actedPlayers + player.id

          case 3 =>

            if player.chips > 0 then

              val amount =
                readInt(
                  "Raise amount: ",
                  1,
                  player.chips
                )

              try

                currentState =
                  BettingRound.applyAction(
                    currentState,
                    Action.Raise(amount)
                  )

                if currentState.currentBet > oldBet then
                  actedPlayers =
                    Set(player.id)
                else
                  actedPlayers =
                    actedPlayers + player.id

              catch
                case error: IllegalArgumentException =>
                  println(error.getMessage)

            else

              println(
                "You have no chips left."
              )

          case 4 =>

            currentState =
              BettingRound.applyAction(
                currentState,
                Action.Fold
              )

            actedPlayers =
              actedPlayers + player.id

          case 5 =>

            currentState =
              BettingRound.applyAction(
                currentState,
                Action.AllIn
              )

            println(
              "You are ALL-IN!"
            )

            if currentState.currentBet > oldBet then
              actedPlayers =
                Set(player.id)
            else
              actedPlayers =
                actedPlayers + player.id

      else

        val oldBet =
          currentState.currentBet

        val action =
          randomAction(
            currentState,
            player
          )

        println(
          s"${player.name} chooses: $action"
        )

        try

          currentState =
            BettingRound.applyAction(
              currentState,
              action
            )

          if currentState.currentBet > oldBet then
            actedPlayers =
              Set(player.id)
          else
            actedPlayers =
              actedPlayers + player.id

        catch

          case _: IllegalArgumentException =>

            currentState =
              BettingRound.applyAction(
                currentState,
                Action.Call
              )

            actedPlayers =
              actedPlayers + player.id

    currentState


  private def bettingRoundFinished(
      state: GameState,
      actedPlayers: Set[Int]
  ): Boolean =

    val activePlayers =
      state.activePlayers

    if activePlayers.size <= 1 then

      true

    else

      activePlayers.forall { player =>
        player.chips == 0 ||
        (
          actedPlayers.contains(player.id) &&
          state.playerBet(player.id) == state.currentBet
        )
      }


  private def randomAction(
      state: GameState,
      player: Player
  ): Action =

    val canCheck =
      state.playerBet(player.id) ==
        state.currentBet

    val choices =

      if canCheck then

        Vector(
          Action.Check,
          Action.Raise(10),
          Action.Fold,
          Action.AllIn
        )

      else

        Vector(
          Action.Call,
          Action.Raise(10),
          Action.Fold,
          Action.AllIn
        )

    Random.shuffle(choices).head


  private def nextPlayer(
      state: GameState
  ): GameState =

    val nextIndex =
      (state.currentPlayerIndex + 1) %
        state.players.size

    state.copy(
      currentPlayerIndex = nextIndex
    )


  private def awardPot(
      state: GameState
  ): GameState =

    val activePlayers =
      state.activePlayers

    if activePlayers.isEmpty then

      state

    else if activePlayers.size == 1 then

      val winner =
        activePlayers.head

      println()
      println(
        s"${winner.name} won the pot of ${state.pot} Chips!"
      )

      val updatedWinner =
        winner.copy(
          chips = winner.chips + state.pot
        )

      state
        .updatePlayer(updatedWinner)
        .copy(pot = 0)

    else

      val evaluatedPlayers =
        activePlayers.map { player =>

          val allCards =
            player.hand.toList ++
              state.communityCards.toList

          val hand =
            HandEvaluator.bestHand(allCards)

          (player, hand)
        }

      val winner =
        evaluatedPlayers.maxBy {
          case (_, hand) =>

            val valueString =
              hand.values
                .map(value => f"$value%02d")
                .mkString

            (
              hand.rank.strength,
              valueString
            )
        }

      println()
      println(
        s"${winner._1.name} won the pot of ${state.pot} Chips!"
      )

      val updatedWinner =
        winner._1.copy(
          chips = winner._1.chips + state.pot
        )

      state
        .updatePlayer(updatedWinner)
        .copy(pot = 0)


  private def showResults(
      state: GameState
  ): Unit =

    state.activePlayers.foreach { player =>

      val allCards =
        player.hand.toList ++
          state.communityCards.toList

      println()
      println(s"${player.name}:")
      println(
        s"Cards: ${player.hand.mkString(" ")}"
      )

      if allCards.size >= 5 then

        val hand =
          HandEvaluator.bestHand(allCards)

        println(
          s"Best hand: ${hand.rank}"
        )

        println(
          s"Hand cards: ${hand.cards.mkString(" ")}"
        )

      else

        println(
          "Best hand: No showdown needed - all other players folded."
        )
    }

    println()
    println(
      s"Final pot: ${state.pot}"
    )


  private def readInt(
      message: String,
      min: Int,
      max: Int
  ): Int =

    var valid = false
    var result = min

    while !valid do

      print(message)

      val input =
        scala.io.StdIn.readLine()

      try

        val number =
          input.toInt

        if number >= min &&
            number <= max then

          result = number
          valid = true

        else

          println(
            s"Please enter a number between $min and $max."
          )

      catch

        case _: NumberFormatException =>
          println(
            "Please enter a valid number."
          )

    result


  private def readYesNo(
      message: String
  ): Boolean =

    var valid = false
    var result = false

    while !valid do

      print(message)

      val input =
        scala.io.StdIn
          .readLine()
          .trim
          .toLowerCase

      input match

        case "y" =>
          result = true
          valid = true

        case "n" =>
          result = false
          valid = true

        case _ =>
          println(
            "Please enter y or n."
          )

    result