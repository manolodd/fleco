/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.gui;

import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
/**
 * @web https://www.jc-mouse.net/
 * @author Mouse
 */
public class MyTreeCellRenderer extends DefaultTreeCellRenderer{

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
    boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
        //altura de cada nodo
        tree.setRowHeight(26);
        setOpaque(false);     
        if( selected )
        //-- Asigna iconos
        // si value es la raiz
        
        if (tree.getModel().getRoot().toString().startsWith("Asset")) {
            this.setClosedIcon(new ImageIcon(getClass().getResource("/com/manolodominguez/flecogui/gui/asset.png")));
            this.setOpenIcon(new ImageIcon(getClass().getResource("/com/manolodominguez/flecogui/gui/asset.png")));
        } 
        /*
        else if( ((DefaultMutableTreeNode) value).getUserObject() instanceof Estudiante) 
        {
            setIcon( ((Estudiante)((DefaultMutableTreeNode) value).getUserObject()).getIcon() );            
        }
        else if( ((DefaultMutableTreeNode) value).getUserObject() instanceof Materia) 
        {       
            setIcon( ((Materia)((DefaultMutableTreeNode) value).getUserObject()).getIcon() );                       
        }
*/
        return this;
}

}//end:MyTreeCellRenderer
