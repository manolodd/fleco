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
import java.util.Comparator;

/**
 * This class implements a chromosome comparator used to sort chromosomes in
 * lists, trees, etc. It is based only on the aggregated fitness value of each
 * chromosome that is compared.
 *
 * @author Manuel Domínguez-Dorado
 */
public class ChromosomeComparator implements Comparator<Chromosome> {

    /**
     * This methods compares two chromosomes in order to establish an order. It
     * follows a lexicographical order starting with the most relevant objective
     * and finishing with the lesser.
     *
     * @author Manuel Domínguez-Dorado
     * @param chromosome1 The first chromosome to be compared.
     * @param chromosome2 The second chromosome to be compared.
     * @return -1, 0, 1 depending on whether the chromosome1 is greater, equal
     * or lesser than chromosome 2, respectively.
     */
    @Override
    public int compare(Chromosome chromosome1, Chromosome chromosome2) {
        if (chromosome1.getFitnessComplianceGoalsCoverage() < chromosome2.getFitnessComplianceGoalsCoverage()) {
            return 1;
        } else if (chromosome1.getFitnessComplianceGoalsCoverage() > chromosome2.getFitnessComplianceGoalsCoverage()) {
            return -1;
        } else {
            if (chromosome1.getFitnessSimilarityToCurrentState() < chromosome2.getFitnessSimilarityToCurrentState()) {
                return 1;
            } else if (chromosome1.getFitnessSimilarityToCurrentState() > chromosome2.getFitnessSimilarityToCurrentState()) {
                return -1;
            } else {
                if (chromosome1.getFitnessGlobalCybersecurityState() < chromosome2.getFitnessGlobalCybersecurityState()) {
                    return 1;
                } else if (chromosome1.getFitnessGlobalCybersecurityState() > chromosome2.getFitnessGlobalCybersecurityState()) {
                    return -1;
                }
                return 0;
            }
        }
    }

}
