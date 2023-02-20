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
import com.manolodominguez.fleco.genetic.Alleles;
import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.genetic.Genes;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.EnumSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a population. A set of chromosomes and the
 * corresponding methods to make the required operations.
 *
 * @author Manuel Domínguez-Dorado
 */
public class Population extends CopyOnWriteArrayList<Chromosome> {

    private static final long serialVersionUID = 1L;

    private final int initialNumberOfChromosomes;
    private final transient ImplementationGroups implementationGroup;
    private float fitnessAverage;
    private Chromosome initialStatus;
    private StrategicGoals strategicGoals;
    private boolean hasConverged;

    /**
     * This is the constructor of the class, which initializes the population
     * parameters and generates some initial chromosomes, including specialized
     * ones derived from the initial cybersecurity status and the defined
     * strategic objectives.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialNumberOfChromosomes The initial number of chromosomes in
     * the population.
     * @param implementationGroup The implementation group that applies to the
     * business asset being considered.
     * @param initialStatus A Chromosome indicating the initial cybersecurity
     * status of the asset.
     * @param strategicGoals A set of strategic cybersecurity goals.
     */
    public Population(int initialNumberOfChromosomes, ImplementationGroups implementationGroup, Chromosome initialStatus, StrategicGoals strategicGoals) {
        super();
        this.initialNumberOfChromosomes = initialNumberOfChromosomes;
        this.implementationGroup = implementationGroup;
        this.initialStatus = initialStatus;
        this.strategicGoals = strategicGoals;
        // Add the initial cybersecurity status as a chromosome in the 
        // population
        add(initialStatus);
        // Depending on the strategic goals, several additional chromosomes can 
        // be inferred that enhances the quality of the population.
        addAll(strategicGoals.generatePrecandidatesBasedOn(initialStatus));
        computeFitnessAndSort();
        fitnessAverage = 0.0f;
        hasConverged = false;
        // Complete the population with random chromosomes until the initial 
        // number of chromosomes are reached.
        populateRandomly();
        // Reduce the population, if neccesary, to have exactly the initial 
        // number of chromosomes.
        reduceTo(this.initialNumberOfChromosomes);
    }

    /**
     * This class insert new random chromosomes in the population until the
     * defined initial number of chromosomes are reached.
     *
     * @author Manuel Domínguez-Dorado
     */
    public final void populateRandomly() {
        while (size() < initialNumberOfChromosomes) {
            Chromosome chromosome = new Chromosome(implementationGroup);
            chromosome.randomizeGenes();
            add(chromosome);
        }
        computeFitnessAndSort();
    }

    /**
     * This class randomly generates the specified number of chromosomes, and
     * insert them in the population, independently on whether the resulting
     * population's size is greater than the initial number of chromosomes or
     * not.
     *
     * @author Manuel Domínguez-Dorado
     * @param additionalChromosomes the number of random chromosomes to add to
     * the population.
     */
    public void populateRandomly(int additionalChromosomes) {
        int targetSize = size() + additionalChromosomes;
        while (size() < targetSize) {
            Chromosome chromosome = new Chromosome(implementationGroup);
            chromosome.randomizeGenes();
            add(chromosome);
        }
        computeFitnessAndSort();
    }

    /**
     * this method select those chromosomes whose fitness is beyond the average
     * of the population as parents for the next generation. It discards the
     * rest.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void selectBestAdapted() {
        // First it compute fitness and sort the population based on it.
        computeFitnessAndSort();
        CopyOnWriteArrayList<Chromosome> bestAdapted = new CopyOnWriteArrayList<>();
        for (Chromosome chromosome : toArray(new Chromosome[0])) {
            if (chromosome.getFitness() >= fitnessAverage) {
                bestAdapted.add(chromosome);
            }
        }
        if (!bestAdapted.isEmpty()) {
            clear();
            addAll(bestAdapted);
        }
    }

    /**
     * This method generate mutated chromosomes from the current population. For
     * each time a mutation applies, a new cromosome is created with 1, 2, or 3
     * genes mutated depending on whether the applicabe implementation group is
     * IG1, IG2 or IG3. This is because the greater the implementation group the
     * larger the chromosome.
     *
     * @author Manuel Domínguez-Dorado
     * @param mutationProbablity the probability that a chromosome is mutated.
     */
    public void mutate(float mutationProbablity) {
        Alleles[] allelesArray = Alleles.values();
        int numberOfMutatedGenes = 0;
        CopyOnWriteArrayList<Chromosome> mutatedChromosomes = new CopyOnWriteArrayList<>();
        Chromosome mutatedChromosome = new Chromosome(implementationGroup);
        numberOfMutatedGenes = implementationGroup.getImplementationGroupIndex() + 1;
        for (Chromosome chromosome : toArray(new Chromosome[0])) {
            // For each chromosome in the population, if the possibility of 
            // being mutated is met, a new clon chromosome is created.
            if (Math.random() < mutationProbablity) {
                EnumSet<Genes> mutatedGenes = EnumSet.noneOf(Genes.class);
                int randomAllele = 0;
                CopyOnWriteArrayList<Genes> genesForTheNewChromosome = new CopyOnWriteArrayList<>();
                for (Genes gene : chromosome.getGenes().keySet()) {
                    if (gene.appliesToIG(implementationGroup)) {
                        genesForTheNewChromosome.add(gene);
                    }
                }
                mutatedChromosome.setGenes(chromosome.getGenes());
                // Randomly select mutation points in the chromosome. One for
                // each required mutation without repetitions.
                while (mutatedGenes.size() < numberOfMutatedGenes) {
                    mutatedGenes.add(genesForTheNewChromosome.get(ThreadLocalRandom.current().nextInt(0, genesForTheNewChromosome.size())));
                }
                // Repeat until the number of mutations has been reached.
                for (Genes gene : mutatedGenes) {
                    // Because the number of alleles is very reduced, this loop 
                    // assure the mutation is effective and is not ersulting in 
                    // the same allele for the mutated gene.
                    while (chromosome.getAllele(gene) == mutatedChromosome.getAllele(gene)) {
                        // Select the allele
                        randomAllele = ThreadLocalRandom.current().nextInt(0, allelesArray.length);
                        // Update the allele for the mutated gene
                        mutatedChromosome.updateAllele(gene, allelesArray[randomAllele]);
                    }
                }
                mutatedChromosomes.add(mutatedChromosome);
            }
        }
        if (!mutatedChromosomes.isEmpty()) {
            addAll(mutatedChromosomes);
        }
    }

