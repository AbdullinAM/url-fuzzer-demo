package org.plan.research

import kotlin.random.Random

typealias Species = String
typealias Population = List<Species>
typealias EvaluatedPopulation = List<Pair<Species, Double>>

const val STRING_LENGTH = 10

/***
 * Create an initial population
 */
fun Random.createPopulation(size: Int): Population = buildList {
    repeat(size) {
        this@buildList.add(randomUnicodeString(STRING_LENGTH))
    }
}

/***
 * Evaluate each species in the population
 */
fun Population.evaluate(): EvaluatedPopulation = this.map { it to callCgiDecode(it) }

/***
 * Select a candidate species from the population using tournament of `tournamentSize` species
 */
fun EvaluatedPopulation.select(random: Random, tournamentSize: Int): Species = buildList {
    repeat(tournamentSize) {
        val randomIndex = random.nextInt(0, this@select.size)
        add(this@select[randomIndex])
    }
}.minBy { it.second }.first

/***
 * Crossover between two species
 */
fun crossover(random: Random, lhv: Species, rhv: Species): Pair<Species, Species> {
    val randomIndex = random.nextInt(0, lhv.length)
    val first = lhv.substring(0, randomIndex) + rhv.substring(randomIndex)
    val second = rhv.substring(0, randomIndex) + lhv.substring(randomIndex)
    return first to second
}

/***
 * Mutate the species
 */
fun Species.mutate(random: Random): Species {
    val randomIndex = random.nextInt(0, this.length)
    val char = this[randomIndex]
    val switch = random.nextGaussian() * 100
    val newChar = (char.code + switch).toInt().toChar()

    val sb = StringBuilder(this)
    sb[randomIndex] = newChar
    return sb.toString()
}

/***
 * Generate a new population from the existing one
 */
fun EvaluatedPopulation.generateNewPopulation(random: Random): Population = buildList {
    while (this@buildList.size < this@generateNewPopulation.size) {
        val first = this@generateNewPopulation.select(random, 10)
        if (random.nextDouble() < 0.2) {
            this@buildList.add(first.mutate(random))
        } else {
            val second = this@generateNewPopulation.select(random, 10)
            val (nf, ns) = crossover(random, first, second)
            this@buildList.add(nf.mutate(random))
            this@buildList.add(ns.mutate(random))
        }
    }
}


/***
 * Implement a search-based fuzzer that uses genetic search algorithm
 */
fun geneticCgi(): Unit {
    val random = Random(System.currentTimeMillis())
    var population = random.createPopulation(100)
    var fitness = population.evaluate()
    var best = fitness.minBy { it.second }

    println("Best initial seed is: $best")

    var generation = 0

    while (best.second > 0.0) {
        population = fitness.generateNewPopulation(random)
        fitness = population.evaluate()
        best = fitness.minBy { it.second }
        ++generation
        println("Generation $generation, best $best")
    }

    println("Best value is: $best")
}

fun main() {
    geneticCgi()
}