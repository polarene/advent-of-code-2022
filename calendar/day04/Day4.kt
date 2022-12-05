package day04

import Day
import Lines

class Day4 : Day() {
    override fun part1(input: Lines): Any {
        return input.map(::parseAssignments)
            .count { it.fullyOverlaps() }
    }

    override fun part2(input: Lines): Any {
        return input.map(::parseAssignments)
            .count { it.overlaps() }
    }
}

fun parseAssignments(line: String): ElvesPair {
    return line.split(",")
        .map(::getSections)
        .let { ElvesPair(it[0], it[1]) }
}

private fun getSections(sectionIdsRange: String) = sectionIdsRange.split("-")
    .let {
        val (start, end) = it
        start.toInt()..end.toInt()
    }

class ElvesPair(private val assignment1: IntRange, private val assignment2: IntRange) {
    fun fullyOverlaps(): Boolean {
        return assignment1 fullyContains assignment2
                || assignment2 fullyContains assignment1
    }

    fun overlaps(): Boolean {
        return assignment2.first in assignment1
                || assignment2.last in assignment1
                || assignment1.first in assignment2
                || assignment1.last in assignment2
    }

    private infix fun IntRange.fullyContains(other: IntRange) =
        first <= other.first && last >= other.last
}