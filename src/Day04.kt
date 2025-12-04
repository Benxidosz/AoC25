fun main() {
    fun part1(input: List<String>): Int {
        val solution = mutableListOf<IPos>()
        val neighbours = listOf(
            IPos(-1, -1),
            IPos(0, -1),
            IPos(1, -1),
            IPos(-1, 0),
            IPos(1, 0),
            IPos(-1, 1),
            IPos(0, 1),
            IPos(1, 1),
        )

        val matrix = List(input.size) {
            input[it].toList()
        }

        matrix.forEachIndexed { x, y, value ->
            if (value != '@') return@forEachIndexed

            val pos = IPos(x, y)

            var countNei = 0
            neighbours.forEach { neiPosDiff ->
                val neiPos = pos + neiPosDiff
                if (matrix.isValidIndex(neiPos) && matrix.get(neiPos) == '@') ++countNei
            }

            if (countNei < 4) solution.add(pos)
        }

        return solution.size
    }

    fun part2(input: List<String>): Int {
        val solution = mutableListOf<IPos>()
        val neighbours = listOf(
            IPos(-1, -1),
            IPos(0, -1),
            IPos(1, -1),
            IPos(-1, 0),
            IPos(1, 0),
            IPos(-1, 1),
            IPos(0, 1),
            IPos(1, 1),
        )

        val matrix = List(input.size) {
            input[it].toList()
        }

        fun tryRemove(pos: IPos) {
            val value = matrix.get(pos)
            if (value != '@' || solution.contains(pos)) return

            var countNei = 0
            neighbours.forEach { neiPosDiff ->
                val neiPos = pos + neiPosDiff
                if (matrix.isValidIndex(neiPos) && matrix.get(neiPos) == '@' && !solution.contains(neiPos)) ++countNei
            }

            if (countNei < 4) {
                solution.add(pos)
                neighbours.forEach { neiPosDiff ->
                    val neiPos = pos + neiPosDiff
                    if (matrix.isValidIndex(neiPos)) {
                        tryRemove(neiPos)
                    }
                }
            }
        }

        matrix.forEachIndexed { x, y, _ ->
            tryRemove(IPos(x, y))
        }

        return solution.size
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
