#!/bin/bash

# Available JDK Look and Feel class names (leave empty for default system look and feel)
#   javax.swing.plaf.metal.MetalLookAndFeel
#   javax.swing.plaf.nimbus.NimbusLookAndFeel
#   com.sun.java.swing.plaf.gtk.GTKLookAndFeel

# Available external Look and Feel class names
#   com.jgoodies.looks.plastic.PlasticLookAndFeel
#   com.jgoodies.looks.plastic.Plastic3DLookAndFeel
#   com.jgoodies.looks.plastic.PlasticXPLookAndFeel
#   com.jgoodies.looks.windows.WindowsLookAndFeel

#launch jvm with Java2D OpenGL pipeline
java -Xms128m -Dsun.java2d.opengl=true -Dswing.defaultlaf=com.jgoodies.looks.plastic.PlasticXPLookAndFeel -jar hcc.jar
