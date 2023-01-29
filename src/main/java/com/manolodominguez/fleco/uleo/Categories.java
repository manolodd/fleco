/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.uleo;

import com.manolodominguez.fleco.genetic.Genes;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author manolodd
 */
public enum Categories {
    ID_AM((float) 8/12, (float) 11/24, (float) 11/40, Functions.IDENTIFY),
    ID_BE((float) 0/12, (float)  1/24, (float)  6/40, Functions.IDENTIFY),
    ID_GV((float) 1/12, (float)  3/24, (float)  5/40, Functions.IDENTIFY),
    ID_RA((float) 1/12, (float)  4/24, (float)  9/40, Functions.IDENTIFY),
    ID_RM((float) 0/12, (float)  1/24, (float)  4/40, Functions.IDENTIFY),
    ID_SC((float) 2/12, (float)  4/24, (float)  5/40, Functions.IDENTIFY),
    
    PR_AC((float) 7/29, (float) 11/61, (float) 14/80, Functions.PROTECT),
    PR_AT((float) 1/29, (float)  4/61, (float)  4/80, Functions.PROTECT),
    PR_DS((float) 2/29, (float)  6/61, (float) 10/80, Functions.PROTECT),
    PR_IP((float) 8/29, (float) 18/61, (float) 24/80, Functions.PROTECT),
    PR_MA((float) 6/29, (float) 15/61, (float) 17/80, Functions.PROTECT),
    PR_PT((float) 5/29, (float)  7/61, (float) 11/80, Functions.PROTECT),
    
    DE_AE((float) 1/4,  (float) 3/12,  (float)  6/23, Functions.DETECT),
    DE_CM((float) 2/4,  (float) 6/12,  (float) 11/23, Functions.DETECT),
    DE_DP((float) 1/4,  (float) 3/12,  (float)  6/23, Functions.DETECT), 
    
    RS_AN((float) 0/2,  (float) 1/10, (float)   2/18, Functions.RESPOND), 
    RS_CO((float) 1/2,  (float) 2/10, (float)   3/18, Functions.RESPOND), 
    RS_IM((float) 0/2,  (float) 2/10, (float)   5/18, Functions.RESPOND), 
    RS_MI((float) 1/2,  (float) 3/10, (float)   6/18, Functions.RESPOND),
    RS_RP((float) 0/2,  (float) 2/10, (float)   2/18, Functions.RESPOND), 
    
    RC_CO((float) 0.0f, (float) 0.0f, (float)    1/6, Functions.RECOVER), 
    RC_IM((float) 0.0f, (float) 0.0f, (float)    2/6, Functions.RECOVER), 
    RC_RP((float) 0.0f, (float) 0.0f, (float)    3/6, Functions.RECOVER);
    
    
    private final float weights[] = new float[3];
    private Functions function = Functions.DETECT;
    
    private Categories(float weightIG1, float weightIG2, float weightIG3, Functions function) {
        this.weights[ImplementationGroups.IG1.getImplementationGroup()] = weightIG1;
        this.weights[ImplementationGroups.IG2.getImplementationGroup()] = weightIG2;
        this.weights[ImplementationGroups.IG3.getImplementationGroup()] = weightIG3;
        this.function = function;
    }
    
    public float getWeight(ImplementationGroups implementationGroup) {
        return this.weights[implementationGroup.getImplementationGroup()];
    }
    
    public Functions getFunction() {
        return this.function;
    }
    
    public boolean appliesToIG(ImplementationGroups implementationGroup) {
        return weights[implementationGroup.getImplementationGroup()] > 0.0f;
    }
    
    public LinkedList<Genes> getGenes(ImplementationGroups implementationGroup) {
        LinkedList<Genes> genes = new LinkedList<>();
        for (Genes g: Genes.values()) {
            if ((g.getCategory() == this) && (g.getWeight(implementationGroup) > 0.0f)) {
                genes.add(g);
            }
        }
        return genes;
    }
    
    public static CopyOnWriteArrayList<Categories> getCategoriesFor(Functions function, ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Categories> categoriesList= new CopyOnWriteArrayList<>();
        for (Categories category: Categories.values()) {
            if (category.appliesToIG(implementationGroup) && (category.getFunction() == function)) {
                categoriesList.add(category);
            }
        }
        return categoriesList;
    }
    
    public static CopyOnWriteArrayList<Genes> getGenesFor(Functions function, ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Genes> genesList= new CopyOnWriteArrayList<>();
        for (Categories category: Categories.getCategoriesFor(function, implementationGroup)) {
            genesList.addAll(Genes.getGenesFor(category, implementationGroup));
        }
        return genesList;
    }
    
}
