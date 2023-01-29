/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
