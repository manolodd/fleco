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

import com.manolodominguez.fleco.genetics.Genes;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;

/**
 * This class implements a graphic window to show additional information
 * regarding a given CyberTOMP metric. This is useful not only to design good
 * cybersecurity actions but also to make the cyebrsecurity workforce learn and
 * improve.
 *
 * @author Manuel Domínguez-Dorado
 */
@SuppressWarnings("serial")
public class MetricDetailsWindow extends JFrame {

    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 600;

    private Frame parent;
    private ImageBroker imageBroker;

    private JScrollPane informationPanelScroll;
    private JPanel informationPanel;
    private JPanel buttonsPanel;

    private JLabel labelAcronym;
    private JLabel labelAcronymTxt;
    private JLabel labelPurpose;
    private JTextArea textAreaPurpose;
    private JLabel labelImplementationTips;
    private JTextArea textAreaImplementationTips;
    private JLabel labelReferences;
    private JTextArea textAreaReferences;
    private JLabel labelLeadingArea;
    private JTextArea textAreaLeadingArea;
    private JLabel labelLeadingAreaResponsibilities;
    private JTextArea textAreaLeadingAreaResponsibilities;
    private JLabel labelAdditionalTip;
    private JTextArea textArealabelAdditionalTip;
    private JButton closeButton;

    /**
     * This is the constructor of the class.It creates a new instance and fills
     * it with information related to the gene (expected aoutcome) specified as
     * an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param parent The windows this class is instantiated from. Used to
     * compute the screen position of this window.
     * @param gene The gene (expected outcome) whose information is being
     * presented in this window.
     */
    public MetricDetailsWindow(Frame parent, Genes gene) {
        super();
        this.parent = parent;
        setTitle(gene.getAcronym());
        labelAcronymTxt = new JLabel(gene.getAcronym());
        textAreaPurpose = new JTextArea(gene.getPurpose());
        textAreaImplementationTips = new JTextArea(gene.getImplementationTips());
        textAreaReferences = new JTextArea(gene.getReferences());
        textAreaLeadingArea = new JTextArea(gene.getLeadingFunctionalArea().getAreaName());
        textAreaLeadingAreaResponsibilities = new JTextArea(gene.getLeadingFunctionalArea().getMainResponsibilities());
        initCommonComponents();
    }

    /**
     * This is the constructor of the class.It creates a new instance and fills
     * it with information related to the category specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param parent The windows this class is instantiated from. Used to
     * compute the screen position of this window.
     * @param category The category whose information is being presented in this
     * window.
     */
    public MetricDetailsWindow(Frame parent, Categories category) {
        super();
        this.parent = parent;
        setTitle(category.getAcronym());
        labelAcronymTxt = new JLabel(category.getAcronym());
        textAreaPurpose = new JTextArea(category.getPurpose());
        textAreaImplementationTips = new JTextArea("Implementation tips are not provided at this level. Choose any nested, low-level metric to access to their corresponding implementation tips.");
        textAreaReferences = new JTextArea("References are not provided at this level. Choose any nested, low-level metric to access to their corresponding references.");
        textAreaLeadingArea = new JTextArea("Several functional areas are involved in leading this category's cyberecurity actions. Choose any nested, low-level metric to access to their corresponding leading functional area.");
        textAreaLeadingAreaResponsibilities = new JTextArea("Several functional areas are involved in leading this category's cyberecurity actions. Choose any nested, low-level metric to access to their corresponding leading functional area's responsibility.");
        initCommonComponents();
    }

    /**
     * This is the constructor of the class.It creates a new instance and fills
     * it with information related to the function specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param parent The windows this class is instantiated from. Used to
     * compute the screen position of this window.
     * @param function The function whose information is being presented in this
     * window.
     */
    public MetricDetailsWindow(Frame parent, Functions function) {
        super();
        this.parent = parent;
        setTitle(function.getAcronym());
        labelAcronymTxt = new JLabel(function.getAcronym());
        textAreaPurpose = new JTextArea(function.getPurpose());
        textAreaImplementationTips = new JTextArea("Implementation tips are not provided at this level. Choose any nested, low-level metric to access to their corresponding implementation tips.");
        textAreaReferences = new JTextArea("References are not provided at this level. Choose any nested, low-level metric to access to their corresponding references.");
        textAreaLeadingArea = new JTextArea("Several functional areas are involved in leading this function's cyberecurity actions. Choose any nested, low-level metric to access to their corresponding leading functional area.");
        textAreaLeadingAreaResponsibilities = new JTextArea("Several functional areas are involved in leading this function's cyberecurity actions. Choose any nested, low-level metric to access to their corresponding leading functional area's responsibility.");
        initCommonComponents();
    }

    /**
     * This is the constructor of the class.It creates a new instance and fills
     * it with information related to the business asset.
     *
     * @author Manuel Domínguez-Dorado
     * @param parent The windows this class is instantiated from. Used to
     * compute the screen position of this window.
     */
    public MetricDetailsWindow(Frame parent) {
        super();
        this.parent = parent;
        setTitle("BUSINESS ASSET");
        labelAcronymTxt = new JLabel("BUSINESS ASSET");
        textAreaPurpose = new JTextArea("Achieve a good, holistic, cybersecurity status for the business asset");
        textAreaImplementationTips = new JTextArea("Implementation tips are not provided at this level. Choose any nested, low-level metric to access to their corresponding implementation tips.");
        textAreaReferences = new JTextArea("References are not provided at this level. Choose any nested, low-level metric to access to their corresponding references.");
        textAreaLeadingArea = new JTextArea("Several functional areas are involved in leading this asset's cyberecurity actions. Choose any nested, low-level metric to access to their corresponding leading functional area.");
        textAreaLeadingAreaResponsibilities = new JTextArea("Several functional areas are involved in leading this asset's cyberecurity actions. Choose any nested, low-level metric to access to their corresponding leading functional area's responsibility.");
        initCommonComponents();
    }

