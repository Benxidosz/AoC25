fun main() {
    fun part1(input: List<String>): ULong {
        var result = 0UL
        input[0].split(',').map { range ->
            val bounds = range.split('-').map { it.toULong() }
            (bounds[0] .. bounds[1]).toList()
        }.flatten().map { it.toString() }.forEach { id ->
            if (id.length % 2 == 1) return@forEach
            if (id.take(id.length / 2) == id.takeLast(id.length / 2)) result += id.toULong()
        }
        return result
    }

    fun part2(input: List<String>): ULong {
        var result = 0UL
        input[0].split(',').map { range ->
            val bounds = range.split('-').map { it.toULong() }
            (bounds[0] .. bounds[1]).toList()
        }.flatten().map { it.toString() }.forEach { id ->
            checkLoop@ for (seqLength in 1 .. id.length / 2) {
                if (id.length % seqLength != 0) continue

                val checkValue = id.take(seqLength)
                for (seqIndex in 1 until id.length / seqLength) {
                    if (checkValue != id.substring(seqLength * seqIndex, seqLength * seqIndex + seqLength))
                        continue@checkLoop
                }

                result += id.toULong()
                return@forEach
            }
        }

        return result
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
