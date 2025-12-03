fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            var dec: Int
            var one: Int

            val batteryList = line.map { it.digitToInt() }

            dec = batteryList.take(batteryList.size - 1).max()
            one = batteryList.takeLast(batteryList.size - 1 - batteryList.indexOf(dec)).max()

            result += dec * 10 + one
        }
        return result
    }

    fun part2(input: List<String>): ULong {
        var result = 0UL


        input.forEach { line ->

            val digitList = mutableListOf<Int>()
            val placeList = mutableListOf<Int>()

            val batteryList = line.map { it.digitToInt() }

            repeat(12) { place ->
                if (place == 0) {
                    digitList.add(batteryList.take(batteryList.size - 11).max())
                    placeList.add(batteryList.indexOf(digitList[0]))
                } else {
                    val prevPlace = placeList[place - 1]
                    val searchList = batteryList
                        .takeLast(batteryList.size - 1 - prevPlace)
                        .take(batteryList.size - 1 - prevPlace - 12 + place + 1)
                    digitList.add(searchList.max())
                    placeList.add(prevPlace + 1 + searchList.indexOf(digitList[place]))
                }
            }

//            val tmpResult = digitList.foldIndexed(0UL){ i, sum, value ->
//                sum + (value.toULong() * 10.toFloat().pow(digitList. size - 1 - i).toULong())
//            }
            result += digitList.joinToString("") { it.toString() }.toULong()
        }

        return result
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
