/* 
 *******************************************************************************
 * FLECO (Fast, Lightweight, and Efficient Cybersecurity Optimization) (1) 
 * Adaptive, Constrained, and Multi-objective Genetic Algorithm is a genetic 
 * algorithm designed to assist the Asset's Cybersecurity Committee (ACC) in 
 * making decisions during the application of CyberTOMP (2), aimed at managing 
 * comprehensive cybersecurity at both tactical and operational levels.
 *
 * (1) Domínguez-Dorado, M.; Cortés-Polo, D.; Carmona-Murillo, J.; 
 * Rodríguez-Pérez, F.J.; Galeano-Brajones, J. Fast, Lightweight, and Efficient 
 * Cybersecurity Optimization for Tactical–Operational Management. Appl. Sci. 
 * 2023, 13, 6327. https://doi.org/10.3390/app13106327
 *
 * (2) Dominguez-Dorado, M., Carmona-Murillo, J., Cortés-Polo, D., and
 * Rodríguez-Pérez, F. J. (2022). CyberTOMP: A novel systematic framework to
 * manage asset-focused cybersecurity from tactical and operational levels. IEEE
 * Access, 10, 122454-122485. https://doi.org/10.1109/ACCESS.2022.3223440
 *******************************************************************************
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
 *******************************************************************************
 */
package com.manolodominguez.experiments;

import com.manolodominguez.experiments.definitions.constraints.AFCEOLevelStrategicConstraints;
import com.manolodominguez.experiments.definitions.statuses.InitialStatusForIG1;
import com.manolodominguez.experiments.definitions.statuses.InitialStatusForIG2;
import com.manolodominguez.experiments.definitions.statuses.InitialStatusForIG3;
import com.manolodominguez.fleco.algorithm.FLECO;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.genetics.Chromosome;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import com.manolodominguez.fleco.events.DefaultProgressEventListener;
import com.manolodominguez.fleco.genetics.Alleles;
import com.manolodominguez.fleco.genetics.Genes;

/**
 * This class utilizes FLECO to optimize the cybersecurity status of a specific
 * business asset.
 *
 * @author Manuel Domínguez-Dorado
 */
public class SimpleExample {

