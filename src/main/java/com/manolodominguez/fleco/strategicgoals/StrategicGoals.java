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
package com.manolodominguez.fleco.strategicgoals;

import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.genetic.Genes;
import com.manolodominguez.fleco.genetic.Alleles;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.EnumMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class implements a set of strategic cybersecurity goals. They are
 * defined as target values of metrics coming from CyberTOMP proposal. The
 * purpose of this class is to guide the evolution of FLECO towards a solution
 * that fulfills all strategic goals.
 *
 * @author Manuel Domínguez-Dorado
 */
public class StrategicGoals {

    private EnumMap<Genes, Goal> genesGoals;
    private EnumMap<Categories, Goal> categoryGoals;
    private EnumMap<Functions, Goal> functionGoals;
    private Goal assetGoal;
    private ImplementationGroups implementationGroup;

    /**
     * This is the constructor of the class. It creates a new, empty instance.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup
     */
    public StrategicGoals(ImplementationGroups implementationGroup) {
        genesGoals = new EnumMap<>(Genes.class);
        categoryGoals = new EnumMap<>(Categories.class);
        functionGoals = new EnumMap<>(Functions.class);
        this.implementationGroup = implementationGroup;
        this.assetGoal = null;
    }

    /**
     * This method set a new goal defined for a given cybersecurity expected
     * outcome/gene.
     *
     * @author Manuel Domínguez-Dorado
     * @param gene The gene/expected outcome the goal is defined for.
     * @param goal The defined goal.
     */
    public void addGoal(Genes gene, Goal goal) {
        if (gene.appliesToIG(implementationGroup)) {
            genesGoals.put(gene, goal);
        }
    }

    /**
     * This method set a new goal defined for a given cybersecurity category.
     *
     * @author Manuel Domínguez-Dorado
     * @param category The cybersecurity categroy the goal is defined for.
     * @param goal The defined goal.
     */
    public void addGoal(Categories category, Goal goal) {
        if (category.appliesToIG(implementationGroup)) {
            categoryGoals.put(category, goal);
        }
    }

    /**
     * This method set a new goal defined for a given cybersecurity function.
     *
     * @author Manuel Domínguez-Dorado
     * @param function The cybersecurity function the goal is defined for.
     * @param goal The defined goal.
     */
    public void addGoal(Functions function, Goal goal) {
        if (function.appliesToIG(implementationGroup)) {
            functionGoals.put(function, goal);
        }
    }

    /**
     * This method set a new hig-level goal defined for the global asset's
     * cybersecurity status.
     *
     * @author Manuel Domínguez-Dorado
     * @param goal The defined goal.
     */
    public void addGoal(Goal goal) {
        this.assetGoal = goal;
    }

    /**
     * This method check whether a specific gene/expected outcome has a goal
     * associated to it or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param gene The gene that is bein queried.
     * @return true, if the specified gene has a goal associated to it.
     * Otherwise, false.
     */
    public boolean hasDefinedGoal(Genes gene) {
        return genesGoals.containsKey(gene);
    }

    /**
     * This method check whether a specific cybersecurity category has a goal
     * associated to it or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param category The cybersecurity category that is bein queried.
     * @return true, if the specified cybersecurity category has a goal
     * associated to it. Otherwise, false.
     */
    public boolean hasDefinedGoal(Categories category) {
        return categoryGoals.containsKey(category);
    }

    /**
     * This method check whether a specific cybersecurity function has a goal
     * associated to it or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param function The cybersecurity function that is bein queried.
     * @return true, if the specified cybersecurity function has a goal
     * associated to it. Otherwise, false.
     */
    public boolean hasDefinedGoal(Functions function) {
        return functionGoals.containsKey(function);
    }

    /**
     * This method check whether the global asset's cybersecurity status has a
     * goal associated to it or not.
     *
     * @author Manuel Domínguez-Dorado
     * @return true, if the global asset's cybersecurity status has a goal
     * associated to it. Otherwise, false.
     */
    public boolean hasDefinedGoal() {
        return assetGoal != null;
    }

    /**
     * This method returns the goal associated to the gene/expected outcome
     * specified as a parameter.
     *
     * @author Manuel Domínguez-Dorado
     * @param gene The gene whose goal is being requested.
     * @return the goal associated to the gene/expected outcome specified as a
     * parameter.
     */
    public Goal getGoal(Genes gene) {
        return genesGoals.get(gene);
    }

