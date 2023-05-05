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
package com.manolodominguez.fleco.genetics;

import com.manolodominguez.fleco.strategicconstraints.Constraint;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.EnumMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a chromosome, an individual within FLECO's population.
 *
 * @author Manuel Domínguez-Dorado
 */
public class Chromosome {

    private static final float FITNESS1_WEIGHT = 0.94f;
    private static final float FITNESS2_WEIGHT = 0.05f;
    private static final float FITNESS3_WEIGHT = 0.01f;

    private EnumMap<Genes, Alleles> genes;
    private float fitness1;
    private float fitness2;
    private float fitness3;
    private final ImplementationGroups implementationGroup;

    /**
     * This is the constructor of the class. It creates a new chromosome and set
     * all its chromosomes to the default allele. It also set the implementation
     * group that will apply.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation group that applies to the
     * asset that is being considered. According to CyberTOMP proposal,
     * depending on the implementation group, the number of genes in the
     * chromosome varies.
     */
    public Chromosome(ImplementationGroups implementationGroup) {
        genes = new EnumMap<>(Genes.class);
        for (Genes gene : Genes.values()) {
            genes.put(gene, Alleles.DLI_0);
        }
        fitness1 = 0.0f;
        fitness2 = 0.0f;
        fitness3 = 0.0f;
        this.implementationGroup = implementationGroup;
    }

    public ImplementationGroups getImplementationGroup() {
        return this.implementationGroup;
    }

    /**
     * This method returns the allele of the specified gene.
     *
     * @author Manuel Domínguez-Dorado
     * @param gene the gene whose allele is being requested.
     * @return The allele of the specified gene.
     */
    public Alleles getAllele(Genes gene) {
        return this.genes.get(gene);
    }

    /**
     * This method set the genes and alleles of this chromosome to those
     * specified as a parameter.
     *
     * @author Manuel Domínguez-Dorado
     * @param genes The genes and alleles to configure the chromosome.
     */
    public void setGenes(EnumMap<Genes, Alleles> genes) {
        this.genes.clear();
        this.genes.putAll(genes);
    }

    /**
     * This method returns the genes and alleles of this chromosome.
     *
     * @author Manuel Domínguez-Dorado
     * @return The genes and alleles of this chromosome.
     */
    public EnumMap<Genes, Alleles> getGenes() {
        return genes;
    }

    /**
     * This method add or update a gene and its respective allele in the
     * chromosome.
     *
     * @author Manuel Domínguez-Dorado
     * @param gene The gene to be added or udated.
     * @param allele The allele for the specified gene.
     */
    public void updateAllele(Genes gene, Alleles allele) {
        this.genes.put(gene, allele);
    }

