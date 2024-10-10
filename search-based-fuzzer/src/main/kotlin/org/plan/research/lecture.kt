package org.plan.research

import kotlin.math.abs
import kotlin.random.Random

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

// code under test
var distance = MAX_INT
fun testMeInstrumented(x: Int, y: Int): Boolean {
    distance = calculateDistance(x, y)
    if (x == 2 * (y + 1))
        return true
    else
        return false
}

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

fun Random.generatePair(): Pair<Int, Int> =
    nextInt(MIN_INT, MAX_INT) to nextInt(MIN_INT, MAX_INT)

fun hillclimber() {
    val random = Random.Default
    var current = random.generatePair()
    testMeInstrumented(current.first, current.second)
    var minDistance = distance
    println(
        "Distance: $minDistance, " +
                "x: ${current.first}, y: ${current.second}"
    )
    while (minDistance != 0) {
        for (neighbour in current.getNeighbours()) {
            testMeInstrumented(neighbour.first, neighbour.second)
            if (distance < minDistance) {
                minDistance = distance
                current = neighbour
                println(
                    "Distance: $minDistance, " +
                            "x: ${current.first}, y: ${current.second}"
                )
            }
        }
    }
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