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
package com.manolodominguez.fleco.uleo;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author manolodd
 */
public enum Functions {
    IDENTIFY((float) 4/15, (float) 6/20, (float) 6/23),
    PROTECT( (float) 6/15, (float) 6/20, (float) 6/23),
    DETECT(  (float) 3/15, (float) 3/20, (float) 3/23),
    RESPOND( (float) 2/15, (float) 5/20, (float) 5/23),
    RECOVER( (float) 0/15, (float) 0/20, (float) 3/23);

    private float weights[] = new float[3];

    private Functions(float weightIG1, float weightIG2, float weightIG3) {
        this.weights[0] = weightIG1;
        this.weights[1] = weightIG2;
        this.weights[2] = weightIG3;
        
        this.weights[ImplementationGroups.IG1.getImplementationGroup()] = weightIG1;
        this.weights[ImplementationGroups.IG2.getImplementationGroup()] = weightIG2;
        this.weights[ImplementationGroups.IG3.getImplementationGroup()] = weightIG3;
    }

    public float getWeight(ImplementationGroups implementationGroup) {
        return this.weights[implementationGroup.getImplementationGroup()];
    }

    public boolean appliesToIG(ImplementationGroups implementationGroup) {
        return weights[implementationGroup.getImplementationGroup()] > 0.0f;
    }
    
    public CopyOnWriteArrayList<Categories> getCategories(ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Categories> categories = new CopyOnWriteArrayList<>();
        for (Categories category: Categories.values()) {
            if ((category.getFunction() == this) && (category.getWeight(implementationGroup) > 0.0f)) {
                categories.add(category);
            }
        }
        return categories;
    }
    
    public static CopyOnWriteArrayList<Functions> getFunctionsFor(ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Functions> functionsList= new CopyOnWriteArrayList<>();
        for (Functions function: Functions.values()) {
            if (function.appliesToIG(implementationGroup)) {
                functionsList.add(function);
            }
        }
        return functionsList;
    }    
}
