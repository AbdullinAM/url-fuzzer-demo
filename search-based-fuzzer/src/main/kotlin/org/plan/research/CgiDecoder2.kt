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

fun Random.randomString(length: Int): String = buildString {
    for (i in 0 until length) {
        append((nextInt(95) + 32).toChar())
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
    var fitness = 0.0
    try {
        CgiDecoder2().cgiDecode(s)
    } catch (e: Throwable) {
    }

    // TODO

    return fitness
}

fun hillclimbCgi() {
    TODO("")
}

fun main() {
    hillclimbCgi()
}

