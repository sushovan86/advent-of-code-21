package day06

import readInput
import java.math.BigInteger
import java.time.Duration
import kotlin.system.measureTimeMillis

typealias RemainingLifeCounter = Array<BigInteger>

fun RemainingLifeCounter.rotateLeft() {
    val first = this[0]
    (1 until this.size)
        .forEach { this[it - 1] = this[it] }
    this[this.lastIndex] = first
}


fun part(input: List<String>, days: Int): BigInteger {

    val remainLifeCountArray = remainLifeCountArray(input)

    repeat(days) {
        remainLifeCountArray.rotateLeft()
        remainLifeCountArray[6] += remainLifeCountArray[8]
    }

    return remainLifeCountArray.sumOf { it }
}

private fun remainLifeCountArray(input: List<String>) =
    RemainingLifeCounter(9) { BigInteger.ZERO }
        .apply {
            input.firstOrNull()
                ?.split(",")
                ?.map { it.trim().toInt() }
                ?.forEach { remainingLife ->
                    this[remainingLife]++
                }
        }


fun main() {

    val day = "06"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day${day}/Day${day}_test")

    val part1TestOutput = part(testInput, 18)
    check(part1TestOutput == BigInteger("26")) {
        "Part 1 test failed: $part1TestOutput"
    }

    val part2TestOutput = part(testInput, 256)
    check(part2TestOutput == BigInteger("26984457539")) {
        "Part 2 test failed: $part2TestOutput"
    }

    val input = readInput("day${day}/Day${day}")

    val part1Time = measureTimeMillis {
        val part1Output = part(input, 80)
        println("Part 1: $part1Output")
    }
    println("Part 1 Time = ${Duration.ofMillis(part1Time)}")

    val part2Time = measureTimeMillis {
        val part2Output = part(input, 256)
        println("Part 2: $part2Output")
    }
    println("Part 2 Time = ${Duration.ofMillis(part2Time)}")
}
