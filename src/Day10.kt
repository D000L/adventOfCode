fun main() {
    fun getSignals(input: List<String>): List<Int> {
        val signals = mutableListOf(0)
        var signal = 1
        input.forEach {
            when {
                it.startsWith("noop") -> {
                    signals.add(signal)
                }
                it.startsWith("addx") -> {
                    val va = it.split(" ")[1].toInt()
                    signals.add(signal)
                    signals.add(signal)
                    signal += va
                }
            }
        }
        return signals
    }

    fun part1(input: List<String>): Int {
        val signals = getSignals(input)

        var index = 20
        var result = 0
        while (signals.getOrNull(index) != null) {
            result += index * signals[index]
            index += 40
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val signals = getSignals(input)

        signals.drop(1).mapIndexed { index, value ->
            val index = index % 40
            if (value - 1 <= index && index <= value + 1) "#"
            else "."
        }.chunked(40).forEach { println(it.joinToString("")) }

        return 0
    }

    val input = readInput("Day10")

    println(part1(input))
    println(part2(input))
}
