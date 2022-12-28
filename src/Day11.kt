data class Monkey(
    val number: Int,
    var items: MutableList<Long> = mutableListOf(),
    var operation: String = "",
    var divisible: Int = 0,
    var trueTarget: Int = 0,
    var falseTarget: Int = 0
) {
    var inspectedCount = 0L

    fun getOperatedItems(): List<Long> {
        return items.map {
            inspectedCount++
            val (_, _, _, cmd, num) = operation.split(" ")
            val num2 = if (num == "old") it else num.toLong()
            when (cmd) {
                "+" -> it + num2
                "*" -> it * num2
                "-" -> it - num2
                else -> it
            }
        }
    }
}

class MonkeyTower(input: List<String>) {
    private val monkeys = mutableListOf<Monkey>()

    init {
        var currentMonkey = 0
        input.forEach {
            val message = it.trim()
            when {
                message.startsWith("Monkey") -> {
                    currentMonkey = message.split(" ")[1][0].digitToInt()
                    monkeys.add(Monkey(0))
                }
                message.startsWith("Starting items: ") -> {
                    monkeys[currentMonkey].items =
                        message.replace("Starting items: ", "").split(", ").map { it.toLong() }.toMutableList()
                }
                message.startsWith("Operation: ") -> {
                    monkeys[currentMonkey].operation = message.replace("Operation: ", "")
                }
                message.startsWith("Test: divisible by ") -> {
                    monkeys[currentMonkey].divisible = message.replace("Test: divisible by ", "").toInt()
                }
                message.startsWith("If true: ") -> {
                    monkeys[currentMonkey].trueTarget = message.replace("If true: throw to monkey ", "").toInt()
                }
                message.startsWith("If false:") -> {
                    monkeys[currentMonkey].falseTarget = message.replace("If false: throw to monkey ", "").toInt()
                }
            }
        }
    }

    fun run(times: Int, levelOptimizer: (Long) -> Long): Long {
        val divider = monkeys.map { it.divisible }.fold(1) { acc, n -> acc * n }
        repeat(times) {
            monkeys.forEach { monkey ->
                val items = monkey.getOperatedItems()
                monkey.items = mutableListOf()

                items.forEach {
                    val item = levelOptimizer(it) % divider
                    if (item % monkey.divisible == 0L) monkeys[monkey.trueTarget].items.add(item)
                    else monkeys[monkey.falseTarget].items.add(item)
                }
            }
        }
        return monkeys.map { it.inspectedCount }.sortedDescending().take(2).fold(1L) { acc, n -> acc * n }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        return MonkeyTower(input).run(20) { it / 3 }
    }

    fun part2(input: List<String>): Long {
        return MonkeyTower(input).run(10000) { it }
    }

    val input = readInput("Day11")

    println(part1(input))
    println(part2(input))
}
