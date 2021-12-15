package day05

import readInput
import java.time.Duration
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.system.measureTimeMillis

data class Point(val x: Int = 0, val y: Int = 0) {

    infix fun isInSameAxisWith(other: Point): Boolean =
        x == other.x || y == other.y

    infix fun isInSameAxisOrInDiagonalWith(other: Point): Boolean =
        isInSameAxisWith(other) || ((x - other.x).absoluteValue == (y - other.y).absoluteValue)


    infix fun lineTo(other: Point): List<Point> {

        val deltaX = other.x - x
        val deltaY = other.y - y
        val xSign = deltaX.sign
        val ySign = deltaY.sign

        val steps = maxOf(deltaX.absoluteValue, deltaY.absoluteValue)

        return (0 until steps).scan(this) { last, _ ->
            Point(last.x + xSign, last.y + ySign)
        }
    }
}

fun solve(input: List<String>, criteria: (Pair<Point, Point>) -> Boolean): Int =
    parseInput(input)
        .filter(criteria)
        .flatMap { (point1, point2) -> point1 lineTo point2 }
        .groupingBy { it }
        .eachCount()
        .count { it.value > 1 }

fun part2(input: List<String>): Int = solve(input) { (point1, point2) ->
    point1 isInSameAxisOrInDiagonalWith point2
}

fun part1(input: List<String>): Int = solve(input) { (point1, point2) ->
    point1 isInSameAxisWith point2
}

fun parseInput(input: List<String>): List<Pair<Point, Point>> =
    input.map { line ->
        val coordinates = line.split("->")
            .map(String::trim)
            .map { coordinate ->
                val (x, y) = coordinate.split(",")
                Point(x.toInt(), y.toInt())
            }
        coordinates[0] to coordinates[1]
    }

fun main() {

    val day = "05"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day${day}/Day${day}_test")

    val part1TestOutput = part1(testInput)
    check(part1TestOutput == 5) {
        "Part 1 test failed: $part1TestOutput"
    }

    val part2TestOutput = part2(testInput)
    check(part2TestOutput == 12) {
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
