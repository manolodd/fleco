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

import com.manolodominguez.fleco.genetics.Alleles;
import com.manolodominguez.fleco.genetics.Chromosome;
import com.manolodominguez.fleco.genetics.Genes;
import com.manolodominguez.fleco.strategicconstraints.ComparisonOperators;
import com.manolodominguez.fleco.strategicconstraints.Constraint;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.FunctionalAreas;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.EnumMap;
import javax.swing.table.AbstractTableModel;

/**
 * This class implement a table model for FLECO Studio.
 *
 * @author Manuel Domínguez-Dorado
 */
@SuppressWarnings("serial")
public class FLECOTableModel extends AbstractTableModel {

    public static final int CYBERTOMP_METRIC_KEY = 0;
    public static final int CYBERTOMP_METRIC_ACRONYM = 1;
    public static final int CYBERTOMP_METRIC_NAME = 2;
    public static final int FUNCTIONAL_AREA = 3;
    public static final int CURRENT_STATUS = 4;
    public static final int CONSTRAINT_OPERATOR = 5;
    public static final int CONSTRAINT_VALUE = 6;
    public static final int TARGET_STATUS = 7;

    private static final int MAX_COLUMNS = 8;
    private static final int ASSET_ROW = 0;
    private static final String NO_CONSTRAINT = "N/A";

    private StrategicConstraints strategicConstraints;
    private ImplementationGroups implementationGroup;
    private String[] metricsKeys;

    private Chromosome initialStatus;
    private EnumMap<Genes, Float> genesValuesInitialStatus;
    private EnumMap<Categories, Float> categoriesValuesInitialStatus;
    private EnumMap<Functions, Float> functionsValuesInitialStatus;
    private Float assetValueInitialStatus;

    private Chromosome targetStatus;
    private EnumMap<Genes, Float> genesValuesTargetStatus;
    private EnumMap<Categories, Float> categoriesValuesTargetStatus;
    private EnumMap<Functions, Float> functionsValuesTargetStatus;
    private Float assetValueTargetStatus;

    private IFLECOTableModelChangeListener changeEventListener;

    /**
     * This is the constructor of the class. It creates a new instance and
     * initialize its attributes with their default values.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialStatus the initial status of the case being solved by
     * FLECO.
     * @param strategicConstraints The strategic constraints that FLECO must
     * comply with.
     */
    public FLECOTableModel(Chromosome initialStatus, StrategicConstraints strategicConstraints) {
        this.initialStatus = initialStatus;
        this.strategicConstraints = strategicConstraints;
        this.implementationGroup = initialStatus.getImplementationGroup();
        this.targetStatus = null;
        this.changeEventListener = null;
        int rowCount = 0;
        rowCount++; // +1 For the "Asset" row
        for (Functions function : Functions.getFunctionsFor(implementationGroup)) {
            rowCount++; //+1 for each applicable Function
            for (Categories category : Categories.getCategoriesFor(function, implementationGroup)) {
                rowCount++; //+1 for each applicable Category
                for (Genes gene : Genes.getGenesFor(category, implementationGroup)) {
                    rowCount++; //+1 for each applicable gene/expected outcome
                }
            }
        }
        this.metricsKeys = new String[rowCount];
        int count = 0;
        metricsKeys[count] = "Asset";
        count++;
        for (Functions function : Functions.getFunctionsFor(implementationGroup)) {
            metricsKeys[count] = function.name();
            count++;
            for (Categories category : Categories.getCategoriesFor(function, implementationGroup)) {
                metricsKeys[count] = category.name();
                count++;
                for (Genes gene : Genes.getGenesFor(category, implementationGroup)) {
                    metricsKeys[count] = gene.name();
                    count++;
                }
            }
        }
        computeValuesForInitialStatus();
    }

    /**
     * This method sets the listener that is going to be called when any change
     * in the table model happens, to update whatever needed.
     *
     * @author Manuel Domínguez-Dorado
     * @param changeEventListener the listener that is going to be called when
     * any change in the table model happens
     */
    public void setChangeEventListener(IFLECOTableModelChangeListener changeEventListener) {
        if (this.changeEventListener != null) {
            throw new IllegalArgumentException("A listener has already been defined for this FLECOTableModel. Only one is allowed.");
        }
        this.changeEventListener = changeEventListener;
    }

    /**
     * This method sets the target status of the case, computed by FLECO.
     *
     * @author Manuel Domínguez-Dorado
     * @param targetStatus the target status of the case, computed by FLECO.
     */
    public void setTargetStatus(Chromosome targetStatus) {
        this.targetStatus = targetStatus;
        computeValuesForTargetStatus();
        for (int j = 0; j < metricsKeys.length; j++) {
            fireTableCellUpdated(j, TARGET_STATUS);
        }
        if (changeEventListener != null) {
            changeEventListener.onFLECOTableModelChanged();
        }
    }

    /**
     * This method gets the target status of the case.
     *
     * @author Manuel Domínguez-Dorado
     * @return the target status of the case.
     */
    public Chromosome getTargetStatus() {
        return targetStatus;
    }

