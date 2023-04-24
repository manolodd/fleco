/* 
 *******************************************************************************
 * FLECO (Fast, Lightweight, and Efficient Cybersecurity Optimization) Adaptive, 
 * Constrained, and Multi-objective Genetic Algorithm is a genetic algorithm  
 * designed to assist the Asset's Cybersecurity Committee (ACC) in making 
 * decisions during the application of CyberTOMP(1), aimed at managing 
 * comprehensive cybersecurity at both tactical and operational levels.
 *
 * (1) Dominguez-Dorado, M., Carmona-Murillo, J., Cortés-Polo, D., and
 * Rodríguez-Pérez, F. J. (2022). CyberTOMP: A novel systematic framework to
 * manage asset-focused cybersecurity from tactical and operational levels. IEEE
 * Access, 10, 122454-122485.
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

import com.manolodominguez.fleco.genetics.Chromosome;
import java.util.Comparator;

/**
 * This class implements a chromosome comparator used to sort chromosomes in
 * lists, trees, etc. It is based only on the weighted fitness function of each
 * chromosome that is compared.
 *
 * @author Manuel Domínguez-Dorado
 */
public class ChromosomeComparator implements Comparator<Chromosome> {

    /**
     * This methods compares two chromosomes in order to establish an order.
     *
     * @author Manuel Domínguez-Dorado
     * @param chromosome1 The first chromosome to be compared.
     * @param chromosome2 The second chromosome to be compared.
     * @return -1, 0, 1 depending on whether the chromosome1 is greater, equal
     * or lesser than chromosome 2, respectively.
     */
    @Override
    public int compare(Chromosome chromosome1, Chromosome chromosome2) {
        if (chromosome1.getFitness() < chromosome2.getFitness()) {
            return 1;
        } else if (chromosome1.getFitness() > chromosome2.getFitness()) {
            return -1;
        } else {
            return 0;
        }
    }

}
