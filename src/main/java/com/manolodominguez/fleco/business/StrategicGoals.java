/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.business;

import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.genetic.Genes;
import com.manolodominguez.fleco.genetic.Alleles;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.EnumMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author manolodd
 */
public class StrategicGoals {

    private EnumMap<Genes, Condition> genesGoals;
    private EnumMap<Categories, Condition> categoryGoals;
    private EnumMap<Functions, Condition> functionGoals;
    private Condition assetGoal;
    private ImplementationGroups implementationGroup;

    public StrategicGoals(ImplementationGroups implementationGroup) {
        genesGoals = new EnumMap<>(Genes.class);
        categoryGoals = new EnumMap<>(Categories.class);
        functionGoals = new EnumMap<>(Functions.class);
        this.implementationGroup = implementationGroup;
        this.assetGoal = null;
    }

    public void addGoal(Genes gene, Condition condition) {
        if (gene.appliesToIG(implementationGroup)) {
            genesGoals.put(gene, condition);
        }
    }

    public void addGoal(Categories category, Condition condition) {
        if (category.appliesToIG(implementationGroup)) {
            categoryGoals.put(category, condition);
        }
    }

    public void addGoal(Functions function, Condition condition) {
        if (function.appliesToIG(implementationGroup)) {
            functionGoals.put(function, condition);
        }
    }

    public void addGoal(Condition condition) {
        this.assetGoal = condition;
    }

    public boolean hasDefinedGoal(Genes gene) {
        return genesGoals.containsKey(gene);
    }

    public boolean hasDefinedGoal(Categories category) {
        return categoryGoals.containsKey(category);
    }

    public boolean hasDefinedGoal(Functions function) {
        return functionGoals.containsKey(function);
    }

    public boolean hasDefinedGoal() {
        return assetGoal != null;
    }

    public Condition getGoal(Genes gene) {
        return genesGoals.get(gene);
    }

    public Condition getGoal(Categories category) {
        return categoryGoals.get(category);
    }

    public Condition getGoal(Functions function) {
        return functionGoals.get(function);
    }

    public Condition getGoal() {
        return assetGoal;
    }

    public CopyOnWriteArrayList<Chromosome> generateCandidateChromosomes(Chromosome currentStatus) {
        CopyOnWriteArrayList<Chromosome> candidateChromosomes = new CopyOnWriteArrayList<>();
        Chromosome candidate;
        candidateChromosomes.add(currentStatus);
        if (!genesGoals.isEmpty()) {
            candidate = new Chromosome(implementationGroup);
            candidate.setGenes(currentStatus.getGenes());
            for (Genes gene : genesGoals.keySet()) {
                switch (genesGoals.get(gene).getOperator()) {
                    case LESS:
                        if (Alleles.getLesser(genesGoals.get(gene).getRequirement()) != null) {
                            candidate.updateAllele(gene, Alleles.getLesser(genesGoals.get(gene).getRequirement()));
                        }
                        break;
                    case LESS_OR_EQUAL:
                        if (Alleles.getLesserOrEqual(genesGoals.get(gene).getRequirement()) != null) {
                            candidate.updateAllele(gene, Alleles.getLesserOrEqual(genesGoals.get(gene).getRequirement()));
                        }
                        break;
                    case EQUAL:
                        if (Alleles.getEqual(genesGoals.get(gene).getRequirement()) != null) {
                            candidate.updateAllele(gene, Alleles.getEqual(genesGoals.get(gene).getRequirement()));
                        }
                        break;
                    case GREATER:
                        if (Alleles.getGreater(genesGoals.get(gene).getRequirement()) != null) {
                            candidate.updateAllele(gene, Alleles.getGreater(genesGoals.get(gene).getRequirement()));
                        }
                        break;
                    case GREATER_OR_EQUAL:
                        if (Alleles.getGreaterOrEqual(genesGoals.get(gene).getRequirement()) != null) {
                            candidate.updateAllele(gene, Alleles.getGreaterOrEqual(genesGoals.get(gene).getRequirement()));
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
            candidate.setGenes(currentStatus.getGenes());
            for (Categories category : categoryGoals.keySet()) {
                if (category.appliesToIG(implementationGroup)) {
                    if (categoryGoals.get(category).getOperator() == Operators.EQUAL) {
                        CopyOnWriteArrayList<Genes> genesAplicables;
                        if (categoryGoals.get(category).getRequirement() == Alleles.DLI_0.getDLI()) {
                            genesAplicables = Genes.getGenesFor(category, implementationGroup);
                            for (Genes gene : genesAplicables) {
                                candidate.updateAllele(gene, Alleles.DLI_0);
                                created = true;
                            }
                        } else if (categoryGoals.get(category).getRequirement() == Alleles.DLI_100.getDLI()) {
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
            candidate.setGenes(currentStatus.getGenes());
            for (Functions function : functionGoals.keySet()) {
                if (function.appliesToIG(implementationGroup)) {
                    if (functionGoals.get(function).getOperator() == Operators.EQUAL) {
                        CopyOnWriteArrayList<Genes> genesAplicables;
                        if (functionGoals.get(function).getRequirement() == Alleles.DLI_0.getDLI()) {
                            genesAplicables = Categories.getGenesFor(function, implementationGroup);
                            for (Genes gene : genesAplicables) {
                                candidate.updateAllele(gene, Alleles.DLI_0);
                                created = true;
                            }
                        } else if (functionGoals.get(function).getRequirement() == Alleles.DLI_100.getDLI()) {
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
            candidate.setGenes(currentStatus.getGenes());
            if (assetGoal.getOperator() == Operators.EQUAL) {
                if (assetGoal.getRequirement() == Alleles.DLI_0.getDLI()) {
                    for (Genes gene : Genes.values()) {
                        if (gene.appliesToIG(implementationGroup)) {
                            candidate.updateAllele(gene, Alleles.DLI_0);
                            created = true;
                        }
                    }
                } else if (assetGoal.getRequirement() == Alleles.DLI_100.getDLI()) {
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
}
