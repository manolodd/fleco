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

/**
 * This enum defines all implementation groups as defined in CyberTOMP proposal.
 *
 * @author manuel Domínguez-Dorado
 */
public enum ImplementationGroups {
    IG1(0),
    IG2(1),
    IG3(2);

    private int implementationGroupIndex;

    /**
     * This is the constructor of the class. it creates the enum and assigns the
     * corresponding values.
     *
     * @author manuel Domínguez-Dorado
     * @param implementationGroupIndex The index of this implementation group,
     * that would be used as array index afterwards.
     */
    private ImplementationGroups(int implementationGroupIndex) {
        this.implementationGroupIndex = implementationGroupIndex;
    }

    /**
     * This method returns the index associated to this implementation group.
     *
     * @author manuel Domínguez-Dorado
     * @return the index associated to this implementation group.
     */
    public int getImplementationGroupIndex() {
        return this.implementationGroupIndex;
    }
}
