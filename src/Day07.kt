fun main() {
    fun part1(input: List<String>): Int {
        var solution = 0

        val map = List(input.size) {
            input[it].toList()
        }

        val beamPoses = mutableListOf(IPos(map[0].indexOf('S'), 0))
        val closedPoses = mutableListOf<IPos>()
        while (beamPoses.isNotEmpty()) {
            val currentBeamPos = beamPoses.removeAt(0)
            val newPos = currentBeamPos + IPos(0, 1);

            if ( currentBeamPos in closedPoses) continue

            closedPoses.add(currentBeamPos)

            if (!map.isValidIndex(newPos)) continue


            if (map.get(newPos) == '^') {
                beamPoses.add(newPos + IPos(-1, 0))
                beamPoses.add(newPos + IPos(1, 0))
                ++solution
            } else {
                beamPoses.add(newPos)
            }
        }

        return solution
    }

    fun part2(input: List<String>): ULong {
        var solution = 0

        val map = List(input.size) {
            input[it].toList()
        }

        val startPos = IPos(map[0].indexOf('S'), 0)
        val graph = mutableMapOf (
            startPos to mutableListOf<IPos>()
        )
        val beamPoses = mutableListOf(startPos)
        val closedPoses = mutableListOf<IPos>()
        while (beamPoses.isNotEmpty()) {
            val currentBeamPos = beamPoses.removeAt(0)
            val newPos = currentBeamPos + IPos(0, 1);

            if ( currentBeamPos in closedPoses) continue

            closedPoses.add(currentBeamPos)
            val childList = graph.getOrPut(currentBeamPos) { mutableListOf()}

            if (!map.isValidIndex(newPos)) continue

            if (map.get(newPos) == '^') {
                beamPoses.add(newPos + IPos(-1, 0))
                beamPoses.add(newPos + IPos(1, 0))

                childList.add(newPos + IPos(-1, 0))
                childList.add(newPos + IPos(1, 0))
            } else {
                beamPoses.add(newPos)
                childList.add(newPos)
            }
        }

        val sorted = mutableListOf<IPos>()
        fun countPathDfs(current: IPos) {
            if (current in sorted) return
            val children = graph.getOrDefault(current, mutableListOf())
            children.forEach { child ->
                countPathDfs(child)
            }
            sorted.add(0, current)
        }

        countPathDfs(startPos)

        val pathsTo = mutableMapOf(
            IPos(-1, -1) to 1UL
        )

        graph.forEach {
            if (it.key.y == map.size - 1) {
                it.value.add(IPos(-1, -1))
            }
        }

        sorted.reverse()
        sorted.forEach {
            var count = 0UL
            graph.getOrDefault(it, mutableListOf()).forEach { child ->
                count += pathsTo.getOrDefault(child, 0UL)
            }
            pathsTo[it] = count
        }

        return pathsTo.getOrDefault(startPos, 0UL)
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
