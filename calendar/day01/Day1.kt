package day01

import Day
import Lines

class Day1 : Day() {
    override fun part1(input: Lines): Any {
        return input.mostCalories(1).first()
    }

    override fun part2(input: Lines): Any {
        return input.mostCalories(3).sum()
    }

    private fun Lines.mostCalories(take: Int): List<Int> {
        val mostCalories = sortedSetOf<Int>()
        var totalCalories = 0
        forEach { line ->
            if (line.isNotEmpty()) {
                totalCalories += line.toInt()
            } else {
                mostCalories += totalCalories
                totalCalories = 0
            }
        }
        return mostCalories.descendingIterator()
            .asSequence()
            .take(take)
            .toList()
    }
}