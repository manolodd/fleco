/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.manolodominguez.fleco.main;

import com.manolodominguez.fleco.gui.MainWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author manolodd
 */
public class FlecoGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            boolean nimbusSet = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusSet = true;
                    break;
                }
            }
            if (!nimbusSet) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // FIX: I189N required
            System.out.println("An error happened when starting OpenSimMPLS. Cannot set LaF.");
        }
        MainWindow flecogui = new MainWindow();
        SwingUtilities.invokeLater(() -> {
            flecogui.setVisible(true);
        });
    }

}
