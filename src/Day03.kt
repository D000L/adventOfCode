fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val halfSize = it.length / 2
            val wordA = it.subSequence(0,halfSize)
            val wordB = it.subSequence(halfSize,it.length)

            wordA.toSet().intersect(wordB.toSet()).first()
        }.sumOf {
            it.code - if (it.isLowerCase())96 else 38
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).map {
            val (a, b, c) = it

            a.toSet().intersect(b.toSet()).intersect(c.toSet()).first()
        }.sumOf {
            it.code - if (it.isLowerCase()) 96 else 38
        }
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
