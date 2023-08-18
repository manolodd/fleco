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

import com.manolodominguez.fleco.algorithm.FLECO;
import com.manolodominguez.fleco.genetics.Chromosome;
import com.manolodominguez.fleco.genetics.Genes;
import com.manolodominguez.fleco.gui.flecoio.FLECOFilter;
import com.manolodominguez.fleco.gui.flecoio.FLECOLoader;
import com.manolodominguez.fleco.gui.flecoio.FLECOSaver;
import com.manolodominguez.fleco.strategicconstraints.ComparisonOperators;
import com.manolodominguez.fleco.strategicconstraints.Constraint;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import net.miginfocom.swing.MigLayout;

/**
 * This is the constructor of the class, it creates a new instance and assigns
 * the corresponding default values to its attributes.
 *
 * @author Manuel Domínguez-Dorado
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame implements IFLECOGUI, IFLECOTableModelChangeListener {

    private JMenuBar menuBar;
    private JMenu menuCase;
    private JMenuItem menuCaseItemNew;
    private JMenuItem menuCaseItemLoad;
    private JMenuItem menuCaseItemSave;
    private JMenuItem menuCaseItemSaveAs;
    private JMenuItem menuCaseItemRunFLECO;
    private JMenuItem menuCaseItemExit;
    private JMenu menuAbout;
    private JMenuItem menuAboutItemAbout;
    private JMenuItem menuAboutItemLicense;
    private JToolBar toolBar;
    private JLabel messageSpace;
    private FLECOProgressBar progressBar;
    private FLECOTableModel tableModel;
    private JTable table;
    private TableColumnAdjuster tableColumnAdjuster;
    private JComboBox<Float> comboBoxColumn1;
    private JComboBox<String> comboBoxColumn2;
    private JScrollPane scrollPane;

    private IFLECOGUI gui;
    private JButton runButton;
    private JButton randomButton;
    private JButton newButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton saveAsButton;
    private JButton generateConstraintsButton;

    private Object[] igOptions = {ImplementationGroups.IG1, ImplementationGroups.IG2, ImplementationGroups.IG3};
    private ImageBroker imageBroker = new ImageBroker();

    private CaseConfig caseConfig;

    public MainWindow() throws HeadlessException {
        super();

        caseConfig = new CaseConfig();

        gui = this;

        getContentPane().setLayout(new MigLayout("fillx, filly"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width * 1 / 8, screenSize.height * 1 / 8, screenSize.width * 3 / 4, screenSize.height * 3 / 4);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        if (caseConfig.getFileName() != null) {
            setTitle("FLECO Studio - " + caseConfig.getFileName());
        } else {
            setTitle("FLECO Studio - No case is active!");
        }

        menuCase = new JMenu("Case");
        menuCase.setMnemonic('C');
        menuBar.add(menuCase);
        menuCaseItemNew = new JMenuItem("New");
        menuCaseItemNew.setMnemonic('N');
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        menuCaseItemNew.setAccelerator(ctrlN);
        menuCaseItemNew.setIcon(imageBroker.getImageIcon16x16(AvailableImages.NEW));
        menuCaseItemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNew();
            }
        });
        menuCaseItemLoad = new JMenuItem("Load");
        KeyStroke ctrlL = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK);
        menuCaseItemLoad.setAccelerator(ctrlL);
        menuCaseItemLoad.setMnemonic('L');
        menuCaseItemLoad.setIcon(imageBroker.getImageIcon16x16(AvailableImages.LOAD));
        menuCaseItemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoad();
            }
        });
        menuCaseItemSave = new JMenuItem("Save");
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        menuCaseItemSave.setAccelerator(ctrlS);
        menuCaseItemSave.setMnemonic('S');
        menuCaseItemSave.setIcon(imageBroker.getImageIcon16x16(AvailableImages.SAVE));
        menuCaseItemSave.setEnabled(false);
        menuCaseItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });
        menuCaseItemSaveAs = new JMenuItem("Save as");
        KeyStroke ctrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK);
        menuCaseItemSaveAs.setAccelerator(ctrlA);
        menuCaseItemSaveAs.setMnemonic('a');
        menuCaseItemSaveAs.setIcon(imageBroker.getImageIcon16x16(AvailableImages.SAVE_AS));
        menuCaseItemSaveAs.setEnabled(false);
        menuCaseItemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveAs();
            }
        });
        menuCaseItemRunFLECO = new JMenuItem("Run FLECO");
        KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        menuCaseItemRunFLECO.setAccelerator(ctrlR);
        menuCaseItemRunFLECO.setMnemonic('R');
        menuCaseItemRunFLECO.setIcon(imageBroker.getImageIcon16x16(AvailableImages.RUN));
        menuCaseItemRunFLECO.setEnabled(false);
        menuCaseItemRunFLECO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRunFLECO();
            }
        });
        menuCaseItemExit = new JMenuItem("Exit");
        KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);
        menuCaseItemExit.setAccelerator(ctrlX);
        menuCaseItemExit.setMnemonic('E');
        menuCaseItemExit.setIcon(imageBroker.getImageIcon16x16(AvailableImages.EXIT));
        menuCaseItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });
        menuCase.add(menuCaseItemNew);
        menuCase.add(menuCaseItemLoad);
        menuCase.add(menuCaseItemSave);
        menuCase.add(menuCaseItemSaveAs);
        menuCase.add(menuCaseItemRunFLECO);
        menuCase.addSeparator();
        menuCase.add(menuCaseItemExit);

        menuAbout = new JMenu("About");
        menuAbout.setMnemonic('A');
        menuBar.add(menuAbout);
        menuAboutItemAbout = new JMenuItem("About FLECO");
        KeyStroke F1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        menuAboutItemAbout.setAccelerator(F1);
        menuAboutItemAbout.setMnemonic('b');
        menuAboutItemAbout.setIcon(imageBroker.getImageIcon16x16(AvailableImages.ABOUT));
        menuAboutItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAbout();
            }
        });
        menuAboutItemLicense = new JMenuItem("FLECO license");
        KeyStroke ctrlI = KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK);
        menuAboutItemLicense.setAccelerator(ctrlI);
        menuAboutItemLicense.setMnemonic('F');
        menuAboutItemLicense.setIcon(imageBroker.getImageIcon16x16(AvailableImages.LICENSE));
        menuAboutItemLicense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onFLECOLicense();
            }
        });
        menuAbout.add(menuAboutItemAbout);
        menuAbout.add(menuAboutItemLicense);

        toolBar = new JToolBar();
        toolBar.setLayout(new MigLayout("gapx 5px"));
        runButton = new JButton();
        runButton.setIcon(imageBroker.getImageIcon32x32(AvailableImages.RUN));
        runButton.setFocusable(false);
        runButton.setEnabled(false);
        runButton.setToolTipText("Run FLECO to find a solution to discuss");
        randomButton = new JButton();
        randomButton.setIcon(imageBroker.getImageIcon32x32(AvailableImages.RANDOM));
        randomButton.setFocusable(false);
        randomButton.setToolTipText("Generate a random current state to test and learn");
        newButton = new JButton();
        newButton.setIcon(imageBroker.getImageIcon32x32(AvailableImages.NEW));
        newButton.setFocusable(false);
        newButton.setToolTipText("Create a new case");
        loadButton = new JButton();
        loadButton.setIcon(imageBroker.getImageIcon32x32(AvailableImages.LOAD));
        loadButton.setFocusable(false);
        loadButton.setToolTipText("Load a case previously saved");
        saveButton = new JButton();
        saveButton.setIcon(imageBroker.getImageIcon32x32(AvailableImages.SAVE));
        saveButton.setFocusable(false);
        saveButton.setEnabled(false);
        saveButton.setToolTipText("Save the latest changes to the already saved case");
        saveAsButton = new JButton();
        saveAsButton.setIcon(imageBroker.getImageIcon32x32(AvailableImages.SAVE_AS));
        saveAsButton.setFocusable(false);
        saveAsButton.setEnabled(false);
        saveAsButton.setToolTipText("Save the case choosing a name and location");
        generateConstraintsButton = new JButton();
        generateConstraintsButton.setIcon(imageBroker.getImageIcon32x32(AvailableImages.RULES));
        generateConstraintsButton.setFocusable(false);
        generateConstraintsButton.setEnabled(false);
        generateConstraintsButton.setToolTipText("Set current cybersecurity status as minimum to compute target status");

        messageSpace = new JLabel();
        messageSpace.setBackground(Color.WHITE);
        messageSpace.setOpaque(true);
        messageSpace.setHorizontalAlignment(SwingConstants.CENTER);
        messageSpace.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        messageSpace.setText("Load an existing case or create a new one");
        toolBar.add(randomButton);
        toolBar.add(newButton);
        toolBar.add(loadButton);
        toolBar.add(saveButton);
        toolBar.add(saveAsButton);
        toolBar.add(generateConstraintsButton);
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(runButton);
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(messageSpace, "width 100%");
        toolBar.setFloatable(false);
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        getContentPane().add(toolBar, "span, north, width 100%, wrap");
        table = new JTable();
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableColumnAdjuster = new TableColumnAdjuster(table);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 && table.getSelectedColumn() == 0) {
                    onDoubleClicOnTable(table);
                }
            }
        });
        scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, "span, width 100%, height 100%, wrap");
        progressBar = new FLECOProgressBar();
        getContentPane().add(progressBar, "span, width 100%, height 20, wrap");
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRunFLECO();
            }
        });

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRandom();
            }
        });

        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveAs();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNew();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoad();
            }
        });

        generateConstraintsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConstraints();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                onExit();
            }
        });
    }

    private void onDoubleClicOnTable(JTable table) {
        System.out.println("Doble clic en la fila: " + table.getSelectedRow() + " (" + tableModel.getValueAt(table.getSelectedRow(), table.getSelectedColumn()) + ")");
    }

    private void onLoad() {
        boolean load = false;
        if (caseConfig.isInitialized()) {
            if (caseConfig.isAlreadySaved()) {
                if (caseConfig.isModified()) {
                    int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save changes before loading a new case?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                    if (option == JOptionPane.OK_OPTION) {
                        if (onSave()) {
                            load = true;
                        }
                    } else if (option == JOptionPane.NO_OPTION) {
                        load = true;
                    }
                } else {
                    load = true;
                }
            } else {
                int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save the case before loading a new one?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                if (option == JOptionPane.OK_OPTION) {
                    if (onSaveAs()) {
                        load = true;
                    }
                } else if (option == JOptionPane.NO_OPTION) {
                    load = true;
                }
            }
        } else {
            load = true;
        }
        if (load) {
            boolean openProcessFinished = false;
            while (!openProcessFinished) {
                JFileChooser loadDialog = new JFileChooser();
                loadDialog.setFileFilter(new FLECOFilter());
                loadDialog.setDialogType(JFileChooser.CUSTOM_DIALOG);
                loadDialog.setApproveButtonMnemonic('O');
                loadDialog.setApproveButtonText("Ok");
                loadDialog.setDialogTitle("Load FLECO case");
                loadDialog.setAcceptAllFileFilterUsed(false);
                loadDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int fileChoosingResult = loadDialog.showOpenDialog(this);
                if (fileChoosingResult == JFileChooser.APPROVE_OPTION) {
                    if (loadDialog.getSelectedFile().exists()) {
                        if (loadDialog.getSelectedFile().canRead()) {
                            FLECOLoader flecoLoader = new FLECOLoader();
                            boolean isLoaded = flecoLoader.load(loadDialog.getSelectedFile());
                            if (isLoaded) {
                                caseConfig.reset();
                                caseConfig.setCurrentIG(flecoLoader.getInitialStatus().getImplementationGroup());
                                if (caseConfig.getCurrentIG() != null) {
                                    caseConfig.setInitialStatus(flecoLoader.getInitialStatus());
                                    caseConfig.setStrategicConstraints(flecoLoader.getStrategicConstraints());
                                    caseConfig.setTargetStatus(flecoLoader.getTargetStatus());
                                    tableModel = new FLECOTableModel(caseConfig.getInitialStatus(), caseConfig.getStrategicConstraints());
                                    tableModel.setTargetStatus(caseConfig.getTargetStatus());
                                    configureMainTable(tableModel);
                                    caseConfig.setAlreadySaved(true);
                                    caseConfig.setModified(false);
                                    caseConfig.setPathAndFileName(loadDialog.getSelectedFile().getAbsolutePath());
                                    //AFTER
                                    randomButton.setEnabled(true);
                                    newButton.setEnabled(true);
                                    loadButton.setEnabled(true);
                                    if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                                        saveButton.setEnabled(true);
                                    } else {
                                        saveButton.setEnabled(false);
                                    }
                                    saveAsButton.setEnabled(true);
                                    runButton.setEnabled(true);
                                    generateConstraintsButton.setEnabled(true);
                                    menuCaseItemNew.setEnabled(true);
                                    menuCaseItemLoad.setEnabled(true);
                                    if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                                        menuCaseItemSave.setEnabled(true);
                                    } else {
                                        menuCaseItemSave.setEnabled(false);
                                    }
                                    menuCaseItemSaveAs.setEnabled(true);
                                    menuCaseItemRunFLECO.setEnabled(true);
                                    menuCaseItemExit.setEnabled(true);
                                    menuAbout.setEnabled(true);
                                    menuAboutItemAbout.setEnabled(true);
                                    menuAboutItemLicense.setEnabled(true);
                                    table.setEnabled(true);
                                    menuBar.setEnabled(true);
                                    menuCase.setEnabled(true);
                                    menuAbout.setEnabled(true);
                                    menuBar.setEnabled(true);
                                    progressBar.setValue(0);
                                    messageSpace.setText("Set the values of current status, constraint operator, and contraint value and run FLECO");
                                    if (caseConfig.getFileName() != null) {
                                        setTitle("FLECO Studio - " + caseConfig.getFileName());
                                    } else {
                                        setTitle("FLECO Studio - Current case is not saved!");
                                    }
                                    caseConfig.setInitialized(true);
                                }
                                openProcessFinished = true;
                            } else {
                                JOptionPane.showInternalMessageDialog(this.getContentPane(), "This file is not a FLECO case.\nTry again.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
                            }
                        } else {
                            JOptionPane.showInternalMessageDialog(this.getContentPane(), "The specified file cannot be read.\nTry again.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
                        }
                    } else {
                        JOptionPane.showInternalMessageDialog(this.getContentPane(), "The specified file does not exist.\nTry again.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
                    }
                } else {
                    openProcessFinished = true;
                }
            }
        }
    }

    /**
     * This method is called when a the Save option are chosen from the menú. It
     * makes everything needed to save the file if it needs to be saved and
     * update the GUI accordingly.
     *
     * @author Manuel Domínguez-Dorado
     */
    private boolean onSave() {
        boolean saved = false;
        if (caseConfig.isInitialized()) {
            FLECOSaver flecoSaver = new FLECOSaver(caseConfig.getInitialStatus(), caseConfig.getStrategicConstraints(), caseConfig.getTargetStatus());
            boolean savedCorrectly = flecoSaver.save(new File(caseConfig.getPathAndFileName()));
            if (savedCorrectly) {
                setTitle(caseConfig.getFileName());
                caseConfig.setAlreadySaved(true);
                caseConfig.setInitialized(true);
                caseConfig.setModified(false);
                saved = true;
            } else {
                JOptionPane.showInternalMessageDialog(this.getContentPane(), "There were errors when saving the case to disk." + "\nTry again choosing Save As to another place.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
            }
            // AFTER
            randomButton.setEnabled(true);
            newButton.setEnabled(true);
            loadButton.setEnabled(true);
            if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
            saveAsButton.setEnabled(true);
            runButton.setEnabled(true);
            generateConstraintsButton.setEnabled(true);
            menuCaseItemNew.setEnabled(true);
            menuCaseItemLoad.setEnabled(true);
            if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                menuCaseItemSave.setEnabled(true);
            } else {
                menuCaseItemSave.setEnabled(false);
            }
            menuCaseItemSaveAs.setEnabled(true);
            menuCaseItemRunFLECO.setEnabled(true);
            menuCaseItemExit.setEnabled(true);
            menuAbout.setEnabled(true);
            menuAboutItemAbout.setEnabled(true);
            menuAboutItemLicense.setEnabled(true);
            table.setEnabled(true);
            menuBar.setEnabled(true);
            menuCase.setEnabled(true);
            menuAbout.setEnabled(true);
            menuBar.setEnabled(true);
            messageSpace.setText("FLECO is running...");
            progressBar.setValue(0);
            messageSpace.setText("Set the values of current status, constraint operator, and contraint value and run FLECO");
            if (caseConfig.getFileName() != null) {
                setTitle("FLECO Studio - " + caseConfig.getFileName());
            } else {
                setTitle("FLECO Studio - unnamed.fleco");
            }
        }
        return saved;
    }

    /**
     * This method is called when a the Save as option are chosen from the menú.
     * It makes everything needed to save the file if it needs to be saved with
     * a name and update the GUI accordingly.
     *
     * @author Manuel Domínguez-Dorado
     */
    private boolean onSaveAs() {
        boolean saved = false;
        if (caseConfig.isInitialized()) {
            JFileChooser saveAsDialog = new JFileChooser();
            saveAsDialog.setFileFilter(new FLECOFilter());
            saveAsDialog.setDialogType(JFileChooser.CUSTOM_DIALOG);
            saveAsDialog.setApproveButtonMnemonic('O');
            saveAsDialog.setApproveButtonText("Ok");
            saveAsDialog.setDialogTitle("Save current FLECO case");
            saveAsDialog.setAcceptAllFileFilterUsed(false);
            if (caseConfig.getFileName() != null) {
                saveAsDialog.setSelectedFile(new File(caseConfig.getPathAndFileName()));
            }
            saveAsDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = saveAsDialog.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String extension = null;
                caseConfig.setPathAndFileName(saveAsDialog.getSelectedFile().getPath());
                int i = caseConfig.getPathAndFileName().lastIndexOf('.');
                if (i > 0 && i < caseConfig.getPathAndFileName().length() - 1) {
                    extension = caseConfig.getPathAndFileName().substring(i + 1).toLowerCase();
                }
                if (extension == null) {
                    caseConfig.setPathAndFileName(caseConfig.getPathAndFileName() + ".fleco");
                } else if (!extension.equals("fleco")) {
                    caseConfig.setPathAndFileName(caseConfig.getPathAndFileName() + ".fleco");
                }
                saveAsDialog.setSelectedFile(new File(caseConfig.getPathAndFileName()));
                FLECOSaver flecoSaver = new FLECOSaver(caseConfig.getInitialStatus(), caseConfig.getStrategicConstraints(), caseConfig.getTargetStatus());
                boolean savedCorrectly = flecoSaver.save(new File(caseConfig.getPathAndFileName()));
                if (savedCorrectly) {
                    setTitle(caseConfig.getFileName());
                    caseConfig.setAlreadySaved(true);
                    caseConfig.setInitialized(true);
                    caseConfig.setModified(false);
                    saved = true;
                } else {
                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "There were errors when saving the case to disk." + "\nTry again choosing another place.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
                }
            }
            // AFTER
            randomButton.setEnabled(true);
            newButton.setEnabled(true);
            loadButton.setEnabled(true);
            if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
            saveAsButton.setEnabled(true);
            runButton.setEnabled(true);
            generateConstraintsButton.setEnabled(true);
            menuCaseItemNew.setEnabled(true);
            menuCaseItemLoad.setEnabled(true);
            if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                menuCaseItemSave.setEnabled(true);
            } else {
                menuCaseItemSave.setEnabled(false);
            }
            menuCaseItemSaveAs.setEnabled(true);
            menuCaseItemRunFLECO.setEnabled(true);
            menuCaseItemExit.setEnabled(true);
            menuAbout.setEnabled(true);
            menuAboutItemAbout.setEnabled(true);
            menuAboutItemLicense.setEnabled(true);
            table.setEnabled(true);
            menuBar.setEnabled(true);
            menuCase.setEnabled(true);
            menuAbout.setEnabled(true);
            menuBar.setEnabled(true);
            messageSpace.setText("FLECO is running...");
            progressBar.setValue(0);
            messageSpace.setText("Set the values of current status, constraint operator, and contraint value and run FLECO");
            if (caseConfig.getFileName() != null) {
                setTitle("FLECO Studio - " + caseConfig.getFileName());
            } else {
                setTitle("FLECO Studio - unnamed.fleco");
            }
        }
        return saved;
    }

    /**
     * This method is called when a the Exit option are chosen from the menú or
     * the main windows Close button is pressed. It makes everything needed to
     * exit FLECO Studio safely.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void onExit() {
        if (caseConfig.isInitialized()) {
            if (caseConfig.isAlreadySaved()) {
                if (caseConfig.isModified()) {
                    int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save changes before exit?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                    if (option == JOptionPane.OK_OPTION) {
                        onSave();
                        if (!caseConfig.isModified()) {
                            dispose();
                        }
                    } else if (option == JOptionPane.NO_OPTION) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            } else {
                int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save the current case before exit?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                if (option == JOptionPane.OK_OPTION) {
                    onSaveAs();
                    if (caseConfig.isAlreadySaved() && !caseConfig.isModified()) {
                        dispose();
                    }
                } else if (option == JOptionPane.NO_OPTION) {
                    dispose();
                }
            }
        } else {
            dispose();
        }
    }

    /**
     * This method is called when a the About option are chosen from the menú.
     * It opens in a web browser the URL of the project in Github.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void onAbout() {
        if (Desktop.isDesktopSupported()) {
            try {
                URL url = new URL("https://github.com/manolodd/fleco");
                Desktop.getDesktop().browse(url.toURI());
            }
            catch (IOException | URISyntaxException ex) {
                JOptionPane.showInternalMessageDialog(this.getContentPane(), "It was not possible to access the home page of\nFLECO project.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
            }
        } else {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "It was not possible to access the home page of\nFLECO project.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
        }
    }

    /**
     * This method is called when a the FLECO License option are chosen from the
     * menú. It opens in a web browser the URL of the project's license.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void onFLECOLicense() {
        if (Desktop.isDesktopSupported()) {
            try {
                URL url = new URL("https://www.gnu.org/licenses/lgpl-3.0.html");
                Desktop.getDesktop().browse(url.toURI());
            }
            catch (IOException | URISyntaxException ex) {
                JOptionPane.showInternalMessageDialog(this.getContentPane(), "It was not possible to access the home page of\nLGPL-3.0-or-later license.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
            }
        } else {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "It was not possible to access the home page of\nLGPL-3.0-or-later license.", null, JOptionPane.INFORMATION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.ABOUT));
        }
    }

    /**
     * This method is link the table in the GUI to its corresponding model,
     * specified as an argument.
     *
     * @author Manuel Domínguez-Dorado
     * @param tableModel the model to associate the GUI's table to.
     */
    private void configureMainTable(FLECOTableModel tableModel) {
        table.setModel(tableModel);
        tableModel.setChangeEventListener(this);
        comboBoxColumn1 = new JComboBox<>();
        comboBoxColumn1.addItem(0.0f);
        comboBoxColumn1.addItem(0.33f);
        comboBoxColumn1.addItem(0.67f);
        comboBoxColumn1.addItem(1.00f);
        table.getColumnModel().getColumn(FLECOTableModel.CURRENT_STATUS).setCellEditor(new DefaultCellEditor(comboBoxColumn1));
        comboBoxColumn2 = new JComboBox<>();
        comboBoxColumn2.addItem("N/A");
        comboBoxColumn2.addItem("LESS");
        comboBoxColumn2.addItem("LESS_OR_EQUAL");
        comboBoxColumn2.addItem("EQUAL");
        comboBoxColumn2.addItem("GREATER_OR_EQUAL");
        comboBoxColumn2.addItem("GREATER");
        table.getColumnModel().getColumn(FLECOTableModel.CONSTRAINT_OPERATOR).setCellEditor(new DefaultCellEditor(comboBoxColumn2));
        table.getColumnModel().getColumn(FLECOTableModel.TARGET_STATUS).setCellRenderer(new TargetStatusCellRenderer());
        tableColumnAdjuster.adjustColumns();
        // Remove the "CyberTOMP Metric Key" column from the view, but it 
        // remains in teh model.
        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.removeColumn(tableColumnModel.getColumn(FLECOTableModel.CYBERTOMP_METRIC_KEY));
    }

    /**
     * This method is called when a clic on Constraints icon is done in the
     * toolbar. It creates automatically a constraint for every expected outcome
     * as GREATER_OR_EQUAL and the current value of them.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void onConstraints() {
        caseConfig.getStrategicConstraints().removeAll();
        for (Genes gene : Genes.getGenesFor(caseConfig.getInitialStatus().getImplementationGroup())) {
            Constraint constraint = new Constraint(ComparisonOperators.GREATER_OR_EQUAL, caseConfig.getInitialStatus().getAllele(gene).getDLI());
            caseConfig.getStrategicConstraints().addConstraint(gene, constraint);
        }
        tableModel.setStrategicConstraints(caseConfig.getStrategicConstraints());
    }

    /**
     * This method is called when a clic on Random icon is done in the toolbar.
     * It creates automatically an initial status with dummy values just to
     * start plaing with FLECO Studio.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void onRandom() {

        boolean newRandom = false;
        if (caseConfig.isInitialized()) {
            if (caseConfig.isAlreadySaved()) {
                if (caseConfig.isModified()) {
                    int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save changes before creating a new random case?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                    if (option == JOptionPane.OK_OPTION) {
                        if (onSave()) {
                            newRandom = true;
                        }
                    } else if (option == JOptionPane.NO_OPTION) {
                        newRandom = true;
                    }
                } else {
                    newRandom = true;
                }
            } else {
                int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save the case before creating a new random one?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                if (option == JOptionPane.OK_OPTION) {
                    if (onSaveAs()) {
                        newRandom = true;
                    }
                } else if (option == JOptionPane.NO_OPTION) {
                    newRandom = true;
                }
            }
        } else {
            newRandom = true;
        }
        if (newRandom) {
            ImplementationGroups auxIG = (ImplementationGroups) JOptionPane.showInputDialog(this, "Choose the implementation group for the new random case", null, JOptionPane.PLAIN_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.GENES), igOptions, ImplementationGroups.IG1);
            if (auxIG != null) {
                caseConfig.reset();
                caseConfig.setCurrentIG(auxIG);
                caseConfig.setInitialized(true);
                caseConfig.setInitialStatus(new Chromosome(caseConfig.getCurrentIG()));
                caseConfig.getInitialStatus().randomizeGenes();
                caseConfig.setStrategicConstraints(new StrategicConstraints(caseConfig.getCurrentIG()));
                tableModel = new FLECOTableModel(caseConfig.getInitialStatus(), caseConfig.getStrategicConstraints());
                configureMainTable(tableModel);
                //AFTER
                randomButton.setEnabled(true);
                newButton.setEnabled(true);
                loadButton.setEnabled(true);
                if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
                saveAsButton.setEnabled(true);
                runButton.setEnabled(true);
                generateConstraintsButton.setEnabled(true);
                menuCaseItemNew.setEnabled(true);
                menuCaseItemLoad.setEnabled(true);
                if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                    menuCaseItemSave.setEnabled(true);
                } else {
                    menuCaseItemSave.setEnabled(false);
                }
                menuCaseItemSaveAs.setEnabled(true);
                menuCaseItemRunFLECO.setEnabled(true);
                menuCaseItemExit.setEnabled(true);
                menuAbout.setEnabled(true);
                menuAboutItemAbout.setEnabled(true);
                menuAboutItemLicense.setEnabled(true);
                table.setEnabled(true);
                menuBar.setEnabled(true);
                menuCase.setEnabled(true);
                menuAbout.setEnabled(true);
                menuBar.setEnabled(true);
                messageSpace.setText("FLECO is running...");
                progressBar.setValue(0);
                messageSpace.setText("Set the values of current status, constraint operator, and contraint value and run FLECO");
                if (caseConfig.getFileName() != null) {
                    setTitle("FLECO Studio - " + caseConfig.getFileName());
                } else {
                    setTitle("FLECO Studio - Current case is not saved!");
                }
                caseConfig.setInitialized(true);
            }
        }
    }

    /**
     * This method is called when a clic on New icon is done in the toolbar or
     * the same option is chosen from the menu. It creates a new case, taking
     * care of the current one if there is such a current case.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void onNew() {
        boolean donew = false;
        if (caseConfig.isInitialized()) {
            if (caseConfig.isAlreadySaved()) {
                if (caseConfig.isModified()) {
                    int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save changes before creating a new case?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                    if (option == JOptionPane.OK_OPTION) {
                        if (onSave()) {
                            donew = true;
                        }
                    } else if (option == JOptionPane.NO_OPTION) {
                        donew = true;
                    }
                } else {
                    donew = true;
                }
            } else {
                int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "Save the case before creating a new one?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                if (option == JOptionPane.OK_OPTION) {
                    if (onSaveAs()) {
                        donew = true;
                    }
                } else if (option == JOptionPane.NO_OPTION) {
                    donew = true;
                }
            }
        } else {
            donew = true;
        }
        if (donew) {
            ImplementationGroups auxIG = (ImplementationGroups) JOptionPane.showInputDialog(this, "Choose the implementation group for the new case", null, JOptionPane.PLAIN_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.GENES), igOptions, ImplementationGroups.IG1);
            if (auxIG != null) {
                caseConfig.reset();
                caseConfig.setCurrentIG(auxIG);
                caseConfig.setInitialized(true);
                caseConfig.setInitialStatus(new Chromosome(caseConfig.getCurrentIG()));
                caseConfig.setStrategicConstraints(new StrategicConstraints(caseConfig.getCurrentIG()));
                tableModel = new FLECOTableModel(caseConfig.getInitialStatus(), caseConfig.getStrategicConstraints());
                configureMainTable(tableModel);
                //AFTER
                randomButton.setEnabled(true);
                newButton.setEnabled(true);
                loadButton.setEnabled(true);
                if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
                saveAsButton.setEnabled(true);
                runButton.setEnabled(true);
                generateConstraintsButton.setEnabled(true);
                menuCaseItemNew.setEnabled(true);
                menuCaseItemLoad.setEnabled(true);
                if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                    menuCaseItemSave.setEnabled(true);
                } else {
                    menuCaseItemSave.setEnabled(false);
                }
                menuCaseItemSaveAs.setEnabled(true);
                menuCaseItemRunFLECO.setEnabled(true);
                menuCaseItemExit.setEnabled(true);
                menuAbout.setEnabled(true);
                menuAboutItemAbout.setEnabled(true);
                menuAboutItemLicense.setEnabled(true);
                table.setEnabled(true);
                menuBar.setEnabled(true);
                menuCase.setEnabled(true);
                menuAbout.setEnabled(true);
                menuBar.setEnabled(true);
                messageSpace.setText("FLECO is running...");
                progressBar.setValue(0);
                messageSpace.setText("Set the values of current status, constraint operator, and contraint value and run FLECO");
                if (caseConfig.getFileName() != null) {
                    setTitle("FLECO Studio - " + caseConfig.getFileName());
                } else {
                    setTitle("FLECO Studio - Current case is not saved!");
                }
                caseConfig.setInitialized(true);
            }
        }
    }

    /**
     * This method is called when a clic on Run FLECO icon is done in the
     * toolbar or the same option is chosen from the menu. It start running
     * FLECO to find a target status that fulfill the defined constraints. It
     * also takes care of the current case as it is going to be modified.
     *
     * @author Manuel Domínguez-Dorado
     */
    private void onRunFLECO() {
        boolean run = false;
        if (caseConfig.isInitialized()) {
            if (caseConfig.isAlreadySaved()) {
                if (caseConfig.isModified()) {
                    int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "This action may change the target status.\nSave changes before proceeding?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                    if (option == JOptionPane.OK_OPTION) {
                        if (onSave()) {
                            run = true;
                        }
                    } else if (option == JOptionPane.NO_OPTION) {
                        run = true;
                    }
                } else {
                    run = true;
                }
            } else {
                int option = JOptionPane.showInternalConfirmDialog(this.getContentPane(), "This action may change the target status.\nSave case before proceeding?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, imageBroker.getImageIcon32x32(AvailableImages.QUESTION));
                if (option == JOptionPane.OK_OPTION) {
                    if (onSaveAs()) {
                        run = true;
                    }
                } else if (option == JOptionPane.NO_OPTION) {
                    run = true;
                }
            }
        } else {
            run = true;
        }
        if (run) {
            //BEFORE
            randomButton.setEnabled(false);
            newButton.setEnabled(false);
            loadButton.setEnabled(false);
            if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
            saveAsButton.setEnabled(false);
            runButton.setEnabled(false);
            generateConstraintsButton.setEnabled(false);
            menuCaseItemNew.setEnabled(false);
            menuCaseItemLoad.setEnabled(false);
            if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
                menuCaseItemSave.setEnabled(true);
            } else {
                menuCaseItemSave.setEnabled(false);
            }
            menuCaseItemSaveAs.setEnabled(false);
            menuCaseItemRunFLECO.setEnabled(false);
            menuCaseItemExit.setEnabled(false);
            menuAbout.setEnabled(false);
            menuAboutItemAbout.setEnabled(false);
            menuAboutItemLicense.setEnabled(false);
            table.setEnabled(false);
            menuBar.setEnabled(false);
            menuCase.setEnabled(false);
            menuAbout.setEnabled(false);
            menuBar.setEnabled(false);
            messageSpace.setText("FLECO is running...");
            progressBar.setValue(0);
            tableModel.removeTargetStatus();
            //MAIN
            int initialPopulation = 30;
            int maxSeconds = 30;
            float crossoverProbability = 0.90f;
            caseConfig.setFleco(new FLECO(initialPopulation, maxSeconds, crossoverProbability, tableModel.getImplementationGroup(), tableModel.getInitialStatus(), tableModel.getStrategicConstraints()));
            caseConfig.getFleco().setProgressEventListener(progressBar);
            FLECOSwingWorker flecoSwingWorker = new FLECOSwingWorker(caseConfig.getFleco(), gui);
            flecoSwingWorker.execute();
            //afterOnRunFLECO(); <-- this is called automátically by FLECOSwingWorker.
        }
    }

    /**
     * This method is called automatically when a FLECO execution finishes,
     * independently of its result. It updates the case and the GUI as needed.
     *
     * @author Manuel Domínguez-Dorado
     */
    @Override
    public void afterOnRunFLECO() {
        randomButton.setEnabled(true);
        newButton.setEnabled(true);
        loadButton.setEnabled(true);
        if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
            saveButton.setEnabled(true);
        } else {
            saveButton.setEnabled(false);
        }
        saveAsButton.setEnabled(true);
        runButton.setEnabled(true);
        generateConstraintsButton.setEnabled(true);
        menuCaseItemNew.setEnabled(true);
        menuCaseItemLoad.setEnabled(true);
        if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
            menuCaseItemSave.setEnabled(true);
        } else {
            menuCaseItemSave.setEnabled(false);
        }
        menuCaseItemSaveAs.setEnabled(true);
        menuCaseItemRunFLECO.setEnabled(true);
        menuCaseItemExit.setEnabled(true);
        menuAbout.setEnabled(true);
        menuAboutItemAbout.setEnabled(true);
        menuAboutItemLicense.setEnabled(true);
        table.setEnabled(true);
        progressBar.setValue(100);
        menuBar.setEnabled(true);
        menuCase.setEnabled(true);
        menuAbout.setEnabled(true);
        if (caseConfig.getFleco().hasConverged()) {
            messageSpace.setText("FLECO execution has finished. A compliant combination was found!");
        } else {
            messageSpace.setText("FLECO execution has finished. No compliant combination was found! Could be the constraints too restrictive?");
        }
        caseConfig.setTargetStatus(caseConfig.getFleco().getBestChromosome());
        tableModel.setTargetStatus(caseConfig.getTargetStatus());
    }

    /**
     * This method is called automatically when a modification is done in the
     * GUI's table. It prepares theFLECO Studio to know there are changes that
     * has to be saved (or specifically discarded) in a future moment.
     *
     * @author Manuel Domínguez-Dorado
     */
    @Override
    public void onFLECOTableModelChanged() {
        caseConfig.setModified(true);
        if (caseConfig.isAlreadySaved() && caseConfig.isModified()) {
            saveButton.setEnabled(true);
            menuCaseItemSave.setEnabled(true);
            setTitle("FLECO Studio - " + caseConfig.getFileName() + "*");
        } else {
            saveButton.setEnabled(false);
            menuCaseItemSave.setEnabled(false);
        }
    }

}
