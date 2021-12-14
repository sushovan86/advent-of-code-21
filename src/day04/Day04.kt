package day04

import readInput
import java.time.Duration
import kotlin.system.measureTimeMillis

const val boardSize = 5

data class BingoElement(val number: Int, var marked: Boolean = false)
data class BingoBoard(var win: Boolean = false, val elements: List<List<BingoElement>>)

typealias BingoBoards = List<BingoBoard>
typealias WinningBoardWithDraw = Pair<Int, BingoBoard>

data class BingBoardsWithDraws(val draws: Set<Int>, val boards: BingoBoards)

fun BingoBoard.markIfPresent(number: Int) {
    this.elements.asSequence()
        .flatten()
        .find { it.number == number }
        ?.apply {
            this.marked = true
        }
}

fun BingoBoard.isWinningBoard(): Boolean =
    this.elements.any { row ->
        row.all { bingoElement -> bingoElement.marked }
    } || (0 until boardSize)
        .any { index ->
            this.elements.all { it[index].marked }
        }

fun BingoBoards.winningBoard(draw: Int): BingoBoard? {
    val winningBoards = this.filter { !it.win }
        .onEach { it.markIfPresent(draw) }
        .filter { it.isWinningBoard() }
        .onEach { it.win = true }

    return winningBoards.firstOrNull()
}

fun WinningBoardWithDraw?.winningScore(): Int =
    this?.let { winningBoardWithDraw ->
        val (draw, winningBoard) = winningBoardWithDraw
        return draw * winningBoard.elements.asSequence()
            .flatten()
            .filter { !it.marked }
            .sumOf { it.number }
    } ?: 0


fun parseInput(input: List<String>): BingBoardsWithDraws {
    val draws = input.first().split(",").map { it.toInt() }.toSet()
    val boards = input
        .drop(2)
        .filter { it.isNotBlank() }
        .chunked(boardSize)
        .map { matrix ->
            val board = matrix.map { row ->
                row.split(" ")
                    .filter { it.isNotBlank() }
                    .map { BingoElement(it.toInt()) }
                    .toList()
            }.toList()
            BingoBoard(elements = board)
        }
    return BingBoardsWithDraws(draws, boards)
}

fun part2(input: List<String>): Int {

    val (draws, bingoBoards) = parseInput(input)

    val lastWinningBoardWithDraw = draws.asSequence()
        .map { number ->
            bingoBoards.winningBoard(number)?.let {
                number to it
            }
        }
        .filterNotNull()
        .lastOrNull()

    return lastWinningBoardWithDraw.winningScore()
}

fun part1(input: List<String>): Int {

    val (draws, bingoBoards) = parseInput(input)

    val winningBoardWithDraw = draws.asSequence()
        .map { number ->
            bingoBoards.winningBoard(number)?.let {
                number to it
            }
        }
        .filterNotNull()
        .firstOrNull()

    return winningBoardWithDraw.winningScore()
}

fun main() {

    val day = "04"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day${day}/Day${day}_test")

    val part1TestOutput = part1(testInput)
    check(part1TestOutput == 4512) {
        "Part 1 test failed: $part1TestOutput"
    }

    val part2TestOutput = part2(testInput)
    check(part2TestOutput == 1924) {
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