
fun main() {
    fun part1(input: List<String>): Int {
        val graph = mutableMapOf<String, List<String>>()
        input.forEach { line ->
            val split = line.split(":")
            graph[split[0].trim()] = split[1].trim().split(" ")
        }

        val sorted = mutableListOf<String>()
        fun countPathDfs(current: String) {
            if (current in sorted) return
            val children = graph.getOrDefault(current, mutableListOf())
            children.forEach { child ->
                countPathDfs(child)
            }
            sorted.add(0, current)
        }

        countPathDfs("you")

        val pathsTo = mutableMapOf(
            "out" to 1
        )

        sorted.reverse()
        val visited = mutableSetOf("out")
        sorted.forEach { current ->
            var count = 0
            val children = graph.getOrDefault(current, mutableListOf())

            if (!visited.any { it in children }) {
                return@forEach
            }

            children.forEach { child ->
                count += pathsTo.getOrDefault(child, 0)
            }
            pathsTo[current] = count
            visited.add(current)
        }

        return pathsTo["you"]!!
    }

    fun part2(input: List<String>): ULong {
        val graph = mutableMapOf<String, List<String>>()
        input.forEach { line ->
            val split = line.split(":")
            graph[split[0].trim()] = split[1].trim().split(" ")
        }

        val sorted = mutableListOf<String>()
        fun countPathDfs(current: String) {
            if (current in sorted) return
            val children = graph.getOrDefault(current, mutableListOf())
            children.forEach { child ->
                countPathDfs(child)
            }
            sorted.add(0, current)
        }

        countPathDfs("svr")

        sorted.reverse()

        fun getPathCount(src: String, dest: String): ULong {
            val pathsTo = mutableMapOf(
                dest to 1UL
            )
            val visited = mutableSetOf(dest)
            sorted.forEach { current ->
                var count = 0UL
                val children = graph.getOrDefault(current, mutableListOf())

                if (!visited.any { it in children }) {
                    return@forEach
                }

                children.forEach { child ->
                    count += pathsTo.getOrDefault(child, 0UL)
                }
                pathsTo[current] = count
                visited.add(current)
            }

            return pathsTo.getOrDefault(src, 0UL)
        }

        return getPathCount("svr", "fft") * getPathCount("fft", "dac") * getPathCount("dac", "out") +
                getPathCount("svr", "dac") * getPathCount("dac", "fft") * getPathCount("fft", "out")
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day11_test")
//    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
