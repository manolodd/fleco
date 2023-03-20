/* 
 *******************************************************************************
 * FLECO (Fast, Lightweight, and Efficient Cybersecurity Optimization) Dynamic, 
 * Constrained and Multi-objective Genetic Algorithm is a genetic algorithm 
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
package com.manolodominguez.fleco.strategicconstraints;

/**
 * This class implements a strategic constraint that consist of a comparison
 * operator and a thresshold value it refers to.
 *
 * @author Manuel Domínguez-Dorado
 */
public class Constraint {

    private ComparisonOperators comparisonOperator;
    private float thresshold;

    /**
     * This is the constructor of the class. It sets the initial values of all
     * attributes and create a new instance.
     *
     * @author Manuel Domínguez-Dorado
     * @param operator A comparison operator.
     * @param thresshold A normalized float value, between 0.0 and 1.0, that
     * represents a percentaje between 0% and 100% and is related to the
     * defined operator.
     */
    public Constraint(ComparisonOperators operator, float thresshold) {
        this.comparisonOperator = operator;
        this.thresshold = thresshold;
    }

    /**
     * This method returns the the thresshold of this strategic constraint.
     *
     * @author Manuel Domínguez-Dorado
     * @return the thresshold of this strategic constraint.
     */
    public float getThresshold() {
        return thresshold;
    }

    /**
     * This method returns the comparison operator of this strategic constraint.
     *
     * @author Manuel Domínguez-Dorado
     * @return the comparison operator of this strategic constraint.
     */
    public ComparisonOperators getComparisonOperator() {
        return comparisonOperator;
    }
}
