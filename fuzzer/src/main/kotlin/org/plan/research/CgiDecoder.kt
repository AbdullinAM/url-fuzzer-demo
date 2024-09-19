package org.plan.research


class CgiDecoder {

    fun cgiDecode(s: String): String {
        // Mapping of hex digits to their integer values
        val hexValues: MutableMap<Char, Int> = HashMap()
        hexValues['0'] = 0
        hexValues['1'] = 1
        hexValues['2'] = 2
        hexValues['3'] = 3
        hexValues['4'] = 4
        hexValues['5'] = 5
        hexValues['6'] = 6
        hexValues['7'] = 7
        hexValues['8'] = 8
        hexValues['9'] = 9
        hexValues['a'] = 10
        hexValues['b'] = 11
        hexValues['c'] = 12
        hexValues['d'] = 13
        hexValues['e'] = 14
        hexValues['f'] = 15
        hexValues['A'] = 10
        hexValues['B'] = 11
        hexValues['C'] = 12
        hexValues['D'] = 13
        hexValues['E'] = 14
        hexValues['F'] = 15

        val t = StringBuilder()
        var i = 0
        while (i < s.length) {
            val c = s[i]
            if (c == '+') {
                t.append(' ')
            } else if (c == '%') {
                val digitHigh = s[i + 1]
                val digitLow = s[i + 2]
                i += 2
                if (hexValues.containsKey(digitHigh) && hexValues.containsKey(digitLow)) {
                    val v = hexValues[digitHigh]!! * 16 + hexValues[digitLow]!!
                    t.append(v.toChar())
                } else {
                    throw IllegalArgumentException("Invalid encoding")
                }
            } else {
                t.append(c)
            }
            i++
        }
        return t.toString()
    }
}
