/*
 * www.javagl.de - JTreeTable
 *
 * Copyright (c) 2016 Marco Hutter - http://www.javagl.de
 */
package com.manolodominguez.fleco.gui;

import de.javagl.treetable.AbstractTreeTableModel;
import de.javagl.treetable.TreeTableModel;

/**
 * Methods to create simple {@link TreeTableModel} instances
 */
public class ExampleTreeTableModels {

    /**
     * Create a simple dummy {@link TreeTableModel}
     *
     * @return The {@link TreeTableModel}
     */
    public static TreeTableModel createSimple() {
        Object root = "Asset";
        TreeTableModel treeTableModel = new AbstractTreeTableModel(root) {
            @Override
            public int getChildCount(Object node) {
                if (node.toString().startsWith("Asset")) {
                    return 3;
                }
                if (node.toString().startsWith("child")) {
                    return 10;
                }
                return 0;
            }

            @Override
            public Object getChild(Object node, int childIndex) {
                if (node.toString().startsWith("Asset")) {
                    return "child" + childIndex;
                }
                if (node.toString().startsWith("child")) {
                    return "leaf" + childIndex;
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public Object getValueAt(Object node, int column) {
                return node+", column "+column;
            }

            @Override
            public String getColumnName(int column) {
                return "columna" + column;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return TreeTableModel.class;
                }
                return Object.class;
            }
        };
        return treeTableModel;
    }
}
