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
