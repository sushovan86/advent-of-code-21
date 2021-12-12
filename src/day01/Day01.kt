package day01

import readInput
import java.time.Duration
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(2)
            .count { it[1] > it[0] }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .windowed(3)
            .windowed(2)
            .count { it[1].sum() > it[0].sum() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")

    val part1TestOutput = part1(testInput)
    check(part1TestOutput == 7)

    val part2TestOutput = part2(testInput)
    check(part2TestOutput == 5)

    val input = readInput("day01/Day01")

    val part1Time = measureTimeMillis {
        val part1Output = part1(input)
        println("Part 1: $part1Output")
    }
    println("Part 1 Time = ${Duration.ofMillis(part1Time)}")

    val part2Time = measureTimeMillis {
        val part2Output = part2(input)
        println("Part 2: $part2Output")
    }
    println("Part 2 Time = ${Duration.ofMillis(part2Time)}")
}
