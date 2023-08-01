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
 * Copyright (C) 2008-2022 Rob Camick 
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
 * This software class is an evolution of TableColumnAdjuster.java from Rob
 * Camick, that is released under a kind of Public Domain license as shown in
 * https://tips4java.wordpress.com/about
 *
 * The software has been evolved and then relicensed under the specified 
 * LGPL-3.0-or-later license.
 *******************************************************************************
 */
package com.manolodominguez.fleco.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * This class implements a way to manage the widths of colunmns in a JTable.
 * Various properties control how the width of the column is calculated. Another
 * property controls whether column width calculation should be dynamic.
 * Finally, various Actions will be added to the table to allow the user to
 * customize the functionality.
 *
 * This class is designed to be used with JTables that use an auto resize mode
 * of AUTO_RESIZE_OFF. With all other modes you are constrained as the width of
 * the columns must fit inside the table. So if you increase one column, one or
 * more of the other columns must decrease. Because of this the resize mode of
 * RESIZE_ALL_COLUMNS will work the best.
 *
 * @author Rob Camick
 * @author Manuel Domínguez-Dorado
 */
public class TableColumnAdjuster implements PropertyChangeListener, TableModelListener {

    private static final int DEFAULT_SPACING = 6;
    private static final boolean DEFAULT_COLUMN_HEADER_INCLUDED = true;
    private static final boolean DEFAULT_COLUMN_DATA_INCLUDED = true;
    private static final boolean DEFAULT_IS_ONLY_ENLARGE_COLUMN = false;
    private static final boolean DEFAULT_DINAMIC_ADJUSTMENT = true;

    private JTable table;
    private int spacing;
    private boolean isColumnHeaderIncluded;
    private boolean isColumnDataIncluded;
    private boolean isOnlyEnlargeColumn;
    private boolean isDynamicAdjustment;
    private Map<TableColumn, Integer> columnSizes = new HashMap<>();

    /**
     * This is the constructor of the class. It creates a new instance and
     * initialize its attributes with their default values.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param table the table whose columns width are going to be managed.
     */
    public TableColumnAdjuster(JTable table) {
        this(table, DEFAULT_SPACING);
    }

