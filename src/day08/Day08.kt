package day08

import readInput
import java.time.Duration
import kotlin.system.measureTimeMillis

val EASY_DIGIT_SEGMENTS = arrayOf(2, 3, 4, 7)

fun String.splitWithSpace() = split(" ").map { it.trim() }

fun part1(inputList: List<String>): Int = inputList
    .map {
        val split = it.split("|")
        split.getOrNull(1)?.splitWithSpace() ?: emptyList()
    }
    .flatten()
    .count { it.length in EASY_DIGIT_SEGMENTS }

fun part2(inputList: List<String>): Int {
    return 0
}

fun main() {

    val day = "08"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day${day}/Day${day}_test")

    val part1TestOutput = part1(testInput)
    check(part1TestOutput == 26) {
        "Part 1 test failed: $part1TestOutput"
    }

    val part2TestOutput = part2(testInput)
    check(part2TestOutput == 0) {
        "Part 2 test failed: $part2TestOutput"
    }

    val input = readInput("day${day}/Day${day}")

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