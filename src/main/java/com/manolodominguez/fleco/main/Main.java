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
package com.manolodominguez.fleco.main;

import com.manolodominguez.fleco.algorithm.FLECO;
import com.manolodominguez.fleco.business.Condition;
import com.manolodominguez.fleco.business.Operators;
import com.manolodominguez.fleco.business.StrategicGoals;
import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.genetic.Genes;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 *
 * @author manolodd
 */
public class Main {

    public static void main(String[] args) {
        Chromosome currentStatus = new Chromosome(ImplementationGroups.IG3);
        currentStatus.randomize();
        //currentStatus.computeFitness(currentStatus);
        StrategicGoals sg = new StrategicGoals(ImplementationGroups.IG3);
        
        sg.addGoal(Genes.DE_CM_CSC_13_1, new Condition(Operators.LESS, 1.0f));
        sg.addGoal(Genes.RC_CO_RC_CO_3, new Condition(Operators.EQUAL, 0.0f));
        sg.addGoal(Categories.PR_AT, new Condition(Operators.GREATER_OR_EQUAL, 0.75f));
        sg.addGoal(Functions.RESPOND, new Condition(Operators.LESS, 0.5f));
        sg.addGoal(Functions.IDENTIFY, new Condition(Operators.LESS_OR_EQUAL, 0.7f));
        sg.addGoal(new Condition(Operators.GREATER_OR_EQUAL, 0.6f));

        FLECO fleco = new FLECO(ImplementationGroups.IG3, currentStatus, sg);
        fleco.evolve();
        Chromosome chromosome = fleco.getBestChromosome();
        System.out.println("Best solution's fitness: " + chromosome.getFitness()+" ("+chromosome.getFitnessGlobalCybersecurityState()+"(0.05) and "+chromosome.getFitnessSimilarityToCurrentState(currentStatus)+"(0.15) and "+chromosome.getFitnessComplianceGoalsCoverage(sg)+"(0.8))");
        chromosome.print();
//System.out.println("Best solution's fitness: " + currentStatus.getFitness());
/*
        fleco.evolve();
        chromosome = fleco.getBestChromosome();
        System.out.println("Best solution's fitness (2nd round): " + chromosome.getFitness());
        fleco.evolve();
        chromosome = fleco.getBestChromosome();
        System.out.println("Best solution's fitness (3rd round): " + chromosome.getFitness());
        fleco.evolve();
        chromosome = fleco.getBestChromosome();
        System.out.println("Best solution's fitness (4th round): " + chromosome.getFitness());
*/
    }
}
