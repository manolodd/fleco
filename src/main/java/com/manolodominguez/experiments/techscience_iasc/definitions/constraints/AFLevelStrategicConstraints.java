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
package com.manolodominguez.experiments.techscience_iasc.definitions.constraints;

import com.manolodominguez.fleco.strategicconstraints.Constraint;
import com.manolodominguez.fleco.strategicconstraints.ComparisonOperators;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 * This class implement a predefined set of strategic cybersecurity constraints
 * that are of medium complexity to solve because it contains a constraint
 * related to the overall asset's cybersecurity status and also constraints
 * related to the cybersecurity functions. It is intended only to ease the
 * experiments.
 *
 * @author Manuel Domínguez-Dorado
 */
public class AFLevelStrategicConstraints extends StrategicConstraints {

    /**
     * This is the constuctor of the class. It creates a new instance and adds
     * constraints for the overall asset's cybersecurity status and also for
     * cybersecurity functions.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation groups that applies to the
     * asset being considered.
     */
    public AFLevelStrategicConstraints(ImplementationGroups implementationGroup) {
        super(implementationGroup);
        // Asset constraint
        addConstraint(new Constraint(ComparisonOperators.GREATER, 0.65f));
        // Functions constraints
        addConstraint(Functions.IDENTIFY, new Constraint(ComparisonOperators.GREATER_OR_EQUAL, 0.6f));
    }

}
