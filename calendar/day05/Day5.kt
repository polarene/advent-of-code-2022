package day05

import Day
import Lines

class Day5 : Day() {
    override fun part1(input: Lines): Any {
        val crates = setupCrates(input)
        val crane = Crane9000(crates)
        input.asSequence()
            .dropWhile { it.isNotEmpty() }
            .drop(1)
            .map(::readStep)
            .forEach {
                crane.rearrange(it.first, it.second, it.third)
            }
        return crates.top()
    }

    override fun part2(input: Lines): Any {
        val crates = setupCrates(input)
        println(crates)
        val crane = Crane9001(crates)
        input.asSequence()
            .dropWhile { it.isNotEmpty() }
            .drop(1)
            .map(::readStep)
            .forEach {
                crane.rearrange(it.first, it.second, it.third)
            }
        return crates.top()
    }
}

typealias Crate = Char
typealias Stack = MutableList<Crate>

fun setupCrates(input: Lines): Crates {
    val cratesConfiguration = readConfiguration(input)
    val stacksCount = countStacks(cratesConfiguration)
    val stacks = initStacks(stacksCount)
    val itemsDistance = 4
    cratesConfiguration
        .forEach { layer ->
            (1 until layer.length step itemsDistance).forEach { i ->
                layer[i].takeUnless { it.isWhitespace() }
                    ?.run { stacks[i / itemsDistance] += this }
            }
        }
    return Crates(stacks)
}

private fun initStacks(stacksCount: Int) = buildList<Stack>(stacksCount) {
    repeat(stacksCount) { add(mutableListOf()) }
}

private fun countStacks(cratesConfiguration: MutableList<String>) = cratesConfiguration.removeFirst()
    .trim()
    .split(" ")
    .last()
    .toInt()

private fun readConfiguration(input: Lines) = input.takeWhile { it.isNotEmpty() }
    .reversed()
    .toMutableList()

val STEP = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
fun readStep(line: String): Triple<Int, Int, Int> {
    val (count, from, to) = STEP.matchEntire(line)!!
        .groupValues.asSequence()
        .drop(1)
        .map { it.toInt() }
        .toList()
    return Triple(count, from - 1, to - 1)
}

class Crates(private val stacks: List<Stack>) {
    fun top(): String = stacks.map { it.last() }.joinToString("")

    fun move(from: Int, to: Int) {
        require(stacks[from].isNotEmpty()) { "can't move $from -> $to" }
        stacks[to] += stacks[from].removeLast()
    }    
    
    fun move(count: Int, from: Int, to: Int) {
        require(stacks[from].isNotEmpty()) { "can't move $from -> $to" }
        stacks[to] += buildList {
            repeat(count) { add(0, stacks[from].removeLast()) }
        }
    }

    override fun toString(): String {
        return buildString {
            stacks.forEach { this.append(it).append('\n') }
        }
    }
}

class Crane9000(private val crates: Crates) {
    fun rearrange(count: Int, from: Int, to: Int) {
        repeat(count) { crates.move(from, to) }
    }
}

class Crane9001(private val crates: Crates) {
    fun rearrange(count: Int, from: Int, to: Int) {
        crates.move(count, from, to)
    }
}