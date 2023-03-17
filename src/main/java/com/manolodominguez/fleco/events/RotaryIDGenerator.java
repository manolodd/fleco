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
package com.manolodominguez.fleco.events;

/**
 * This class implements a ID generator that generates consecutive numeric IDs,
 * in a cycle that never ends.
 *
 * @author Manuel Domínguez Dorado
 */
@SuppressWarnings("serial")
public class RotaryIDGenerator {

    private static final int DEFAULT_ID = 0;

    private int identifier;

    /**
     * This method is the constructor of the class. It is create a new instance.
     *
     * @author Manuel Domínguez Dorado
     */
    public RotaryIDGenerator() {
        identifier = DEFAULT_ID;
    }

    /**
     * This method resets the ID generator to its initial internal value.
     *
     * @author Manuel Domínguez Dorado
     */
    public synchronized void reset() {
        identifier = DEFAULT_ID;
    }

    /**
     * This method generates a new ID.
     *
     * @author Manuel Domínguez Dorado
     * @return an integer value that is unique.
     */
    synchronized public int getNextIdentifier() {
        if (identifier >= Integer.MAX_VALUE) {
            identifier = DEFAULT_ID;
        } else {
            identifier++;
        }
        return (identifier);
    }

    /**
     * This method sets the new internal value of the ID generator .
     *
     * @author Manuel Domínguez Dorado
     * @param newInternalIDValue the ID generator new internal value.
     */
    synchronized public void setIdentifier(int newInternalIDValue) {
        if (newInternalIDValue < RotaryIDGenerator.DEFAULT_ID) {
            throw new IllegalArgumentException("newInternalIDValue is out of range");
        } else {
            identifier = newInternalIDValue;
        }
    }
}
