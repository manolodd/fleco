/* 
 *******************************************************************************
 * FLECO (Fast, Lightweight, and Efficient Cybersecurity Optimization) Dynamic, 
 * Constrained and Multi-objective Genetic Algorithm is a genetic algorithm 
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

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This enum defines all cyebrsecurity functions and also its weights as defined
 * in CyberTOMP proposal, depending on whether implementation groups 1, 2, or 3
 * applies.
 *
 * @author manuel Domínguez-Dorado
 */
public enum Functions {
    IDENTIFY((float) 4 / 15, (float) 6 / 20, (float) 6 / 23),
    PROTECT((float) 6 / 15, (float) 6 / 20, (float) 6 / 23),
    DETECT((float) 3 / 15, (float) 3 / 20, (float) 3 / 23),
    RESPOND((float) 2 / 15, (float) 5 / 20, (float) 5 / 23),
    RECOVER((float) 0 / 15, (float) 0 / 20, (float) 3 / 23);

    private float weights[] = new float[3];

    /**
     * This is the constructor of the class. it creates the enum and assigns the
     * corresponding values.
     *
     * @author Manuel Domínguez-Dorado
     * @param weightIG1 A float value representing the weight of this
     * cybersecurity function when applying implementation group 1. A number
     * between 0.0 and 1.0.
     * @param weightIG2 A float value representing the weight of this
     * cybersecurity function when applying implementation group 2. A number
     * between 0.0 and 1.0.
     * @param weightIG3 A float value representing the weight of this
     * cybersecurity function when applying implementation group 3. A number
     * between 0.0 and 1.0.
     */
    private Functions(float weightIG1, float weightIG2, float weightIG3) {
        this.weights[0] = weightIG1;
        this.weights[1] = weightIG2;
        this.weights[2] = weightIG3;

        this.weights[ImplementationGroups.IG1.getImplementationGroupIndex()] = weightIG1;
        this.weights[ImplementationGroups.IG2.getImplementationGroupIndex()] = weightIG2;
        this.weights[ImplementationGroups.IG3.getImplementationGroupIndex()] = weightIG3;
    }

    /**
     * Thies method returns the weight of this cybersecurity function taking
     * into consideration the impleentation group that applies.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation group that applies.
     * @return the weight of the cybersecurity function taking into
     * consideration the impleentation group that applies.
     */
    public float getWeight(ImplementationGroups implementationGroup) {
        return this.weights[implementationGroup.getImplementationGroupIndex()];
    }

    /**
     * Given an implementation group, this method returns whether the
     * cybersecurity function applies for it or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The applicable implementation group.
     * @return true, if the cybersecurity function applies. Otherwise, false.
     */
    public boolean appliesToIG(ImplementationGroups implementationGroup) {
        return weights[implementationGroup.getImplementationGroupIndex()] > 0.0f;
    }

    /**
     * Given an implementation group, this method returns a list of
     * cybersecurity categories that applies to that implementation group and
     * belongs to the cybersecurity function.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The applicable implementation group.
     * @return a list of cybersecurity categories that applies to that
     * implementation group and belongs to the cybersecurity function.
     */
    public CopyOnWriteArrayList<Categories> getCategories(ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Categories> categories = new CopyOnWriteArrayList<>();
        for (Categories category : Categories.values()) {
            if ((category.getFunction() == this) && (category.getWeight(implementationGroup) > 0.0f)) {
                categories.add(category);
            }
        }
        return categories;
    }

    /**
     * This method returns the list of cybersecurity categories that are
     * applicable for a given implementation group.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The applicable implementation group.
     * @return the list of cybersecurity categories that are applicable for a
     * given implementation group
     */
    public static CopyOnWriteArrayList<Functions> getFunctionsFor(ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Functions> functionsList = new CopyOnWriteArrayList<>();
        for (Functions function : Functions.values()) {
            if (function.appliesToIG(implementationGroup)) {
                functionsList.add(function);
            }
        }
        return functionsList;
    }
}
