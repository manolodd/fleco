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

import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.strategicgoals.StrategicGoals;

/**
 * This class implement the singleton patter to provide a method to evaluate the
 * fitnesses of a chromosome (required by JMetal).
 *
 * @author Manuel Domínguez Dorado
 */
public class FitnessEvaluatorFactory {

    private static FitnessEvaluatorFactory instance = null;
    private float fitnessValues[];

    /**
     * This is the constructor of the class. It creates a new instance and a new
     * array to store the fitness values.
     *
     * @author Manuel Domínguez Dorado
     */
    private FitnessEvaluatorFactory() {
        float fitnessValues[] = new float[3];
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
     * @param chromosome The chromosome whose fitnes is gpoing to be computed.
     * @param strategicGoals The set of strategic goals the allow the
     * computation of f1(x).
     * @param initialStatus A chromosome representing the initial status, needed
     * to compute f2(x).
     * @return the fitness value for every function to be optimized in an array
     * of floats
     */
    public float[] getFitness(Chromosome chromosome, StrategicGoals strategicGoals, Chromosome initialStatus) {
        chromosome.computeFitness(initialStatus, strategicGoals);
        fitnessValues[0] = chromosome.getFitnessComplianceGoalsCoverage();
        fitnessValues[1] = chromosome.getFitnessSimilarityToCurrentState();
        fitnessValues[2] = chromosome.getFitnessGlobalCybersecurityState();
        return fitnessValues;
    }
}