    /**
     * This method assigns a random allele to every gene in the chromosome.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void randomizeGenes() {
        for (Genes gene : Genes.values()) {
            if (gene.appliesToIG(implementationGroup)) {
                Alleles[] allelesArray = Alleles.values();
                int randomAllele = ThreadLocalRandom.current().nextInt(0, allelesArray.length);
                genes.put(gene, allelesArray[randomAllele]);
            } else {
                genes.put(gene, Alleles.DLI_0);
            }
        }
    }

    /**
     * This method prints in console a beautified version of the chromosome.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void print() {
        EnumMap<Genes, Float> genesValues = new EnumMap<>(Genes.class);
        EnumMap<Categories, Float> categoriesValues = new EnumMap<>(Categories.class);
        EnumMap<Functions, Float> functionsValues = new EnumMap<>(Functions.class);
        Float assetValue = 0.0f;

        float auxFunctionFitness = 0.0f;
        float auxCategoryFitness = 0.0f;
        int num = 0;
        for (Functions f : Functions.values()) {
            if (f.appliesToIG(implementationGroup)) {
                auxFunctionFitness = 0.0f;

                for (Categories c : f.getCategories(implementationGroup)) {
                    auxCategoryFitness = 0.0f;
                    for (Genes g : c.getGenes(implementationGroup)) {
                        // Gene raw value
                        genesValues.put(g, getAllele(g).getDLI());
                        // To compute category fitness
                        num++;
                        auxCategoryFitness += getAllele(g).getDLI() * g.getWeight(implementationGroup);
                    }
                    // Category raw value
                    categoriesValues.put(c, auxCategoryFitness);
                    // To compute Function fitness
                    auxCategoryFitness *= c.getWeight(implementationGroup);
                    if (auxCategoryFitness > c.getWeight(implementationGroup)) {
                        auxCategoryFitness = c.getWeight(implementationGroup);
                    }
                    auxFunctionFitness += auxCategoryFitness;
                }
                // Function raw value
                functionsValues.put(f, auxFunctionFitness);
                // To compute asset fitness
                auxFunctionFitness *= f.getWeight(implementationGroup);
                if (auxFunctionFitness > f.getWeight(implementationGroup)) {
                    auxFunctionFitness = f.getWeight(implementationGroup);
                }
                assetValue += auxFunctionFitness;
            }
        }
        System.out.println("\tAsset: " + assetValue);
        for (Functions function : Functions.getFunctionsFor(implementationGroup)) {
            System.out.println("\t\t" + function.name() + ": " + functionsValues.get(function));
            for (Categories category : function.getCategories(implementationGroup)) {
                System.out.println("\t\t\t" + category.name() + ": " + categoriesValues.get(category));
                for (Genes gene : category.getGenes(implementationGroup)) {
                    System.out.println("\t\t\t\t" + gene.name().substring(6) + ": " + getAllele(gene).getDLI());
                }
            }
        }
    }

    /**
     * This method returns the genes of this chromosome as JSON strings to be
     * treated automatically whenever needed.
     *
     * @author Manuel Domínguez-Dorado
     * @return the genes of this chromosome as JSON strings.
     */
    public String getGenesAsJSONString() {
        String JSONString = "";
        int genesNum = 0;
        for (Genes gene : genes.keySet()) {
            if (gene.appliesToIG(implementationGroup)) {
                JSONString += "\t\t{\"gene\":\"" + gene.name() + "\",\"allele\":\"" + getAllele(gene).name() + "\"}";
                if (genesNum < (Genes.getGenesFor(implementationGroup).size() - 1)) {
                    JSONString += ",\n";
                } else {
                    JSONString += "\n";
                }
                genesNum++;
            }
        }
        return JSONString;
    }

