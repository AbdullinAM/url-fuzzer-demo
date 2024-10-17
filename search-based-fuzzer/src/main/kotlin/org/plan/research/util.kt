package org.plan.research

import kotlin.random.Random

fun Random.randomString(length: Int): String = buildString {
    for (i in 0 until length) {
        append((nextInt(95) + 32).toChar())
    }
}

fun Random.randomUnicodeString(length: Int): String = buildString {
    for (i in 0 until length) {
        append(nextInt(65536).toChar())
    }
}

private var haveNextNextGaussian = false
private var nextNextGaussian = Double.NaN
fun Random.nextGaussian(): Double {
    // See Knuth, TAOCP, Vol. 2, 3rd edition, Section 3.4.1 Algorithm C.
    if (haveNextNextGaussian) {
        haveNextNextGaussian = false
        return nextNextGaussian
    } else {
        var v1: Double
        var v2: Double
        var s: Double
        do {
            v1 = 2 * nextDouble() - 1 // between -1 and 1
            v2 = 2 * nextDouble() - 1 // between -1 and 1
            s = v1 * v1 + v2 * v2
        } while (s >= 1 || s == 0.0)
        val multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s)
        nextNextGaussian = v2 * multiplier
        haveNextNextGaussian = true
        return v1 * multiplier
    }
}