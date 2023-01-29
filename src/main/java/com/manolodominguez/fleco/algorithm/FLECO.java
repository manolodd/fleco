/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.algorithm;

import com.manolodominguez.fleco.business.StrategicGoals;
import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 *
 * @author manolodd
 */
public class FLECO {

    private float mutationProbability = 0.01f;
    private float crossoverProbability = 0.75f;
    private ImplementationGroups implementationGroup = ImplementationGroups.IG1;
    private int maxGenerations = 20000;
    private Population population = null;
    private StrategicGoals strategicGoal = null;
    private int initialPopulation = 100;
    
    public FLECO(ImplementationGroups implementationGroup, Chromosome currentStatus, StrategicGoals sg) {
        this.implementationGroup = implementationGroup;
        population = new Population(initialPopulation, implementationGroup, currentStatus, sg);
        population.populate();
    }

    public boolean evolve() {
        int generation = 0;
        while (!hasToFinish(generation, population)) {
            population.selectBestAdapted();
            population.reduce(initialPopulation);
            population.mutate(mutationProbability);
            population.crossover(crossoverProbability);
            if ((generation % 100) == 0) {
                System.out.println("Generación#"+generation+"#Solución intermedia:#" + population.get(0).getFitness() + "#Población:#" + population.size() + "#Fitness average:#" + population.getFitnessAverage());
            }
            generation++;
        }
        return true;
    }

    private boolean hasToFinish(int generation, Population population) {
        if (population.hasConverged() && (generation >= (maxGenerations/2))){
            return true;
        }
        return (generation >= maxGenerations);
    }

    public Chromosome getBestChromosome() {
        return population.get(0);
    }
}
