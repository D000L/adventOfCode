fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val (a, b, x, y) = it.split(",", "-").map { it.toInt() }

            val ra = IntRange(a, b)
            val rb = IntRange(x, y)

            if (ra.all { it in rb } || rb.all { it in ra }) 1
            else 0
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val (a, b, x, y) = it.split(",", "-").map { it.toInt() }

            val ra = IntRange(a, b)
            val rb = IntRange(x, y)

            if (ra.intersect(rb).isNotEmpty()) 1
            else 0
        }.sumOf { it }
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
