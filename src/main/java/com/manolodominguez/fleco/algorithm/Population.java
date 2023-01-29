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

import com.manolodominguez.fleco.business.StrategicGoals;
import com.manolodominguez.fleco.genetic.Alleles;
import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.genetic.Genes;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author manolodd
 */
public class Population extends CopyOnWriteArrayList<Chromosome> {

    private static final long serialVersionUID = 1L;

    private final int initialNumberOfChromosomes;
    private final transient ImplementationGroups implementationGroup;
    private float fitnessAverage;
    private Chromosome currentStatus;
    private StrategicGoals sg;
    private boolean converged;
        
    public Population(int initialNumberOfChromosomes, ImplementationGroups implementationGroup, Chromosome currentStatus, StrategicGoals sg) {
        super();
        this.initialNumberOfChromosomes = initialNumberOfChromosomes;
        this.implementationGroup = implementationGroup;
        this.currentStatus = currentStatus;
        this.sg = sg;
        //addAll(sg.generateCandidateChromosomes(currentStatus));
        //computeFitnessAndSort();
        fitnessAverage = 0.0f;
        converged = false;
    }

    public void populate() {
        while (size() < initialNumberOfChromosomes) {
            Chromosome chromosome = new Chromosome(implementationGroup);
            chromosome.randomize();
            add(chromosome);
        }
        computeFitnessAndSort();
    }

    public void selectBestAdapted() {
        computeFitnessAndSort();
        CopyOnWriteArrayList<Chromosome> bestAdapted = new CopyOnWriteArrayList<>();
        removeRepeated();
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

    private void removeRepeated() {
        CopyOnWriteArrayList<Chromosome> auxPopulation = new CopyOnWriteArrayList<>();
        auxPopulation.addAll(this);
        boolean areEqual = true;
        for (Chromosome inspectedChromosome : auxPopulation) {
            for (Chromosome otherChromosome : auxPopulation) {
                areEqual = true;
                for (Genes gene : Genes.values()) {
                    if (inspectedChromosome.getAllele(gene) != otherChromosome.getAllele(gene)) {
                        areEqual = false;
                    }
                }
                if (areEqual) {
                    auxPopulation.remove(inspectedChromosome);
                    break;
                }
            }
        }
        if (!auxPopulation.isEmpty()) {
            clear();
            addAll(auxPopulation);
        }
    }

    public void mutate(float mutationProbablity) {
        Alleles[] allelesArray = Alleles.values();
        CopyOnWriteArrayList<Chromosome> mutatedChromosomes = new CopyOnWriteArrayList<>();
        Chromosome mutatedChromosome = new Chromosome(implementationGroup);
        for (Chromosome chromosome : toArray(new Chromosome[0])) {
            if (Math.random() < mutationProbablity) {
                Genes mutatedGene;
                int randomAllele = 0;
                CopyOnWriteArrayList<Genes> correspondingGenes = new CopyOnWriteArrayList<>();
                for (Genes gene : chromosome.getGenes().keySet()) {
                    if (gene.appliesToIG(implementationGroup)) {
                        correspondingGenes.add(gene);
                    }
                }
                mutatedGene = correspondingGenes.get(ThreadLocalRandom.current().nextInt(0, correspondingGenes.size()));
                mutatedChromosome.setGenes(chromosome.getGenes());
                while (chromosome.getAllele(mutatedGene) == mutatedChromosome.getAllele(mutatedGene)) {
                    randomAllele = ThreadLocalRandom.current().nextInt(0, allelesArray.length);
                    mutatedChromosome.updateAllele(mutatedGene, allelesArray[randomAllele]);
                }
                mutatedChromosomes.add(mutatedChromosome);
            }
        }
        if (!mutatedChromosomes.isEmpty()) {
            addAll(mutatedChromosomes);
        }
    }

    public void crossover2(float crossoverProbability) {
        Chromosome chromosomeA = new Chromosome(implementationGroup);
        Chromosome chromosomeB = new Chromosome(implementationGroup);
        CopyOnWriteArrayList<Chromosome> crossedChromosomes = new CopyOnWriteArrayList<>();
        for (int i = 0; i < (size() - 1); i += 2) {
            if (Math.random() < crossoverProbability) {
                Genes crossoverGene;
                CopyOnWriteArrayList<Genes> correspondingGenes = new CopyOnWriteArrayList<>();
                chromosomeA.setGenes(get(i).getGenes());
                chromosomeB.setGenes(get(i + 1).getGenes());
                for (Genes gene : chromosomeA.getGenes().keySet()) {
                    if (gene.appliesToIG(implementationGroup)) {
                        correspondingGenes.add(gene);
                    }
                }
                crossoverGene = correspondingGenes.get(ThreadLocalRandom.current().nextInt(0, correspondingGenes.size()));
                chromosomeA.updateAllele(crossoverGene, get(i + 1).getAllele(crossoverGene));
                chromosomeB.updateAllele(crossoverGene, get(i).getAllele(crossoverGene));
                crossedChromosomes.add(chromosomeA);
                crossedChromosomes.add(chromosomeB);
            }
        }
        if (!crossedChromosomes.isEmpty()) {
            addAll(crossedChromosomes);
        }
    }

    public void crossover(float crossoverProbability) {
        Chromosome chromosomeA = new Chromosome(implementationGroup);
        Chromosome chromosomeB = new Chromosome(implementationGroup);
        CopyOnWriteArrayList<Chromosome> crossedChromosomes = new CopyOnWriteArrayList<>();
        for (int i = 0; i < (size() - 1); i += 2) {
            if (Math.random() < crossoverProbability) {
                CopyOnWriteArrayList<Genes> correspondingGenes = new CopyOnWriteArrayList<>();
                chromosomeA.setGenes(get(i).getGenes());
                chromosomeB.setGenes(get(i + 1).getGenes());
                for (Genes gene : chromosomeA.getGenes().keySet()) {
                    if (gene.appliesToIG(implementationGroup)) {
                        correspondingGenes.add(gene);
                    }
                }
                int crossingPoint = ThreadLocalRandom.current().nextInt(0, correspondingGenes.size());
                for (int j = crossingPoint; j < correspondingGenes.size(); j++) {
                    chromosomeA.updateAllele(correspondingGenes.get(j), get(i + 1).getAllele(correspondingGenes.get(j)));
                    chromosomeB.updateAllele(correspondingGenes.get(j), get(i).getAllele(correspondingGenes.get(j)));
                }
                crossedChromosomes.add(chromosomeA);
                crossedChromosomes.add(chromosomeB);
            }
        }
        if (!crossedChromosomes.isEmpty()) {
            addAll(crossedChromosomes);
        }
    }

    private void computeFitnessAndSort() {
        converged = false;
        fitnessAverage = 0.0f;
        for (Chromosome chromosome : toArray(new Chromosome[0])) {
            chromosome.computeFitness(currentStatus, sg);
            fitnessAverage += chromosome.getFitness();
        }
        fitnessAverage /= size();
        sort(new ChromosomeComparator());
        if (get(0).getFitnessComplianceGoalsCoverage(sg) == 1.0f) {
            converged = true;
        }
    }

    public boolean hasConverged() {
        return this.converged;
    }
    
    public float getFitnessAverage() {
        return fitnessAverage;
    }

    public void reduce(int finalNumber) {
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
