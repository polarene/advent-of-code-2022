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
            .map { it.map(String::toInt).sum() }
            .sorted()
            .takeLast(take)
}

fun <T> Iterable<T>.chunkedWhile(predicate: (T) -> Boolean): List<List<T>> {
    val result = mutableListOf<List<T>>()
    var chunk = mutableListOf<T>()
    for (element in this) {
        if (predicate(element)) {
            chunk += element
        } else {
            result += chunk
            chunk = mutableListOf()
        }
    }
    return result
}