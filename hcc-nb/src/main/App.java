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

import gui.MainWindow;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.falappa.utils.LogUtils;
import net.falappa.wwind.widgets.WWindPanel;

/**
 * Application entry point.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class App {

    public static final String PREF_ROOT = "alexfalappa.hcc-nb";
    public static final String PREF_LAFCLASS = "LAF-classname";
    public static final String PREF_DUMP_REQS_FLAG = "dump-requests";
    public static final String PREF_DUMP_REQS_DIR = "dump-requests-dir";
    public static final String PREF_DUMP_RESPS_FLAG = "dump-responses";
    public static final String PREF_DUMP_RESPS_DIR = "dump-responses-dir";
    public static MainWindow frame;

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        LogUtils.silenceJUL();
        WWindPanel.setupPropsForWWind();
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
