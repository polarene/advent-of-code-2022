package day07

import Day
import Lines

class Day7 : Day() {
    override fun part1(input: Lines): Any {
        val fs = Filesystem.init(input)
        return fs.findDirsBySize { it <= 100_000 }.sum()
    }

    override fun part2(input: Lines): Any {
        val totalSpace = 70_000_000
        val updateSpace = 30_000_000
        val fs = Filesystem.init(input)
        val unused = totalSpace - fs.size()
        val needed = updateSpace - unused
        return fs.findDirsBySize { it >= needed }.min()
    }
}

class Node(
    private val name: String,
    private val size: Int = 0,
    val children: MutableList<Node> = mutableListOf(),
    var parent: Node? = null
) {
    val isDir: Boolean get() = children.isNotEmpty()

    fun size(): Int =
        if (isDir)
            children.sumOf { it.size() }
        else
            size

    operator fun plusAssign(other: Node) {
        other.parent = this
        children.add(other)
    }

    operator fun get(name: String): Node {
        return children.first { it.name == name }
    }

    override fun toString() = "- $name ($size)"
}

class Filesystem(private val root: Node) {
    companion object {
        fun init(lines: Lines): Filesystem {
            val root = Node("/")
            var current = root
            lines.drop(1).forEach { line ->
                current = when {
                    line.startsWith("$") -> cmd(current, line.substring(2))
                    else -> create(current, line)
                }
            }
            return Filesystem(root)
        }

        const val INDENTATION = 2
    }

    fun findDirsBySize(condition: (Int) -> Boolean): List<Int> {
        return findDirs(root).asSequence()
            .map { it.size() }
            .filter(condition)
            .toList()
    }

    private fun findDirs(current: Node): List<Node> {
        return if (current.isDir)
            listOf(current) + current.children.flatMap { findDirs(it) }
        else
            emptyList()
    }

    fun size(): Int {
        return root.size()
    }

    override fun toString() = toString(0, root)

    private fun toString(level: Int, node: Node): String {
        return "${level.ind(INDENTATION)}$node\n" + buildString {
            node.children.forEach {
                append(toString(level + 1, it))
            }
        }
    }

    private fun Int.ind(indentation: Int) = " ".repeat(this * indentation)
}

// ----------------------------------------------------------------
// parsing
// ----------------------------------------------------------------
fun cmd(current: Node, line: String): Node {
    val args = line.split(" ")
    return when (args[0]) {
        "cd" -> if (args[1] == "..") {
            current.parent!!
        } else {
            current[args[1]]
        }

        "ls" -> current
        else -> error("Unknown command")
    }
}

fun create(current: Node, line: String): Node {
    val (typeOrSize, name) = line.split(" ")
    current += when (typeOrSize) {
        "dir" -> Node(name)
        else -> Node(name, typeOrSize.toInt())
    }
    return current
}