    /**
     * This is the constructor of the class. It creates a new instance and
     * initialize its attributes with the values specified as parameters.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param table teh table whose columns width are going to be managed.
     * @param spacing the table's default spacing between columns.
     */
    public TableColumnAdjuster(JTable table, int spacing) {
        this.table = table;
        this.spacing = spacing;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(0);
        }
        setColumnHeaderIncluded(DEFAULT_COLUMN_HEADER_INCLUDED);
        setColumnDataIncluded(DEFAULT_COLUMN_DATA_INCLUDED);
        setOnlyEnlargeColumn(DEFAULT_IS_ONLY_ENLARGE_COLUMN);
        setDynamicAdjustment(DEFAULT_DINAMIC_ADJUSTMENT);
    }

    /**
     * This methods adjust the width of every column in the table.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     */
    public void adjustColumns() {
        TableColumnModel tableColumnModel = table.getColumnModel();
        for (int i = 0; i < tableColumnModel.getColumnCount(); i++) {
            adjustColumn(i);
        }
    }

    /**
     * This methods adjust the width of the column specified as an argument.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param column the index of the column to adjust.
     */
    public void adjustColumn(int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (!tableColumn.getResizable()) {
            return;
        }
        int columnHeaderWidth = getColumnHeaderWidth(column);
        int columnDataWidth = getColumnDataWidth(column);
        int preferredWidth = Math.max(columnHeaderWidth, columnDataWidth);
        updateTableColumn(column, preferredWidth);
    }

    /**
     * This methods computes the required width of the column header specified
     * as an argument.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param column the index of the column header whose width is being
     * computed.
     */
    private int getColumnHeaderWidth(int column) {
        if (!isColumnHeaderIncluded) {
            return 0;
        }
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        Object value = tableColumn.getHeaderValue();
        TableCellRenderer renderer = tableColumn.getHeaderRenderer();
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component component = renderer.getTableCellRendererComponent(table, value, false, false, -1, column);
        return component.getPreferredSize().width;
    }

    /**
     * This methods computes the required width of the column specified as an
     * argument.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param column the index of the column whose width is being computed.
     */
    private int getColumnDataWidth(int column) {
        if (!isColumnDataIncluded) {
            return 0;
        }
        int preferredWidth = 0;
        int maxWidth = table.getColumnModel().getColumn(column).getMaxWidth();
        for (int row = 0; row < table.getRowCount(); row++) {
            preferredWidth = Math.max(preferredWidth, getCellDataWidth(row, column));
            //  We've exceeded the maximum width, no need to check other rows
            if (preferredWidth >= maxWidth) {
                break;
            }
        }
        return preferredWidth;
    }

    /**
     * This methods computes the required width for a specific cell determined
     * by a the row and the column soecified as an argument.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param row The row that determines the cell.
     * @param column The column that determines the cell.
     */
    private int getCellDataWidth(int row, int column) {
        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
        Component component = table.prepareRenderer(cellRenderer, row, column);
        int width = component.getPreferredSize().width + table.getIntercellSpacing().width;
        return width;
    }

    /**
     * This method updates the specified column's width with the value specified
     * as an argument.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param width The new width value for the column.
     * @param column The column to be adjusted.
     */
    private void updateTableColumn(int column, int width) {
        final TableColumn tableColumn = table.getColumnModel().getColumn(column);
        if (!tableColumn.getResizable()) {
            return;
        }
        width += spacing;
        //  Shrink the column width
        if (isOnlyEnlargeColumn) {
            width = Math.max(width, tableColumn.getPreferredWidth());
        }
        columnSizes.put(tableColumn, tableColumn.getWidth());
        table.getTableHeader().setResizingColumn(tableColumn);
        tableColumn.setWidth(width);
    }

    /**
     * This method restores every column in the table to its previous width.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     */
    public void restoreColumns() {
        TableColumnModel tableColumnModel = table.getColumnModel();
        for (int i = 0; i < tableColumnModel.getColumnCount(); i++) {
            restoreColumn(i);
        }
    }

    /**
     * This methods restores the width of the column specified as an argument to
     * its previous width.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param column the index of the column to adjust.
     */
    private void restoreColumn(int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        Integer width = columnSizes.get(tableColumn);
        if (width != null) {
            table.getTableHeader().setResizingColumn(tableColumn);
            tableColumn.setWidth(width);
        }
    }

    /**
     * This methods sets whether the column header is counted on the computation
     * of the column width or not.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param isColumnHeaderIncluded whether the column header is counted on the
     * computation of the column width or not.
     */
    public final void setColumnHeaderIncluded(boolean isColumnHeaderIncluded) {
        this.isColumnHeaderIncluded = isColumnHeaderIncluded;
    }

    /**
     * This methods sets whether the column data is counted on the computation
     * of the column width or not.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param isColumnDataIncluded whether the column data is counted on the
     * computation of the column width or not.
     */
    public final void setColumnDataIncluded(boolean isColumnDataIncluded) {
        this.isColumnDataIncluded = isColumnDataIncluded;
    }

    /**
     * This methods sets whether the column mus be shrinked apart from enlarged,
     * if needed.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param isOnlyEnlargeColumn whether the column mus be shrinked apart from
     * enlarged, if needed.
     */
    public final void setOnlyEnlargeColumn(boolean isOnlyEnlargeColumn) {
        this.isOnlyEnlargeColumn = isOnlyEnlargeColumn;
    }

    /**
     * This methods sets whether changes to the model should cause the width to
     * be dynamically recalculated.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param isDynamicAdjustment whether changes to the model should cause the
     * width to be dynamically recalculated.
     */
    public final void setDynamicAdjustment(boolean isDynamicAdjustment) {
        if (this.isDynamicAdjustment != isDynamicAdjustment) {
            if (isDynamicAdjustment) {
                table.addPropertyChangeListener(this);
                table.getModel().addTableModelListener(this);
            } else {
                table.removePropertyChangeListener(this);
                table.getModel().removeTableModelListener(this);
            }
        }
        this.isDynamicAdjustment = isDynamicAdjustment;
    }

    /**
     * This methods is called when there is a change in a table property. It is
     * used to adjusting all columns when the table model change.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param e The property change event that informs of the change in a table
     * property.
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        //  When the TableModel changes we need to update the listeners
        //  and column widths
        if ("model".equals(e.getPropertyName())) {
            TableModel model = (TableModel) e.getOldValue();
            model.removeTableModelListener(this);
            model = (TableModel) e.getNewValue();
            model.addTableModelListener(this);
            adjustColumns();
        }
    }

    /**
     * This methods is called when there is a change in a table model. It is
     * used to adjusting all columns when the content of a cell varies.
     *
     * @author Rob Camick
     * @author Manuel Domínguez-Dorado
     * @param e The table model event that informs of the change within a table.
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        if (!isColumnDataIncluded) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            int column = table.convertColumnIndexToView(e.getColumn());
            if (e.getType() == TableModelEvent.UPDATE && column != -1) {
                if (isOnlyEnlargeColumn) {
                    int row = e.getFirstRow();
                    TableColumn tableColumn = table.getColumnModel().getColumn(column);

                    if (tableColumn.getResizable()) {
                        int width = getCellDataWidth(row, column);
                        updateTableColumn(column, width);
                    }
                } else {
                    adjustColumn(column);
                }
            } else {
                adjustColumns();
            }
        });
    }

}
