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
package com.manolodominguez.fleco.genetic;

import com.manolodominguez.fleco.business.Condition;
import com.manolodominguez.fleco.business.Operators;
import com.manolodominguez.fleco.business.StrategicGoals;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.EnumMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author manolodd
 */
public class Chromosome {

    private EnumMap<Genes, Alleles> genes;
    private float fitness;
    private final ImplementationGroups implementationGroup;

    public Chromosome(ImplementationGroups implementationGroup) {
        genes = new EnumMap<>(Genes.class);
        for (Genes gene : Genes.values()) {
            genes.put(gene, Alleles.DLI_0);
        }
        fitness = 0.0f;
        this.implementationGroup = implementationGroup;
    }

    public Alleles getAllele(Genes gene) {
        return this.genes.get(gene);
    }

    public void setGenes(EnumMap<Genes, Alleles> genes) {
        this.genes.clear();
        this.genes.putAll(genes);
    }

    public EnumMap<Genes, Alleles> getGenes() {
        return genes;
    }

    public void updateAllele(Genes gene, Alleles allele) {
        this.genes.put(gene, allele);
    }

    public void randomize() {
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

    public void print() {
    }

    public float getFitness() {
        return fitness;
    }

    public void computeFitness(Chromosome currentStatus, StrategicGoals sg) {
        float fitness1 = 0.0f;
        float fitness2 = 0.0f;
        float fitness3 = 0.0f;
        fitness1 = getFitnessGlobalCybersecurityState();
        fitness2 = getFitnessSimilarityToCurrentState(currentStatus);
        fitness3 = getFitnessComplianceGoalsCoverage(sg);
        fitness = (fitness1 * 0.05f) + (fitness2 * 0.2f) + (fitness3 * 0.75f);
    }

    public float getFitnessComplianceGoalsCoverage(StrategicGoals strategicGoals) {
        float numberOfGoals = strategicGoals.numberOfGoals();
        float coveredGoals = 0.0f;
        EnumMap<Genes, Float> genesValues = new EnumMap<>(Genes.class);
        EnumMap<Categories, Float> categoriesValues = new EnumMap<>(Categories.class);
        EnumMap<Functions, Float> functionsValues = new EnumMap<>(Functions.class);
        Float assetValue = 0.0f;

        float auxFunctionFitness = 0.0f;
        float auxCategoryFitness = 0.0f;
        int num = 0;
        //System.out.println("Goals: " + numberOfGoals);
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

        for (Genes gene : genesValues.keySet()) {
            if (strategicGoals.hasDefinedGoal(gene)) {
                Condition condition = strategicGoals.getGoal(gene);
                //System.out.println("Gene current: " + genesValues.get(gene) + " Gene required: " + condition.getOperator() + " " + condition.getRequirement());
                switch (condition.getOperator()) {

                    case LESS:
                        if (genesValues.get(gene) < condition.getRequirement()) {
                            coveredGoals++;
                            // To avoid division by zero
                        } else if ((genesValues.get(gene) == 1.0f) && (condition.getRequirement() == 1.0f)) {
                            coveredGoals += 0.99f;
                        } else {
                            coveredGoals += (-0.99f / (1.0f - condition.getRequirement())) * genesValues.get(gene) + (0.99f - (-0.99f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        }
                        break;

                    case LESS_OR_EQUAL:
                        if (genesValues.get(gene) <= condition.getRequirement()) {
                            coveredGoals++;
                        } else {
                            coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * genesValues.get(gene) + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        }
                        break;

                    case EQUAL:
                        if (genesValues.get(gene) == condition.getRequirement()) {
                            coveredGoals++;
                        } else if (genesValues.get(gene) > condition.getRequirement()) {
                            coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * genesValues.get(gene) + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        } else if (genesValues.get(gene) > condition.getRequirement()) {
                            coveredGoals += ((genesValues.get(gene) / condition.getRequirement()));
                        }
                        break;

                    case GREATER:
                        if (genesValues.get(gene) > condition.getRequirement()) {
                            coveredGoals++;
                            // To avoid division by zero
                        } else if ((genesValues.get(gene) == 0.0f) && (condition.getRequirement() == 0.0f)) {
                            coveredGoals += 0.99f;
                        } else {
                            coveredGoals += ((0.99f * genesValues.get(gene)) / condition.getRequirement());
                        }
                        break;

                    case GREATER_OR_EQUAL:
                        if (genesValues.get(gene) >= condition.getRequirement()) {
                            coveredGoals++;
                        } else {
                            coveredGoals += ((genesValues.get(gene) / condition.getRequirement()));
                        }
                        break;

                    default:
                        break;
                }
            }
        }

        for (Categories category : categoriesValues.keySet()) {
            if (strategicGoals.hasDefinedGoal(category)) {
                Condition condition = strategicGoals.getGoal(category);
                //System.out.println("Category current: " + categoriesValues.get(category) + " Category required: " + condition.getOperator() + " " + condition.getRequirement());
                switch (condition.getOperator()) {
                    case LESS:
                        if (categoriesValues.get(category) < condition.getRequirement()) {
                            coveredGoals++;
                            // To avoid division by zero
                        } else if ((categoriesValues.get(category) == 1.0f) && (condition.getRequirement() == 1.0f)) {
                            coveredGoals += 0.99f;
                        } else {
                            coveredGoals += (-0.99f / (1.0f - condition.getRequirement())) * categoriesValues.get(category) + (0.99f - (-0.99f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        }
                        break;
                    case LESS_OR_EQUAL:
                        if (categoriesValues.get(category) <= condition.getRequirement()) {
                            coveredGoals++;
                        } else {
                            coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * categoriesValues.get(category) + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        }
                        break;
                    case EQUAL:
                        if (categoriesValues.get(category) == condition.getRequirement()) {
                            coveredGoals++;
                        } else if (categoriesValues.get(category) > condition.getRequirement()) {
                            coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * categoriesValues.get(category) + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        } else if (categoriesValues.get(category) > condition.getRequirement()) {
                            coveredGoals += ((categoriesValues.get(category) / condition.getRequirement()));
                        }
                        break;
                    case GREATER:
                        if (categoriesValues.get(category) > condition.getRequirement()) {
                            coveredGoals++;
                            // To avoid division by zero
                        } else if ((categoriesValues.get(category) == 0.0f) && (condition.getRequirement() == 0.0f)) {
                            coveredGoals += 0.99f;
                        } else {
                            coveredGoals += ((0.99f * categoriesValues.get(category)) / condition.getRequirement());
                        }
                        break;
                    case GREATER_OR_EQUAL:
                        if (categoriesValues.get(category) >= condition.getRequirement()) {
                            coveredGoals++;
                        } else {
                            coveredGoals += ((categoriesValues.get(category) / condition.getRequirement()));
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        for (Functions function : functionsValues.keySet()) {
            if (strategicGoals.hasDefinedGoal(function)) {
                Condition condition = strategicGoals.getGoal(function);
                //System.out.println("Function current: " + functionsValues.get(function) + " Function required: " + condition.getOperator() + " " + condition.getRequirement());
                switch (condition.getOperator()) {
                    case LESS:
                        if (functionsValues.get(function) < condition.getRequirement()) {
                            coveredGoals++;
                            // To avoid division by zero
                        } else if ((functionsValues.get(function) == 1.0f) && (condition.getRequirement() == 1.0f)) {
                            coveredGoals += 0.99f;
                        } else {
                            coveredGoals += (-0.99f / (1.0f - condition.getRequirement())) * functionsValues.get(function) + (0.99f - (-0.99f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        }
                        break;
                    case LESS_OR_EQUAL:
                        if (functionsValues.get(function) <= condition.getRequirement()) {
                            coveredGoals++;
                        } else {
                            coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * functionsValues.get(function) + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        }
                        break;
                    case EQUAL:
                        if (functionsValues.get(function) == condition.getRequirement()) {
                            coveredGoals++;
                        } else if (functionsValues.get(function) > condition.getRequirement()) {
                            coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * functionsValues.get(function) + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                        } else if (functionsValues.get(function) > condition.getRequirement()) {
                            coveredGoals += ((functionsValues.get(function) / condition.getRequirement()));
                        }
                        break;
                    case GREATER:
                        if (functionsValues.get(function) > condition.getRequirement()) {
                            coveredGoals++;
                            // To avoid division by zero
                        } else if ((functionsValues.get(function) == 0.0f) && (condition.getRequirement() == 0.0f)) {
                            coveredGoals += 0.99f;
                        } else {
                            coveredGoals += ((0.99f * functionsValues.get(function)) / condition.getRequirement());
                        }
                        break;
                    case GREATER_OR_EQUAL:
                        if (functionsValues.get(function) >= condition.getRequirement()) {
                            coveredGoals++;
                        } else {
                            coveredGoals += ((functionsValues.get(function) / condition.getRequirement()));
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        if (strategicGoals.hasDefinedGoal()) {
            Condition condition = strategicGoals.getGoal();
            //System.out.println("Asset current: " + assetValue + " Asset required: " + condition.getOperator() + " " + condition.getRequirement());
            switch (condition.getOperator()) {
                case LESS:
                    if (assetValue < condition.getRequirement()) {
                        coveredGoals++;
                    // To avoid division by zero
                    } else if ((assetValue == 1.0f) && (condition.getRequirement() == 1.0f)) {
                        coveredGoals += 0.99f;
                    } else {
                        coveredGoals += (-0.99f / (1.0f - condition.getRequirement())) * assetValue + (0.99f - (-0.99f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                    }
                    break;
                case LESS_OR_EQUAL:
                    if (assetValue <= condition.getRequirement()) {
                        coveredGoals++;
                    } else {
                        coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * assetValue + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                    }
                    break;
                case EQUAL:
                    if (assetValue == condition.getRequirement()) {
                        coveredGoals++;
                    } else if (assetValue > condition.getRequirement()) {
                        coveredGoals += (-1.0f / (1.0f - condition.getRequirement())) * assetValue + (1.0f - (-1.0f / (1.0f - condition.getRequirement())) * condition.getRequirement());
                    } else if (assetValue > condition.getRequirement()) {
                        coveredGoals += ((assetValue / condition.getRequirement()));
                    }
                    break;
                case GREATER:
                    if (assetValue > condition.getRequirement()) {
                        coveredGoals++;
                    // To avoid division by zero
                    } else if ((assetValue == 0.0f) && (condition.getRequirement() == 0.0f)) {
                        coveredGoals += 0.99f;
                    } else {
                        coveredGoals += ((0.99f * assetValue) / condition.getRequirement());
                    }
                    break;
                case GREATER_OR_EQUAL:
                    if (assetValue >= condition.getRequirement()) {
                        coveredGoals++;
                    } else {
                        coveredGoals += ((assetValue / condition.getRequirement()));
                    }
                    break;
                default:
                    break;
            }
        }

        //System.out.println("Covered goals: " + coveredGoals);

        if (numberOfGoals == 0.0f) {
            return 1.0f;
        } else {
            return (coveredGoals / numberOfGoals);
        }
    }

    public float getFitnessSimilarityToCurrentState(Chromosome referenceChromosome) {
        /*
        float maxSimilarGenes = 0.0f;
        float similarGenes = 0.0f;
        CopyOnWriteArrayList<Genes> genes = new CopyOnWriteArrayList<>();
        for (Genes gene : Genes.values()) {
            if (gene.appliesToIG(implementationGroup)) {
                genes.add(gene);
                if (getAllele(gene) == otherChromosome.getAllele(gene)) {
                    similarGenes++;
                }
            }
        }
        maxSimilarGenes = genes.size();
        if (maxSimilarGenes > 0.0f) {
            if ((similarGenes / maxSimilarGenes) > 1.0f) {
                return 1.0f;
            }
            return (similarGenes / maxSimilarGenes);
        }
        return 0.0f;
         */
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

    public float getFitnessGlobalCybersecurityState() {
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
        // Because of roundness it ccoul be 1.000000000012, for instance. So here
        // we set it to 1.0f
        if (assetFitness > 1.0f) {
            assetFitness = 1.0f;
        }
        return assetFitness;
    }

}
