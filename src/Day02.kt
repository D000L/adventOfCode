enum class RPS(val point: Int) {
    Rock(1), Paper(2), Scissors(3);

    companion object {
        fun from(word: String): RPS {
            return when (word) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                else -> Scissors
            }
        }
    }
}

enum class Result(val point: Int) {
    Lost(0), Draw(3), Win(6);

    companion object {
        fun from(word: String): Result {
            return when (word) {
                "X" -> Lost
                "Y" -> Draw
                else -> Win
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (player1, player2) = it.split(" ").map { RPS.from(it) }

            val result = if (player1 == player2) Result.Draw
            else if (
                player1 == RPS.Rock && player2 == RPS.Scissors ||
                player1 == RPS.Scissors && player2 == RPS.Paper ||
                player1 == RPS.Paper && player2 == RPS.Rock
            ) Result.Lost
            else Result.Win

            result.point + player2.point
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (player1Word, resultWord) = it.split(" ")

            val player1 = RPS.from(player1Word)
            val result = Result.from(resultWord)

            val player2 = when (result) {
                Result.Draw -> player1
                Result.Win -> {
                    when (player1) {
                        RPS.Paper -> RPS.Scissors
                        RPS.Scissors -> RPS.Rock
                        else -> RPS.Paper
                    }
                }
                else -> {
                    when (player1) {
                        RPS.Paper -> RPS.Rock
                        RPS.Scissors -> RPS.Paper
                        else -> RPS.Scissors
                    }
                }
            }

            result.point + player2.point
        }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
