package day01

import Day
import Lines

class Day1 : Day() {
    override fun part1(input: Lines): Any {
        return input.mostCalories(1).single()
    }

    override fun part2(input: Lines): Any {
        return input.mostCalories(3).sum()
    }

    private fun Lines.mostCalories(take: Int) =
        chunkedWhile { it.isNotEmpty() }
            .map { it.sumOf(String::toInt) }
            .sorted()
            .takeLast(take)
}

fun <T> Iterable<T>.chunkedWhile(predicate: (T) -> Boolean): List<List<T>> {
    return fold(listOf(listOf())) { acc, t ->
        if (predicate(t)) {
            acc.dropLast(1) + listOf(acc.last() + t)
        } else {
            acc + listOf(listOf())
        }
    }
}