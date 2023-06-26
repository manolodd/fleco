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

/**
 * This is an enum implementation to identify all available resources that can
 * be used in FLECO.
 *
 * @author Manuel Domínguez-Dorado
 */
public enum AvailableResources {
    FLECO_JSON_SCHEMA("FLECOJSONSchema.json");

    private final String resourceFileName;

    /**
     * This is the constructor of the enum. It creates a new enum item and
     * associates a filename to it.
     *
     * @param resourceFileName the filename that corresponds to the new
     * available resource created.
     * @author Manuel Domínguez-Dorado
     */
    private AvailableResources(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    /**
     * This methods gets the complete file path to the resource associated to
     * the enum item.
     *
     * @author Manuel Domínguez-Dorado
     * @return the complete file path to the resource associated to the enum
     * item.
     */
    public String getResource() {
        return AvailableResources.RESOURCES_PATH + resourceFileName;
    }

    private static final String RESOURCES_PATH = "/com/manolodominguez/fleco/json/";
}