    /**
     * This method returns the goal associated to the cybersecurity category
     * specified as a parameter.
     *
     * @author Manuel Domínguez-Dorado
     * @param category The cybersecurity category whose goal is being requested.
     * @return the goal associated to the cybersecurity category specified as a
     * parameter.
     */
    public Goal getGoal(Categories category) {
        return categoryGoals.get(category);
    }

    /**
     * This method returns the goal associated to the cybersecurity function
     * specified as a parameter.
     *
     * @author Manuel Domínguez-Dorado
     * @param function The cybersecurity function whose goal is being requested.
     * @return the goal associated to the cybersecurity function specified as a
     * parameter.
     */
    public Goal getGoal(Functions function) {
        return functionGoals.get(function);
    }

    /**
     * This method returns the goal associated to the global asset's
     * cybersecurity status.
     *
     * @author Manuel Domínguez-Dorado
     * @return the goal associated to the global asset's cybersecurity status.
     */
    public Goal getGoal() {
        return assetGoal;
    }

    /**
     * This method generate some individuals of high quality based on the set of
     * defined strategic goals and also depending on the initial status of
     * cybersecurity. They can be used as part of the starting population for
     * FLECO.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialStatus The initial status of cybersecurity of the asset
     * being protected.
     * @return the goal associated to the global asset's cybersecurity status.
     */
    public CopyOnWriteArrayList<Chromosome> generatePrecandidatesBasedOn(Chromosome initialStatus) {
        CopyOnWriteArrayList<Chromosome> candidateChromosomes = new CopyOnWriteArrayList<>();
        Chromosome candidate;
        candidateChromosomes.add(initialStatus);
        if (!genesGoals.isEmpty()) {
            candidate = new Chromosome(implementationGroup);
            candidate.setGenes(initialStatus.getGenes());
            for (Genes gene : genesGoals.keySet()) {
                switch (genesGoals.get(gene).getComparisonOperator()) {
                    case LESS:
                        if (Alleles.getLesser(genesGoals.get(gene).getThresshold()) != null) {
                            candidate.updateAllele(gene, Alleles.getLesser(genesGoals.get(gene).getThresshold()));
                        }
                        break;
                    case LESS_OR_EQUAL:
                        if (Alleles.getLesserOrEqual(genesGoals.get(gene).getThresshold()) != null) {
                            candidate.updateAllele(gene, Alleles.getLesserOrEqual(genesGoals.get(gene).getThresshold()));
                        }
                        break;
                    case EQUAL:
                        if (Alleles.getEqual(genesGoals.get(gene).getThresshold()) != null) {
                            candidate.updateAllele(gene, Alleles.getEqual(genesGoals.get(gene).getThresshold()));
                        }
                        break;
                    case GREATER:
                        if (Alleles.getGreater(genesGoals.get(gene).getThresshold()) != null) {
                            candidate.updateAllele(gene, Alleles.getGreater(genesGoals.get(gene).getThresshold()));
                        }
                        break;
                    case GREATER_OR_EQUAL:
                        if (Alleles.getGreaterOrEqual(genesGoals.get(gene).getThresshold()) != null) {
                            candidate.updateAllele(gene, Alleles.getGreaterOrEqual(genesGoals.get(gene).getThresshold()));
                        }
                        break;
                    default:
                        break;
                }
            }
            candidateChromosomes.add(candidate);
        }
        if (!categoryGoals.isEmpty()) {
            boolean created = false;
            candidate = new Chromosome(implementationGroup);
            candidate.setGenes(initialStatus.getGenes());
            for (Categories category : categoryGoals.keySet()) {
                if (category.appliesToIG(implementationGroup)) {
                    if (categoryGoals.get(category).getComparisonOperator() == ComparisonOperators.EQUAL) {
                        CopyOnWriteArrayList<Genes> genesAplicables;
                        if (categoryGoals.get(category).getThresshold() == Alleles.DLI_0.getDLI()) {
                            genesAplicables = Genes.getGenesFor(category, implementationGroup);
                            for (Genes gene : genesAplicables) {
                                candidate.updateAllele(gene, Alleles.DLI_0);
                                created = true;
                            }
                        } else if (categoryGoals.get(category).getThresshold() == Alleles.DLI_100.getDLI()) {
                            genesAplicables = Genes.getGenesFor(category, implementationGroup);
                            for (Genes gene : genesAplicables) {
                                candidate.updateAllele(gene, Alleles.DLI_100);
                                created = true;
                            }
                        }
                    }
                }
            }
            if (created) {
                candidateChromosomes.add(candidate);
            }
        }
        if (!functionGoals.isEmpty()) {
            boolean created = false;
            candidate = new Chromosome(implementationGroup);
            candidate.setGenes(initialStatus.getGenes());
            for (Functions function : functionGoals.keySet()) {
                if (function.appliesToIG(implementationGroup)) {
                    if (functionGoals.get(function).getComparisonOperator() == ComparisonOperators.EQUAL) {
                        CopyOnWriteArrayList<Genes> genesAplicables;
                        if (functionGoals.get(function).getThresshold() == Alleles.DLI_0.getDLI()) {
                            genesAplicables = Categories.getGenesFor(function, implementationGroup);
                            for (Genes gene : genesAplicables) {
                                candidate.updateAllele(gene, Alleles.DLI_0);
                                created = true;
                            }
                        } else if (functionGoals.get(function).getThresshold() == Alleles.DLI_100.getDLI()) {
                            genesAplicables = Categories.getGenesFor(function, implementationGroup);
                            for (Genes gene : genesAplicables) {
                                candidate.updateAllele(gene, Alleles.DLI_100);
                                created = true;
                            }
                        }
                        candidateChromosomes.add(candidate);
                    }
                }
            }
            if (created) {
                candidateChromosomes.add(candidate);
            }
        }
        if (assetGoal != null) {
            boolean created = false;
            candidate = new Chromosome(implementationGroup);
            candidate.setGenes(initialStatus.getGenes());
            if (assetGoal.getComparisonOperator() == ComparisonOperators.EQUAL) {
                if (assetGoal.getThresshold() == Alleles.DLI_0.getDLI()) {
                    for (Genes gene : Genes.values()) {
                        if (gene.appliesToIG(implementationGroup)) {
                            candidate.updateAllele(gene, Alleles.DLI_0);
                            created = true;
                        }
                    }
                } else if (assetGoal.getThresshold() == Alleles.DLI_100.getDLI()) {
                    for (Genes gene : Genes.values()) {
                        if (gene.appliesToIG(implementationGroup)) {
                            candidate.updateAllele(gene, Alleles.DLI_100);
                            created = true;
                        }
                    }
                }
                candidateChromosomes.add(candidate);
            }
            if (created) {
                candidateChromosomes.add(candidate);
            }
        }

        return candidateChromosomes;
    }

