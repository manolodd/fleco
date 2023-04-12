/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.gui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JTree;
import java.util.List;
import java.util.stream.StreamSupport;
import java.util.Spliterators;
import java.util.Spliterator;
import java.util.Collections;
import java.util.stream.Collectors;

public class JTreeUtil {

    public static void setTreeExpandedState(JTree tree, boolean expanded) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        setNodeExpandedState(tree, node, expanded);
    }

    private static void setNodeExpandedState(JTree tree, DefaultMutableTreeNode node, boolean expanded) {
        while (node.children().asIterator().hasNext()) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node.children().asIterator().next();
            setNodeExpandedState(tree, treeNode, expanded);
        }
        if (!expanded && node.isRoot()) {
            return;
        }
        TreePath path = new TreePath(node.getPath());
        if (expanded) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }
}
