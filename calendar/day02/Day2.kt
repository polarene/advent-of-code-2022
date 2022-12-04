package day02

import Day
import Lines

class Day2 : Day() {
    override fun part1(input: Lines): Any {
        return Game().run {
            input.forEach { play(it.toHandRound()) }
            total()
        }
    }

    override fun part2(input: Lines): Any {
        return Game().run {
            input.forEach { play(it.toStrategyRound()) }
            total()
        }
    }
}

class Game() {
    private val points = mutableListOf<Int>()

    fun play(round: Round) {
        points += round.points()
    }

    fun total(): Int {
        println(points)
        return points.sum()
    }
}

interface Round {
    fun points(): Int
}

const val LOST = 0
const val DRAW = 3
const val WON = 6

/*
 rock      A X
 paper     B Y
 scissors  C Z
*/
// Rock (A) defeats Scissors (Z), Scissors (C) defeats Paper (Y), and Paper (B) defeats Rock (X)
val defeats = mapOf(
    // opponent vs me
    'A' to 'Z',
    'C' to 'Y',
    'B' to 'X',
)
const val ROCK = 1
const val PAPER = 2
const val SCISSORS = 3
val handPoints = mapOf(
    'X' to ROCK,
    'Y' to PAPER,
    'Z' to SCISSORS,
)

class HandRound(val opponent: Char, val me: Char) : Round {
    override fun points(): Int = (opponent against me) + handPoints[me]!!

    private infix fun Char.against(me: Char) = when {
        me == this.plus(23) -> DRAW
        me != defeats[this] -> WON
        else -> LOST
    }
}

fun String.toHandRound() =
    split(' ').let { HandRound(opponent = it[0][0], me = it[1][0]) }

// ------------------------part 2---------------------------------------
// Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
const val DEFEATS_RULE = "ACB"
const val DEFEATED_RULE = "CAB"
val handPoints2 = mapOf(
    'A' to ROCK,
    'B' to PAPER,
    'C' to SCISSORS,
)
val strategyPoints = mapOf(
    'X' to LOST,
    'Y' to DRAW,
    'Z' to WON,
)

class StrategyRound(val opponent: Char, val strategy: Char) : Round {
    override fun points(): Int = handPoints2[strategy selectMove opponent]!! + strategyPoints[strategy]!!

    private infix fun Char.selectMove(opponent: Char) = when (this) {
        'X' -> DEFEATS_RULE[(DEFEATS_RULE.indexOf(opponent) + 1) % 3]
        'Z' -> DEFEATED_RULE[(DEFEATED_RULE.indexOf(opponent) + 1) % 3]
        else -> opponent
    }
}

fun String.toStrategyRound() =
    split(' ').let { StrategyRound(opponent = it[0][0], strategy = it[1][0]) }