    /**
     * This method prints in console a plain version of the chromosome showing
     * the value of every gene.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void printGenes() {
        EnumMap<Genes, Float> genesValues = new EnumMap<>(Genes.class);
        EnumMap<Categories, Float> categoriesValues = new EnumMap<>(Categories.class);
        EnumMap<Functions, Float> functionsValues = new EnumMap<>(Functions.class);
        Float assetValue = 0.0f;

        float auxFunctionFitness = 0.0f;
        float auxCategoryFitness = 0.0f;
        int num = 0;
        for (Functions f : Functions.values()) {
            if (f.appliesToIG(implementationGroup)) {
                auxFunctionFitness = 0.0f;

                for (Categories c : f.getCategories(implementationGroup)) {
                    auxCategoryFitness = 0.0f;
                    for (Genes g : c.getGenes(implementationGroup)) {
                        // Gene raw value
                        genesValues.put(g, getAllele(g).getDLI());
                        // To compute category fitness
                        num++;
                        auxCategoryFitness += getAllele(g).getDLI() * g.getWeight(implementationGroup);
                    }
                    // Category raw value
                    if (auxCategoryFitness > 1.0f) {
                        auxCategoryFitness = 1.0f;
                    }
                    categoriesValues.put(c, auxCategoryFitness);
                    // To compute Function fitness
                    auxCategoryFitness *= c.getWeight(implementationGroup);
                    if (auxCategoryFitness > c.getWeight(implementationGroup)) {
                        auxCategoryFitness = c.getWeight(implementationGroup);
                    }
                    auxFunctionFitness += auxCategoryFitness;
                }
                // Function raw value
                if (auxFunctionFitness > 1.0f) {
                    auxFunctionFitness = 1.0f;
                }
                functionsValues.put(f, auxFunctionFitness);
                // To compute asset fitness
                auxFunctionFitness *= f.getWeight(implementationGroup);
                if (auxFunctionFitness > f.getWeight(implementationGroup)) {
                    auxFunctionFitness = f.getWeight(implementationGroup);
                }
                assetValue += auxFunctionFitness;
            }
        }
        for (Functions function : Functions.getFunctionsFor(implementationGroup)) {
            for (Categories category : function.getCategories(implementationGroup)) {
                for (Genes gene : category.getGenes(implementationGroup)) {
                    System.out.println(gene.name() + "#" + getAllele(gene).getDLI());
                }
            }
        }
    }

    /**
     * This method prints in console a beautified version of the chromosome.It
     * includes information to compare the chromosome to a previous initial
     * state.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialStatus A chromosome representing an initial cybersecurity
     * status the current chromosome is going to be compared to.
     */
    public void print(Chromosome initialStatus) {
        EnumMap<Genes, Float> genesValues = new EnumMap<>(Genes.class);
        EnumMap<Categories, Float> categoriesValues = new EnumMap<>(Categories.class);
        EnumMap<Functions, Float> functionsValues = new EnumMap<>(Functions.class);
        Float assetValue = 0.0f;

        float auxFunctionFitness = 0.0f;
        float auxCategoryFitness = 0.0f;
        int num = 0;
        for (Functions f : Functions.values()) {
            if (f.appliesToIG(implementationGroup)) {
                auxFunctionFitness = 0.0f;

                for (Categories c : f.getCategories(implementationGroup)) {
                    auxCategoryFitness = 0.0f;
                    for (Genes g : c.getGenes(implementationGroup)) {
                        // Gene raw value
                        genesValues.put(g, getAllele(g).getDLI());
                        // To compute category fitness
                        num++;
                        auxCategoryFitness += getAllele(g).getDLI() * g.getWeight(implementationGroup);
                    }
                    // Category raw value
                    if (auxCategoryFitness > 1.0f) {
                        auxCategoryFitness = 1.0f;
                    }
                    categoriesValues.put(c, auxCategoryFitness);
                    // To compute Function fitness
                    auxCategoryFitness *= c.getWeight(implementationGroup);
                    if (auxCategoryFitness > c.getWeight(implementationGroup)) {
                        auxCategoryFitness = c.getWeight(implementationGroup);
                    }
                    auxFunctionFitness += auxCategoryFitness;
                }
                // Function raw value
                if (auxFunctionFitness > 1.0f) {
                    auxFunctionFitness = 1.0f;
                }
                functionsValues.put(f, auxFunctionFitness);
                // To compute asset fitness
                auxFunctionFitness *= f.getWeight(implementationGroup);
                if (auxFunctionFitness > f.getWeight(implementationGroup)) {
                    auxFunctionFitness = f.getWeight(implementationGroup);
                }
                assetValue += auxFunctionFitness;
            }
        }
        System.out.println("\tAsset: " + assetValue);
        for (Functions function : Functions.getFunctionsFor(implementationGroup)) {
            System.out.println("\t\t" + function.name() + ": " + functionsValues.get(function));
            for (Categories category : function.getCategories(implementationGroup)) {
                System.out.println("\t\t\t" + category.name() + ": " + categoriesValues.get(category));
                for (Genes gene : category.getGenes(implementationGroup)) {
                    System.out.println("\t\t\t\t" + gene.name().substring(6) + ": " + getAllele(gene).getDLI() + getSimilarityText(initialStatus.getAllele(gene), getAllele(gene)));
                }
            }
        }
    }

    /**
     * This method returns a text explaining the evolution between two alleles
     * specified as parameters.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialAllele The initial allele to be compared.
     * @param currentAllele The current/final allele to be compared.
     * @return A text explaining the evolution between two alleles specified as
     * parameters.
     */
    private String getSimilarityText(Alleles initialAllele, Alleles currentAllele) {
        if (initialAllele == currentAllele) {
            return " (UNCHANGED)";
        }
        return " (PREVIOUSLY " + initialAllele.getDLI() + ")";
    }

