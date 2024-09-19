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
        TODO("Not yet implemented")
    }
}
