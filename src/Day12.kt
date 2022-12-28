import java.util.*

fun main() {
    data class Data(val x: Int, val y: Int, val value: Char, val step: Int)

    fun part1(input: List<String>, sx: Int = 0, sy: Int = 0): Int {
        val visited = mutableMapOf<Pair<Int, Int>, Int>()
        val queue = LinkedList<Data>()

        var min = Int.MAX_VALUE

        queue.offer(Data(sx, sy, 'a', 0))
        while (queue.isNotEmpty()) {
            val (x, y, value, step) = queue.poll()

            if (value == 'E') {
                min = min.coerceAtMost(step)
                continue
            }

            if (visited[x to y] == null) {
                visited[x to y] = step
            } else {
                continue
            }

            val dir = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
            val allowed = value + 1
            dir.map { (dx, dy) -> dx + x to dy + y }.forEach { (x, y) ->
                var va = input.getOrNull(y)?.getOrNull(x) ?: return@forEach
                val or = va
                va = if (va == 'E') 'z' else va
                if (va <= allowed) {
                    queue.offer(Data(x, y, or, step + 1))
                }
            }
        }

        return min
    }

    fun part2(input: List<String>): Int {
        val startList = mutableListOf<Pair<Int, Int>>()
        input.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                if (c == 'S' || c == 'a') startList.add(y to x)
            }
        }
        return startList.minOf { (y, x) -> part1(input, x, y) }
    }

    val input = readInput("Day12")
    println(part1(input, 0, input.indexOfFirst { it.startsWith("S") }))
    println(part2(input))
}
