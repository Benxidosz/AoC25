fun main() {
    fun part1(input: List<String>): Int {
        val boxes = input.map {
            val strings = it.split(',')
            IPos3D(strings[0].toInt(), strings[1].toInt(), strings[2].toInt())
        }

        val cycle = 1000
        val circuits = mutableListOf<MutableList<IPos3D>>()

        val pairs = mutableListOf<Pair<IPos3D, IPos3D>>()
        boxes.forEachIndexed { i, a ->
            for (j in i + 1 until boxes.size) {
                val b = boxes[j]
                pairs.add(a to b)
            }
        }
        pairs.sortWith { a, b ->
            distance(a.first, a.second).compareTo(distance(b.first, b.second))
        }

        repeat(cycle) {
            val minPair = pairs[it]
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

        circuits.sortWith { a, b ->
            b.size.compareTo(a.size)
        }

        return circuits[0].size * circuits[1].size * circuits[2].size
    }

    fun part2(input: List<String>): ULong {
        var solution = 0UL

        val boxes = input.map {
            val strings = it.split(',')
            IPos3D(strings[0].toInt(), strings[1].toInt(), strings[2].toInt())
        }

        val circuits = MutableList<MutableList<IPos3D>>(boxes.size) { mutableListOf(boxes[it]) }

        val pairs = mutableListOf<Pair<IPos3D, IPos3D>>()
        boxes.forEachIndexed { i, a ->
            for (j in i + 1 until boxes.size) {
                val b = boxes[j]
                pairs.add(a to b)
            }
        }
        pairs.sortWith { a, b ->
            distance(a.first, a.second).compareTo(distance(b.first, b.second))
        }

        run {
            pairs.forEach { minPair ->
                val connected = circuits.find { circuit ->
                    minPair.first in circuit && minPair.second in circuit
                } != null

                if (connected) {
                    return@forEach
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

                if (circuits.size == 1) {
                    solution = minPair.first.x.toULong() * minPair.second.x.toULong()
                    return@run
                }
            }
        }

        circuits.sortWith { a, b ->
            b.size.compareTo(a.size)
        }

        return solution
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
//    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
