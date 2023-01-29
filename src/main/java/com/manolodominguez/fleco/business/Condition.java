/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.business;

/**
 *
 * @author manolodd
 */
public class Condition {
    private Operators operator;
    private float requirement;
    
    public Condition(Operators operator, float requirement) {
        this.operator = operator;
        this.requirement = requirement;
    }
    
    public float getRequirement() {
        return requirement;
    }

    public Operators getOperator() {
        return operator;
    }
}
