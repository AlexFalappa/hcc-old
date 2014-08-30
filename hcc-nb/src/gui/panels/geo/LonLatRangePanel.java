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
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwindx.examples.util.SectorSelector;
import gui.panels.GeoAreaPanel;
import gui.wwind.FootprintsLayer;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JSpinner;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class LonLatRangePanel extends javax.swing.JPanel {

    private boolean selecting = false;
    private FootprintsLayer footprints;
    private SectorSelector selector;

    public LonLatRangePanel() {
        initComponents();
        // set spinners locale to english (to get point as decimal separator)
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        ((JSpinner.NumberEditor) spMaxLat.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spMaxLon.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spMinLat.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spMinLon.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
    }

    @Override
    public void setEnabled(boolean enabled) {
        setSpinnersEnabled(enabled);
        bGraphSel.setEnabled(enabled);
//        bDraw.setEnabled(enabled);
        lUom1.setEnabled(enabled);
        lUom2.setEnabled(enabled);
        lUom3.setEnabled(enabled);
        lUom4.setEnabled(enabled);
    }

    public void linkTo(WorldWindow wwd, FootprintsLayer footprints) {
        this.footprints = footprints;
        selector = new SectorSelector(wwd);
        selector.setBorderColor(GeoAreaPanel.COL_BOUNDARY);
        selector.setInteriorColor(GeoAreaPanel.COL_FILL);
        selector.addPropertyChangeListener(SectorSelector.SECTOR_PROPERTY, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                Sector sec = (Sector) evt.getNewValue();
                EventQueue.invokeLater(new LonLatRangePanel.RangeUpdater(sec));
            }
        });
    }

    public String getLowerCorner() {
        return spMinLat.getValue().toString().concat(" ").concat(spMinLon.getValue().toString());
    }

    public String getUpperCorner() {
        return spMaxLat.getValue().toString().concat(" ").concat(spMaxLon.getValue().toString());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lMinLon = new javax.swing.JLabel();
        lMaxLon = new javax.swing.JLabel();
        lMinLat = new javax.swing.JLabel();
        lMaxLat = new javax.swing.JLabel();
        bDraw = new javax.swing.JButton();
        bGraphSel = new javax.swing.JButton();
        spMinLon = new javax.swing.JSpinner();
        spMaxLon = new javax.swing.JSpinner();
        spMinLat = new javax.swing.JSpinner();
        spMaxLat = new javax.swing.JSpinner();
        lUom1 = new javax.swing.JLabel();
        lUom2 = new javax.swing.JLabel();
        lUom3 = new javax.swing.JLabel();
        lUom4 = new javax.swing.JLabel();

        lMinLon.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lMinLon.setText("Min Lon");

        lMaxLon.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lMaxLon.setText("Max Lon");

        lMinLat.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lMinLat.setText("Min Lat");

        lMaxLat.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lMaxLat.setText("Max Lat");

        bDraw.setText("Draw");
        bDraw.setEnabled(false);
        bDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDrawActionPerformed(evt);
            }
        });

        bGraphSel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_099_vector_path_all.png"))); // NOI18N
        bGraphSel.setText("Graphical Selection");
        bGraphSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGraphSelActionPerformed(evt);
            }
        });

        spMinLon.setModel(new javax.swing.SpinnerNumberModel(0.0d, -180.0d, 180.0d, 1.0d));

        spMaxLon.setModel(new javax.swing.SpinnerNumberModel(0.0d, -180.0d, 180.0d, 1.0d));

        spMinLat.setModel(new javax.swing.SpinnerNumberModel(0.0d, -90.0d, 90.0d, 1.0d));

        spMaxLat.setModel(new javax.swing.SpinnerNumberModel(0.0d, -90.0d, 90.0d, 1.0d));

        lUom1.setText("deg.");

        lUom2.setText("deg.");

        lUom3.setText("deg.");

        lUom4.setText("deg.");

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
                            .addComponent(lMaxLon)
                            .addComponent(lMinLat)
                            .addComponent(lMaxLat)
                            .addComponent(lMinLon))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spMinLon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spMaxLat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spMinLat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spMaxLon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lUom2)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lMaxLat, lMaxLon, lMinLat, lMinLon});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lMinLon)
                    .addComponent(spMinLon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lMaxLon)
                    .addComponent(spMaxLon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lMinLat)
                    .addComponent(spMinLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lMaxLat)
                    .addComponent(spMaxLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom4))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bGraphSel)
                    .addComponent(bDraw))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bGraphSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGraphSelActionPerformed
        if (selector == null) {
            return;
        }
        setSpinnersEnabled(selecting);
        footprints.setHighlightingEnabled(selecting);
        if (selecting) {
            bGraphSel.setText("Graphical selection");
            Sector s = selector.getSector();
            updateRanges(s);
            //TODO implement sector AOI
            //            App.frame.aois.setSurfSect(s);
            selector.disable();
            selecting = false;
        } else {
            bGraphSel.setText("Accept");
            selector.enable();
            selecting = true;
        }
    }//GEN-LAST:event_bGraphSelActionPerformed

    private void updateRanges(Sector s) {
        if (s != null) {
            spMinLon.setValue(s.getMinLongitude().degrees);
            spMaxLon.setValue(s.getMaxLongitude().degrees);
            spMinLat.setValue(s.getMinLatitude().degrees);
            spMaxLat.setValue(s.getMaxLatitude().degrees);
        }
    }

    private void setSpinnersEnabled(boolean enabled) {
        lMaxLat.setEnabled(enabled);
        spMaxLat.setEnabled(enabled);
        lMaxLon.setEnabled(enabled);
        spMaxLon.setEnabled(enabled);
        lMinLat.setEnabled(enabled);
        spMinLat.setEnabled(enabled);
        lMinLon.setEnabled(enabled);
        spMinLon.setEnabled(enabled);
    }

    private void bDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDrawActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bDrawActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDraw;
    private javax.swing.JButton bGraphSel;
    private javax.swing.JLabel lMaxLat;
    private javax.swing.JLabel lMaxLon;
    private javax.swing.JLabel lMinLat;
    private javax.swing.JLabel lMinLon;
    private javax.swing.JLabel lUom1;
    private javax.swing.JLabel lUom2;
    private javax.swing.JLabel lUom3;
    private javax.swing.JLabel lUom4;
    private javax.swing.JSpinner spMaxLat;
    private javax.swing.JSpinner spMaxLon;
    private javax.swing.JSpinner spMinLat;
    private javax.swing.JSpinner spMinLon;
    // End of variables declaration//GEN-END:variables

    public double getLatMin() {
        return (double) spMinLat.getValue();
    }

    public double getLatMax() {
        return (double) spMaxLat.getValue();
    }

    public double getLonMin() {
        return (double) spMinLon.getValue();
    }

    public double getLonMax() {
        return (double) spMaxLon.getValue();
    }

    class RangeUpdater implements Runnable {

        private Sector sec;

        public RangeUpdater(Sector sec) {
            this.sec = sec;
        }

        @Override
        public void run() {
            updateRanges(sec);
        }
    }
}
