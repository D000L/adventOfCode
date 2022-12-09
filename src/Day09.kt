import kotlin.math.pow

data class Position(val x: Int = 0, val y: Int = 0) {
    private fun isNear(position: Position): Boolean = distance(position) <= 2.0
    private fun distance(position: Position) =
        (x - position.x.toDouble()).pow(2.0) + (y - position.y.toDouble()).pow(2.0)

    fun moved(dx: Int, dy: Int): Position = Position(x + dx, y + dy)

    fun movedNearTo(target: Position): Position {
        if (isNear(target)) return this
        val dir = listOf(0 to 0, 0 to 1, 0 to -1, 1 to 0, -1 to 0, 1 to -1, 1 to 1, -1 to 1, -1 to -1)
        return dir.map { (dx, dy) -> moved(dx, dy) }.minByOrNull { it.distance(target) } ?: this
    }
}

class Snake(length: Int) {
    private val headAndTails = MutableList(length) { Position() }
    private val lastTailHistory = mutableSetOf(headAndTails.last())

    fun move(dir: Char, amount: Int) {
        val (dx, dy) = when (dir) {
            'R' -> 1 to 0
            'U' -> 0 to 1
            'L' -> -1 to 0
            'D' -> 0 to -1
            else -> 0 to 0
        }
        repeat(amount) {
            headAndTails[0] = headAndTails[0].moved(dx, dy)
            for (index in 1 until headAndTails.size) {
                headAndTails[index] = headAndTails[index].movedNearTo(headAndTails[index - 1])
            }
            lastTailHistory.add(headAndTails.last())
        }
    }

    fun count(): Int {
        return lastTailHistory.count()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val snake = Snake(2)
        input.forEach {
            val (dir, amount) = it.split(" ")
            snake.move(dir[0], amount.toInt())
        }
        return snake.count()
    }

    fun part2(input: List<String>): Int {
        val snake = Snake(10)
        input.forEach {
            val (dir, amount) = it.split(" ")
            snake.move(dir[0], amount.toInt())
        }
        return snake.count()
    }

    val input = readInput("Day09")

    println(part1(input))
    println(part2(input))
}
