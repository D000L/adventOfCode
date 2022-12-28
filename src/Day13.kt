sealed interface Packet {
    infix fun compareTo(other: Packet): Int
}

class NumPacket(val num: Int) : Packet {
    override fun toString(): String {
        return num.toString()
    }

    override fun compareTo(other: Packet): Int {
        return when (other) {
            is NumPacket -> when {
                num < other.num -> -1
                num > other.num -> 1
                else -> 0
            }
            is ListPacket -> ListPacket(listOf(this)) compareTo other
        }
    }
}

class ListPacket(val inners: List<Packet>) : Packet {
    override fun toString(): String {
        return "[" + inners.joinToString(",") + "]"
    }

    override fun compareTo(other: Packet): Int {
        return when (other) {
            is NumPacket -> this compareTo ListPacket(listOf(other))
            is ListPacket -> inners.zip(other.inners).map { it.first compareTo it.second }.filterNot { it == 0 }
                .firstOrNull() ?: (inners.size compareTo other.inners.size)
        }
    }

}

fun String.toPacket(): Packet = removeSurrounding("[", "]").run {
    if (toIntOrNull() != null) return NumPacket(toInt())
    if (isEmpty()) return ListPacket(emptyList())
    val inner = mutableListOf<Packet>()

    var word = ""
    var brackets = 0
    this.forEachIndexed { index, char ->
        when (char) {
            '[' -> brackets++
            ']' -> brackets--
        }
        if (char == ',' && brackets == 0) {
            inner.add(word.toPacket())
            word = ""
            return@forEachIndexed
        }
        word += char
    }
    if (word.isNotEmpty()) inner.add(word.toPacket())

    return ListPacket(inner)
}

fun main() {

    fun part1(input: List<String>): Int {
        var result = 0
        var index = 0
        input.filter { it.isNotEmpty() }.chunked(2).forEach { (a, b) ->
            index++
            val aWords = a.toPacket()
            val bWords = b.toPacket()

            if (aWords compareTo bWords < 0) result += index
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val first = "[[2]]".toPacket()
        val second = "[[6]]".toPacket()
        val sorted = ((input.filter { it.isNotEmpty() }.map { it.toPacket() } + listOf(
            first, second
        ))).sortedWith(Packet::compareTo)
        return (sorted.indexOf(first) + 1) * (sorted.indexOf(second) + 1)
    }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
