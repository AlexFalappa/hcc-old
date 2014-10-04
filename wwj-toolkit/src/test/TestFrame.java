/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import gov.nasa.worldwind.geom.LatLon;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.falappa.wwind.layers.MultiPolygonShapesLayer;
import net.falappa.wwind.layers.NoSuchShapeException;
import net.falappa.wwind.widgets.WWindPanel;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class TestFrame extends javax.swing.JFrame {

    /**
     * Creates new form TestFrame
     */
    public TestFrame() {
        initComponents();
        mpsl = new MultiPolygonShapesLayer("mp");
        wWindPanel1.addSurfShapeLayer(mpsl);
    }
    private final MultiPolygonShapesLayer mpsl;

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitter = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        bAdd = new javax.swing.JButton();
        bAdd2 = new javax.swing.JButton();
        bChgColor = new javax.swing.JButton();
        bResetColor = new javax.swing.JButton();
        bFlyToPrimo = new javax.swing.JButton();
        bFlyToPrimo1 = new javax.swing.JButton();
        surfShapeLayersVisibilityPanel1 = new net.falappa.wwind.widgets.SurfShapeLayersVisibilityPanel();
        wWindPanel1 = new net.falappa.wwind.widgets.WWindPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Test");

        splitter.setResizeWeight(0.3);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        bAdd.setText("Add multipolygon");
        bAdd.setAlignmentX(0.5F);
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });
        jPanel1.add(bAdd);

        bAdd2.setText("Add anothrt multipolygon");
        bAdd2.setAlignmentX(0.5F);
        bAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdd2ActionPerformed(evt);
            }
        });
        jPanel1.add(bAdd2);

        bChgColor.setText("Change color");
        bChgColor.setAlignmentX(0.5F);
        bChgColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bChgColorActionPerformed(evt);
            }
        });
        jPanel1.add(bChgColor);

        bResetColor.setText("Reset color");
        bResetColor.setAlignmentX(0.5F);
        bResetColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetColorActionPerformed(evt);
            }
        });
        jPanel1.add(bResetColor);

        bFlyToPrimo.setText("Fly to first");
        bFlyToPrimo.setAlignmentX(0.5F);
        bFlyToPrimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFlyToPrimoActionPerformed(evt);
            }
        });
        jPanel1.add(bFlyToPrimo);

        bFlyToPrimo1.setText("Fly-highlight first");
        bFlyToPrimo1.setAlignmentX(0.5F);
        bFlyToPrimo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFlyToPrimo1ActionPerformed(evt);
            }
        });
        jPanel1.add(bFlyToPrimo1);
        jPanel1.add(surfShapeLayersVisibilityPanel1);

        splitter.setLeftComponent(jPanel1);
        splitter.setRightComponent(wWindPanel1);

        getContentPane().add(splitter, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 810, 630);
    }// </editor-fold>//GEN-END:initComponents

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        ArrayList<LatLon> p1 = new ArrayList<>();
        p1.add(LatLon.fromDegrees(-30, 0));
        p1.add(LatLon.fromDegrees(-30, 10));
        p1.add(LatLon.fromDegrees(4, 4));
        ArrayList<LatLon> p2 = new ArrayList<>();
        p2.add(LatLon.fromDegrees(10, 0));
        p2.add(LatLon.fromDegrees(10, 10));
        p2.add(LatLon.fromDegrees(4, 6));
        List<List<LatLon>> mp = new ArrayList<>();
        mp.add(p2);
        mp.add(p1);
        mpsl.addMultiPoly("primo", mp);
        wWindPanel1.redraw();
    }//GEN-LAST:event_bAddActionPerformed

    private void bChgColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bChgColorActionPerformed
        try {
            mpsl.setSurfShapeColor("primo", Color.red, 0.5);
            wWindPanel1.redraw();
        } catch (NoSuchShapeException ex) {
        }
    }//GEN-LAST:event_bChgColorActionPerformed

    private void bAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdd2ActionPerformed
        ArrayList<LatLon> p1 = new ArrayList<>();
        p1.add(LatLon.fromDegrees(0, 20));
        p1.add(LatLon.fromDegrees(0, 25));
        p1.add(LatLon.fromDegrees(5, 25));
        p1.add(LatLon.fromDegrees(5, 20));
        ArrayList<LatLon> p2 = new ArrayList<>();
        p2.add(LatLon.fromDegrees(10, 20));
        p2.add(LatLon.fromDegrees(10, 25));
        p2.add(LatLon.fromDegrees(15, 25));
        p2.add(LatLon.fromDegrees(15, 20));
        List<List<LatLon>> mp = new ArrayList<>();
        mp.add(p2);
        mp.add(p1);
        mpsl.addMultiPoly("secondo", mp);
        wWindPanel1.redraw();
    }//GEN-LAST:event_bAdd2ActionPerformed

    private void bResetColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetColorActionPerformed
        try {
            mpsl.resetSurfShapeColor("primo");
            wWindPanel1.redraw();
        } catch (NoSuchShapeException ex) {
        }
    }//GEN-LAST:event_bResetColorActionPerformed

    private void bFlyToPrimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFlyToPrimoActionPerformed
        try {
            mpsl.flyToShape("primo");
        } catch (NoSuchShapeException ex) {
        }
    }//GEN-LAST:event_bFlyToPrimoActionPerformed

    private void bFlyToPrimo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFlyToPrimo1ActionPerformed
        try {
            mpsl.flyToHiglhlightShape("primo");
        } catch (NoSuchShapeException ex) {
        }
    }//GEN-LAST:event_bFlyToPrimo1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        WWindPanel.setupPropsForWWind();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bAdd2;
    private javax.swing.JButton bChgColor;
    private javax.swing.JButton bFlyToPrimo;
    private javax.swing.JButton bFlyToPrimo1;
    private javax.swing.JButton bResetColor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane splitter;
    private net.falappa.wwind.widgets.SurfShapeLayersVisibilityPanel surfShapeLayersVisibilityPanel1;
    private net.falappa.wwind.widgets.WWindPanel wWindPanel1;
    // End of variables declaration//GEN-END:variables
}
