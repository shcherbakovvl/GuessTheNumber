import kotlin.random.Random

class GuessingGame {
    private val secretNumber: Int = Random.nextInt(101)

    fun play(player1: Player, player2: ComputerPlayer) {
        println("${player1.name}, it's your turn to guess the number.")

        val player1Attempts = mutableListOf<Int>()
        var player1Guess = player1.guess()
        player1Attempts.add(player1Guess)

        while (player1Guess != secretNumber) {
            if (player1Guess < secretNumber) {
                println("${player1.name}'s guess is too low.")
            } else {
                println("${player1.name}'s guess is too high.")
            }

            player1Guess = player1.guess()
            player1Attempts.add(player1Guess)
        }

        println("${player1.name} guessed the number in ${player1Attempts.size} attempts.")

        println("Now it's ${player2.name}'s turn to guess the number.")
        val player2Attempts = mutableListOf<Int>()
        var player2Guess = player2.guess()
        player2Attempts.add(player2Guess)

        while (player2Guess != secretNumber) {
            println("${player2.name}'s guess: $player2Guess")
            player2.addUnsuccessfulGuess(player2Guess)
            player2Guess = player2.guess()
            player2Attempts.add(player2Guess)
        }

        println("${player2.name} guess the number in ${player2Attempts.size} attempts.")

        val player1Score = player1Attempts.size
        val player2Score = player2Attempts.size

        if (player1Score < player2Score) {
            println("${player1.name} wins!")
        } else if (player1Score > player2Score) {
            println("${player2.name} wins!")
        } else {
            println("It's a tie!")
        }
    }
}

interface Player {
    val name: String
    fun guess(): Int
}

class HumanPlayer(override val name: String) : Player {
    override fun guess(): Int {
        println("$name, please enter your guess: ")
        val guess = readlnOrNull()?.toIntOrNull()
        if (guess == null) {
            println("Invalid input. Please enter a valid number.")
            return guess()
        }
        return guess
    }
}

class ComputerPlayer : Player {
    override val name: String = "Computer"
    private val unsuccessfulGuesses = mutableSetOf<Int>()

    override fun guess(): Int {
        var guess: Int
        do {
            guess = Random.nextInt(101)
        } while (guess in unsuccessfulGuesses)
        return guess
    }

    fun addUnsuccessfulGuess(guess: Int) {
        unsuccessfulGuesses.add(guess)
    }
}

fun main() {
    val player1 = HumanPlayer("Player 1")
    val player2 = ComputerPlayer()

    val game = GuessingGame()
    game.play(player1, player2)
}
