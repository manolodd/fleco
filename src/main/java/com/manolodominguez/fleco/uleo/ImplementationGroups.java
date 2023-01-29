/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.uleo;

/**
 *
 * @author manolodd
 */
public enum ImplementationGroups {
    IG1(0),
    IG2(1),
    IG3(2);
    
    private int implementationGroup;
    
    private ImplementationGroups(int implementationGroup) {
        this.implementationGroup = implementationGroup;
    }
    
    public int getImplementationGroup() {
        return this.implementationGroup;
    }
}
