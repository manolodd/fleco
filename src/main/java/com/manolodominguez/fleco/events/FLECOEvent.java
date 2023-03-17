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

import com.manolodominguez.fleco.algorithm.FLECO;
import java.time.Instant;
import java.util.EventObject;

/**
 * This class is the superclass of all events generated in FLECO. It is an
 * abstract class that has to be implemented by all subclassess.
 *
 * @author Manuel Domínguez Dorado
 */
@SuppressWarnings("serial")
public abstract class FLECOEvent extends EventObject implements Comparable<FLECOEvent> {

    private long eventID;
    private Instant instant;

    /**
     * This is the constructor of the class that will be called by all
     * subclasses to create a new event in FLECO.
     *
     * @author Manuel Domínguez Dorado
     * @param instant Every events includes the moment of their generation, in
     * nanoseconds. It allow syncronizing everything that is happening during a
     * simulation.
     * @param source The object that generates the event.
     * @param eventID The unique event identifier.
     */
    public FLECOEvent(FLECO source, long eventID, Instant instant) {
        super(source);
        this.eventID = eventID;
        this.instant = instant;
    }

    /**
     * This method gets the instant in wich the event was generated.
     *
     * @author Manuel Domínguez Dorado
     * @return the instant in wich the event was generated.
     */
    public Instant getInstant() {
        return this.instant;
    }

    /**
     * This method gets the event unique identifier.
     *
     * @author Manuel Domínguez Dorado
     * @return the event unique identifier.
     */
    public long getEventID() {
        return this.eventID;
    }

    /**
     * This method sets the event unique identifier.
     *
     * @author Manuel Domínguez Dorado
     * @param eventID the event unique identifier.
     */
    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    /**
     * This method compares the current instance to another instance of
     * FLECOEvent to know the ordinal position of one to respect the other.
     *
     * @author Manuel Domínguez Dorado
     * @param anotherEvent a FLECOEvent instance to be compared to the current
     * one.
     * @return -1, 0 or 1 depending on whether the current instance is lesser,
     * equal or greater than the one specified as an argument.
     */
    @Override
    public int compareTo(FLECOEvent anotherEvent) {
        if (instant.toEpochMilli() < anotherEvent.getInstant().toEpochMilli()) {
            return -1;
        } else if (instant.toEpochMilli() > anotherEvent.getInstant().toEpochMilli()) {
            return 1;
        } else {
            if (getEventID() < anotherEvent.getEventID()) {
                return -1;
            } else if (getEventID() == anotherEvent.getEventID()) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * This method gets the event type of this event. It will be one of the
     * constants defined in this class.
     *
     * @return the event type of this event.It will be one of the constants
     * defined in this class.
     * @author Manuel Domínguez Dorado
     */
    public abstract EventTypes getType();
}
