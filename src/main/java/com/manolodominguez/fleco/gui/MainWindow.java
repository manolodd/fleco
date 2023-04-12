/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.gui;

import de.javagl.treetable.JTreeTable;
import de.javagl.treetable.TreeTableModel;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.tree.TreePath;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author manolodd
 */
public class MainWindow extends JFrame {

    public MainWindow() throws HeadlessException {
        super();
        getContentPane().setLayout(new MigLayout("fillx, filly"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 4, screenSize.height / 4, screenSize.width / 2, screenSize.height / 2);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        setTitle("FLECO GUI");
        JMenu menu = new JMenu("Case");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem("Load");
        JMenuItem menuItem2 = new JMenuItem("Save");
        JMenuItem menuItem3 = new JMenuItem("Save as");
        JMenuItem menuItem4 = new JMenuItem("Export");
        JMenuItem menuItem5 = new JMenuItem("Exit");
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);
        menu.addSeparator();
        menu.add(menuItem5);
        JMenu menu2 = new JMenu("About");
        menuBar.add(menu2);
        JMenuItem menuItem2_1 = new JMenuItem("About FlecoGUI");
        JMenuItem menuItem2_2 = new JMenuItem("FlecoGUI license");
        menu2.add(menuItem2_1);
        menu2.add(menuItem2_2);

        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton("Botón 1"));
        toolBar.add(new JButton("Botón 2"));
        toolBar.add(new JButton("Botón 3"));
        toolBar.add(new JButton("Botón 4"));
        toolBar.setFloatable(false);
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        getContentPane().add(toolBar, "span, north, width 100%, wrap");
        /*        
        JTextArea textArea = new JTextArea();
        textArea.setOpaque(true);
        textArea.setEditable(true);
        textArea.setEnabled(true);
        textArea.setText("ESTO ES UN TEXTO DE PRUEBA");
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, "span, width 100%, height 100%, wrap");
         */
        TreeTableModel treeTableModel = ExampleTreeTableModels.createSimple();
        JTreeTable treeTable = new JTreeTable(treeTableModel);
        JScrollPane scrollPane = new JScrollPane(treeTable);
        MyTreeCellRenderer treeCellRenderer = new MyTreeCellRenderer();
        treeTable.getTree().setCellRenderer(treeCellRenderer);
        getContentPane().add(scrollPane, "span, width 100%, height 100%, wrap");
        //JTreeUtil.setTreeExpandedState(treeTable.getTree(), true);
        for (int i = 0; i<treeTable.getTree().getRowCount();i++) {
        treeTable.getTree().expandRow(i);
    }
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, "span, width 100%, height 20, wrap");
    }

}