    /**
     * This method returns the global fitness as an aggregation of previously
     * computed chromosome's fitnesses for each optimization objective.
     *
     * @author Manuel Domínguez-Dorado
     * @return the previously computed chromosome's fitness.
     */
    public float getFitness() {
        return ((fitness1 * FITNESS1_WEIGHT) + (fitness2 * FITNESS2_WEIGHT) + (fitness3 * FITNESS3_WEIGHT));
    }

    /**
     * This method computes the chromosome's fitness. It is an aggregated
     * fitness that, through a set of weigths, takes into account three
     * optimization objetives.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialStatus A chromosome representing an initial cybersecurity
     * status.
     * @param strategicConstraints A ser of strategic constraints to be takein
     * into consideration when optimizing the three optimization objectives.
     */
    public void computeFitness(Chromosome initialStatus, StrategicConstraints strategicConstraints) {
        fitness1 = computeFitnessConstraintsCoverage(strategicConstraints);
        fitness2 = computeFitnessSimilarityToCurrentState(initialStatus);
        fitness3 = computeFitnessGlobalCybersecurityState();
    }

    /**
     * This method returns the fitness related to the optimization objective 1
     * (compliance with the defined strategic constraints).
     *
     * @author Manuel Domínguez-Dorado
     * @return the fitness related to the optimization objective 1 (compliance
     * with the defined strategic constraints).
     */
    public float getFitnessConstraintsCoverage() {
        return this.fitness1;
    }

