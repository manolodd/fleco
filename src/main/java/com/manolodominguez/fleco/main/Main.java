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
package com.manolodominguez.fleco.main;

import com.manolodominguez.fleco.algorithm.FLECO;
import com.manolodominguez.fleco.strategicgoals.StrategicGoals;
import com.manolodominguez.fleco.main.experiments.goals.VeryComplexStrategicGoals;
import com.manolodominguez.fleco.main.experiments.statuses.InitialStatusForIG1;
import com.manolodominguez.fleco.main.experiments.statuses.InitialStatusForIG2;
import com.manolodominguez.fleco.main.experiments.statuses.InitialStatusForIG3;
import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.main.experiments.goals.BasicStrategicGoals;
import com.manolodominguez.fleco.main.experiments.goals.ComplexStrategicGoals;
import com.manolodominguez.fleco.main.experiments.goals.MediumStrategicGoals;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 * This class utilizes FLECO to optimize the cybersecurity status of a specific
 * business asset.
 *
 * @author Manuel Domínguez-Dorado
 */
public class Main {

    public static void main(String[] args) {
        // *************************
        // Define FLECO's parameters 
        // *************************
        int initialPopulation = 50;
        int maxGenerations = 50000;
        float mutationProbability = 0.01f;
        float crossoverProbability = 0.75f;
        ImplementationGroups implementationGroup = ImplementationGroups.IG3;
        // Define current cybersecurity status according to CyberTOMP. While it
        // is possible to create a custom status by configuring a Chromosome, 
        // some preconfigured statuses are available for quick use. See 
        // com.manolodominguez.fleco.examples.status 
        Chromosome currentStatus;
        switch (implementationGroup) {
            case IG1:
                currentStatus = new InitialStatusForIG1();
                break;
            case IG2:
                currentStatus = new InitialStatusForIG2();
                break;
            case IG3:
            default:
                currentStatus = new InitialStatusForIG3();
                break;
        }
        // According to CyberTOMP, establish the strategic cybersecurity 
        // objectives for this asset.
        //StrategicGoals strategicGoals = new BasicStrategicGoals(implementationGroup);
        //StrategicGoals strategicGoals = new MediumStrategicGoals(implementationGroup);
        //StrategicGoals strategicGoals = new ComplexStrategicGoals(implementationGroup);
        StrategicGoals strategicGoals = new VeryComplexStrategicGoals(implementationGroup);
        // Prints FLECO parameters
        System.out.println("####################################################");
        System.out.println("# FLECO dynamic, genetic, multi-criteria algorithm #");
        System.out.println("####################################################");
        System.out.println("Initial population..........: " + initialPopulation);
        System.out.println("Maximum generations.........: " + maxGenerations);
        System.out.println("Mutation probability........: " + mutationProbability);
        System.out.println("Crossover probability.......: " + crossoverProbability);
        System.out.println("Asset's implementation group: " + implementationGroup.name());
        System.out.println("Current status..............:");
        currentStatus.print();
        System.out.println("Strategic goals.............: " + strategicGoals.numberOfGoals());
        strategicGoals.print();
        System.out.println();
        System.out.println("Algorithm objetives.........: 3");
        System.out.println("\tObjective 1) Fulfill all defined cybersecurity strategic goals (once achieved, FLECO has converged)");
        System.out.println("\tObjective 2) Maximize the similarity between the current status and the solution");
        System.out.println("\tObjective 3) Maximize overall cybersecurity level");
        System.out.println("####################################################\n");
        // FLECO is employed to identify a collection of anticipated outcomes 
        // and their corresponding discrete implementation levels that meet the 
        // necessary strategic cybersecurity objectives for this asset.
        System.out.println("Evolving population to find a solution. FLECO will");
        System.out.println("stop after finding an optimum solution or after the");
        System.out.println("max number of generations is reached.\n");
        Chromosome bestChromosome;
        FLECO fleco = new FLECO(initialPopulation, maxGenerations, mutationProbability, crossoverProbability, implementationGroup, currentStatus, strategicGoals);
//        for (int i = 0; i < 100; i++) {
//            FLECO fleco = new FLECO(initialPopulation, maxGenerations, mutationProbability, crossoverProbability, implementationGroup, currentStatus, strategicGoals);
            fleco.evolve();
            bestChromosome = fleco.getBestChromosome();
            if (fleco.hasConverged()) {
                    System.out.println("\nFLECO has converged. An optimal solutions has been found.\n");
                //System.out.println(i + "#CONVERGED#" + fleco.getRequiredTime() + "#" + fleco.getRequiredGenerations() + "#" + bestChromosome.getFitness() + "#" + bestChromosome.getFitnessComplianceGoalsCoverage() + "#" + bestChromosome.getFitnessSimilarityToCurrentState() + "#" + bestChromosome.getFitnessGlobalCybersecurityState());
            } else {
                //System.out.println(i + "#!CONVERGED#" + fleco.getRequiredTime() + "#" + fleco.getRequiredGenerations() + "#" + bestChromosome.getFitness() + "#" + bestChromosome.getFitnessComplianceGoalsCoverage() + "#" + bestChromosome.getFitnessSimilarityToCurrentState() + "#" + bestChromosome.getFitnessGlobalCybersecurityState());
                    System.out.println("\nFLECO has not converged. A sub-optimal solutions has been found.\n");
            }
//        }
        // After the completion of FLECO, the optimal solution can be accessed.
        
        System.out.println("####################################################");
        System.out.println("                       SOLUTION");
        System.out.println("####################################################\n");
        System.out.println("Best solution's aggregated fitness: " + bestChromosome.getFitness());
        System.out.println("\tObjective 1): " + bestChromosome.getFitnessComplianceGoalsCoverage() + " (x0.94)");
        System.out.println("\tObjective 2): " + bestChromosome.getFitnessSimilarityToCurrentState() + " (x0.05)");
        System.out.println("\tObjective 3): " + bestChromosome.getFitnessGlobalCybersecurityState() + " (x0.01)");
        System.out.println("Solution breakdown................: \n");
        bestChromosome.print(currentStatus);
        System.out.println("####################################################\n");
        System.out.println("\nTime required: " + fleco.getRequiredTime() + " seconds  (" + fleco.getRequiredGenerations() + " generations)\n");
        
    }
}
