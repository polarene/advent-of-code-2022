package day03

import Day
import Lines

class Day3 : Day() {
    override fun part1(input: Lines): Any {
        return input.map(::Rucksack)
            .map { it.duplicate() }
            .sumOf { it.priority }
    }

    override fun part2(input: Lines): Any {
        return input.chunked(3) { Group(it.toList()) }
            .sumOf { it.badge().priority }
    }
}

const val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

val Char.priority
    get() = alphabet.indexOf(this) + 1

class Rucksack(items: String) {
    private val first = items.substring(0, items.length / 2)
    private val second = items.substring(items.length / 2)

    fun duplicate(): Char = first.find { it in second }!!
}

class Group(private val rucksacks: Lines) {
    fun badge(): Char =
        rucksacks.map { it.toSet() }
            .reduce { acc, chars -> acc.intersect(chars) }
            .first()
}