    /**
     * This method generate chromosomes by applying a crossover int the current
     * population. For each time a crossover applies, a new couple of cromosome
     * are created whose genes are exchanged from a random point to the end of
     * the chromosomes or from the beginning of the chromosome to the crossing
     * point, randomly.
     *
     * @author Manuel Domínguez-Dorado
     * @param crossoverProbability
     */
    public void crossover(float crossoverProbability) {
        Chromosome chromosomeA = new Chromosome(implementationGroup);
        Chromosome chromosomeB = new Chromosome(implementationGroup);
        CopyOnWriteArrayList<Chromosome> crossedChromosomes = new CopyOnWriteArrayList<>();
        for (int i = 0; i < (size() - 1); i += 2) {
            // For each chromosome in the population, if the possibility of 
            // being applied a crossover is met, a couple of chromosomes are 
            // cloned from two chromosomes of the current population.
            if (Math.random() < crossoverProbability) {
                CopyOnWriteArrayList<Genes> genesForTheNewChromosome = new CopyOnWriteArrayList<>();
                chromosomeA.setGenes(get(i).getGenes());
                chromosomeB.setGenes(get(i + 1).getGenes());
                for (Genes gene : chromosomeA.getGenes().keySet()) {
                    if (gene.appliesToIG(implementationGroup)) {
                        genesForTheNewChromosome.add(gene);
                    }
                }
                // Randomly select one crossing point in the chromosome.
                int crossingPoint = ThreadLocalRandom.current().nextInt(0, genesForTheNewChromosome.size());
                // Ramdomly select whether the crossover will be from the 
                // beginning of the chromosome to the crossing point or from the 
                // crossing point to the end of the chromosome.
                boolean fromBeginToCrossingPoint = (ThreadLocalRandom.current().nextInt(0, 1) == 0);
                if (fromBeginToCrossingPoint) {
                    // Genes from chromosome A and B are exchanged from the 
                    // beginning of the chromosome to the crossing point.
                    for (int j = crossingPoint; j < genesForTheNewChromosome.size(); j++) {
                        chromosomeA.updateAllele(genesForTheNewChromosome.get(j), get(i + 1).getAllele(genesForTheNewChromosome.get(j)));
                        chromosomeB.updateAllele(genesForTheNewChromosome.get(j), get(i).getAllele(genesForTheNewChromosome.get(j)));
                    }
                    crossedChromosomes.add(chromosomeA);
                    crossedChromosomes.add(chromosomeB);
                } else {
                    // Genes from chromosome A and B are exchanged from the 
                    // crossing point to the end of the chromosome.
                    for (int j = 0; j < crossingPoint; j++) {
                        chromosomeA.updateAllele(genesForTheNewChromosome.get(j), get(i + 1).getAllele(genesForTheNewChromosome.get(j)));
                        chromosomeB.updateAllele(genesForTheNewChromosome.get(j), get(i).getAllele(genesForTheNewChromosome.get(j)));
                    }
                    crossedChromosomes.add(chromosomeA);
                    crossedChromosomes.add(chromosomeB);
                }
            }
        }
        if (!crossedChromosomes.isEmpty()) {
            addAll(crossedChromosomes);
        }
    }

    /**
     * This method computes the fitness for every chromosome in the population
     * and also the average fitness of all them.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void computeFitnessAndSort() {
        hasConverged = false;
        fitnessAverage = 0.0f;
        for (Chromosome chromosome : toArray(new Chromosome[0])) {
            chromosome.computeFitness(initialStatus, strategicGoals);
            fitnessAverage += chromosome.getFitness();
        }
        fitnessAverage /= size();
        sort(new ChromosomeComparator());
        if (!isEmpty()) {
            if (get(0).getFitnessComplianceGoalsCoverage() >= 1.0f) {
                hasConverged = true;
            }
        }
    }

    /**
     * This method returns whether the population has converged towards an
     * optimal solution or not.
     *
     * @author Manuel Domínguez-Dorado
     * @return true, if the population has converged to an optimal solution.
     * Otherwise, false.
     */
    public boolean hasConverged() {
        return hasConverged;
    }

    /**
     * This method returns the average fitness of all chromosomes in the
     * population.
     *
     * @author Manuel Domínguez-Dorado
     * @return The average fitness of all chromosomes in the population.
     */
    public float getFitnessAverage() {
        return fitnessAverage;
    }

    /**
     * This method reduces the number of chromosomes in the population
     * maintaining only the best ones especified as a parameter.
     *
     * @author Manuel Domínguez-Dorado
     * @param finalNumber The number of best chromosomes that will survive to
     * the pupulation reduction.
     */
    public final void reduceTo(int finalNumber) {
        computeFitnessAndSort();
        CopyOnWriteArrayList<Chromosome> auxPopulation = new CopyOnWriteArrayList<>();
        if (size() >= finalNumber) {
            for (int i = 0; i < finalNumber; i++) {
                auxPopulation.add(get(i));
            }
            clear();
            addAll(auxPopulation);
        }
    }
}
