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

import com.manolodominguez.fleco.events.IProgressEventListener;
import com.manolodominguez.fleco.events.ProgressEvent;
import com.manolodominguez.fleco.events.RotaryIDGenerator;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
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
    private int maxSeconds;
    private int initialPopulation;
    private Population population;
    private StrategicConstraints strategicConstraints;
    private Chromosome initialStatus;
    private float requiredTime;
    private int requiredGenerations;
    private IProgressEventListener progressEventListener;
    private RotaryIDGenerator rotaryIDGenerator;

    private static final float LOCAL_MINIMUM_PROBABILITY_PERCENTAGE = 0.05f;
    private static final float TOO_MUCH_TIME_STAGNATED_FACTOR = 1.25f;
    private static final int DEFAULT_MUTATION_INCREASING_FACTOR = 1;
    private static final int HIGHER_MUTATION_INCREASING_FACTOR = 20;
    private static final float POPULATION_INCREASING_FACTOR = 1.50f;
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
     * @param maxSeconds The max number of seconds before finishing the
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
     * @param strategicConstraints A set of constraints over the asset,
     * functions, categories or expected outcomes.
     */
    public FLECO(int initialPopulation, int maxSeconds, float mutationProbability, float crossoverProbability, ImplementationGroups implementationGroup, Chromosome initialStatus, StrategicConstraints strategicConstraints) {
        this.implementationGroup = implementationGroup;
        this.initialStatus = initialStatus;
        this.strategicConstraints = strategicConstraints;
        this.initialPopulation = initialPopulation;
        this.maxSeconds = maxSeconds;
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = crossoverProbability;
        requiredTime = 0.0f;
        requiredGenerations = 0;
        population = new Population(initialPopulation, this.implementationGroup, this.initialStatus, this.strategicConstraints);
        rotaryIDGenerator = new RotaryIDGenerator();
        progressEventListener = null;
    }

    /**
     * This method sets the progress event listener for FLECO.
     *
     * @author Manuel Domínguez-Dorado
     * @param progressEventListener the progress event listener.
     */
    public void setProgressEventListener(IProgressEventListener progressEventListener) {
        if (this.progressEventListener != null) {
            throw new IllegalArgumentException("FLECO already has a progress event listener. Only one is allowed.");
        }
        this.progressEventListener = progressEventListener;
    }

    /**
     * The population is developed according to FLECO principles using this
     * approach, until either the algorithm reaches convergence or the maximum
     * number of generations is attained.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void evolve() {
        int currentGeneration = 0;
        float currentBestFitness = 0.0f;
        int mutationIncreasingFactor = 1;
        float localMinimumprobabilityThresshold = maxSeconds * LOCAL_MINIMUM_PROBABILITY_PERCENTAGE;
        boolean isInALocalMinimum = false;
        boolean tooMuchTimeInALocalMinimum = false;
        Temporal begin = Instant.now();
        Temporal end;
        Temporal latestBestFitnessChange = Instant.now();
        Duration duration;
        requiredTime = 0.0f;
        while (!hasToFinish(begin)) {
            // The probability of being in a local minimum is raised each time 
            // the best fitness remains constant. Otherwise, the probability is 
            // reset to its default value.
            if (currentBestFitness != population.get(BEST_CHROMOSOME_INDEX).getFitness()) {
                currentBestFitness = population.get(BEST_CHROMOSOME_INDEX).getFitness();
                latestBestFitnessChange = Instant.now();
            }
            // Once the cumulative probability of being in a local minimum 
            // surpasses the predetermined threshold, the algorithm is 
            // considered to be in a local minimum, requiring an escape plan.
            // When this period reach the double, it is considered to be too
            // much time.
            Duration stagnationTime = Duration.between(latestBestFitnessChange, Instant.now());
            isInALocalMinimum = false;
            tooMuchTimeInALocalMinimum = false;
            if (stagnationTime.get(ChronoUnit.SECONDS) > localMinimumprobabilityThresshold) {
                isInALocalMinimum = true;
                if (stagnationTime.get(ChronoUnit.SECONDS) > (localMinimumprobabilityThresshold * TOO_MUCH_TIME_STAGNATED_FACTOR)) {
                    tooMuchTimeInALocalMinimum = true;
                }
            }
            // If the algorithm is in a local minimum, it amplifies the mutation
            // rate to the predefined higher value; otherwise, it resets the 
            // rate to the default value.
            if (isInALocalMinimum) {
                mutationIncreasingFactor = HIGHER_MUTATION_INCREASING_FACTOR;
            } else {
                mutationIncreasingFactor = DEFAULT_MUTATION_INCREASING_FACTOR;
            }
            // Calculate the fitness and arrange the population accordingly. 
            // Reduce the population removing the worst individuals.
            population.selectBestAdapted();
            // Spread progress event.
            if (progressEventListener != null) {
                long totalTime = maxSeconds * 1000;
                long currentTime = Instant.now().toEpochMilli() - Instant.from(begin).toEpochMilli();
                ProgressEvent event = new ProgressEvent(this, rotaryIDGenerator.getNextIdentifier(), totalTime, currentTime, currentGeneration, population.get(0), population.hasConverged());
                progressEventListener.onProgressEventReceived(event);
            }
            // If the algorithm is trapped in a local minimum, injects a 
            // predefined quantity of random chromosomes into the population to 
            // increase diversity. Moreover, if it has been stagnated too much
            // time, it removes the currently selected best chromosome.
            if (isInALocalMinimum) {
                if (tooMuchTimeInALocalMinimum) {
                    if (!population.isEmpty()) {
                        population.remove(BEST_CHROMOSOME_INDEX);
                    }
                }
                population.populateRandomly((int) (initialPopulation * POPULATION_INCREASING_FACTOR));
            }
            // Apply a mutation to the population with a predefined probability,
            // which can be raised if the algorithm is in a local minimum.
            population.mutate(mutationProbability * mutationIncreasingFactor);
            // Perform a crossover on the population.
            population.crossover(crossoverProbability);
            // To maintain stable the number of individuals in the population,
            // complete the population adding some random individuals if needed.
            population.populateRandomly();
            // To prevent uncontrolled growth, reduce the population to the 
            // default number of chromosomes in case it is higher.
            population.reduceTo(initialPopulation);
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

    public Population getPopulation() {
        return population;
    }

    /**
     * This method check whether the conditions to finish FLECO algorithm exist
     * or not.
     *
     * @param begin the time when the algorithm started to evolve the
     * population.
     *
     * @author Manuel Domínguez-Dorado
     * @return true, if the conditions to finish FLECO execution exist.
     * Otherwise return false.
     */
    private boolean hasToFinish(Temporal begin) {
        Duration duration = Duration.between(begin, Instant.now());
        return (population.hasConverged() || (duration.get(ChronoUnit.SECONDS) > maxSeconds));
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
