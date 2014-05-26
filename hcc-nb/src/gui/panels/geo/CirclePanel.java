/*
 * Copyright 2014 Alessandro Falappa <alex.falappa@gmail.com>.
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
package gui.panels.geo;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.util.measure.MeasureTool;
import gui.wwind.AOILayer;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JSpinner;
import main.App;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class CirclePanel extends javax.swing.JPanel {

    private WorldWindow wwd;
    private AOILayer aoi;
    private MeasureTool mt;
    private boolean mtVisible = false;

    public CirclePanel() {
        initComponents();
        // set spinners locale to english (to get point as decimal separator)
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        ((JSpinner.NumberEditor) spCenterLat.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spCenterLon.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spRadius.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
    }

    @Override
    public void setEnabled(boolean enabled) {
        lCenterLat.setEnabled(enabled);
        spCenterLat.setEnabled(enabled);
        lCenterLon.setEnabled(enabled);
        spCenterLon.setEnabled(enabled);
        lRadius.setEnabled(enabled);
        spRadius.setEnabled(enabled);
        lUom1.setEnabled(enabled);
        lUom2.setEnabled(enabled);
        lUom3.setEnabled(enabled);
    }

    public void linkTo(MeasureTool mTool, AOILayer aoi) {
        this.wwd = mTool.getWwd();
        this.aoi = aoi;
        this.mt = mTool;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lCenterLat = new javax.swing.JLabel();
        lCenterLon = new javax.swing.JLabel();
        lRadius = new javax.swing.JLabel();
        spCenterLat = new javax.swing.JSpinner();
        spCenterLon = new javax.swing.JSpinner();
        spRadius = new javax.swing.JSpinner();
        bGraphSel = new javax.swing.JButton();
        bDraw = new javax.swing.JButton();
        lUom1 = new javax.swing.JLabel();
        lUom2 = new javax.swing.JLabel();
        lUom3 = new javax.swing.JLabel();

        lCenterLat.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lCenterLat.setText("Center Lat");

        lCenterLon.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lCenterLon.setText("Center Lon");

        lRadius.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lRadius.setText("Radius");

        spCenterLat.setModel(new javax.swing.SpinnerNumberModel(0.0d, -90.0d, 90.0d, 1.0d));

        spCenterLon.setModel(new javax.swing.SpinnerNumberModel(0.0d, -180.0d, 180.0d, 1.0d));

        spRadius.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(500.0d)));

        bGraphSel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_095_vector_path_circle.png"))); // NOI18N
        bGraphSel.setText("Graphical Selection");
        bGraphSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGraphSelActionPerformed(evt);
            }
        });

        bDraw.setText("Draw");
        bDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDrawActionPerformed(evt);
            }
        });

        lUom1.setText("mt.");

        lUom2.setText("deg.");

        lUom3.setText("deg.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lCenterLat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spCenterLat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lCenterLon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spCenterLon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lRadius)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bGraphSel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bDraw)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lCenterLat, lCenterLon, lRadius});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCenterLat)
                    .addComponent(spCenterLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCenterLon)
                    .addComponent(spCenterLon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lRadius)
                    .addComponent(spRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bGraphSel)
                    .addComponent(bDraw))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDrawActionPerformed
        LatLon center = LatLon.fromDegrees((double) spCenterLat.getValue(), (double) spCenterLon.getValue());
        App.frame.aois.setSurfCircle(center, (double) spRadius.getValue());
        App.frame.wwindPane.redraw();
    }//GEN-LAST:event_bDrawActionPerformed

    private void bGraphSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGraphSelActionPerformed
        spCenterLat.setEnabled(mtVisible);
        spCenterLon.setEnabled(mtVisible);
        spRadius.setEnabled(mtVisible);
        if (mtVisible) {
            mtVisible = false;
            bGraphSel.setText("Graphical Selection");
            finishGraphSel();
        } else {
            mtVisible = true;
            mt.setMeasureShapeType(MeasureTool.SHAPE_CIRCLE);
            mt.setArmed(true);
            bGraphSel.setText("Close & Accept");
        }
    }//GEN-LAST:event_bGraphSelActionPerformed

    private void finishGraphSel() {
        Position center = mt.getCenterPosition();
        double radius = mt.getWidth() / 2.0;
        if (center != null) {
            // unarm and clear line
            mt.setArmed(false);
            mt.clear();
            // set spinners
            spCenterLat.setValue(center.getLatitude().getDegrees());
            spCenterLon.setValue(center.getLongitude().getDegrees());
            spRadius.setValue(radius);
            // add circular area of interest
            aoi.setSurfCircle(center, radius);
            wwd.redraw();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDraw;
    private javax.swing.JButton bGraphSel;
    private javax.swing.JLabel lCenterLat;
    private javax.swing.JLabel lCenterLon;
    private javax.swing.JLabel lRadius;
    private javax.swing.JLabel lUom1;
    private javax.swing.JLabel lUom2;
    private javax.swing.JLabel lUom3;
    private javax.swing.JSpinner spCenterLat;
    private javax.swing.JSpinner spCenterLon;
    private javax.swing.JSpinner spRadius;
    // End of variables declaration//GEN-END:variables

    public double getLat() {
        return (double) spCenterLat.getValue();
    }

    public double getLon() {
        return (double) spCenterLon.getValue();
    }

    public double getRadius() {
        return (double) spRadius.getValue();
    }
}
