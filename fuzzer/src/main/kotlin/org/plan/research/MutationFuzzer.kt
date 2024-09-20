package org.plan.research

import kotlin.random.Random
import kotlin.time.Duration

val ASCII_CHARSET = (32..126).map { it.toChar() }.toSet()

enum class ExecutionResult {
    PASS, FAIL
}

open class MutationFuzzer {
    protected val random: Random = Random.Default
    protected val mutator = StringMutator(random, ASCII_CHARSET)

    open fun fuzz(
        seeds: List<String>,
        target: (String) -> ExecutionResult,
        timeout: Duration = Duration.INFINITE,
        mutationsPerAttempt: IntRange
    ) {
        var attempts = 0
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < timeout.inWholeMilliseconds) {
            ++attempts
            var candidate = seeds.random(random)
            repeat(mutationsPerAttempt.random(random)) {
                candidate = mutator.mutate(candidate)
            }

            if (target(candidate) == ExecutionResult.FAIL) {
                println("Attempt $attempts, found error on input $candidate")
            }
        }
    }
}
