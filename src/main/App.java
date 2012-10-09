/**
 * 
 */
package main;

import gov.nasa.worldwind.Configuration;
import gui.MainFrame;
import java.awt.EventQueue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

/**
 * @author sasha
 * 
 */
public class App {
  public static final String NAME = "HCC";
  public static final String VERSION_MAJOR = "0";
  public static final String VERSION_MINOR = "6";
  public static final String VERSION = VERSION_MAJOR + "." + VERSION_MINOR;
  public static final String FRAME_TITLE = "HMA Catalogue Client";
  public static final String PREFS_ROOT = "alexfalappa.hcc";
  public static final String PREFS_KEY_SERVICES = "catalogues";
  public static final Logger logger = Logger.getLogger("hcc.main");
  public static MainFrame frame = null;
  static Set<String> servicesUrls = new HashSet<>();

  static {
    System.setProperty("gov.nasa.worldwind.app.config.document", "conf/hcc.worldwind.xml");
    if (Configuration.isMacOS()) {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", App.FRAME_TITLE);
      System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
      // System.setProperty("apple.awt.brushMetalLook", "true");// provokes
      // flashing
    } else if (Configuration.isWindowsOS()) {
      // prevents flashing during window resizing
      System.setProperty("sun.awt.noerasebackground", "true");
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // configure logging
    configureLoggers();
    logger.info(NAME + " version " + VERSION + " starting");
    // show the main application window
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          // set the jgoodies Looks look and feel
          UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
          // create and pack main window
          logger.info("Creating and showing ui");
          frame = new MainFrame();
          frame.pack();
          // center on screen and show
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        } catch (Exception e) {
          logger.severe("Could not create main window!");
          logger.throwing(App.class.getName(), "main", e);
        }
      }
    });
  }

  @SuppressWarnings("unused")
  private void setNimbusLookAndFeel() {
    // try to choose Nimbus L&F
    try {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (Exception e) {
      // Nimbus not available, go with the system L&F
    }
  }

  private static void configureLoggers() {
    // configure java.util.logging
    logger.setLevel(Level.FINEST);
    MainFrame.logger.setLevel(Level.FINEST);
    Logger.getLogger("").getHandlers()[0].setLevel(Level.FINEST);
    Logger.getLogger("").getHandlers()[0].setFormatter(new OneLineFormatter());
    // silence log4j (used in the axis2 web service client)
    System.setProperty("log4j.defaultInitOverride", "true");
    org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
  }

  public static void currServiceInPrefs() {
    App.servicesUrls.add(frame.getWebServEdp());
    StringBuilder sb = new StringBuilder();
    Iterator<String> it = App.servicesUrls.iterator();
    sb.append(it.next());
    while (it.hasNext())
      sb.append(',').append(it.next());
    Preferences prefs = Preferences.userRoot().node(PREFS_ROOT);
    prefs.put(PREFS_KEY_SERVICES, sb.toString());
    logger.info("Adding to known catalogues: " + frame.getWebServEdp());
  }

}
