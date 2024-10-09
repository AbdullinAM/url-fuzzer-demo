package org.plan.research

import kotlin.math.abs

const val MIN_INT = -100
const val MAX_INT = 100

// code under test
fun testMe(x: Int, y: Int): Boolean {
    if (x == 2 * (y + 1))
        return true
    else
        return false
}
/***
 * testMe(0, 0) -> False
 * testMe(4, 2) -> False
 * testMe(22, 10) -> True
 */

fun Pair<Int, Int>.isValid(): Boolean =
    this.first in MIN_INT..MAX_INT && this.second in MIN_INT..MAX_INT

fun Pair<Int, Int>.getNeighbours(): List<Pair<Int, Int>> = buildList {
    for (dx in -1..1) {
        for (dy in -1..1) {
            val neighbour = Pair(first + dx, second + dy)
            if ((dx != 0 || dy != 0) && neighbour.isValid()) {
                add(neighbour)
            }
        }
    }
}

fun calculateDistance(x: Int, y: Int): Int {
    return abs(x - 2 * (y + 1))
}

fun hillclimber() {
    TODO("Implement during the lecture")
}

fun testMe2(x: Int, y: Int): Boolean {
    if (x * x == y * y * (x % 20))
        return true
    else
        return false
}

fun hillclimber2() {
    TODO("Test \"testMe2\" function")
}

fun main() {
    hillclimber()
//    hillclimber2()
}