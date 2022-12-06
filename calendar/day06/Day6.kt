package day06

import Day
import Lines

class Day6 : Day() {
    override fun part1(input: Lines): Any {
        return input[0].findMarker(4)
    }

    override fun part2(input: Lines): Any {
        return input[0].findMarker(14)
    }
}

private fun String.findMarker(distinctChars: Int): Int {
    var streamIndex = 0
    windowed(distinctChars)
        .first {
            ++streamIndex
            it.toSet().size == distinctChars
        }
    return streamIndex + distinctChars - 1
}