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
package com.manolodominguez.fleco.main.experiments.goals;

import com.manolodominguez.fleco.strategicgoals.Goal;
import com.manolodominguez.fleco.strategicgoals.ComparisonOperators;
import com.manolodominguez.fleco.strategicgoals.StrategicGoals;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 * This class implement a predefined set of strategic cybersecurity goals that
 * are simple to solve because it contains only a goal related to the overall
 * asset's cybersecurity status. It is intended only to ease the experiments.
 *
 * @author Manuel Domínguez-Dorado
 */
public class BasicStrategicGoals extends StrategicGoals {

    /**
     * This is the constuctor of the class. It creates a new instance and adds a
     * single goal related the the overall asset's cybersecurity status.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation groups that applies to the
     * asset being considered.
     */
    public BasicStrategicGoals(ImplementationGroups implementationGroup) {
        super(implementationGroup);
        addGoal(new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.6f));
    }

}
