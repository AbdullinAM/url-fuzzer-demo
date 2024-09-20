package org.plan.research

import kotlin.random.Random

class StringMutator(
    private val random: Random,
    private val charset: Set<Char>,
) {
    private val operations = listOf(
        ::addRandomChar,
        ::removeRandomChar,
        ::changeRandomChar
    )

    private fun addRandomChar(string: String): String {
        val char = charset.random(random)
        if (string.isEmpty()) return char.toString()

        val index = string.indices.random(random)
        return string.substring(0, index) + char + string.substring(index)
    }

    private fun removeRandomChar(string: String): String {
        if (string.isEmpty()) return string

        val index = string.indices.random(random)
        return string.substring(0, index) + string.substring(index + 1)
    }

    private fun changeRandomChar(string: String): String {
        if (string.isEmpty()) return ""

        val index = string.indices.random(random)
        val char = charset.random(random)
        val ml = string.toMutableList()
        ml[index] = char
        return ml.joinToString("")
    }

    fun mutate(input: String): String = operations.random(random).invoke(input)
}
