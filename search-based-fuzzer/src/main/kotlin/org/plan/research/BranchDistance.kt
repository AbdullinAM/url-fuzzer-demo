package org.plan.research

import kotlin.math.abs
import kotlin.math.min

object BranchDistance {
    val distancesTrue = mutableMapOf<String, Int>()
    val distancesFalse = mutableMapOf<String, Int>()

    enum class Operation {
        EQUALS, LESS, IN,
    }
    
    private fun convertToNumber(value: Any) = when (value) {
        is String -> value[0].code
        is Char -> value.code
        is Int -> value
        else -> -1
    }

    fun evaluateCondition(id: String, op: Operation, lhs: Any, rhs: Any): Boolean {
        val lhsValue = convertToNumber(lhs)
        val rhsValue = convertToNumber(rhs)
        var distanceTrue = 0
        var distanceFalse = 0

        when (op) {
            Operation.EQUALS -> {
                if (lhs == rhs) {
                    distanceFalse = 1
                    distanceTrue = 0
                } else {
                    distanceTrue = abs((lhsValue - rhsValue).toDouble()).toInt()
                    distanceFalse = 0
                }
            }
            Operation.LESS -> {
                if (lhsValue < rhsValue) {
                    distanceFalse = rhsValue - lhsValue
                    distanceTrue = 0
                } else {
                    distanceTrue = lhsValue - rhsValue + 1
                    distanceFalse = 0
                }
            }
            Operation.IN -> {
                var minimum = Int.MAX_VALUE
                if (rhs is Map<*, *>) {
                    for (elem in rhs.keys) {
                        val distance =
                            abs((lhsValue - (elem as Char).code).toDouble()).toInt()
                        if (distance < minimum) {
                            minimum = distance
                        }
                    }
                    distanceTrue = minimum
                    if (distanceTrue == 0) {
                        distanceFalse = 1
                    } else {
                        distanceFalse = 0
                    }
                }
            }
        }

        distancesTrue[id] = min(distancesTrue.getOrDefault(id, Int.MAX_VALUE), distanceTrue)
        distancesFalse[id] = min(distancesFalse.getOrDefault(id, Int.MAX_VALUE), distanceFalse)

        return distanceTrue == 0
    }
}