    /**
     * This method creates all common graphical components of the window.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void initCommonComponents() {
        imageBroker = new ImageBroker();
        addWindowStateListener((WindowEvent arg0) -> {
            keep();
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        //setResizable(false);
        buttonsPanel = new JPanel(new MigLayout("fillx, insets 20"));
        //getContentPane().add(informationPanelScroll, "north, wmin 10, hmin 10, height 100%");
        //getContentPane().setLayout(new MigLayout("fill, align left, wrap 2, debug, insets 10", "[left][left]", "[top][top]"));
        getContentPane().setLayout(new MigLayout("wrap 2, fill, insets 20", "[align left, shrink][align left, shrink]", "[align top, shrink][align top, shrink][align top, shrink][align top, shrink][align top, shrink][align top, shrink][align top, shrink]"));
        setBounds((parent.getWidth() - DEFAULT_WIDTH) / 2, (parent.getHeight() - DEFAULT_HEIGHT) / 2, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setMaximumSize(new Dimension((int) (DEFAULT_WIDTH * 1.2f), (int) (DEFAULT_HEIGHT * 1.2f)));
        setMinimumSize(new Dimension((int) (DEFAULT_WIDTH * 0.8f), (int) (DEFAULT_HEIGHT * 0.8f)));

        labelAcronym = new JLabel("Acronym: ");
        labelPurpose = new JLabel("Purpose: ");
        labelImplementationTips = new JLabel("Implementation tips: ");
        labelReferences = new JLabel("References: ");
        labelLeadingArea = new JLabel("Leading functional area: ");
        labelLeadingAreaResponsibilities = new JLabel("Area's responsibilities: ");

        getContentPane().add(labelAcronym, "growx, wmin 10");
        getContentPane().add(labelAcronymTxt, "growx, wmin 10");

        textAreaPurpose.setEditable(false);
        textAreaPurpose.setWrapStyleWord(true);
        textAreaPurpose.setLineWrap(true);
        textAreaPurpose.setAutoscrolls(true);
        getContentPane().add(labelPurpose, "growx, wmin 10");
        getContentPane().add(textAreaPurpose, "growx, wmin 10");

        textAreaImplementationTips.setEditable(false);
        textAreaImplementationTips.setWrapStyleWord(true);
        textAreaImplementationTips.setLineWrap(true);
        textAreaImplementationTips.setAutoscrolls(true);
        getContentPane().add(labelImplementationTips, "growx, wmin 10");
        getContentPane().add(textAreaImplementationTips, "growx, wmin 10");

        textAreaReferences.setEditable(false);
        textAreaReferences.setWrapStyleWord(true);
        textAreaReferences.setLineWrap(true);
        textAreaReferences.setAutoscrolls(true);
        getContentPane().add(labelReferences, "growx, wmin 10");
        getContentPane().add(textAreaReferences, "growx, wmin 10");

        textAreaLeadingArea.setEditable(false);
        textAreaLeadingArea.setWrapStyleWord(true);
        textAreaLeadingArea.setLineWrap(true);
        textAreaLeadingArea.setAutoscrolls(true);
        getContentPane().add(labelLeadingArea, "growx, wmin 10");
        getContentPane().add(textAreaLeadingArea, "growx, wmin 10");

        textAreaLeadingAreaResponsibilities.setEditable(false);
        textAreaLeadingAreaResponsibilities.setWrapStyleWord(true);
        textAreaLeadingAreaResponsibilities.setLineWrap(true);
        textAreaLeadingAreaResponsibilities.setAutoscrolls(true);
        getContentPane().add(labelLeadingAreaResponsibilities, "growx, wmin 10");
        getContentPane().add(textAreaLeadingAreaResponsibilities, "growx, wmin 10");

        labelAdditionalTip = new JLabel("Additional tip:");
        textArealabelAdditionalTip = new JTextArea("The information in this window aims to serve as assistance in achieving the desired value for this metric. In any case, the cybersecurity team must convert all this information into specific tasks that will primarily depend on the nature of the asset in question. These tasks should be focused, considering the cybersecurity function and category that this metric contributes to, as well as the specialized field of the identified main functional area.");
        textArealabelAdditionalTip.setEditable(false);
        textArealabelAdditionalTip.setWrapStyleWord(true);
        textArealabelAdditionalTip.setLineWrap(true);
        textArealabelAdditionalTip.setAutoscrolls(true);
        getContentPane().add(labelAdditionalTip, "growx, wmin 10");
        getContentPane().add(textArealabelAdditionalTip, "growx, wmin 10");

        getContentPane().add(buttonsPanel, "span, south, width 100%, height 20, wrap, align right");
        closeButton = new JButton("Close");
        closeButton.setMnemonic('C');
        closeButton.setIcon(imageBroker.getImageIcon16x16(AvailableImages.CLOSE));
        closeButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        buttonsPanel.add(closeButton, "align right");
    }

    /**
     * This method prevent the window from being iconified or maximized.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void keep() {
        this.setExtendedState(Frame.NORMAL);
    }
}