    /**
     * This method sets the strategic constraints that FLECO must apply to the
     * case.
     *
     * @author Manuel Domínguez-Dorado
     * @param strategicConstraints the strategic constraints that FLECO must
     * apply to the case
     */
    public void setStrategicConstraints(StrategicConstraints strategicConstraints) {
        this.strategicConstraints = strategicConstraints;
        for (int j = 0; j < metricsKeys.length; j++) {
            fireTableCellUpdated(j, CONSTRAINT_OPERATOR);
            fireTableCellUpdated(j, CONSTRAINT_VALUE);
        }
        if (changeEventListener != null) {
            changeEventListener.onFLECOTableModelChanged();
        }
    }

    /**
     * This method sets the initial status of the case that FLECO must solve.
     *
     * @author Manuel Domínguez-Dorado
     * @param initialStatus the initial status of the case that FLECO must
     * solve.
     */
    public void setInitialStatus(Chromosome initialStatus) {
        this.initialStatus = initialStatus;
        int rowCount = 0;
        rowCount++; // +1 For the "Asset" row
        for (Functions function : Functions.getFunctionsFor(implementationGroup)) {
            rowCount++; //+1 for each applicable Function
            for (Categories category : Categories.getCategoriesFor(function, implementationGroup)) {
                rowCount++; //+1 for each applicable Category
                for (Genes gene : Genes.getGenesFor(category, implementationGroup)) {
                    rowCount++; //+1 for each applicable gene/expected outcome
                }
            }
        }
        this.metricsKeys = new String[rowCount];
        int count = 0;
        metricsKeys[count] = "Asset";
        count++;
        for (Functions function : Functions.getFunctionsFor(implementationGroup)) {
            metricsKeys[count] = function.name();
            count++;
            for (Categories category : Categories.getCategoriesFor(function, implementationGroup)) {
                metricsKeys[count] = category.name();
                count++;
                for (Genes gene : Genes.getGenesFor(category, implementationGroup)) {
                    metricsKeys[count] = gene.name();
                    count++;
                }
            }
        }
        computeValuesForInitialStatus();
        for (int j = 0; j < metricsKeys.length; j++) {
            fireTableCellUpdated(j, CURRENT_STATUS);
        }
        if (changeEventListener != null) {
            changeEventListener.onFLECOTableModelChanged();
        }
    }

    /**
     * This method removes the target status of the case.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void removeTargetStatus() {
        targetStatus = null;
        computeValuesForTargetStatus();
    }

    /**
     * This method removes the strategic constraints of the case.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void removeStrategicConstraints() {
        if (strategicConstraints != null) {
            this.strategicConstraints.removeAll();
        }
    }

    /**
     * This method removes the initial status of the case.
     *
     * @author Manuel Domínguez-Dorado
     */
    public void removeInitialStatus() {
        targetStatus = null;
        computeValuesForTargetStatus();
    }

    /**
     * This method gets the implementation group of the case.
     *
     * @author Manuel Domínguez-Dorado
     * @return the implementation group of the case.
     */
    public ImplementationGroups getImplementationGroup() {
        return implementationGroup;
    }

    /**
     * This method gets the strategic constraints of the case.
     *
     * @author Manuel Domínguez-Dorado
     * @return the strategic constraints of the case.
     */
    public StrategicConstraints getStrategicConstraints() {
        return strategicConstraints;
    }

    /**
     * This method gets the initial status of the case.
     *
     * @author Manuel Domínguez-Dorado
     * @return the initial status of the case.
     */
    public Chromosome getInitialStatus() {
        return initialStatus;
    }

    /**
     * This method gets the number of columns in the table model.
     *
     * @author Manuel Domínguez-Dorado
     * @return the number of columns in the table model.
     */
    @Override
    public int getColumnCount() {
        return MAX_COLUMNS;
    }

    /**
     * This method gets the name of the specified colum in the table to be use
     * as header.
     *
     * @author Manuel Domínguez-Dorado
     * @param column the column whose name are needed.
     * @return the name of the specified colum in the table to be use as header.
     */
    @Override
    public String getColumnName(int column) {

        switch (column) {
            case CYBERTOMP_METRIC_KEY:
                return "CyberTOMP metric key";
            case CYBERTOMP_METRIC_ACRONYM:
                return "CyberTOMP metric";
            case CYBERTOMP_METRIC_NAME:
                return "Purpose";
            case FUNCTIONAL_AREA:
                return "Leading functional area";
            case CURRENT_STATUS:
                return "Current status";
            case CONSTRAINT_OPERATOR:
                return "Constraint operator";
            case CONSTRAINT_VALUE:
                return "Constraint value";
            case TARGET_STATUS:
                return "Target status";
            default:
                return "Column name not defined";
        }
    }

    /**
     * This method gets the type of the specified colum in the table.
     *
     * @author Manuel Domínguez-Dorado
     * @param column the column whose type is needed.
     * @return the type of the specified colum in the table to be use as header.
     */
    @Override
    public Class<?> getColumnClass(int column) {
        /*
        switch (column) {
            case CYBERTOMP_METRIC_KEY:
                return String.class;
            case CYBERTOMP_METRIC_ACRONYM:
                return String.class;
            case CYBERTOMP_METRIC_NAME:
                return String.class;
            case FUNCTIONAL_AREA:
                return String.class;
            case CURRENT_STATUS:
                return String.class;
            case CONSTRAINT_OPERATOR:
                return String.class;
            case CONSTRAINT_VALUE:
                return String.class;
            case TARGET_STATUS:
                return String.class;
            default:
                return String.class;
        }
         */
        // While all values returns String.class it makes no sense to
        // implement a switch statement. So, return always String.class
        return String.class;
    }

