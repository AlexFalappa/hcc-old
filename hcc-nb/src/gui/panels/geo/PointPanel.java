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
import gov.nasa.worldwind.geom.Position;
import gui.wwind.FootprintsLayer;
import gui.wwind.MOILayer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JSpinner;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class PointPanel extends javax.swing.JPanel {

    private WorldWindow wwd;
    private MOILayer moi;
    private FootprintsLayer footprints;
    private boolean selecting = false;
    private final MouseAdapter ma = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent mev) {
            if (mev.getButton() == MouseEvent.BUTTON1) {
                Position curPos = wwd.getCurrentPosition();
                spLat.setValue(curPos.getLatitude().getDegrees());
                spLon.setValue(curPos.getLongitude().getDegrees());
                moi.setMarker(curPos);
                wwd.redraw();
                mev.consume();
            }
        }

    };

    public PointPanel() {
        initComponents();
        // set spinners locale to english (to get point as decimal separator)
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        ((JSpinner.NumberEditor) spLat.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spLon.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
    }

    @Override
    public void setEnabled(boolean enabled) {
        lLat.setEnabled(enabled);
        spLat.setEnabled(enabled);
        lLon.setEnabled(enabled);
        spLon.setEnabled(enabled);
        bGraphSel.setEnabled(enabled);
//        bDraw.setEnabled(enabled);
        lUom1.setEnabled(enabled);
        lUom2.setEnabled(enabled);
    }

    public void linkTo(WorldWindow wwd, MOILayer moi, FootprintsLayer footprints) {
        this.footprints = footprints;
        this.wwd = wwd;
        this.moi = moi;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lLat = new javax.swing.JLabel();
        lLon = new javax.swing.JLabel();
        bGraphSel = new javax.swing.JButton();
        bDraw = new javax.swing.JButton();
        lUom1 = new javax.swing.JLabel();
        lUom2 = new javax.swing.JLabel();
        spLat = new javax.swing.JSpinner();
        spLon = new javax.swing.JSpinner();

        lLat.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lLat.setText("Lat");

        lLon.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lLon.setText("Lon");

        bGraphSel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_185_screenshot.png"))); // NOI18N
        bGraphSel.setText("Graphical Selection");
        bGraphSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGraphSelActionPerformed(evt);
            }
        });

        bDraw.setText("Draw");
        bDraw.setEnabled(false);
        bDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDrawActionPerformed(evt);
            }
        });

        lUom1.setText("deg.");

        lUom2.setText("deg.");

        spLat.setModel(new javax.swing.SpinnerNumberModel(0.0d, -90.0d, 90.0d, 1.0d));

        spLon.setModel(new javax.swing.SpinnerNumberModel(0.0d, -180.0d, 180.0d, 1.0d));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bGraphSel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bDraw))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lLon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spLon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lLat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spLat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lLat, lLon});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lLat)
                    .addComponent(lUom1)
                    .addComponent(spLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lLon)
                    .addComponent(lUom2)
                    .addComponent(spLon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bGraphSel)
                    .addComponent(bDraw))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bGraphSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGraphSelActionPerformed
        spLat.setEnabled(selecting);
        spLon.setEnabled(selecting);
        footprints.setHighlightingEnabled(selecting);
        if (selecting) {
            this.wwd.getInputHandler().removeMouseListener(ma);
            bGraphSel.setText("Graphical Selection");
            selecting = false;
        } else {
            this.wwd.getInputHandler().addMouseListener(ma);
            bGraphSel.setText("Accept");
            selecting = true;
        }
    }//GEN-LAST:event_bGraphSelActionPerformed

    private void bDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDrawActionPerformed

    }//GEN-LAST:event_bDrawActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDraw;
    private javax.swing.JButton bGraphSel;
    private javax.swing.JLabel lLat;
    private javax.swing.JLabel lLon;
    private javax.swing.JLabel lUom1;
    private javax.swing.JLabel lUom2;
    private javax.swing.JSpinner spLat;
    private javax.swing.JSpinner spLon;
    // End of variables declaration//GEN-END:variables

    public double getLat() {
        return (double) spLat.getValue();
    }

    public double getLon() {
        return (double) spLon.getValue();
    }
}
