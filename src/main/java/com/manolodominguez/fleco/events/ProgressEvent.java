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
import com.manolodominguez.fleco.genetic.Chromosome;
import java.time.Instant;

/**
 * This class implements a progress event that will allow knowing the state of
 * the FLECO execution when it is running.
 *
 * @author Manuel Domínguez Dorado
 */
@SuppressWarnings("serial")
public class ProgressEvent extends FLECOEvent {

    private final long totalTime;
    private final long currentTime;
    private final long currentGeneration;
    private Chromosome currentBestChromosome;
    private boolean converged;

    private static final int ZERO = 0;

    /**
     * This method is the constrctor of the class.It creates a new instance of
 TProgressEvent.
     *
     * @author Manuel Domínguez Dorado
     * @param eventID The unique event identifier.
     * @param eventGenerator The object that generates the event.
     * @param totalTime
     * @param currentTime
     * @param currentGeneration
     * @param currentBestChromosome
     * @param converged
     */
    public ProgressEvent(FLECO eventGenerator, long eventID, long totalTime, long currentTime, long currentGeneration, Chromosome currentBestChromosome, boolean converged) {
        super(eventGenerator, eventID, Instant.now());
        this.totalTime = totalTime;
        this.currentTime = currentTime;
        this.currentGeneration = currentGeneration;
        this.currentBestChromosome = currentBestChromosome;
        this.converged = converged;
        if (currentBestChromosome == null) {
            throw new IllegalArgumentException("currentBestChromosome cannot be null");
        }
    }

    /**
     * This method returns the progress percentage advertised by this event.
     *
     * @author Manuel Domínguez Dorado
     * @return The progress percentage that the event is carrying out and will
     * be received by the listener.
     */
    public float getProgressPercentage() {
        if (totalTime != 0) {
            return (float) (currentTime / totalTime);
        }
        return 1.0f;
    }

    public long getCurrentGeneration() {
        return currentGeneration;
    }

    public Chromosome getCurrentBestChromosome() {
        return this.currentBestChromosome;
    }

    public boolean hasConverged() {
        return this.converged;
    }

    public void print() {
        if ((currentGeneration % 100) == 0) {
            System.out.println("Time: " + currentTime + "/" + totalTime + "(" + (float) currentTime / totalTime + "%) Generation: " + currentGeneration + " Current best solution: " + currentBestChromosome.getFitness());
        }
    }

    /**
     * This method return the type of this event. It is one of the constants
     * defined in TOpenSimMPLSEvent.
     *
     * @author Manuel Domínguez Dorado
     * @return The type of this event. It is one of the constants defined in
     * TOpenSimMPLSEvent.
     */
    @Override
    public EventTypes getType() {
        return EventTypes.PROGRESS;
    }
}