    /**
     * This method returns the number of strategic goals that has been defined.
     *
     * @author Manuel Domínguez-Dorado
     * @return the number of strategic goals that has been defined.
     */
    public int numberOfGoals() {
        int number = 0;
        number += genesGoals.size();
        number += categoryGoals.size();
        number += functionGoals.size();
        if (assetGoal != null) {
            number++;
        }
        return number;
    }

    /**
     * This method prints the strategic goals that has been defined, classifying
     * them in asset goals, functions goals, categories goals and expected
     * outcomes goals.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void print() {
        System.out.println("\tAsset goal...........: " + this.assetGoal.getComparisonOperator().name() + " " + this.assetGoal.getThresshold());
        for (Functions function : this.functionGoals.keySet()) {
            System.out.println("\tFunction goal........: " + function.name() + " " + this.functionGoals.get(function).getComparisonOperator().name() + " " + this.functionGoals.get(function).getThresshold());
        }
        for (Categories category : this.categoryGoals.keySet()) {
            System.out.println("\tCategory goal........: " + category.name() + " " + this.categoryGoals.get(category).getComparisonOperator().name() + " " + this.categoryGoals.get(category).getThresshold());
        }
        for (Genes gene : this.genesGoals.keySet()) {
            System.out.println("\tExpected outcome goal: " + gene.name() + " " + this.genesGoals.get(gene).getComparisonOperator().name() + " " + this.genesGoals.get(gene).getThresshold());
        }
    }
}
