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

import com.manolodominguez.fleco.events.ProgressEvent;
import javax.swing.JProgressBar;
import com.manolodominguez.fleco.events.IFLECOProgressEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements a progress bar that also is able to receive
 * progressevents from an instance of FLECO algorithm.
 *
 * @author Manuel Domínguez-Dorado
 */
public class FLECOProgressBar extends JProgressBar implements IFLECOProgressEventListener {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(FLECOProgressBar.class);
    
    /**
     * This is the constructor of the class. it creates a new instance of
     * FLECOProgressBar and initialize its attributes.
     *
     * @author Manuel Domínguez-Dorado
     */
    public FLECOProgressBar() {
        super();
    }

    /**
     * This class is called when an instance of FLECO algorithm this progress
     * bar is subscribed to, send a progress event.
     *
     * @param progressEvent the progress event sent by amn instance of FLECO
     * algorithm.
     * @author Manuel Domínguez-Dorado
     */
    @Override
    public void onProgressEventReceived(ProgressEvent progressEvent) {
        setValue((int) (progressEvent.getProgressPercentage() * 100));
    }

}
