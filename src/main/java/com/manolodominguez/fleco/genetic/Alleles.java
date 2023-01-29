/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.genetic;

/**
 *
 * @author manolodd
 */
public enum Alleles {
    DLI_0(0.0f),
    DLI_33(0.33f),
    DLI_67(0.67f),
    DLI_100(1.0f);

    private final float DLI;

    private Alleles(float DLI) {
        this.DLI = DLI;
    }

    public float getDLI() {
        return this.DLI;
    }
    
    public static Alleles getGreater(Float value) {
        Alleles result = null;
        for (Alleles al: Alleles.values()) {
            if (al.getDLI() > value) {
                result = al;
                break;
            }
        }
        return result;
    }
    
    public static Alleles getGreaterOrEqual(Float value) {
        Alleles result = null;
        for (Alleles al: Alleles.values()) {
            if (al.getDLI() >= value) {
                result = al;
                break;
            }
        }
        return result;
    }
    
    public static Alleles getEqual(Float value) {
        Alleles result = null;
        for (Alleles al: Alleles.values()) {
            if (al.getDLI() == value) {
                result = al;
                break;
            }
        }
        return result;
    }

    public static Alleles getLesserOrEqual(Float value) {
        Alleles result = null;
        for (Alleles al: Alleles.values()) {
            if (al.getDLI() <= value) {
                result = al;
                break;
            }
        }
        return result;
    }

    public static Alleles getLesser(Float value) {
        Alleles result = null;
        for (Alleles al: Alleles.values()) {
            if (al.getDLI() < value) {
                result = al;
                break;
            }
        }
        return result;
    }
}