    /**
     * This method returns the fitness related to the optimization objective 1
     * (compliance with the defined strategic constraints). This method goes
     * constraint by constraint checking whether the constraint is satisfied by
     * the current chromosome. If this is the case, the constraint is considered
     * as satisfied (+1.0). Otherwise, a linear value between 0.0 and 1.0 is
     * asigned depending on the degree of compliance regarding the constraint.
     * Finally a value representing the number of constraints satisfied by this
     * chromosome in relation to the total number of constraints is returned as
     * a normalized value between 0.0 and 1.0.
     *
     * @author Manuel Domínguez-Dorado
     * @return the fitness related to the optimization objective 1 (compliance
     * with the defined strategic constraints).
     */
    private float computeFitnessConstraintsCoverage(StrategicConstraints strategicConstraints) {
        float numberOfConstraints = strategicConstraints.numberOfConstraints();
        float satisfiedConstraints = 0.0f;
        EnumMap<Genes, Float> genesValues = new EnumMap<>(Genes.class);
        EnumMap<Categories, Float> categoriesValues = new EnumMap<>(Categories.class);
        EnumMap<Functions, Float> functionsValues = new EnumMap<>(Functions.class);
        Float assetValue = 0.0f;

        float auxFunctionFitness = 0.0f;
        float auxCategoryFitness = 0.0f;
        int counter = 0;
        for (Functions function : Functions.values()) {
            if (function.appliesToIG(implementationGroup)) {
                auxFunctionFitness = 0.0f;

                for (Categories category : function.getCategories(implementationGroup)) {
                    auxCategoryFitness = 0.0f;
                    for (Genes gene : category.getGenes(implementationGroup)) {
                        // Gene raw value
                        genesValues.put(gene, getAllele(gene).getDLI());
                        // To compute category fitness
                        counter++;
                        auxCategoryFitness += getAllele(gene).getDLI() * gene.getWeight(implementationGroup);
                    }
                    // Category raw value
                    categoriesValues.put(category, auxCategoryFitness);
                    // To compute Function fitness
                    auxCategoryFitness *= category.getWeight(implementationGroup);
                    if (auxCategoryFitness > category.getWeight(implementationGroup)) {
                        auxCategoryFitness = category.getWeight(implementationGroup);
                    }
                    auxFunctionFitness += auxCategoryFitness;
                }
                // Function raw value
                functionsValues.put(function, auxFunctionFitness);
                // To compute asset fitness
                auxFunctionFitness *= function.getWeight(implementationGroup);
                if (auxFunctionFitness > function.getWeight(implementationGroup)) {
                    auxFunctionFitness = function.getWeight(implementationGroup);
                }
                assetValue += auxFunctionFitness;
            }
        }

        for (Genes gene : genesValues.keySet()) {
            if (strategicConstraints.hasDefinedConstraint(gene)) {
                Constraint constraint = strategicConstraints.getConstraint(gene);
                switch (constraint.getComparisonOperator()) {

                    case LESS:
                        if (genesValues.get(gene) < constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if ((genesValues.get(gene) == 1.0f) && (constraint.getThreshold() == 1.0f)) {
                            satisfiedConstraints += 0.99f;
                        } else {
                            satisfiedConstraints += (-0.99f / (1.0f - constraint.getThreshold())) * genesValues.get(gene) + (0.99f - (-0.99f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        }
                        break;

                    case LESS_OR_EQUAL:
                        if (genesValues.get(gene) <= constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else {
                            satisfiedConstraints += (-1.0f / (1.0f - constraint.getThreshold())) * genesValues.get(gene) + (1.0f - (-1.0f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        }
                        break;

                    case EQUAL:
                        if (genesValues.get(gene) == constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if (genesValues.get(gene) > constraint.getThreshold()) {
                            satisfiedConstraints += (-1.0f / (1.0f - constraint.getThreshold())) * genesValues.get(gene) + (1.0f - (-1.0f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        } else if (genesValues.get(gene) > constraint.getThreshold()) {
                            satisfiedConstraints += ((genesValues.get(gene) / constraint.getThreshold()));
                        }
                        break;

                    case GREATER:
                        if (genesValues.get(gene) > constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if ((genesValues.get(gene) == 0.0f) && (constraint.getThreshold() == 0.0f)) {
                            satisfiedConstraints += 0.99f;
                        } else {
                            satisfiedConstraints += ((0.99f * genesValues.get(gene)) / constraint.getThreshold());
                        }
                        break;

                    case GREATER_OR_EQUAL:
                        if (genesValues.get(gene) >= constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else {
                            satisfiedConstraints += ((genesValues.get(gene) / constraint.getThreshold()));
                        }
                        break;

                    default:
                        break;
                }
            }
        }

        for (Categories category : categoriesValues.keySet()) {
            if (strategicConstraints.hasDefinedConstraint(category)) {
                Constraint constraint = strategicConstraints.getConstraint(category);
                switch (constraint.getComparisonOperator()) {
                    case LESS:
                        if (categoriesValues.get(category) < constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if ((categoriesValues.get(category) == 1.0f) && (constraint.getThreshold() == 1.0f)) {
                            satisfiedConstraints += 0.99f;
                        } else {
                            satisfiedConstraints += (-0.99f / (1.0f - constraint.getThreshold())) * categoriesValues.get(category) + (0.99f - (-0.99f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        }
                        break;
                    case LESS_OR_EQUAL:
                        if (categoriesValues.get(category) <= constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else {
                            satisfiedConstraints += (-1.0f / (1.0f - constraint.getThreshold())) * categoriesValues.get(category) + (1.0f - (-1.0f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        }
                        break;
                    case EQUAL:
                        if (categoriesValues.get(category) == constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if (categoriesValues.get(category) > constraint.getThreshold()) {
                            satisfiedConstraints += (-1.0f / (1.0f - constraint.getThreshold())) * categoriesValues.get(category) + (1.0f - (-1.0f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        } else if (categoriesValues.get(category) > constraint.getThreshold()) {
                            satisfiedConstraints += ((categoriesValues.get(category) / constraint.getThreshold()));
                        }
                        break;
                    case GREATER:
                        if (categoriesValues.get(category) > constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if ((categoriesValues.get(category) == 0.0f) && (constraint.getThreshold() == 0.0f)) {
                            satisfiedConstraints += 0.99f;
                        } else {
                            satisfiedConstraints += ((0.99f * categoriesValues.get(category)) / constraint.getThreshold());
                        }
                        break;
                    case GREATER_OR_EQUAL:
                        if (categoriesValues.get(category) >= constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else {
                            satisfiedConstraints += ((categoriesValues.get(category) / constraint.getThreshold()));
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        for (Functions function : functionsValues.keySet()) {
            if (strategicConstraints.hasDefinedConstraint(function)) {
                Constraint constraint = strategicConstraints.getConstraint(function);
                switch (constraint.getComparisonOperator()) {
                    case LESS:
                        if (functionsValues.get(function) < constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if ((functionsValues.get(function) == 1.0f) && (constraint.getThreshold() == 1.0f)) {
                            satisfiedConstraints += 0.99f;
                        } else {
                            satisfiedConstraints += (-0.99f / (1.0f - constraint.getThreshold())) * functionsValues.get(function) + (0.99f - (-0.99f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        }
                        break;
                    case LESS_OR_EQUAL:
                        if (functionsValues.get(function) <= constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else {
                            satisfiedConstraints += (-1.0f / (1.0f - constraint.getThreshold())) * functionsValues.get(function) + (1.0f - (-1.0f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        }
                        break;
                    case EQUAL:
                        if (functionsValues.get(function) == constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if (functionsValues.get(function) > constraint.getThreshold()) {
                            satisfiedConstraints += (-1.0f / (1.0f - constraint.getThreshold())) * functionsValues.get(function) + (1.0f - (-1.0f / (1.0f - constraint.getThreshold())) * constraint.getThreshold());
                        } else if (functionsValues.get(function) > constraint.getThreshold()) {
                            satisfiedConstraints += ((functionsValues.get(function) / constraint.getThreshold()));
                        }
                        break;
                    case GREATER:
                        if (functionsValues.get(function) > constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else if ((functionsValues.get(function) == 0.0f) && (constraint.getThreshold() == 0.0f)) {
                            satisfiedConstraints += 0.99f;
                        } else {
                            satisfiedConstraints += ((0.99f * functionsValues.get(function)) / constraint.getThreshold());
                        }
                        break;
                    case GREATER_OR_EQUAL:
                        if (functionsValues.get(function) >= constraint.getThreshold()) {
                            satisfiedConstraints++;
                        } else {
                            satisfiedConstraints += ((functionsValues.get(function) / constraint.getThreshold()));
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        if (strategicConstraints.hasDefinedConstraint()) {
            Constraint condition = strategicConstraints.getConstraint();
            switch (condition.getComparisonOperator()) {
                case LESS:
                    if (assetValue < condition.getThreshold()) {
                        satisfiedConstraints++;
                    } else if ((assetValue == 1.0f) && (condition.getThreshold() == 1.0f)) {
                        satisfiedConstraints += 0.99f;
                    } else {
                        satisfiedConstraints += (-0.99f / (1.0f - condition.getThreshold())) * assetValue + (0.99f - (-0.99f / (1.0f - condition.getThreshold())) * condition.getThreshold());
                    }
                    break;
                case LESS_OR_EQUAL:
                    if (assetValue <= condition.getThreshold()) {
                        satisfiedConstraints++;
                    } else {
                        satisfiedConstraints += (-1.0f / (1.0f - condition.getThreshold())) * assetValue + (1.0f - (-1.0f / (1.0f - condition.getThreshold())) * condition.getThreshold());
                    }
                    break;
                case EQUAL:
                    if (assetValue == condition.getThreshold()) {
                        satisfiedConstraints++;
                    } else if (assetValue > condition.getThreshold()) {
                        satisfiedConstraints += (-1.0f / (1.0f - condition.getThreshold())) * assetValue + (1.0f - (-1.0f / (1.0f - condition.getThreshold())) * condition.getThreshold());
                    } else if (assetValue > condition.getThreshold()) {
                        satisfiedConstraints += ((assetValue / condition.getThreshold()));
                    }
                    break;
                case GREATER:
                    if (assetValue > condition.getThreshold()) {
                        satisfiedConstraints++;
                    } else if ((assetValue == 0.0f) && (condition.getThreshold() == 0.0f)) {
                        satisfiedConstraints += 0.99f;
                    } else {
                        satisfiedConstraints += ((0.99f * assetValue) / condition.getThreshold());
                    }
                    break;
                case GREATER_OR_EQUAL:
                    if (assetValue >= condition.getThreshold()) {
                        satisfiedConstraints++;
                    } else {
                        satisfiedConstraints += ((assetValue / condition.getThreshold()));
                    }
                    break;
                default:
                    break;
            }
        }
        if (numberOfConstraints == 0.0f) {
            return 1.0f;
        } else {
            return (satisfiedConstraints / numberOfConstraints);
        }
    }

    /**
     * This method returns the fitness related to the optimization objective 2
     * (similarity between the current chromosome and the one supplied as the
     * initial status).
     *
     * @author Manuel Domínguez-Dorado
     * @return The fitness related to the optimization objective 2 (similarity
     * between the current chromosome and the one supplied as the initial
     * status).
     */
    public float getFitnessSimilarityToCurrentState() {
        return this.fitness2;
    }

    /**
     * This method returns the fitness related to the optimization objective 2
     * (similarity between the current chromosome and the one supplied as the
     * initial status). This method goes gene by gene comparing the current
     * chromosome and the one supplied as initial status. If the corresponding
     * couple of genes are equals this gene is assigned a value of 1.0.
     * Otherwise, linear value between 0.0 and 1.0 is asigned depending on the
     * degree of similarity between both genes. Finally a value representing the
     * number of genes that are equal in relation to the total number of genes
     * is returned as a normalized value between 0.0 and 1.0.
     *
     * @author Manuel Domínguez-Dorado
     * @return the fitness related to the optimization objective 2 (similarity
     * between the current chromosome and the one supplied as the initial
     * status).
     */
    private float computeFitnessSimilarityToCurrentState(Chromosome referenceChromosome) {
        float maxSimilarGenes = 0.0f;
        float auxFitness = 0.0f;
        CopyOnWriteArrayList<Genes> genes = new CopyOnWriteArrayList<>();
        for (Genes gene : Genes.values()) {
            if (gene.appliesToIG(implementationGroup)) {
                genes.add(gene);
                auxFitness += (1.0f - Math.abs(referenceChromosome.getAllele(gene).getDLI() - getAllele(gene).getDLI()));
            }
        }
        maxSimilarGenes = genes.size();
        if (maxSimilarGenes > 0.0f) {
            if ((auxFitness / maxSimilarGenes) > 1.0f) {
                return 1.0f;
            }
            return (auxFitness / maxSimilarGenes);
        }
        return 0.0f;
    }

    /**
     * This method returns the fitness related to the optimization objective 3
     * (enhance gobal cybersecurity status).
     *
     * @author Manuel Domínguez-Dorado
     * @return the fitness related to the optimization objective 3 (enhance
     * gobal cybersecurity status).
     */
    public float getFitnessGlobalCybersecurityState() {
        return this.fitness3;
    }

    /**
     * This method returns the fitness related to the optimization objective 3
     * (enhance gobal asset's cybersecurity status). This method computes the
     * complete set of metrics defined in CyberTOMP, following a bottom-up
     * approach, from the expected outcomes level to the asset level, starting
     * from evaluating the discrete level of implementation of each expected
     * outcome (gene). Finally a value representing the asset's cybersecurity
     * status is returned as a normalized value between 0.0 and 1.0.
     *
     * @author Manuel Domínguez-Dorado
     * @return The fitness related to the optimization objective 3 (enhance
     * gobal asset's cybersecurity status).
     */
    private float computeFitnessGlobalCybersecurityState() {
        float assetFitness = 0.0f;
        float functionFitness = 0.0f;
        float categoryFitness = 0.0f;
        int num = 0;
        for (Functions f : Functions.values()) {
            functionFitness = 0.0f;
            for (Categories c : f.getCategories(implementationGroup)) {
                categoryFitness = 0.0f;
                for (Genes g : c.getGenes(implementationGroup)) {
                    num++;
                    categoryFitness += getAllele(g).getDLI() * g.getWeight(implementationGroup);
                }
                categoryFitness *= c.getWeight(implementationGroup);
                if (categoryFitness > c.getWeight(implementationGroup)) {
                    categoryFitness = c.getWeight(implementationGroup);
                }
                functionFitness += categoryFitness;
            }
            functionFitness *= f.getWeight(implementationGroup);
            if (functionFitness > f.getWeight(implementationGroup)) {
                functionFitness = f.getWeight(implementationGroup);
            }
            assetFitness += functionFitness;
        }
        // Because of roundness it could be 1.000000000000012, for instance. So 
        // here we set it to 1.0f
        if (assetFitness > 1.0f) {
            assetFitness = 1.0f;
        }
        return assetFitness;
    }

}
