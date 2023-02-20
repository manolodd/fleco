/* 
 * Open Licensing Risk Analysis Engine (Open LRAE) is a licensing risk analysis 
 * engine in the form of Java library that allow the detection of risks related 
 * to licensing from the set of components (and their respective licenses) you
 * are using in a given project.
 * 
 * Copyright (C) Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com.
 * 
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program. If not, see 
 * https://www.gnu.org/licenses/lgpl-3.0.en.html.
 */
package com.manolodominguez.fleco.algorithm;

import com.manolodominguez.fleco.strategicgoals.StrategicGoals;
import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

/**
 * The present class implements the FLECO (Fast, Lightweight, and Efficient
 * Cybersecurity Optimization) Dynamic Genetic Algorithm. This genetic algorithm
 * is designed to assist the Asset's Cybersecurity Committee (ACC) in making
 * decisions during the application of CyberTOMP(1), aimed at managing
 * comprehensive cybersecurity at both tactical and operational levels.
 *
 * (1) Dominguez-Dorado, M., Carmona-Murillo, J., Cortés-Polo, D., and
 * Rodríguez-Pérez, F. J. (2022). CyberTOMP: A novel systematic framework to
 * manage asset-focused cybersecurity from tactical and operational levels. IEEE
 * Access, 10, 122454-122485.
 *
 * @author Manuel Domínguez Dorado
 */
public class FLECO {

    private float mutationProbability;
    private float crossoverProbability;
    private ImplementationGroups implementationGroup;
    private int maxGenerations;
    private int initialPopulation;
    private Population population;
    private StrategicGoals strategicGoals;
    private Chromosome initialStatus;
    private float requiredTime;
    private int requiredGenerations;

    private static final float DEFAULT_LOCAL_MINIMUM_PROBABILITY = 0.0f;
    private static final float LOCAL_MINIMUM_PROBABILITY_THRESSHOLD = 0.05f;
    private static final int DEFAULT_MUTATION_INCREASING_FACTOR = 1;
    private static final int HIGHER_MUTATION_INCREASING_FACTOR = 100;
    private static final float POPULATION_INCREASING_FACTOR = 1.5f;
    private static final int BEST_CHROMOSOME_INDEX = 0;
    private static final int REPORTING_CYCLE = 100;

    /**
     * This is the constructor of the class. It creates a new instance of FLECO
     * (Fast, Lightweight, and Efficient Cybersecurity Optimization) Dynamic
     * Genetic Algorithm with the parameters specified.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialPopulation The initial number of chromosomes in the
     * population.
     * @param maxGenerations The max number of generations before finishing the
     * population's evolution.
     * @param mutationProbability The probability of mutating a chromosome
     * during population's evolution.
     * @param crossoverProbability The probability of crossing over a couple of
     * chromosomes during population's evolution.
     * @param implementationGroup The applicable implementation group as defined
     * in CyberTOMP. it can be IG1, IG2 and IG3 depending on whether the asset
     * criticality is LOW, MEDIUM or HIGH.
     * @param initialStatus A chromosome representing the initial cybersecurity
     * status of the asset, as defined in CyberTOMP.
     * @param strategicGoals A set of constraints/goals over the asset,
     * functions, categories or expected outcomes, that are understood as the
     * strategic cybersecurity goals.
     */
    public FLECO(int initialPopulation, int maxGenerations, float mutationProbability, float crossoverProbability, ImplementationGroups implementationGroup, Chromosome initialStatus, StrategicGoals strategicGoals) {
        this.implementationGroup = implementationGroup;
        this.initialStatus = initialStatus;
        this.strategicGoals = strategicGoals;
        this.initialPopulation = initialPopulation;
        this.maxGenerations = maxGenerations;
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = crossoverProbability;
        requiredTime = 0.0f;
        requiredGenerations = 0;
        population = new Population(initialPopulation, this.implementationGroup, this.initialStatus, this.strategicGoals);
    }