    /**
     * This method gets the number of rows in the table model.
     *
     * @author Manuel Domínguez-Dorado
     * @return the number of rows in the table model.
     */
    @Override
    public int getRowCount() {
        if (initialStatus != null) {
            return metricsKeys.length;
        }
        return 0;
    }

    /**
     * This method gets whether the specific cell determined by row and column
     * is editable or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the row that determines the cell.
     * @param column the column that determines the cell.
     * @return TRUE if the cell determined by row and column is editable.
     * Otherwise, FALSE.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        if (initialStatus != null) {
            if ((column == CYBERTOMP_METRIC_KEY) || (column == CYBERTOMP_METRIC_ACRONYM) || (column == CYBERTOMP_METRIC_NAME) || (column == FUNCTIONAL_AREA) || (column == TARGET_STATUS)) {
                return false;
            }
            if (column == CURRENT_STATUS) {
                for (Genes gene : Genes.getGenesFor(implementationGroup)) {
                    if (gene.name().equals(metricsKeys[row])) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * This method gets the value of the specific cell determined by row and
     * column.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the row that determines the cell.
     * @param column the column that determines the cell.
     * @return the value of the specific cell determined by row and column.
     */
    @Override
    public Object getValueAt(int row, int column) {
        if (initialStatus != null) {
            switch (column) {
                case CYBERTOMP_METRIC_KEY:
                    return getCyberTOMPMetricKeyAt(row);
                case CYBERTOMP_METRIC_ACRONYM:
                    return getCorrespondingMetricAcronymAt(row);
                case CYBERTOMP_METRIC_NAME:
                    return getCorrespondingPurposeAt(row);
                case FUNCTIONAL_AREA:
                    return getCorrespondingLeadingFunctionalAreaAt(row);
                case CURRENT_STATUS:
                    return getInitialStatusAt(row);
                case CONSTRAINT_OPERATOR:
                    return getConstraintOperatorAt(row);
                case CONSTRAINT_VALUE:
                    return getConstraintValueAt(row);
                case TARGET_STATUS:
                    return getTargetStatusAt(row);
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * This method gets the acronym corresponding to the CyberTOMP metric that
     * is located in the row specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the acronym corresponding to the CyberTOMP metric that is located
     * in the row specified as an argument.
     */
    private Object getCorrespondingMetricAcronymAt(int row) {
        if (initialStatus != null) {
            try {
                // Case it is an expected outcome
                Genes gene = Genes.valueOf((String) getValueAt(row, 0));
                return "            " + gene.getAcronym();
            }
            catch (IllegalArgumentException e) {
                try {
                    // Case it is a category
                    Categories category = Categories.valueOf((String) getValueAt(row, 0));
                    return "        " + category.getAcronym();
                }
                catch (IllegalArgumentException e2) {
                    try {
                        // Case it is a function
                        Functions function = Functions.valueOf((String) getValueAt(row, 0));
                        return "    " + function.getAcronym();
                    }
                    catch (IllegalArgumentException e3) {
                        // Case it is a function
                        return "BUSINESS ASSET";
                    }
                }
            }
        }
        return "UNDEFINED";
    }

    /**
     * This method gets the purpose corresponding to the CyberTOMP metric that
     * is located in the row specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the purpose corresponding to the CyberTOMP metric that is located
     * in the row specified as an argument.
     */
    private Object getCorrespondingPurposeAt(int row) {
        if (initialStatus != null) {
            try {
                // Case it is an expected outcome
                Genes gene = Genes.valueOf((String) getValueAt(row, 0));
                return gene.getPurpose();
            }
            catch (IllegalArgumentException e) {
                try {
                    // Case it is a category
                    Categories category = Categories.valueOf((String) getValueAt(row, 0));
                    return category.getPurpose();
                }
                catch (IllegalArgumentException e2) {
                    try {
                        // Case it is a function
                        Functions function = Functions.valueOf((String) getValueAt(row, 0));
                        return function.getPurpose();
                    }
                    catch (IllegalArgumentException e3) {
                        // Case it is a function
                        return "---";
                    }
                }
            }
        }
        return "UNDEFINED";
    }

    /**
     * This method gets the functional area that should lead the implementation
     * of actions corresponding to the CyberTOMP metric that is located in the
     * row specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the functional area that should lead the implementation of
     * actions corresponding to the CyberTOMP metric that is located in the row
     * specified as an argument.
     */
    private Object getCorrespondingLeadingFunctionalAreaAt(int row) {
        if (initialStatus != null) {
            try {
                // Case it is an expected outcome
                Genes gene = Genes.valueOf((String) getValueAt(row, 0));
                return gene.getLeadingFunctionalArea().getAreaName();
            }
            catch (IllegalArgumentException e) {
                // Case it is a category, function or asset
                return FunctionalAreas.SEVERAL.getAreaName();
            }
        }
        return "UNDEFINED";
    }

    /**
     * This method gets the CyberTOMP metric that is located in the row
     * specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the CyberTOMP metric that is located in the row pecified as an
     * argument.
     */
    private Object getCyberTOMPMetricKeyAt(int row) {
        if (initialStatus != null) {
            return metricsKeys[row];
        }
        return null;
    }

    /**
     * This method sets the CyberTOMP metric that is located in the row
     * specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @param value the CyberTOMP metric to be set for the specified row.
     * @return the CyberTOMP metric that is located in the row pecified as an
     * argument.
     */
    private void setCyberTOMPMetricAt(Object value, int row) {
        // do nothing;
    }

    /**
     * This method gets the initial status that is located in the row specified
     * as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the initial status that is located in the row specified as an
     * argument.
     */
    private Object getInitialStatusAt(int row) {
        if (initialStatus != null) {
            computeValuesForInitialStatus();
            try {
                // Case it is an expected outcome
                return genesValuesInitialStatus.get(Genes.valueOf((String) getValueAt(row, 0)));
            }
            catch (IllegalArgumentException e) {
                try {
                    // Case it is a category
                    return categoriesValuesInitialStatus.get(Categories.valueOf((String) getValueAt(row, 0)));
                }
                catch (IllegalArgumentException e2) {
                    try {
                        // Case it is an asset
                        return functionsValuesInitialStatus.get(Functions.valueOf((String) getValueAt(row, 0)));
                    }
                    catch (IllegalArgumentException e3) {
                        // Case it is a function
                        return assetValueInitialStatus;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method gets the target status that is located in the row specified
     * as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the target status that is located in the row specified as an
     * argument.
     */
    private Object getTargetStatusAt(int row) {
        if (targetStatus != null) {
            computeValuesForTargetStatus();
            try {
                // Case it is an expected outcome
                return genesValuesTargetStatus.get(Genes.valueOf((String) getValueAt(row, 0)));
            }
            catch (IllegalArgumentException e) {
                try {
                    // Case it is a category
                    return categoriesValuesTargetStatus.get(Categories.valueOf((String) getValueAt(row, 0)));
                }
                catch (IllegalArgumentException e2) {
                    try {
                        // Case it is an asset
                        return functionsValuesTargetStatus.get(Functions.valueOf((String) getValueAt(row, 0)));
                    }
                    catch (IllegalArgumentException e3) {
                        // Case it is a function
                        return assetValueTargetStatus;
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method gets the constraint's operator that is located in the row
     * specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the constraint's operator that is located in the row specified as
     * an argument.
     */
    private Object getConstraintOperatorAt(int row) {
        if (initialStatus != null) {
            try {
                // Case it is an expected outcome
                Genes gene = Genes.valueOf(metricsKeys[row]);
                if (gene.appliesToIG(implementationGroup)) {
                    if (strategicConstraints.hasDefinedConstraint(gene)) {
                        return strategicConstraints.getConstraint(gene).getComparisonOperator().name();
                    }
                }
            }
            catch (IllegalArgumentException e1) {
                try {
                    // Case it is a category
                    Categories category = Categories.valueOf(metricsKeys[row]);
                    if (category.appliesToIG(implementationGroup)) {
                        if (strategicConstraints.hasDefinedConstraint(category)) {
                            return strategicConstraints.getConstraint(category).getComparisonOperator().name();
                        }
                    }
                }
                catch (IllegalArgumentException e2) {
                    try {
                        // Case it is a function
                        Functions function = Functions.valueOf(metricsKeys[row]);
                        if (function.appliesToIG(implementationGroup)) {
                            if (strategicConstraints.hasDefinedConstraint(function)) {
                                return strategicConstraints.getConstraint(function).getComparisonOperator().name();
                            }
                        }
                    }
                    catch (IllegalArgumentException e3) {
                        // Case it is an asset
                        if (row == ASSET_ROW) {
                            if (strategicConstraints.hasDefinedConstraint()) {
                                return strategicConstraints.getConstraint().getComparisonOperator().name();
                            }
                        }
                    }
                }
            }
            return NO_CONSTRAINT;
        }
        return null;
    }

    /**
     * This method gets the constraint's value that is located in the row
     * specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param row the specified row.
     * @return the constraint's value that is located in the row specified as an
     * argument.
     */
    public Object getConstraintValueAt(int row) {
        if (initialStatus != null) {
            try {
                // Case it is an expected outcome
                Genes gene = Genes.valueOf(metricsKeys[row]);
                if (gene.appliesToIG(implementationGroup)) {
                    if (strategicConstraints.hasDefinedConstraint(gene)) {
                        return (Float) strategicConstraints.getConstraint(gene).getThreshold();
                    }
                }
            }
            catch (IllegalArgumentException e1) {
                try {
                    // Case it is a category
                    Categories category = Categories.valueOf(metricsKeys[row]);
                    if (category.appliesToIG(implementationGroup)) {
                        if (strategicConstraints.hasDefinedConstraint(category)) {
                            return (Float) strategicConstraints.getConstraint(category).getThreshold();
                        }
                    }
                }
                catch (IllegalArgumentException e2) {
                    try {
                        // Case it is a function
                        Functions function = Functions.valueOf(metricsKeys[row]);
                        if (function.appliesToIG(implementationGroup)) {
                            if (strategicConstraints.hasDefinedConstraint(function)) {
                                return (Float) strategicConstraints.getConstraint(function).getThreshold();
                            }
                        }
                    }
                    catch (IllegalArgumentException e3) {
                        // Case it is an asset
                        if (row == ASSET_ROW) {
                            if (strategicConstraints.hasDefinedConstraint()) {
                                return (Float) strategicConstraints.getConstraint().getThreshold();
                            }
                        }
                    }
                }
            }
            return (Float) 0.0f;
        }
        return null;
    }

    /**
     * This method sets the value of the cell determined by the row and column
     * specified as arguments.
     *
     * @author Manuel Domínguez-Dorado
     * @param value the value to be asigned to the cell.
     * @param row the specified row.
     * @param column the specified column.
     */
    @Override
    public void setValueAt(Object value, int row, int column) {
        if (initialStatus != null) {
            switch (column) {
                case CYBERTOMP_METRIC_KEY:
                    setCyberTOMPMetricAt(value, row);
                    break;
                case CURRENT_STATUS:
                    setInitialStatusAt(value, row);
                    break;
                case CONSTRAINT_OPERATOR:
                    setConstraintOperatorAt(value, row);
                    break;
                case CONSTRAINT_VALUE:
                    setConstraintValueAt(value, row);
                    break;
                default:
                    break;
            }
            if (changeEventListener != null) {
                changeEventListener.onFLECOTableModelChanged();
            }
        }
    }

    /**
     * This method sets the initial status that corresponds to the row specified
     * as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param value the value to be asigned to initial status column.
     * @param row the specified row.
     */
    private void setInitialStatusAt(Object value, int row) {
        if (initialStatus != null) {
            try {
                Alleles allele = Alleles.DLI_0;
                Genes gene;
                gene = Genes.valueOf(metricsKeys[row]);
                if (value instanceof Float) {
                    float valueFloat = (float) value;
                    if (valueFloat == Alleles.DLI_0.getDLI()) {
                        allele = Alleles.DLI_0;
                    } else if (valueFloat == Alleles.DLI_33.getDLI()) {
                        allele = Alleles.DLI_33;
                    } else if (valueFloat == Alleles.DLI_67.getDLI()) {
                        allele = Alleles.DLI_67;
                    } else if (valueFloat == Alleles.DLI_100.getDLI()) {
                        allele = Alleles.DLI_100;
                    }
                    initialStatus.updateAllele(gene, allele);
                    computeValuesForInitialStatus();
                    for (int j = 0; row < metricsKeys.length; row++) {
                        fireTableCellUpdated(j, CURRENT_STATUS);
                    }
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println("ERROR en setInitialStatusAt");
            }
        }
    }

    /**
     * This method sets the constraint's operator that corresponds to the row
     * specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param value the value to be asigned to constraint's operator column.
     * @param row the specified row.
     */
    private void setConstraintOperatorAt(Object value, int row) {
        int GENE = 0;
        int CATEGORY = 1;
        int FUNCTION = 2;
        int ASSET = 3;
        int typeOfMetric = GENE;
        if (initialStatus != null) {
            try {
                Genes.valueOf(metricsKeys[row]);
                typeOfMetric = GENE;
            }
            catch (IllegalArgumentException e11) {
                try {
                    Categories.valueOf(metricsKeys[row]);
                    typeOfMetric = CATEGORY;
                }
                catch (IllegalArgumentException e12) {
                    try {
                        Functions.valueOf(metricsKeys[row]);
                        typeOfMetric = FUNCTION;
                    }
                    catch (IllegalArgumentException e13) {
                        typeOfMetric = ASSET;
                    }
                }
            }
            try {
                ComparisonOperators comparisonOperator;
                if (value instanceof String) {
                    String valueString = (String) value;
                    if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == GENE)) {
                        strategicConstraints.removeConstraint(Genes.valueOf(metricsKeys[row]));
                    } else {
                        comparisonOperator = ComparisonOperators.valueOf(valueString);
                        Genes gene = Genes.valueOf(metricsKeys[row]);
                        Constraint updatedConstraint;
                        if (strategicConstraints.hasDefinedConstraint(gene)) {
                            updatedConstraint = new Constraint(comparisonOperator, strategicConstraints.getConstraint(gene).getThreshold());
                            strategicConstraints.removeConstraint(gene);
                            strategicConstraints.addConstraint(gene, updatedConstraint);
                        } else {
                            switch (comparisonOperator) {
                                case LESS:
                                case LESS_OR_EQUAL:
                                case EQUAL:
                                    updatedConstraint = new Constraint(comparisonOperator, 1.0f);
                                    strategicConstraints.addConstraint(gene, updatedConstraint);
                                    break;
                                case GREATER:
                                case GREATER_OR_EQUAL:
                                    updatedConstraint = new Constraint(comparisonOperator, 0.0f);
                                    strategicConstraints.addConstraint(gene, updatedConstraint);
                                    break;
                            }
                        }
                    }
                    for (int j = 0; j < metricsKeys.length; j++) {
                        fireTableCellUpdated(j, CONSTRAINT_OPERATOR);
                        fireTableCellUpdated(j, CONSTRAINT_VALUE);
                    }
                }
            }
            catch (IllegalArgumentException e2) {
                try {
                    ComparisonOperators comparisonOperator;
                    if (value instanceof String) {
                        String valueString = (String) value;
                        if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == CATEGORY)) {
                            strategicConstraints.removeConstraint(Categories.valueOf(metricsKeys[row]));
                        } else {
                            comparisonOperator = ComparisonOperators.valueOf(valueString);
                            Constraint updatedConstraint;
                            Categories category = Categories.valueOf(metricsKeys[row]);
                            if (strategicConstraints.hasDefinedConstraint(category)) {
                                updatedConstraint = new Constraint(comparisonOperator, strategicConstraints.getConstraint(category).getThreshold());
                                strategicConstraints.removeConstraint(category);
                                strategicConstraints.addConstraint(category, updatedConstraint);
                            } else {
                                switch (comparisonOperator) {
                                    case LESS:
                                    case LESS_OR_EQUAL:
                                    case EQUAL:
                                        updatedConstraint = new Constraint(comparisonOperator, 1.0f);
                                        strategicConstraints.addConstraint(category, updatedConstraint);
                                        break;
                                    case GREATER:
                                    case GREATER_OR_EQUAL:
                                        updatedConstraint = new Constraint(comparisonOperator, 0.0f);
                                        strategicConstraints.addConstraint(category, updatedConstraint);
                                        break;
                                }
                            }
                        }
                        for (int j = 0; j < metricsKeys.length; j++) {
                            fireTableCellUpdated(j, CONSTRAINT_OPERATOR);
                            fireTableCellUpdated(j, CONSTRAINT_VALUE);
                        }
                    }
                }
                catch (IllegalArgumentException e3) {
                    try {
                        ComparisonOperators comparisonOperator;
                        if (value instanceof String) {
                            String valueString = (String) value;
                            if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == FUNCTION)) {
                                strategicConstraints.removeConstraint(Functions.valueOf(metricsKeys[row]));
                            } else {
                                comparisonOperator = ComparisonOperators.valueOf(valueString);
                                Constraint updatedConstraint;
                                Functions function = Functions.valueOf(metricsKeys[row]);
                                if (strategicConstraints.hasDefinedConstraint(function)) {
                                    updatedConstraint = new Constraint(comparisonOperator, strategicConstraints.getConstraint(function).getThreshold());
                                    strategicConstraints.removeConstraint(function);
                                    strategicConstraints.addConstraint(function, updatedConstraint);
                                } else {
                                    switch (comparisonOperator) {
                                        case LESS:
                                        case LESS_OR_EQUAL:
                                        case EQUAL:
                                            updatedConstraint = new Constraint(comparisonOperator, 1.0f);
                                            strategicConstraints.addConstraint(function, updatedConstraint);
                                            break;
                                        case GREATER:
                                        case GREATER_OR_EQUAL:
                                            updatedConstraint = new Constraint(comparisonOperator, 0.0f);
                                            strategicConstraints.addConstraint(function, updatedConstraint);
                                            break;
                                    }
                                }
                            }
                            for (int j = 0; j < metricsKeys.length; j++) {
                                fireTableCellUpdated(j, CONSTRAINT_OPERATOR);
                                fireTableCellUpdated(j, CONSTRAINT_VALUE);

                            }
                        }
                    }
                    catch (IllegalArgumentException e4) {
                        try {
                            ComparisonOperators comparisonOperator;
                            if (value instanceof String) {
                                String valueString = (String) value;
                                if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == ASSET)) {
                                    strategicConstraints.removeConstraint();
                                } else {
                                    comparisonOperator = ComparisonOperators.valueOf(valueString);
                                    Constraint updatedConstraint;
                                    if (strategicConstraints.hasDefinedConstraint()) {
                                        updatedConstraint = new Constraint(comparisonOperator, strategicConstraints.getConstraint().getThreshold());
                                        strategicConstraints.removeConstraint();
                                        strategicConstraints.addConstraint(updatedConstraint);
                                    } else {
                                        switch (comparisonOperator) {
                                            case LESS:
                                            case LESS_OR_EQUAL:
                                            case EQUAL:
                                                updatedConstraint = new Constraint(comparisonOperator, 1.0f);
                                                strategicConstraints.addConstraint(updatedConstraint);
                                                break;
                                            case GREATER:
                                            case GREATER_OR_EQUAL:
                                                updatedConstraint = new Constraint(comparisonOperator, 0.0f);
                                                strategicConstraints.addConstraint(updatedConstraint);
                                                break;
                                        }
                                    }
                                }
                                for (int j = 0; j < metricsKeys.length; j++) {
                                    fireTableCellUpdated(j, CONSTRAINT_OPERATOR);
                                    fireTableCellUpdated(j, CONSTRAINT_VALUE);

                                }
                            }
                        }
                        catch (IllegalArgumentException e5) {
                            System.out.println("ERROR en setConstraintOperatorAt");
                        }
                    }
                }
            }
        }
    }

    /**
     * This method sets the constraint's value that corresponds to the row
     * specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param value the value to be asigned to constraint's value column.
     * @param row the specified row.
     */
    public void setConstraintValueAt(Object value, int row) {
        int GENE = 0;
        int CATEGORY = 1;
        int FUNCTION = 2;
        int ASSET = 3;
        int typeOfMetric = GENE;
        float threshold = 0.0f;
        if (initialStatus != null) {
            if (value instanceof Float) {
                threshold = (float) value;
                if (threshold < 0.0f) {
                    threshold = 0.0f;
                } else if (threshold > 1.0f) {
                    threshold = 1.0f;
                }
            } else {
                try {
                    if (value instanceof String) {
                        Float.parseFloat((String) value);
                        threshold = Float.parseFloat((String) value);
                        if (threshold < 0.0f) {
                            threshold = 0.0f;
                        } else if (threshold > 1.0f) {
                            threshold = 1.0f;
                        }
                    }
                }
                catch (NumberFormatException ex) {
                    System.out.println("Nuevo valor no es un float: " + ((String) value));
                }
            }
            try {
                Genes.valueOf(metricsKeys[row]);
                typeOfMetric = GENE;
            }
            catch (IllegalArgumentException e11) {
                try {
                    Categories.valueOf(metricsKeys[row]);
                    typeOfMetric = CATEGORY;
                }
                catch (IllegalArgumentException e12) {
                    try {
                        Functions.valueOf(metricsKeys[row]);
                        typeOfMetric = FUNCTION;
                    }
                    catch (IllegalArgumentException e13) {
                        typeOfMetric = ASSET;
                    }
                }
            }

            try {
                ComparisonOperators comparisonOperator;
                if (value instanceof String) {
                    String valueString = (String) value;
                    if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == GENE)) {
                        strategicConstraints.removeConstraint(Genes.valueOf(metricsKeys[row]));
                    } else {
                        Genes gene = Genes.valueOf(metricsKeys[row]);
                        Constraint updatedConstraint;
                        if (strategicConstraints.hasDefinedConstraint(gene)) {
                            updatedConstraint = new Constraint(strategicConstraints.getConstraint(gene).getComparisonOperator(), threshold);
                            strategicConstraints.removeConstraint(gene);
                            strategicConstraints.addConstraint(gene, updatedConstraint);
                        }
                    }
                    for (int j = 0; j < metricsKeys.length; j++) {
                        fireTableCellUpdated(j, CONSTRAINT_VALUE);
                    }
                }
            }
            catch (IllegalArgumentException e2) {
                try {
                    ComparisonOperators comparisonOperator;
                    if (value instanceof String) {
                        String valueString = (String) value;
                        if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == CATEGORY)) {
                            strategicConstraints.removeConstraint(Categories.valueOf(metricsKeys[row]));
                        } else {
                            Constraint updatedConstraint;
                            Categories category = Categories.valueOf(metricsKeys[row]);
                            if (strategicConstraints.hasDefinedConstraint(category)) {
                                updatedConstraint = new Constraint(strategicConstraints.getConstraint(category).getComparisonOperator(), threshold);
                                strategicConstraints.removeConstraint(category);
                                strategicConstraints.addConstraint(category, updatedConstraint);
                            }
                        }
                        for (int j = 0; j < metricsKeys.length; j++) {
                            fireTableCellUpdated(j, CONSTRAINT_VALUE);
                        }
                    }
                }
                catch (IllegalArgumentException e3) {
                    try {
                        ComparisonOperators comparisonOperator;
                        if (value instanceof String) {
                            String valueString = (String) value;
                            if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == FUNCTION)) {
                                strategicConstraints.removeConstraint(Functions.valueOf(metricsKeys[row]));
                            } else {
                                Constraint updatedConstraint;
                                Functions function = Functions.valueOf(metricsKeys[row]);
                                if (strategicConstraints.hasDefinedConstraint(function)) {
                                    updatedConstraint = new Constraint(strategicConstraints.getConstraint(function).getComparisonOperator(), threshold);
                                    strategicConstraints.removeConstraint(function);
                                    strategicConstraints.addConstraint(function, updatedConstraint);
                                }
                            }
                            for (int j = 0; j < metricsKeys.length; j++) {
                                fireTableCellUpdated(j, CONSTRAINT_VALUE);
                            }
                        }
                    }
                    catch (IllegalArgumentException e4) {
                        try {
                            ComparisonOperators comparisonOperator;
                            if (value instanceof String) {
                                String valueString = (String) value;
                                if (valueString.equals(NO_CONSTRAINT) && (typeOfMetric == ASSET)) {
                                    strategicConstraints.removeConstraint();
                                } else {
                                    Constraint updatedConstraint;
                                    if (strategicConstraints.hasDefinedConstraint()) {
                                        updatedConstraint = new Constraint(strategicConstraints.getConstraint().getComparisonOperator(), threshold);
                                        strategicConstraints.removeConstraint();
                                        strategicConstraints.addConstraint(updatedConstraint);
                                    }
                                }
                                for (int j = 0; j < metricsKeys.length; j++) {
                                    fireTableCellUpdated(j, CONSTRAINT_VALUE);
                                }
                            }
                        }
                        catch (IllegalArgumentException e5) {
                            System.out.println("ERROR en setConstraintValueAt");
                        }
                    }
                }
            }
        }
    }

