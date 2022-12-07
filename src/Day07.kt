sealed interface Component

data class File(val size: Long, val name: String) : Component
data class Directory(
    val name: String,
    val parent: Directory? = null
) : Component {
    private val children: MutableList<Component> = mutableListOf()

    fun addChild(component: Component) {
        children.add(component)
    }

    fun findDirectory(name: String): Directory? {
        return children.filterIsInstance<Directory>().find { it.name == name }
    }

    fun totalSize(): Long {
        return children.sumOf {
            when (it) {
                is Directory -> it.totalSize()
                is File -> it.size
            }
        }
    }

    fun sumOfSizeInRange(upper: Long): Long {
        val current = if (totalSize() in 0L..upper) totalSize() else 0L
        return current + children.filterIsInstance<Directory>().sumOf { it.sumOfSizeInRange(upper) }
    }

    fun findNearDirectorySize(amount: Long): Long {
        return if (totalSize() > amount) {
            totalSize().coerceAtMost(children.filterIsInstance<Directory>().minOf { it.findNearDirectorySize(amount) })
        } else {
            Long.MAX_VALUE
        }
    }
}

class FileSystem(data: List<String>) {
    private val root = Directory("/")

    init {
        parse(data)
    }

    private fun parse(data: List<String>) {
        var current: Directory? = root

        data.forEach {
            when {
                it == "$ ls" -> return@forEach
                it.startsWith("$ cd") -> {
                    val target = it.removePrefix("$ cd ")
                    current = when (target) {
                        ".." -> current?.parent ?: current
                        else -> {
                            current?.findDirectory(target) ?: current
                        }
                    }
                }
                else -> {
                    val (component, name) = it.split(" ")
                    when (component) {
                        "dir" -> current?.addChild(Directory(name, current))
                        else -> current?.addChild(File(component.toLong(), name))
                    }
                }
            }
        }
    }

    fun sumOfSizeInRange(upper: Long): Long = root.sumOfSizeInRange(upper)

    fun optimize(max: Long, need: Long): Long {
        return root.findNearDirectorySize(need - (max - root.totalSize()))
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        return FileSystem(input).sumOfSizeInRange(upper = 100000L)
    }

    fun part2(input: List<String>): Long {
        return FileSystem(input).optimize(max = 70000000L, need = 30000000L)
    }

    val input = readInput("Day07")

    println(part1(input))
    println(part2(input))
}
