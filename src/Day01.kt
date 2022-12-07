fun main() {
    fun part1(input: List<Int>): Int {
        return input.max()
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01").joinToString("\n").split("\n\n")
    val sumList = input.map { it.lines().sumOf { it.toInt() } }

    println(part1(sumList))
    println(part2(sumList))
}
