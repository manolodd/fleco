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
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 * This class implement a predefined set of strategic cybersecurity goals that
 * are of medium complexity to solve because it contains a goal related to the
 * overall asset's cybersecurity status and also goals related to the
 * cybersecurity functions. It is intended only to ease the experiments.
 *
 * @author Manuel Domínguez-Dorado
 */
public class MediumStrategicGoals extends StrategicGoals {

    /**
     * This is the constuctor of the class. It creates a new instance and adds
     * goals for the overall asset's cybersecurity status and also for
     * cybersecurity functions.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation groups that applies to the
     * asset being considered.
     */
    public MediumStrategicGoals(ImplementationGroups implementationGroup) {
        super(implementationGroup);
        addGoal(new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.6f));
        addGoal(Functions.IDENTIFY, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.5f));
        addGoal(Functions.PROTECT, new Goal(ComparisonOperators.GREATER, 0.5f));
        addGoal(Functions.DETECT, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.7f));
        addGoal(Functions.RESPOND, new Goal(ComparisonOperators.LESS, 0.8f));
        addGoal(Functions.RECOVER, new Goal(ComparisonOperators.LESS_OR_EQUAL, 0.9f));
    }

}
