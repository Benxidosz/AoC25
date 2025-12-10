import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): ULong {
        val tiles = MutableList(input.size) {
            val split = input[it].split(',')
            IPos(split[0].toInt(), split[1].toInt())
        }

        val pairs = mutableListOf<Pair<IPos, IPos>>()
        tiles.forEachIndexed { i, a ->
            for (j in i + 1 until tiles.size) {
                val b = tiles[j]
                pairs.add(a to b)
            }
        }

        return pairs.maxOf {
            val diff = (it.first - it.second).abs()
            (diff.x + 1).toULong() * (diff.y + 1).toULong()
        }
    }

    fun part2(input: List<String>): ULong {
        var width = 0
        var height = 0

        val tiles = MutableList(input.size) {
            val split = input[it].split(',')
            val pos = IPos(split[0].toInt(), split[1].toInt())
            if (pos.x > width) width = pos.x
            if (pos.y > height) height = pos.y
            pos
        }

        val pairs = mutableListOf<Pair<IPos, IPos>>()
        tiles.forEachIndexed { i, a ->
            for (j in i + 1 until tiles.size) {
                val b = tiles[j]
                pairs.add(a to b)
            }
        }

        tiles.add(tiles.first())
        val cyclicTiles = tiles.zipWithNext()
        val horizontalSides = cyclicTiles.filter { side ->
            side.first.y == side.second.y
        }.map {
            IPos(min(it.first.x, it.second.x), it.first.y) to IPos(max(it.first.x, it.second.x), it.second.y)
        }
        val verticalSides = cyclicTiles.filter { side ->
            side.first.x == side.second.x
        }.map {
            IPos(it.first.x, min(it.first.y, it.second.y)) to IPos(it.second.x, max(it.first.y, it.second.y))
        }

        val testValues = mutableMapOf<IPos, Boolean>()
        fun testPoint(point: IPos): Boolean {
            return testValues.getOrPut(point) {
                horizontalSides.forEach { side ->
                    if (point in side) {
                        // Vertical Border
                        return@getOrPut true
                    }
                }
                var intersection = 0
                verticalSides.forEach { side ->
                    if (point in side) {
                        // Vertical Border
                        return@getOrPut true
                    }

                    if (point.x > side.first.x && point.y > side.first.y && point.y <= side.second.y) {
                        ++intersection
                    }
                }
                return@getOrPut intersection % 2 == 1
            }
        }

        fun testRect(rect: Pair<IPos, IPos>): Boolean {
            val minCorner = IPos(min(rect.first.x, rect.second.x), min(rect.first.y, rect.second.y))
            val a = IPos(min(rect.first.x, rect.second.x), max(rect.first.y, rect.second.y))
            val b = IPos(max(rect.first.x, rect.second.x), min(rect.first.y, rect.second.y))
            val maxCorner = IPos(max(rect.first.x, rect.second.x), max(rect.first.y, rect.second.y))

            if (!(testPoint(minCorner) && testPoint(a) && testPoint(b) && testPoint(maxCorner))) return false

            for (x in minCorner.x + 1..< maxCorner.x) {
                for (y in minCorner.y + 1..< maxCorner.y) {
                    if (!testPoint(IPos(x, y))) {
                        return false
                    }
                }
            }

            return true
        }

        return pairs.maxOf {
            if (!testRect(it)) 0UL
            else {
                val diff = (it.first - it.second).abs()
                (diff.x + 1).toULong() * (diff.y + 1).toULong()
            }
        }
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day09_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
