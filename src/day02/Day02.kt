package day02

import readInput
import java.time.Duration
import kotlin.system.measureTimeMillis

private const val FORWARD = "forward"
private const val UP = "up"
private const val DOWN = "down"

data class State(var position: Int = 0, var depth: Int = 0, var aim: Int = 0) {

    fun move(direction: String, arg: Int) {
        when (direction) {
            FORWARD -> position += arg
            UP -> depth -= arg
            DOWN -> depth += arg
            else -> throw IllegalArgumentException("Unknown direction: $direction")
        }
    }

    fun moveAndAim(direction: String, arg: Int) {
        when (direction) {
            FORWARD -> {
                position += arg
                depth += (aim * arg)
            }
            UP -> aim -= arg
            DOWN -> aim += arg
            else -> throw IllegalArgumentException("Unknown direction: $direction")
        }
    }

    fun coordinates() = position * depth
}

data class Operation(val instruction: String, val amount: Int) {
    fun executeMove(state: State) = state.move(instruction, amount)
    fun executeMoveAndAim(state: State) = state.moveAndAim(instruction, amount)
}

fun part1(input: List<String>): Int {
    val state = State()
    input.map {
        val (instruction, amount) = it.split(" ")
        Operation(instruction, amount.toInt())
    }
        .forEach { it.executeMove(state) }

    return state.coordinates()
}

fun part2(input: List<String>): Int {
    val state = State()
    input.map {
        val (instruction, amount) = it.split(" ")
        Operation(instruction, amount.toInt())
    }
        .forEach { it.executeMoveAndAim(state) }

    return state.coordinates()
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")

    val part1TestOutput = part1(testInput)
    check(part1TestOutput == 150)

    val part2TestOutput = part2(testInput)
    check(part2TestOutput == 900)

    val input = readInput("day02/Day02")

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