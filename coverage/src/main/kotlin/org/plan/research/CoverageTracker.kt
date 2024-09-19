package org.plan.research.org.plan.research

object CoverageTracker {
    val coverage = linkedSetOf<String>()
    val fullCoverage = linkedSetOf<String>()


    @Suppress("unused")
    fun logCoverage(methodSignature: String, lineNumber: String) {
        coverage.add("$methodSignature:$lineNumber")
    }

    fun logFullCoverage(methodSignature: String, lineNumber: String) {
        fullCoverage.add("$methodSignature:$lineNumber")
    }
}
