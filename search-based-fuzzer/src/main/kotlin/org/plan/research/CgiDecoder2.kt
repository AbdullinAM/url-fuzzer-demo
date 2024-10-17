package org.plan.research

import kotlin.random.Random

class CgiDecoder2 {

    fun cgiDecode(s: String): String {
        val hexValues = mapOf(
            '0' to 0, '1' to 1, '2' to 2, '3' to 3, '4' to 4,
            '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9,
            'a' to 10, 'b' to 11, 'c' to 12, 'd' to 13, 'e' to 14, 'f' to 15,
            'A' to 10, 'B' to 11, 'C' to 12, 'D' to 13, 'E' to 14, 'F' to 15,
        )

        return buildString {
            var i = 0
            while (i < s.length) {
                val c = s[i]
                if (c == '+') {
                    append(' ')
                } else if (c == '%') {
                    val digitHigh = s[i + 1]
                    val digitLow = s[i + 2]
                    i += 2
                    if (hexValues.containsKey(digitHigh) && hexValues.containsKey(digitLow)) {
                        val v = hexValues[digitHigh]!! * 16 + hexValues[digitLow]!!
                        append(v.toChar())
                    } else {
                        throw IllegalArgumentException("Invalid encoding")
                    }
                } else {
                    append(c)
                }
                i++
            }
        }
    }

    fun cgiDecodeInstrumented(s: String): String {
        val hexValues = mapOf(
            '0' to 0, '1' to 1, '2' to 2, '3' to 3, '4' to 4,
            '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9,
            'a' to 10, 'b' to 11, 'c' to 12, 'd' to 13, 'e' to 14, 'f' to 15,
            'A' to 10, 'B' to 11, 'C' to 12, 'D' to 13, 'E' to 14, 'F' to 15,
        )

        return buildString {
            var i = 0
            while (BranchDistance.evaluateCondition("i < s.length", BranchDistance.Operation.LESS, i, s.length)) {
                val c = s[i]
                if (BranchDistance.evaluateCondition("c == '+'", BranchDistance.Operation.EQUALS, c, '+')) {
                    append(' ')
                } else if (BranchDistance.evaluateCondition("c == '%'", BranchDistance.Operation.EQUALS, c, '%')) {
                    val digitHigh = s[i + 1]
                    val digitLow = s[i + 2]
                    i += 2
                    if (BranchDistance.evaluateCondition(
                            "hexValues.containsKey(digitHigh)",
                            BranchDistance.Operation.IN,
                            digitHigh,
                            hexValues
                        )
                        && BranchDistance.evaluateCondition(
                            "hexValues.containsKey(digitLow)",
                            BranchDistance.Operation.IN,
                            digitLow,
                            hexValues
                        )
                    ) {
                        val v = hexValues[digitHigh]!! * 16 + hexValues[digitLow]!!
                        append(v.toChar())
                    } else {
                        throw IllegalArgumentException("Invalid encoding")
                    }
                } else {
                    append(c)
                }
                i++
            }
        }
    }
}

fun String.neighborStrings(): List<String> = buildList {
    for (pos in this@neighborStrings.indices) {
        val c = this@neighborStrings[pos]
        if (c.code < 126) {
            add(substring(0, pos) + (c.code + 1).toChar() + substring(pos + 1))
        }
        if (c.code > 32) {
            add(substring(0, pos) + (c.code - 1).toChar() + substring(pos + 1))
        }
    }
}

fun normalize(x: Int) = x.toDouble() / (x + 1)

fun callCgiDecode(s: String): Double {
    BranchDistance.distancesTrue.clear()
    BranchDistance.distancesFalse.clear()

    var fitness = 0.0
    try {
        CgiDecoder2().cgiDecodeInstrumented(s)
    } catch (e: Throwable) {
    }

    val trueScores = setOf("i < s.length", "c == '+'", "c == '%'", "hexValues.containsKey(digitHigh)", "hexValues.containsKey(digitLow)")
    val falseScores = setOf("c == '%'", "hexValues.containsKey(digitHigh)", "hexValues.containsKey(digitLow)")
    for (id in trueScores) {
        fitness += normalize(BranchDistance.distancesTrue[id] ?: 1)
    }
    for (id in falseScores) {
        fitness += normalize(BranchDistance.distancesFalse[id] ?: 1)
    }

    return fitness
}

fun hillclimbCgi() {
    val random = Random.Default
    var current = random.randomString(4)
    var minDistance = callCgiDecode(current)
    println(
        "Distance: $minDistance, $current"
    )
    while (minDistance != 0.0) {
        println(
            "Distance: $minDistance, $current"
        )
        for (neighbour in current.neighborStrings()) {
            val newDistance = callCgiDecode(neighbour)
            if (distance < minDistance) {
                minDistance = newDistance
                current = neighbour
                println(
                    "New Distance: $minDistance, $current"
                )
            }
        }
        println(
            "Distance: $minDistance, $current"
        )
    }
}

fun main() {
    hillclimbCgi()
}

