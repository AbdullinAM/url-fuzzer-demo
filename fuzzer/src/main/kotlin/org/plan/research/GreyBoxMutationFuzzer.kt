package org.plan.research

import kotlin.time.Duration

open class GreyBoxMutationFuzzer(
) : MutationFuzzer() {
    override fun fuzz(
        seeds: List<String>,
        target: (String) -> ExecutionResult,
        timeout: Duration,
        mutationsPerAttempt: IntRange
    ) = TODO()

    protected fun runWithCoverage(
        input: String,
        target: (String) -> ExecutionResult,
    ): Pair<Boolean, ExecutionResult> = TODO()
}
