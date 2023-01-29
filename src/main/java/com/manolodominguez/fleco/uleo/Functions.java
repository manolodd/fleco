/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.uleo;

import java.util.LinkedList;

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
    
    public LinkedList<Categories> getCategories(ImplementationGroups implementationGroup) {
        LinkedList<Categories> categories = new LinkedList<>();
        for (Categories c: Categories.values()) {
            if ((c.getFunction() == this) && (c.getWeight(implementationGroup) > 0.0f)) {
                categories.add(c);
            }
        }
        return categories;
    }
    
}
