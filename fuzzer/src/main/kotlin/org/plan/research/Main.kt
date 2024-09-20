package org.plan.research

import kotlin.time.Duration.Companion.minutes

fun example(input: String) {
    if (input.length == 3) {
        if (input[0] == 'a') { println("a");            throw RuntimeException("a") }
        if (input[1] == 'b') { println("b"); throw RuntimeException("b")
        }
        if (input[2] == 'b') {
            println("c")
            throw RuntimeException("c")
        }
    }
}

fun main() {
    val mutator = GreyBoxMutationFuzzer()
//    val mutator = MutationFuzzer()

    val target = { input: String ->
        try {
            example(input)
            ExecutionResult.PASS
        } catch (e: Throwable) {
            ExecutionResult.FAIL
        }
    }

//    val target = { input: String ->
//        try {
//            URLValidator.validate(input)
//            ExecutionResult.PASS
//        } catch (e: IllegalArgumentException) {
//            ExecutionResult.PASS
//        } catch (e: Throwable) {
//            ExecutionResult.FAIL
//        }
//    }

//    val target = { input: String ->
//        try {
//            CgiDecoder().cgiDecode(input)
//            ExecutionResult.PASS
//        } catch (e: IllegalArgumentException) {
//            ExecutionResult.PASS
//        } catch (e: Throwable) {
//            ExecutionResult.FAIL
//        }
//    }

    mutator.fuzz(
        listOf(""),
        target,
        timeout = 1.minutes,
        1..5
    )
}
