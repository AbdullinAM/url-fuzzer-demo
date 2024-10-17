package org.plan.research

import kotlin.random.Random

typealias Species = String
typealias Population = List<Species>
typealias EvaluatedPopulation = Map<Species, Double>

/***
 * Create an initial population
 */
fun Random.createPopulation(size: Int): Population = TODO()

/***
 * Evaluate each species in the population
 */
fun Population.evaluate(): EvaluatedPopulation = TODO()

/***
 * Select a candidate species from the population using tournament of `tournamentSize` species
 */
fun EvaluatedPopulation.select(tournamentSize: Int): Species = TODO()

/***
 * Crossover between two species
 */
fun crossover(lhv: Species, rhv: Species): Pair<Species, Species> = TODO()

/***
 * Mutate the species
 */
fun Species.mutate(): Species = TODO()


/***
 * Implement a search-based fuzzer that uses genetic search algorithm
 */
fun geneticCgi(): Unit = TODO()