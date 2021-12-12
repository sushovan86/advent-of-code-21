package day03

import readInput
import java.time.Duration
import kotlin.system.measureTimeMillis

enum class Type {
    OXY, CO2
}

fun part2(input: List<String>): Int {

    val oxy = countRating(Type.OXY, input)
    val co2 = countRating(Type.CO2, input)

    return oxy * co2
}

fun countRating(type: Type, input: List<String>): Int {

    var modifiedList = input
    for (column in input.first().indices) {

        val (zeros, ones) = countBit(column, modifiedList)
        val bitToKeep = when (type) {
            Type.OXY -> if (ones >= zeros) '1' else '0'
            Type.CO2 -> if (zeros <= ones) '0' else '1'
        }
        modifiedList = modifiedList.filter { it[column] == bitToKeep }
        if (modifiedList.size == 1) {
            break
        }
    }

    return modifiedList.single().toInt(2)
}

fun part1(input: List<String>): Int {

    val gammaString = buildString {
        for (column in input.first().indices) {
            val (zeros, ones) = countBit(column, input)
            append(if (ones > zeros) "1" else "0")
        }
    }
    val epsilonString = gammaString
        .asIterable()
        .joinToString("") {
            if (it == '1') "0" else "1"
        }

    return gammaString.toInt(2) * epsilonString.toInt(2)
}

fun countBit(column: Int, input: List<String>): BitCount = input.map { it[column] }
    .groupingBy { it }
    .eachCount()
    .let {
        BitCount(it['0'] ?: 0, it['1'] ?: 0)
    }

data class BitCount(var zeros: Int = 0, var ones: Int = 0)

fun main() {

    val day = "03"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day${day}/Day${day}_test")

    val part1TestOutput = part1(testInput)
    check(part1TestOutput == 198) {
        "Part 1 test failed: $part1TestOutput"
    }

    val part2TestOutput = part2(testInput)
    check(part2TestOutput == 230) {
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
