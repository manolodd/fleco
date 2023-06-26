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

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * This class implements a filter that is used in load/save dialogs to only
 * permit FLECO files.
 *
 * @author Manuel Domínguez-Dorado
 */
public class FLECOFilter extends FileFilter {

    /**
     * This is the constructor of the class. It creates a new file filter and
     * sets its initial values.
     *
     * @author Manuel Domínguez-Dorado
     */
    public FLECOFilter() {
        super();
    }

    /**
     * This method, applied to a file, is used to know whether the file should
     * be showed in the load/save dialog or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param file the file that is being considered to be showed in the dialog
     * or not.
     * @return true if the file should be showed in the dialog. otherwise,
     * false.
     */
    @Override
    public boolean accept(File file) {
        if (!file.isDirectory()) {
            String extension = this.getExtension(file);
            if (extension != null) {
                return extension.equals(FLECOFilter.FLECO_EXTENSION);
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * This method extract and return the extension of the file specified as an
     * argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param file the file whose extension is being extracted.
     * @return the extension of the file specified as an argument.
     */
    private String getExtension(File file) {
        String extension = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            extension = s.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * This method returns the FLECO case description to be showed in the
     * load/save dialogs.
     *
     * @author Manuel Domínguez-Dorado
     * @return the FLECO case description.
     */
    @Override
    public String getDescription() {
        return "FLECO case file";
    }

    public static final String FLECO_EXTENSION = "fleco";
}
