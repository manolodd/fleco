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
package com.manolodominguez.fleco.gui;

import com.manolodominguez.fleco.algorithm.FLECO;
import javax.swing.SwingWorker;

/**
 * This class implements a worker that executes FLECO algorithm from a swing GUI
 * without freezing it.
 *
 * @author Manuel Domínguez-Dorado
 */
public class FLECOSwingWorker extends SwingWorker<FLECO, FLECO> {

    private FLECO fleco;
    private IFLECOGUI gui;

    /**
     * This is the constructor of the class. It creates a new instance and
     * initialize its attributes with their default values.
     *
     * @author Manuel Domínguez-Dorado
     * @param fleco The instance of FLECO algorithm to be run in background.
     * @param gui The GUI from which FLECO is launched.
     */
    public FLECOSwingWorker(FLECO fleco, IFLECOGUI gui) {
        this.fleco = fleco;
        this.gui = gui;
    }

    /**
     * This method executes the FLECO algorithm in background.
     *
     * @author Manuel Domínguez-Dorado
     * @return The executed instance of the FLECO algorithm.
     * @throws Exception when something uncontrolled happens while computing in
     * background.
     */
    @Override
    protected FLECO doInBackground() throws Exception {
        if (!isCancelled()) {
            fleco.evolve();
        }
        return fleco;
    }

    /**
     * This method is called when the background execution is finished. It calls
     * a methoid of the GUI in order to update the corresponding components if
     * needed.
     *
     * @author Manuel Domínguez-Dorado
     */
    @Override
    protected void done() {
        gui.afterOnRunFLECO();
    }

}
