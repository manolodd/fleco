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
package com.manolodominguez.fleco.algorithm;

import com.manolodominguez.fleco.genetics.Alleles;
import com.manolodominguez.fleco.genetics.Chromosome;
import com.manolodominguez.fleco.genetics.Genes;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class implement the singleton patter to provide a method to evaluate the
 * fitnesses of a chromosome. This implementation is not required by FLECO
 * itself but eases the integration of FLECO into JMetal Framework to run
 * pre-existent genetic algorithm using FLECO's fitnes functions.
 *
 * @author Manuel Domínguez Dorado
 */
public class FitnessEvaluatorFactory {

    private static final int NUMBER_OF_OBJECTIVES = 2;
    private static final int IG1_LENGTH = 47;
    private static final int IG2_LENGTH = 107;
    private static final int IG3_LENGTH = 167;
    
    private static FitnessEvaluatorFactory instance = null;
    private float[] fitnessValues;

    /**
     * This is the constructor of the class. It creates a new instance and a new
     * array to store the fitness values.
     *
     * @author Manuel Domínguez Dorado
     */
    private FitnessEvaluatorFactory() {
        fitnessValues = new float[NUMBER_OF_OBJECTIVES];
    }

    /**
     * This is a static method that return the existing instance of
     * FitnessEvaluatorFactory (and creates it, previously, if needed).
     *
     * @author Manuel Domínguez Dorado
     * @return the existing instance of FitnessEvaluatorFactory
     */
    public static FitnessEvaluatorFactory getFitnessEvaluator() {
        FitnessEvaluatorFactory localInstance = FitnessEvaluatorFactory.instance;
        if (localInstance == null) {
            synchronized (FitnessEvaluatorFactory.class) {
                localInstance = FitnessEvaluatorFactory.instance;
                if (localInstance == null) {
                    FitnessEvaluatorFactory.instance = localInstance = new FitnessEvaluatorFactory();
                }
            }
        }
        return localInstance;
    }

    /**
     * This method computes the fitnesses for the chromosome specified as a
     * parameter. It returns the fitness value for every function to be
     * optimized in an array of floats.
     *
     * @author Manuel Domínguez Dorado
     * @param intChromosome The set of alleles (JMetal style) for each gen of
     * the chromosome.
     * @param strategicConstraints The set of strategic constraints the allow
     * the computation of f1(x).
     * @param initialStatus A chromosome representing the initial status, needed
     * to compute f2(x).
     * @return the fitness value for every function to be optimized in an array
     * of floats
     */
    public float[] getFitness(int[] intChromosome, StrategicConstraints strategicConstraints, Chromosome initialStatus) {
        // Detects the implementation group from the lenght of intChromosome
        ImplementationGroups implementationGroup;
        switch (intChromosome.length) {
            case IG1_LENGTH:
                implementationGroup = ImplementationGroups.IG1;
                break;
            case IG2_LENGTH:
                implementationGroup = ImplementationGroups.IG2;
                break;
            case IG3_LENGTH:
                implementationGroup = ImplementationGroups.IG3;
                break;
            default:
                throw new IllegalArgumentException("Incorrect length for intChromosomes. It should be 47, 107 or 167.");
        }
        // Create a new Chromosome using the correct implementation group
        Chromosome chromosome = new Chromosome(implementationGroup);
        // In a loop, parse each gen of the chromosome to the discrete level of 
        // implementation instead of the int values provided as a parameter
        CopyOnWriteArrayList<Genes> applicableGenes = Genes.getGenesFor(implementationGroup);
        Alleles allele;
        for (int i = 0; i < applicableGenes.size(); i++) {
            switch (intChromosome[i]) {
                case 0:
                    allele = Alleles.DLI_0;
                    break;
                case 1:
                    allele = Alleles.DLI_33;
                    break;
                case 2:
                    allele = Alleles.DLI_67;
                    break;
                case 3:
                    allele = Alleles.DLI_100;
                    break;
                default:
                    throw new IllegalArgumentException("Value in position " + i + " of intChromosome is out of the range 0-3");
            }
            chromosome.updateAllele(applicableGenes.get(i), allele);
        }
        // Compute fitness
        chromosome.computeFitness(initialStatus, strategicConstraints);
        chromosome.print();
        fitnessValues[0] = chromosome.getFitnessConstraintsCoverage();
        fitnessValues[1] = chromosome.getFitnessSimilarityToCurrentState();
        fitnessValues[2] = chromosome.getFitnessGlobalCybersecurityState();
        return fitnessValues;
    }
}