    /**
     * This methods run an experiment that takes a initial cybersecurity status
     * for a given asset and also a set of strategic cybersecurity constraints
     * and run FLECO to find a solution in the form of a desired cybersecurity
     * status.
     *
     * @author Manuel Domínguez-Dorado
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // *************************
        // Define FLECO's parameters 
        // *************************
        int initialPopulation = 30;
        int maxSeconds = 5 * 60;
        float crossoverProbability = 0.90f;
        ImplementationGroups implementationGroup = ImplementationGroups.IG3;
        // Create and define your asset's current cybersecurity status according
        // to CyberTOMP proposal.
        // 
        // You must configure each chromosome's allele's value individually to 
        // fit the real state of your asset's expected outcome. Thi should be 
        // done through squential calls to updateAllele(gene, allele). For 
        // instance:
        //
        // Chromosome initialStatus = new Chromosome(implementationGroup);
        // initialStatus.updateAllele(Genes.PR_AC_PR_AC_3, Alleles.DLI_67);
        // initialStatus.updateAllele(Genes.PR_AC_PR_AC_4, Alleles.DLI_67);
        // initialStatus.updateAllele(Genes.PR_AC_PR_AC_5, Alleles.DLI_0);
        // initialStatus.updateAllele(Genes.PR_AC_PR_AC_7, Alleles.DLI_67);
        // initialStatus.updateAllele(Genes.PR_AT_PR_AT_1, Alleles.DLI_0);
        // initialStatus.updateAllele(Genes.PR_DS_CSC_3_4, Alleles.DLI_100);
        // initialStatus.updateAllele(Genes.PR_DS_PR_DS_3, Alleles.DLI_67);
        // initialStatus.updateAllele(Genes.PR_IP_9D_9, Alleles.DLI_0);
        // initialStatus.updateAllele(Genes.PR_IP_CSC_11_1, Alleles.DLI_100);
        // initialStatus.updateAllele(Genes.PR_IP_CSC_4_3, Alleles.DLI_100); 
        // and so on...
        // 
        // However, for this example we will use a random initial status.
        // 
        Chromosome initialStatus = new Chromosome(implementationGroup);
        initialStatus.randomizeGenes();
        
        switch (implementationGroup) {
            case IG1:
                initialStatus = new InitialStatusForIG1();
                break;
            case IG2:
                initialStatus = new InitialStatusForIG2();
                break;
            case IG3:
            default:
                initialStatus = new InitialStatusForIG3();
                break;
        }
        // According to CyberTOMP, this establishes the strategic cybersecurity 
        // objectives for the asset.
        //StrategicConstraints strategicConstraints = new ALevelStrategicConstraints(implementationGroup);
        //StrategicConstraints strategicConstraints = new AFLevelStrategicConstraints(implementationGroup);
        //StrategicConstraints strategicConstraints = new AFCLevelStrategicConstraints(implementationGroup);
        StrategicConstraints strategicConstraints = new AFCEOLevelStrategicConstraints(implementationGroup);

        // Prints FLECO parameters
        System.out.println("######################################################################");
        System.out.println("# FLECO adaptive, constrained, and multi-objective genetic algorithm #");
        System.out.println("######################################################################");
        System.out.println("Initial population..........: " + initialPopulation);
        System.out.println("Maximum seconds.............: " + maxSeconds);
        System.out.println("Mutation probability........: " + 1.0f / (Genes.getGenesFor(implementationGroup).size()));
        System.out.println("Crossover probability.......: " + crossoverProbability);
        System.out.println("Asset's implementation group: " + implementationGroup.name());
        System.out.println("Current status..............:");
        initialStatus.print();
        System.out.println("Strategic constraints.............: " + strategicConstraints.numberOfConstraints());
        strategicConstraints.print();
        System.out.println();
        System.out.println("Algorithm objetives.........: 3");
        System.out.println("\tObjective 1) Maximize the coverage of all defined strategic cybersecurity constraints");
        System.out.println("\tObjective 2) Maximize the similarity between the initial status and the solution");
        System.out.println("\tObjective 3) Maximize overall cybersecurity level");
        System.out.println("####################################################\n");
        // FLECO is employed to identify a collection of anticipated outcomes 
        // and their corresponding discrete implementation levels that meet the 
        // necessary strategic cybersecurity constraints for the asset.
        System.out.println("Evolving population to find a solution. FLECO will stop when one of the following happens:");
        System.out.println("\t· The population converges (at least an individual fulfill the Objective 1 at 100%");
        System.out.println("\t· The maximum number of seconds have elapsed.\n");

        Chromosome bestChromosome;
        FLECO fleco;
        fleco = new FLECO(initialPopulation, maxSeconds, crossoverProbability, implementationGroup, initialStatus, strategicConstraints);
        fleco.setProgressEventListener(new DefaultProgressEventListener());
        fleco.evolve();
        bestChromosome = fleco.getBestChromosome();
        if (fleco.hasConverged()) {
            System.out.println("\nFLECO has converged. A solutions has been found.\n");
        } else {
            System.out.println("\nFLECO has not converged. No solution has been found that meet all criteria.\n");
        }

        // After the completion of FLECO, the optimal solution can be accessed.
        System.out.println("####################################################");
        System.out.println("                       SOLUTION");
        System.out.println("####################################################\n");
        System.out.println("Best solution's aggregated fitness: " + bestChromosome.getFitness());
        System.out.println("\tObjective 1): " + bestChromosome.getFitnessConstraintsCoverage() + " (x0.94)");
        System.out.println("\tObjective 2): " + bestChromosome.getFitnessSimilarityToCurrentState() + " (x0.05)");
        System.out.println("\tObjective 3): " + bestChromosome.getFitnessGlobalCybersecurityState() + " (x0.01)");
        System.out.println("Solution breakdown................: \n");
        bestChromosome.print(initialStatus);
        System.out.println("####################################################\n");
        System.out.println("\nTime required: " + fleco.getUsedTime() + " seconds  (" + fleco.getUsedGenerations() + " generations)\n");
    }
}