    /**
     * The population is developed according to FLECO principles using this
     * approach, until either the algorithm reaches convergence or the maximum
     * number of generations is attained.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void evolve() {
        float localMinimumProbability = 0.0f;
        int currentGeneration = 0;
        float currentBestFitness = 0.0f;
        int mutationIncreasingFactor = 1;
        float localMinimumprobabilityIncrement = 1.0f / maxGenerations;
        boolean isInALocalMinimum = false;
        Temporal begin = Instant.now();
        Temporal end;
        Duration duration;
        requiredTime = 0.0f;
        while (!hasToFinish(currentGeneration)) {
            // The probability of being in a local minimum is raised each time 
            // the best fitness remains constant. Otherwise, the probability is 
            // reset to its default value.
            if (currentBestFitness == population.get(BEST_CHROMOSOME_INDEX).getFitness()) {
                localMinimumProbability += localMinimumprobabilityIncrement;
            } else {
                localMinimumProbability = DEFAULT_LOCAL_MINIMUM_PROBABILITY;
                currentBestFitness = population.get(BEST_CHROMOSOME_INDEX).getFitness();
            }
            // Once the cumulative probability of being in a local minimum 
            // surpasses the predetermined threshold, the algorithm is 
            // considered to be in a local minimum, requiring an escape plan.
            isInALocalMinimum = (localMinimumProbability >= LOCAL_MINIMUM_PROBABILITY_THRESSHOLD);
            // If the algorithm is in a local minimum, it amplifies the mutation
            // rate to the predefined higher value; otherwise, it resets the 
            // rate to the default value.
            if (isInALocalMinimum) {
                mutationIncreasingFactor = HIGHER_MUTATION_INCREASING_FACTOR;
            } else {
                mutationIncreasingFactor = DEFAULT_MUTATION_INCREASING_FACTOR;
            }
            // Output relevant information as necessary.
            if ((currentGeneration % REPORTING_CYCLE) == 0) {
                System.out.println("Generation: " + currentGeneration + "   Current best solution: " + population.get(BEST_CHROMOSOME_INDEX).getFitness() + "   Population size: " + population.size() + "   Population's average fitness: " + population.getFitnessAverage());
            }
            // Calculate the fitness and arrange the population accordingly, 
            // taking it into consideration.
            population.selectBestAdapted();
            // If the algorithm is trapped in a local minimum, it removes the 
            // currently selected best chromosome and injects a predefined 
            // quantity of random chromosomes into the population to enhance 
            // diversity. 
            if (isInALocalMinimum) {
                if (!population.isEmpty()) {
                    population.remove(BEST_CHROMOSOME_INDEX);
                }
                population.populateRandomly((int) (initialPopulation * POPULATION_INCREASING_FACTOR));
            }
            // To prevent uncontrolled growth, reduce the population to the 
            // default number of chromosomes.
            population.reduceTo(initialPopulation);
            // Apply a mutation to the population with a predefined probability,
            // which can be raised if the algorithm is in a local minimum.
            population.mutate(mutationProbability * mutationIncreasingFactor);
            // Perform a crossover on the population.
            population.crossover(crossoverProbability);
            // Increases the generation number
            currentGeneration++;
        }
        end = Instant.now();
        duration = Duration.between(begin, end);
        requiredTime = (duration.get(ChronoUnit.SECONDS) + (duration.get(ChronoUnit.NANOS) / 1000000000.0f));
        requiredGenerations = currentGeneration;
    }

    /**
     * This method returns the number of seconds the execution of FLECO has
     * lasted.
     *
     * @author Manuel Domínguez-Dorado
     * @return the number of seconds the execution of FLECO has lasted.
     */
    public float getRequiredTime() {
        return requiredTime;
    }

    /**
     * This method returns the number of generations the execution of FLECO has
     * required.
     *
     * @author Manuel Domínguez-Dorado
     * @return the number of generations the execution of FLECO has required.
     */
    public int getRequiredGenerations() {
        return requiredGenerations;
    }

    /**
     * This method check whether the conditions to finish FLECO algorithm exist
     * or not.
     *
     * @param currentGenerationNumber the current generation number in the
     * FLECO's execution.
     *
     * @author Manuel Domínguez-Dorado
     * @return true, if the conditions to finish FLECO execution exist.
     * Otherwise return false.
     */
    private boolean hasToFinish(int currentGenerationNumber) {
        return population.hasConverged() || (currentGenerationNumber >= maxGenerations);
    }

    /**
     * This method returns whether FLECO algorithm has converged or not.
     *
     * @author Manuel Domínguez-Dorado
     *
     * @return true if FLECO algorithm has converged. Otherwise, return false.
     */
    public boolean hasConverged() {
        return population.hasConverged();
    }

    /**
     * This method returns the chromosome with the best fitness in the
     * population.
     *
     * @author Manuel Domínguez-Dorado
     *
     * @return The chromosome with the best fitness in the population.
     */
    public Chromosome getBestChromosome() {
        return population.get(BEST_CHROMOSOME_INDEX);
    }
}
