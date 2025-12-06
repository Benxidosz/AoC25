import java.util.Scanner

fun main() {
    fun part1(input: List<String>): ULong {
        var solution = 0UL

        val problems = mutableListOf<MutableList<ULong>>()
        input.take(input.size - 1).forEach { line ->
            Scanner(line).run {
                var i = 0
                while (hasNextInt()) {
                    val value = nextInt().toULong()
                    if (i < problems.size) {
                        problems[i].add(value)
                    } else {
                        problems.add(mutableListOf(value))
                    }
                    ++i
                }
            }
        }
        var i = 0
        input.last().forEach {
            if (it == '*' || it == '+') {
                solution += problems[i].reduce { sum, value ->
                    if (it == '*') {
                        sum * value
                    } else {
                        sum + value
                    }
                }
                ++i
            }
        }

        return solution
    }

    fun part2(input: List<String>): ULong {
        var solution = 0UL

        val problems = mutableListOf<MutableList<ULong>>()

        var maxSize = 0
        val map = List(input.size - 1) { row ->
            val line = input[row]
            if (line.length > maxSize) maxSize = line.length
            line.toList()
        }.map {
            if (it.size == maxSize) it
            else {
                MutableList(maxSize) { i ->
                    if (i < it.size) it[i]
                    else ' '
                }.toList()
            }
        }
        var pi = 0
        for (col in 0 until maxSize) {
            val numString = StringBuilder().apply {
                map.forEach { row ->
                    append(row[col])
                }
            }.toString()

            if (numString.isBlank()) {
                ++pi
            } else {
                if (pi < problems.size) {
                    problems[pi].add(numString.trim().toULong())
                } else {
                    problems.add(mutableListOf(numString.trim().toULong()))
                }
            }
        }

        var i = 0
        input.last().forEach {
            if (it == '*' || it == '+') {
                solution += problems[i].reduce { sum, value ->
                    if (it == '*') {
                        sum * value
                    } else {
                        sum + value
                    }
                }
                ++i
            }
        }

        return solution
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
