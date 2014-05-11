rem Available JDK Look and Feel class names (leave empty for default system look and feel)
rem   javax.swing.plaf.metal.MetalLookAndFeel
rem   javax.swing.plaf.nimbus.NimbusLookAndFeel
rem   com.sun.java.swing.plaf.windows.WindowsLookAndFeel

rem Available external Look and Feel class names
rem   com.jgoodies.looks.plastic.PlasticLookAndFeel
rem   com.jgoodies.looks.plastic.Plastic3DLookAndFeel
rem   com.jgoodies.looks.plastic.PlasticXPLookAndFeel
rem   com.jgoodies.looks.windows.WindowsLookAndFeel

rem launch jvm with natives in windows folder
java -Xms128m -Dswing.defaultlaf=com.jgoodies.looks.plastic.PlasticXPLookAndFeel -Djava.library.path=lib\windows -jar hcc.jar
