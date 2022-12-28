import kotlin.math.max

class Dropper(input: List<String>) {
    enum class StopCondition { CantMove, TouchFloor }

    private val cave = mutableMapOf<Pair<Int, Int>, Char>()
    private var floorHeight = 0

    init {
        input.forEach { line ->
            line.filterNot { it.isWhitespace() }.split("->").windowed(2) { (start, end) ->
                var (sX, sY) = start.split(",").map { it.toInt() }
                var (eX, eY) = end.split(",").map { it.toInt() }

                if (sX > eX) sX = eX.also { eX = sX }
                if (sY > eY) sY = eY.also { eY = sY }

                for (x in sX..eX) {
                    for (y in sY..eY) {
                        cave[x to y] = 'r'
                    }
                }

                floorHeight = max(floorHeight, eY)
            }
        }
    }

    fun run(stopCondition: StopCondition): Int {
        val dir = listOf(0 to 1, -1 to 1, 1 to 1)
        var count = 0

        while (true) {
            var (x, y) = 500 to 0
            var dropping = true

            while (dropping) {
                if (y > floorHeight) {
                    when (stopCondition) {
                        StopCondition.CantMove -> break
                        StopCondition.TouchFloor -> return count
                    }
                }

                dir.map { (dX, dY) -> x + dX to y + dY }.firstOrNull { cave[it] == null }?.let { (nx, ny) ->
                    x = nx
                    y = ny
                } ?: run {
                    dropping = false
                }
            }

            if (cave[x to y] != null) return count
            cave[x to y] = 's'
            count++
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return Dropper(input).run(Dropper.StopCondition.TouchFloor)
    }

    fun part2(input: List<String>): Int {
        return Dropper(input).run(Dropper.StopCondition.CantMove)
    }

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
