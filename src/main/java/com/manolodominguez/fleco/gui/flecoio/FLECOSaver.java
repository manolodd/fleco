/* 
 *******************************************************************************
 * FLECO (Fast, Lightweight, and Efficient Cybersecurity Optimization) (1) 
 * Adaptive, Constrained, and Multi-objective Genetic Algorithm is a genetic 
 * algorithm designed to assist the Asset's Cybersecurity Committee (ACC) in 
 * making decisions during the application of CyberTOMP (2), aimed at managing 
 * comprehensive cybersecurity at both tactical and operational levels.
 *
 * (1) Domínguez-Dorado, M.; Cortés-Polo, D.; Carmona-Murillo, J.; 
 * Rodríguez-Pérez, F.J.; Galeano-Brajones, J. Fast, Lightweight, and Efficient 
 * Cybersecurity Optimization for Tactical–Operational Management. Appl. Sci. 
 * 2023, 13, 6327. https://doi.org/10.3390/app13106327
 *
 * (2) Dominguez-Dorado, M., Carmona-Murillo, J., Cortés-Polo, D., and
 * Rodríguez-Pérez, F. J. (2022). CyberTOMP: A novel systematic framework to
 * manage asset-focused cybersecurity from tactical and operational levels. IEEE
 * Access, 10, 122454-122485. https://doi.org/10.1109/ACCESS.2022.3223440
 *******************************************************************************
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
 *******************************************************************************
 */
package com.manolodominguez.fleco.gui.flecoio;

import com.manolodominguez.fleco.genetics.Chromosome;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This class implements FLECO file saver that can store a case from memory to a
 * file on disk.
 *
 * @author Manuel Domínguez-Dorado
 */
public class FLECOSaver {

    private Chromosome initialStatus;
    private StrategicConstraints strategicConstraints;
    private Chromosome targetStatus;
    private FileOutputStream outputStream;
    private PrintStream output;

    /**
     * This is the constructor of the class.It creates a new FLECO loader and
     * sets its initial values.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialStatus the initial status of the FLECO case being saved.
     * @param strategicConstraints the strategic constraints of the FLECO case
     * being saved.
     * @param targetStatus the target status of the FLECO case being saved.
     */
    public FLECOSaver(Chromosome initialStatus, StrategicConstraints strategicConstraints, Chromosome targetStatus) {
        if (initialStatus == null) {
            throw new IllegalArgumentException("initialStatus is null");
        }
        this.initialStatus = initialStatus;
        this.strategicConstraints = strategicConstraints;
        this.targetStatus = targetStatus;
        outputStream = null;
        output = null;
    }

    /**
     * This method save a FLECO case from memory to a file on disk.
     *
     * @author Manuel Domínguez-Dorado
     * @param outputFile the destination file for the case being saved.
     * @return true, if the FLECO case is saved to the specified file.
     * Otherwise, false.
     */
    public boolean save(File outputFile) {
        if (outputFile == null) {
            throw new IllegalArgumentException("outputFile is null");
        }
        try {
            outputStream = new FileOutputStream(outputFile);
            output = new PrintStream(this.outputStream);
            output.println("{");
            output.println("\t\"caseIG\":\"" + initialStatus.getImplementationGroup().name() + "\",");
            if (targetStatus == null) {
                output.println("\t\"hasTargetStatus\":false,");
            } else {
                output.println("\t\"hasTargetStatus\":true,");
            }
            // INITIAL STATUS
            output.println("\t\"initialStatus\": [");
            output.print(initialStatus.getGenesAsJSONString());
            output.println("\t],");
            // STRATEGIC CONSTRAINTS
            output.println("\t\"strategicConstraints\": [");
            output.print(strategicConstraints.getConstraintsAsJSONString());
            output.println("\t],");
            // TARGET STATUS
            output.println("\t\"targetStatus\": [");
            if (targetStatus != null) {
                output.print(targetStatus.getGenesAsJSONString());
            }
            output.println("\t]");
            output.println("}");
            outputStream.close();
            output.close();
        }
        catch (IOException e) {
            System.out.println("Error saving FLECO case to disk");
            return false;
        }
        return true;
    }

}
