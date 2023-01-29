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