    /**
     * This method computes the initial status of categories and functions from
     * the corresponding expected outcome's value.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void computeValuesForInitialStatus() {
        genesValuesInitialStatus = new EnumMap<>(Genes.class);
        categoriesValuesInitialStatus = new EnumMap<>(Categories.class);
        functionsValuesInitialStatus = new EnumMap<>(Functions.class);
        assetValueInitialStatus = 0.0f;
        if (initialStatus != null) {
            float auxFunctionFitness = 0.0f;
            float auxCategoryFitness = 0.0f;
            int num = 0;
            for (Functions f : Functions.values()) {
                if (f.appliesToIG(implementationGroup)) {
                    auxFunctionFitness = 0.0f;

                    for (Categories c : f.getCategories(implementationGroup)) {
                        auxCategoryFitness = 0.0f;
                        for (Genes g : c.getGenes(implementationGroup)) {
                            // Gene raw value
                            genesValuesInitialStatus.put(g, initialStatus.getAllele(g).getDLI());
                            // To compute category fitness
                            num++;
                            auxCategoryFitness += initialStatus.getAllele(g).getDLI() * g.getWeight(implementationGroup);
                        }
                        // Category raw value
                        if (auxCategoryFitness >= 1.00f) {
                            auxCategoryFitness = 1.00f;
                        }
                        categoriesValuesInitialStatus.put(c, auxCategoryFitness);
                        // To compute Function fitness
                        auxCategoryFitness *= c.getWeight(implementationGroup);
                        if (auxCategoryFitness > c.getWeight(implementationGroup)) {
                            auxCategoryFitness = c.getWeight(implementationGroup);
                        }
                        auxFunctionFitness += auxCategoryFitness;
                    }
                    // Function raw value
                    if (auxFunctionFitness >= 1.00f) {
                        auxFunctionFitness = 1.00f;
                    }
                    functionsValuesInitialStatus.put(f, auxFunctionFitness);
                    // To compute asset fitness
                    auxFunctionFitness *= f.getWeight(implementationGroup);
                    if (auxFunctionFitness > f.getWeight(implementationGroup)) {
                        auxFunctionFitness = f.getWeight(implementationGroup);
                    }
                    assetValueInitialStatus += auxFunctionFitness;
                    if (assetValueInitialStatus >= 1.00f) {
                        assetValueInitialStatus = 1.00f;
                    }
                }
            }
        }
    }

    /**
     * This method computes the target status of categories and functions from
     * the corresponding expected outcome's value.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void computeValuesForTargetStatus() {
        genesValuesTargetStatus = new EnumMap<>(Genes.class);
        categoriesValuesTargetStatus = new EnumMap<>(Categories.class);
        functionsValuesTargetStatus = new EnumMap<>(Functions.class);
        assetValueTargetStatus = 0.0f;
        if (targetStatus != null) {
            float auxFunctionFitness = 0.0f;
            float auxCategoryFitness = 0.0f;
            int num = 0;
            for (Functions f : Functions.values()) {
                if (f.appliesToIG(implementationGroup)) {
                    auxFunctionFitness = 0.0f;

                    for (Categories c : f.getCategories(implementationGroup)) {
                        auxCategoryFitness = 0.0f;
                        for (Genes g : c.getGenes(implementationGroup)) {
                            // Gene raw value
                            genesValuesTargetStatus.put(g, targetStatus.getAllele(g).getDLI());
                            // To compute category fitness
                            num++;
                            auxCategoryFitness += targetStatus.getAllele(g).getDLI() * g.getWeight(implementationGroup);
                        }
                        // Category raw value
                        if (auxCategoryFitness >= 1.00f) {
                            auxCategoryFitness = 1.00f;
                        }
                        categoriesValuesTargetStatus.put(c, auxCategoryFitness);
                        // To compute Function fitness
                        auxCategoryFitness *= c.getWeight(implementationGroup);
                        if (auxCategoryFitness > c.getWeight(implementationGroup)) {
                            auxCategoryFitness = c.getWeight(implementationGroup);
                        }
                        auxFunctionFitness += auxCategoryFitness;
                    }
                    // Function raw value
                    if (auxFunctionFitness >= 1.00f) {
                        auxFunctionFitness = 1.00f;
                    }
                    functionsValuesTargetStatus.put(f, auxFunctionFitness);
                    // To compute asset fitness
                    auxFunctionFitness *= f.getWeight(implementationGroup);
                    if (auxFunctionFitness > f.getWeight(implementationGroup)) {
                        auxFunctionFitness = f.getWeight(implementationGroup);
                    }
                    assetValueTargetStatus += auxFunctionFitness;
                    if (assetValueTargetStatus >= 1.00f) {
                        assetValueTargetStatus = 1.00f;
                    }
                }
            }
        }
    }

}
