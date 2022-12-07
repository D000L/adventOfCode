fun main() {
    fun part1(input: String): Int {
        return input.windowed(4).indexOfFirst { it.toSet().size == 4 } + 4
    }

    fun part2(input: String): Int {
        return input.windowed(14).indexOfFirst { it.toSet().size == 14 } + 14
    }

    val input = readInput("Day06").first()

    println(part1(input))
    println(part2(input))
}
