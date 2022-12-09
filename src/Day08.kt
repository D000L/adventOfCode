fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray().map { it.digitToInt() } }

        fun find(y: Int, x: Int): Boolean {
            val treeHeight = map[y][x]
            val dir = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

            dir.forEach { (dx, dy) ->
                var currentX = x
                var currentY = y

                do {
                    currentX += dx
                    curre   ntY += dy
                    val targetTreeHeight = map.getOrNull(currentY)?.getOrNull(currentX) ?: return true
                } while (treeHeight > targetTreeHeight)
            }
            return false
        }

        return map.indices.flatMap { y -> map[0].indices.map { x -> y to x } }.count { (y, x) -> find(y, x) }
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray().map { it.digitToInt() } }

        fun find(y: Int, x: Int): Int {
            val treeHeight = map[y][x]
            val dir = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

            return dir.map { (dy, dx) ->
                var currentX = x
                var currentY = y

                var count = 0
                do {
                    currentX += dx
                    currentY += dy
                    val targetTreeHeight = map.getOrNull(currentY)?.getOrNull(currentX) ?: break
                    count++
                } while (treeHeight > targetTreeHeight)
                count
            }.fold(1) { total, score -> total * score }
        }

        return map.indices.flatMap { y -> map[0].indices.map { x -> y to x } }.maxOf { (y, x) -> find(y, x) }
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
