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
import com.manolodominguez.fleco.genetic.Genes;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 * This class implement a predefined set of strategic cybersecurity goals that
 * are of very complex to solve because it contains a goal related to the
 * overall asset's cybersecurity status, to the cybersecurity functions and also
 * to the cybersecurity categories and expected outcomes. It is intended only to
 * ease the experiments.
 *
 * @author Manuel Domínguez-Dorado
 */
public class ExpectedOutcomeLevelStrategicGoals extends StrategicGoals {

    /**
     * This is the constuctor of the class. It creates a new instance and adds
     * goals for the overall asset's cybersecurity status and also for
     * cybersecurity functions, cybersecurity categories and expected outcomes.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation groups that applies to the
     * asset being considered.
     */
    public ExpectedOutcomeLevelStrategicGoals(ImplementationGroups implementationGroup) {
        super(implementationGroup);
        addGoal(new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.7f));
        addGoal(Functions.IDENTIFY, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.5f));
        addGoal(Categories.ID_AM, new Goal(ComparisonOperators.GREATER, 0.20f));
        addGoal(Genes.ID_AM_CSC_3_7, new Goal(ComparisonOperators.EQUAL, 0.67f));
        addGoal(Categories.ID_AM, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.5f));
        addGoal(Categories.ID_RA, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.6f));
        addGoal(Genes.ID_RA_ID_RA_3, new Goal(ComparisonOperators.EQUAL, 1.00f));
        addGoal(Categories.ID_SC, new Goal(ComparisonOperators.GREATER, 0.6f));
        addGoal(Genes.ID_SC_ID_SC_2, new Goal(ComparisonOperators.LESS_OR_EQUAL, 0.67f));
        addGoal(Genes.ID_RM_ID_RM_2, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.67f));

        addGoal(Functions.PROTECT, new Goal(ComparisonOperators.GREATER, 0.5f));
        addGoal(Categories.PR_AC, new Goal(ComparisonOperators.LESS, 0.95f));
        addGoal(Genes.PR_AC_CSC_12_6, new Goal(ComparisonOperators.LESS, 1.00f));
        addGoal(Categories.PR_IP, new Goal(ComparisonOperators.LESS_OR_EQUAL, 0.80f));
        addGoal(Genes.PR_IP_PR_IP_5, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.67f));
        addGoal(Categories.PR_PT, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.40f));
        addGoal(Genes.PR_DS_PR_DS_1, new Goal(ComparisonOperators.EQUAL, 0.67f));

        addGoal(Functions.DETECT, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.7f));
        addGoal(Categories.DE_AE, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.6f));
        addGoal(Genes.DE_CM_DE_CM_6, new Goal(ComparisonOperators.EQUAL, 0.67f));
        addGoal(Genes.DE_CM_DE_CM_8, new Goal(ComparisonOperators.EQUAL, 0.00f));

        addGoal(Functions.RESPOND, new Goal(ComparisonOperators.LESS, 0.8f));
        addGoal(Categories.RS_AN, new Goal(ComparisonOperators.GREATER, 0.11f));
        addGoal(Genes.RS_MI_CSC_7_7, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.33f));
        addGoal(Genes.RS_AN_RS_AN_2, new Goal(ComparisonOperators.LESS_OR_EQUAL, 1.00f));

        addGoal(Functions.RECOVER, new Goal(ComparisonOperators.LESS_OR_EQUAL, 0.9f));
        addGoal(Categories.RC_CO, new Goal(ComparisonOperators.GREATER_OR_EQUAL, 0.33f));
        addGoal(Genes.RC_RP_RC_RP_1, new Goal(ComparisonOperators.EQUAL, 1.00f));
    }

}
