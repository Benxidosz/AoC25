fun main() {
    fun part1(input: List<String>): Int {
        var solution = 0

        val boxes = input.map {
            val strings = it.split(',')
            IPos3D(strings[0].toInt(), strings[1].toInt(), strings[2].toInt())
        }

        val cycle = 10
        val circuits = mutableListOf<MutableList<IPos3D>>()
        repeat(cycle) {
            val pairs = mutableListOf<Pair<IPos3D, IPos3D>>()
            boxes.forEachIndexed { i, a ->
                for (j in i + 1 until boxes.size) {
                    val b = boxes[j]
                    pairs.add(a to b)
                }
            }

            val connected = circuits.find { circuit ->
                minPair.first in circuit && minPair.second in circuit
            } != null

            if (connected) {
                return@repeat
            }

            val aCircuit = circuits.find { circuit ->
                minPair.first in circuit
            }

            val bCircuit = circuits.find { circuit ->
                minPair.second in circuit
            }

            if (aCircuit == null && bCircuit == null) {
                circuits.add(mutableListOf(minPair.first, minPair.second))
            } else if (aCircuit != null && bCircuit == null) {
                aCircuit.add(minPair.second)
            } else if (aCircuit == null && bCircuit != null) {
                bCircuit.add(minPair.first)
            } else if (aCircuit != null && bCircuit != null) {
                circuits.remove(bCircuit)
                aCircuit.addAll(bCircuit)
            }
        }

        return solution
    }

    fun part2(input: List<String>): Int {
        var solution = 0

        return solution
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}
