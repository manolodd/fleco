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
package com.manolodominguez.fleco.uleo;

import com.manolodominguez.fleco.genetics.Genes;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This enum defines all cyebrsecurity categories and also its weights as
 * defined in CyberTOMP proposal, depending on whether implementation groups 1,
 * 2, or 3 applies.
 *
 * @author manuel Domínguez-Dorado
 */
public enum Categories {
    ID_AM((float) 8 / 12, (float) 11 / 24, (float) 11 / 40, Functions.IDENTIFY),
    ID_BE((float) 0 / 12, (float) 1 / 24, (float) 6 / 40, Functions.IDENTIFY),
    ID_GV((float) 1 / 12, (float) 3 / 24, (float) 5 / 40, Functions.IDENTIFY),
    ID_RA((float) 1 / 12, (float) 4 / 24, (float) 9 / 40, Functions.IDENTIFY),
    ID_RM((float) 0 / 12, (float) 1 / 24, (float) 4 / 40, Functions.IDENTIFY),
    ID_SC((float) 2 / 12, (float) 4 / 24, (float) 5 / 40, Functions.IDENTIFY),
    PR_AC((float) 7 / 29, (float) 11 / 61, (float) 14 / 80, Functions.PROTECT),
    PR_AT((float) 1 / 29, (float) 4 / 61, (float) 4 / 80, Functions.PROTECT),
    PR_DS((float) 2 / 29, (float) 6 / 61, (float) 10 / 80, Functions.PROTECT),
    PR_IP((float) 8 / 29, (float) 18 / 61, (float) 24 / 80, Functions.PROTECT),
    PR_MA((float) 6 / 29, (float) 15 / 61, (float) 17 / 80, Functions.PROTECT),
    PR_PT((float) 5 / 29, (float) 7 / 61, (float) 11 / 80, Functions.PROTECT),
    DE_AE((float) 1 / 4, (float) 3 / 12, (float) 6 / 23, Functions.DETECT),
    DE_CM((float) 2 / 4, (float) 6 / 12, (float) 11 / 23, Functions.DETECT),
    DE_DP((float) 1 / 4, (float) 3 / 12, (float) 6 / 23, Functions.DETECT),
    RS_AN((float) 0 / 2, (float) 1 / 10, (float) 2 / 18, Functions.RESPOND),
    RS_CO((float) 1 / 2, (float) 2 / 10, (float) 3 / 18, Functions.RESPOND),
    RS_IM((float) 0 / 2, (float) 2 / 10, (float) 5 / 18, Functions.RESPOND),
    RS_MI((float) 1 / 2, (float) 3 / 10, (float) 6 / 18, Functions.RESPOND),
    RS_RP((float) 0 / 2, (float) 2 / 10, (float) 2 / 18, Functions.RESPOND),
    RC_CO((float) 0.0f, (float) 0.0f, (float) 1 / 6, Functions.RECOVER),
    RC_IM((float) 0.0f, (float) 0.0f, (float) 2 / 6, Functions.RECOVER),
    RC_RP((float) 0.0f, (float) 0.0f, (float) 3 / 6, Functions.RECOVER);

    private final float weights[] = new float[3];
    private Functions function = Functions.DETECT;

    /**
     * This is the constructor of the class. it creates the enum and assigns the
     * corresponding values.
     *
     * @author Manuel Domínguez-Dorado
     * @param weightIG1 A float value representing the weight of this
     * cybersecurity category when applying implementation group 1. A number
     * between 0.0 and 1.0.
     * @param weightIG2 A float value representing the weight of this
     * cybersecurity category when applying implementation group 2. A number
     * between 0.0 and 1.0.
     * @param weightIG3 A float value representing the weight of this
     * cybersecurity category when applying implementation group 3. A number
     * between 0.0 and 1.0.
     * @param category The cybersecurity function the category belongs to.
     */
    private Categories(float weightIG1, float weightIG2, float weightIG3, Functions function) {
        this.weights[ImplementationGroups.IG1.getImplementationGroupIndex()] = weightIG1;
        this.weights[ImplementationGroups.IG2.getImplementationGroupIndex()] = weightIG2;
        this.weights[ImplementationGroups.IG3.getImplementationGroupIndex()] = weightIG3;
        this.function = function;
    }

    /**
     * Thies method returns the weight of this cybersecurity category taking
     * into consideration the impleentation group that applies.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation group that applies.
     * @return the weight of the cybersecurity category taking into
     * consideration the impleentation group that applies.
     */
    public float getWeight(ImplementationGroups implementationGroup) {
        return this.weights[implementationGroup.getImplementationGroupIndex()];
    }

    /**
     * This method returns the cybersecurity function the cybersecurity category
     * belongs to.
     *
     * @author Manuel Domínguez-Dorado
     * @return the cybersecurity function the cybersecurity category belongs to.
     */
    public Functions getFunction() {
        return this.function;
    }

    /**
     * Given an implementation group, this method returns whether the
     * cybersecurity category applies for it or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The applicable implementation group.
     * @return true, if the cyebrsecurity category applies. Otherwise, false.
     */
    public boolean appliesToIG(ImplementationGroups implementationGroup) {
        return weights[implementationGroup.getImplementationGroupIndex()] > 0.0f;
    }

    /**
     * Given an implementation group, this method returns a list of
     * genes/expected outcomes that applies to that implementation group and
     * belongs to the cybersecurity category.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The applicable implementation group.
     * @return a list of genes/expected outcomes that applies to that
     * implementation group and belongs to the cybersecurity category.
     */
    public LinkedList<Genes> getGenes(ImplementationGroups implementationGroup) {
        LinkedList<Genes> genes = new LinkedList<>();
        for (Genes g : Genes.values()) {
            if ((g.getCategory() == this) && (g.getWeight(implementationGroup) > 0.0f)) {
                genes.add(g);
            }
        }
        return genes;
    }

    /**
     * This method returns the list of cybersecurity categories that belongs to
     * a given cybersecurity function and are applicable for a given
     * implementation group.
     *
     * @author Manuel Domínguez-Dorado
     * @param function The cybersecurity function whose cbyersecurity categories
     * are required.
     * @param implementationGroup The applicable implementation group.
     * @return the list of cybersecurity categories that belongs to a given
     * cybersecurity function and are applicable for a given implementation
     * group.
     */
    public static CopyOnWriteArrayList<Categories> getCategoriesFor(Functions function, ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Categories> categoriesList = new CopyOnWriteArrayList<>();
        for (Categories category : Categories.values()) {
            if (category.appliesToIG(implementationGroup) && (category.getFunction() == function)) {
                categoriesList.add(category);
            }
        }
        return categoriesList;
    }

    /**
     * This method returns the list of genes/expected outcomes that belongs to a
     * given cybersecurity function and are applicable for a given
     * implementation group.
     *
     * @author Manuel Domínguez-Dorado
     * @param function The cybersecurity function whose cybersecurity categories
     * are required.
     * @param implementationGroup The applicable implementation group.
     * @return the list of genes/expected outcomes that belongs to a given
     * cybersecurity function and are applicable for a given implementation
     * group.
     */
    public static CopyOnWriteArrayList<Genes> getGenesFor(Functions function, ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Genes> genesList = new CopyOnWriteArrayList<>();
        for (Categories category : Categories.getCategoriesFor(function, implementationGroup)) {
            genesList.addAll(Genes.getGenesFor(category, implementationGroup));
        }
        return genesList;
    }

}
