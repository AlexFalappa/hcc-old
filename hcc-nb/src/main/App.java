/*
 * Copyright 2014 Alessandro Falappa <alex.falappa@gmail.com> <alex.falappa@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package main;

import gov.nasa.worldwind.Configuration;
import gui.MainWindow;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.falappa.utils.LogUtils;

/**
 * Application entry point.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class App {

    public static final String PREF_ROOT = "alexfalappa.hcc-nb";
    public static final String PREF_LAFCLASS = "LAF-classname";
    public static MainWindow frame;

    static {
        System.setProperty("gov.nasa.worldwind.app.config.document", "main/hcc.worldwind.xml");
        if (Configuration.isMacOS()) {
            // Use MacOsX global menu bar and set some propereties
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "HMA Catalogue Client");
            System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
        } else if (Configuration.isWindowsOS()) {
            // prevents flashing during window resizing
            System.setProperty("sun.awt.noerasebackground", "true");
            System.setProperty("sun.java2d.noddraw", "true");
        } else if (Configuration.isLinuxOS()) {
            System.setProperty("sun.awt.noerasebackground", "true");
        }
    }

    /**
     * Main method.
     * <p>
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        LogUtils.silenceJUL();
        // Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setPrefLAF();
                frame = new MainWindow();
                frame.setSize(1200, 800);
                frame.setVisible(true);
            }
        });
    }

    private static void setPrefLAF() {
        // get preferences API node
        Preferences prefs = Preferences.userRoot().node(PREF_ROOT);
        try {
            UIManager.setLookAndFeel(prefs.get(PREF_LAFCLASS, UIManager.getSystemLookAndFeelClassName()));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            //ignored will go with default look and feel
        }
    }
}
