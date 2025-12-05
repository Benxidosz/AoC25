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

    fun part2(input: List<String>): Int {
        var solution = 0

        val freshRanges = mutableListOf<ULongRange>()

        return solution
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    part1(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}
