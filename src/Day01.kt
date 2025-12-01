fun main() {
    fun part1(input: List<String>): Int {
        val rotations = input.map { code ->
            val pre = if(code.take(1) == "L") {
                -1
            } else {
                1
            }
            val value = code.substring(1).toInt()
            pre * value
        }

        var countZero = 0
        var value = 50

        rotations.forEach { rotation ->
            value += rotation
            while (value < 0) {
                value += 100
            }

            if (value >= 100) {
                value %= 100
            }

            if (value == 0) {
                ++countZero
            }
        }

        return countZero
    }

    fun part2(input: List<String>): Int {
        val rotations = input.map { code ->
            val pre = if(code.take(1) == "L") {
                -1
            } else {
                1
            }
            val value = code.substring(1).toInt()
            pre * value to code
        }

        var countZero = 0
        var value = 50

        rotations.forEach { rotation ->
            val prevValue = value
            value += rotation.first
            while (value < 0) {
                value += 100
                ++countZero
            }

            if (prevValue == 0 && rotation.first < 0) {
                --countZero
            }

            if (value == 0) {
                ++countZero
            }

            if (value >= 100) {
                countZero += value / 100
                value %= 100
            }
        }

        return countZero
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
