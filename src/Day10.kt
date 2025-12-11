import com.microsoft.z3.Context
import com.microsoft.z3.Status

fun main() {
    fun part1(input: List<String>): Int {
        var solution = 0

        input.forEach { line ->
            val lights = line
                .substring(line.indexOf("[") + 1, line.indexOf("]"))
                .toList()
                .map { if (it == '#') true else false }

            val buttons = line
                .substring(line.indexOf("]") + 2, line.indexOf("{") - 1)
                .split(' ')
                .map { buttonDef ->
                    buttonDef
                        .removePrefix("(")
                        .removeSuffix(")")
                        .split(',').map { it.toInt() }
                }

            val open = mutableListOf(Triple(List(lights.size) { false }, 0, listOf<List<Int>>()))
            while (open.isNotEmpty()) {
                val current = open.first()
                open.removeFirst()
                if (current.first == lights) {
                    solution += current.second
                    break
                }
                buttons.forEach { button ->
                    if (button in current.third) { return@forEach }
                    val result = current.first.toMutableList()
                    button.forEach { result[it] = !result[it] }
                    open.add(Triple(result, current.second + 1, List(current.third.size + 1) {
                        if (it in current.third.indices) current.third[it] else button
                    }))
                }
            }
        }
        return solution
    }

    fun part2(input: List<String>): Int {
        var solution = 0

        input.forEach { line ->
            val buttons = line
                .substring(line.indexOf("]") + 2, line.indexOf("{") - 1)
                .split(' ')
                .map { buttonDef ->
                    buttonDef
                        .removePrefix("(")
                        .removeSuffix(")")
                        .split(',').map { it.toInt() }
                }

            val costs = line
                .substring(line.indexOf("{") + 1, line.indexOf("}"))
                .split(',')
                .map { it.toInt() }

            solution += Context().use { ctx ->
                val opt = ctx.mkOptimize()
                val vars = buttons.indices.map { ctx.mkIntConst("b$it") }
                vars.forEach { opt.Add(ctx.mkGe(it, ctx.mkInt(0))) }
                costs.forEachIndexed { costId, cost ->
                    val terms = buttons.withIndex().filter { costId in it.value }.map { vars[it.index] }
                    if (terms.isNotEmpty()) {
                        val sum = if (terms.size == 1) terms[0]
                        else ctx.mkAdd(*terms.toTypedArray())
                        opt.Add(ctx.mkEq(sum, ctx.mkInt(cost)))
                    } else if (cost != 0) return@use 0
                }
                opt.MkMinimize(ctx.mkAdd(*vars.toTypedArray()))
                if (opt.Check() == Status.SATISFIABLE) {
                    vars.sumOf { opt.model.evaluate(it, false).toString().toInt() }
                } else 0
            }

        }

        return solution
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 3)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
