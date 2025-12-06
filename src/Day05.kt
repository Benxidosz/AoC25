import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        var solution = 0
        val freshRanges = input.takeWhile { it.isNotBlank() }.map {
            val split = it.split('-')
            split[0].toULong()..split[1].toULong()
        }

        input.takeLastWhile { it.isNotBlank() }.map { it.toULong() }.forEach { id ->
            run check@ {
                freshRanges.forEach { range ->
                    if (id in range) {
                        ++solution
                        return@check
                    }
                }
            }
        }

        return solution
    }

    fun part2(input: List<String>): ULong {
        var solution: ULong

        val freshRanges = mutableListOf<ULongRange>()

        input.takeWhile { it.isNotBlank() }.map {
            val split = it.split('-')
            split[0].toULong()..split[1].toULong()
        }.forEach { range ->
            run add@{
                var rangeToAdd = range
                val rangesToDelete = mutableListOf<ULongRange>()
                freshRanges.forEach { freshRange ->
                    if (freshRange.first in rangeToAdd && freshRange.last in rangeToAdd) {
                        // meglevo benne van
                        rangesToDelete.add(freshRange)
                    }
                    if (rangeToAdd.first in freshRange && rangeToAdd.last in freshRange) {
                        // benne van egy meglevoben
                        return@add
                    }
                    if (rangeToAdd.first < freshRange.first && rangeToAdd.last >= freshRange.first ||
                        rangeToAdd.first <= freshRange.last && rangeToAdd.last > freshRange.last ) {
                        rangesToDelete.add(freshRange)
                        rangeToAdd = min(rangeToAdd.first, freshRange.first)..max(rangeToAdd.last, freshRange.last)
                    }
                }
                freshRanges.removeAll(rangesToDelete)
                freshRanges.add(rangeToAdd)
            }
        }

        solution = freshRanges.fold(0UL) { sum, value ->
            sum + (value.last - value.first) + 1UL
        }

        return solution
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
