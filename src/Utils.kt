import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

data class IPos(var x: Int, var y: Int) {
    operator fun plus(other: IPos) = IPos(x + other.x, y + other.y)
    operator fun minus(other: IPos) = IPos(x - other.x, y - other.y)
    operator fun times(other: Int) = IPos(x * other, y * other)
    operator fun div(other: Int) = IPos(x / other, y / other)
    operator fun plusAssign(other: IPos) {
        x += other.x
        y += other.y
    }
    operator fun minusAssign(other: IPos) {
        x -= other.x
        y -= other.y
    }
    operator fun timesAssign(other: Int) {
        x *= other
        y *= other
    }
    operator fun divAssign(other: Int) {
        x /= other
        y /= other
    }
    operator fun unaryMinus() = IPos(-x, -y)
    fun rotate90CW() = IPos(y, x * -1)
    fun rotate90CCW() = IPos(y * -1, x)
    fun length() = x + y
}

fun <T> List<T>.containsAny(other: List<T>): Boolean {
    other.forEach {
        if (contains(it)) {
            return true
        }
    }
    return false
}

fun <T> List<List<T>>.isValidIndex(pos: IPos): Boolean {
    return pos.y in indices && pos.x in get(pos.y).indices
}

fun <T> List<List<T>>.get(pos: IPos) = get(pos.y)[pos.x]

fun <T> List<List<T>>.forEachIndexed(action: (x: Int, y: Int, row: List<T>, value: T) -> Unit) = forEachIndexed { y, row ->
    row.forEachIndexed { x, value ->
        action(x, y, row, value)
    }
}

fun <T, R> List<List<T>>.mapIndexed(action: (x: Int, y: Int, row: List<T>, value: T) -> R): List<List<R>> = mapIndexed { y, row ->
    row.mapIndexed { x, value ->
        action(x, y, row, value)
    }
}

fun <T> List<MutableList<T>>.set(pos: IPos, value: T) {
    get(pos.y)[pos.x] = value
}

fun ULong.con(other: ULong) = (toString() + other.toString()).toULong()

fun gcd(a: Long, b: Long): Long {
    var tmpA = a
    var tmpB = b
    while (tmpB > 0) {
        val tmp = tmpB
        tmpB = tmpA % tmpB
        tmpA = tmp
    }
    return tmpA
}

fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}