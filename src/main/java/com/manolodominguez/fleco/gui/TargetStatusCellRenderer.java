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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class implements a specific trable cell renderer used for those cells
 * that corresponds to values of the target status.
 *
 * @author Manuel Domínguez-Dorado
 */
public class TargetStatusCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private static final int INITIAL_STATUS = 1;

    /**
     * This is the constructor of the class. It sets the initial values of all
     * attributes and create a new instance.
     *
     * @author Manuel Domínguez-Dorado
     */
    public TargetStatusCellRenderer() {
        super();
    }

    /**
     * This method gets the rendered component that hasto be showed in a table
     * model when applicable. It compares the values of target status and
     * initial status, for all metrics and highlights the target status ones
     * when they differ from the initial status ones.
     *
     * @author Manuel Domínguez-Dorado
     * @param table The JTable this method is rendering cells for.
     * @param value the value in the corresponding cell (row and column).
     * @param isSelected true, if the correspondig cell (row, column) is
     * selected. Otherwise, false.
     * @param hasFocus true, if the correspondig cell (row, column) has the
     * focus. Otherwise, false.
     * @param row The row that determines the cell being rendered.
     * @param column The column that determines the cell being rendered.
     * @return The cell renderer.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null) {
            String val = null;
            Float initialValue = (Float) table.getModel().getValueAt(row, INITIAL_STATUS);
            Float targetValue = (Float) value;
            if (initialValue.floatValue() != targetValue.floatValue()) {
                Font f = getFont();
                setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                if (isSelected) {
                    this.setForeground(Color.YELLOW);
                } else {
                    this.setForeground(Color.RED);
                }
            } else {
                Font f = getFont();
                setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
                if (isSelected) {
                    this.setForeground(Color.WHITE);
                } else {
                    this.setForeground(Color.BLACK);
                }
            }
            val = ((Float) value).toString();
            setText(val);
        }
        return this;
    }
}
