fun main() {
    fun process(input: List<String>, withReverse: Boolean): String {
        val stacks = input.takeWhile { it.isNotEmpty() }.dropLast(1)
            .map {
                it.replace("    ", " ")
                    .replace("[", "")
                    .replace("]", "")
            }
        val moves = input.dropWhile { it.isNotEmpty() }.drop(1)

        val stackMap = sortedMapOf<Int, List<String>>()
        stacks.forEach {
            it.split(" ").forEachIndexed { index, s ->
                if (s.isEmpty()) return@forEachIndexed
                stackMap[index + 1] = (stackMap[index + 1] ?: listOf()) + s
            }
        }

        moves.forEach {
            val (amount, a, b) = it.split(" ").mapNotNull { it.toIntOrNull() }
            stackMap[b] =
                if (withReverse) stackMap[a]!!.take(amount).reversed() + stackMap[b]!!
                else stackMap[a]!!.take(amount) + stackMap[b]!!
            stackMap[a] = stackMap[a]!!.drop(amount)
        }

        return stackMap.map { it.value.first() }.joinToString("")
    }

    fun part1(input: List<String>): String {
        return process(input, true)
    }

    fun part2(input: List<String>): String {
        return process(input, false)
    }

    val input = readInput("Day05")

    println(part1(input))
    println(part2(input))
}
