package day07

import readInput
import java.time.Duration
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

fun part2(inputList: List<String>): Int = solve(inputList) {
    it * (it + 1) / 2
}

fun part1(inputList: List<String>): Int = solve(inputList) {
    it
}

fun solve(inputList: List<String>, fuelCost: (distance: Int) -> Int): Int {

    val input = inputList.singleOrNull()
        ?.split(",")
        ?.map { it.trim().toInt() }
        ?: return 0

    val min = input.minOrNull() ?: 0
    val max = input.maxOrNull() ?: 0

    val minFuel = (min..max)
        .map { target ->
            input.asSequence()
                .map { (it - target).absoluteValue }
                .sumOf { distance -> fuelCost(distance) }
        }
        .minOf { it }

    return minFuel
}

fun main() {

    val day = "07"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day${day}/Day${day}_test")

    val part1TestOutput = part1(testInput)
    check(part1TestOutput == 37) {
        "Part 1 test failed: $part1TestOutput"
    }

    val part2TestOutput = part2(testInput)
    check(part2TestOutput == 168) {
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