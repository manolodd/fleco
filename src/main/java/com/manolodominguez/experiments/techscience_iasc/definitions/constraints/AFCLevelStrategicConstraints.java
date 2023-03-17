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
package com.manolodominguez.experiments.techscience_iasc.experiments.constraints;

import com.manolodominguez.fleco.strategicconstraints.Constraint;
import com.manolodominguez.fleco.strategicconstraints.ComparisonOperators;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 * This class implement a predefined set of strategic cybersecurity constraints
 * that are of complex to solve because it contains a constraint related to the
 * overall asset's cybersecurity status, to the cybersecurity functions and also
 * to the cybersecurity categories. It is intended only to ease the experiments.
 *
 * @author Manuel Domínguez-Dorado
 */
public class AFCLevelStrategicConstraints extends StrategicConstraints {

    /**
     * This is the constuctor of the class. It creates a new instance and adds
     * constraints for the overall asset's cybersecurity status and also for
     * cybersecurity functions and cybersecurity categories.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation groups that applies to the
     * asset being considered.
     */
    public AFCLevelStrategicConstraints(ImplementationGroups implementationGroup) {
        super(implementationGroup);
        // Asset constraint
        addConstraint(new Constraint(ComparisonOperators.GREATER, 0.65f));
        // Functions constraints
        addConstraint(Functions.IDENTIFY, new Constraint(ComparisonOperators.GREATER_OR_EQUAL, 0.6f));
        // Category constraints
        addConstraint(Categories.RC_CO, new Constraint(ComparisonOperators.LESS, 0.8f));
        addConstraint(Categories.PR_AC, new Constraint(ComparisonOperators.GREATER, 0.6f));
        addConstraint(Categories.ID_SC, new Constraint(ComparisonOperators.GREATER_OR_EQUAL, 0.5f));
    }

}
