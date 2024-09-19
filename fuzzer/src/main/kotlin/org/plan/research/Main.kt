package org.plan.research

fun target(input: String) {
    if (input.length == 3) {
        if (input[0] == 'a') {
            println("a")
            throw RuntimeException("a")
        }
        if (input[1] == 'b') {
            println("b")
            throw RuntimeException("b")
        }
        if (input[2] == 'b') {
            println("c")
            throw RuntimeException("c")
        }
    }
}

fun main() {
}
