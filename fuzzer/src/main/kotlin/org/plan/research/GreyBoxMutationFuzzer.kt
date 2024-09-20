package org.plan.research

import kotlin.time.Duration

open class GreyBoxMutationFuzzer(
) : MutationFuzzer() {
    private val coveredLines = mutableSetOf<String>()

    override fun fuzz(
        seeds: List<String>,
        target: (String) -> ExecutionResult,
        timeout: Duration,
        mutationsPerAttempt: IntRange
    ) {
        val mutants = seeds.toMutableSet()

        var attempts = 0
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < timeout.inWholeMilliseconds) {
            ++attempts
            var candidate = mutants.random(random)
            repeat(mutationsPerAttempt.random(random)) {
                candidate = mutator.mutate(candidate)
            }

            val (isNewCovered, result) = runWithCoverage(candidate, target)
            if (isNewCovered) {
                println("Attempt $attempts: mutant $candidate, found new coverage! Current coverage: $coveredLines")
                mutants.add(candidate)
            }

            if (result == ExecutionResult.FAIL) {
                println("Attempt $attempts: found error on input $candidate")
            }
        }
    }

    protected fun runWithCoverage(
        input: String,
        target: (String) -> ExecutionResult,
    ): Pair<Boolean, ExecutionResult> {
        CoverageTracker.coverage.clear()
        val result = target(input)
        val newCoverage = CoverageTracker.coverage - coveredLines
        coveredLines.addAll(newCoverage)
        return newCoverage.isNotEmpty() to result
    }
}
