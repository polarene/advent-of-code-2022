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

private fun String.findMarker(length: Int) =
    windowed(length).indexOfFirst { it.allDistinct() } + length

private fun String.allDistinct() = toSet().size == length