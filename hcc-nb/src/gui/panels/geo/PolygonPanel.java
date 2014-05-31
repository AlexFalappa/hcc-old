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
import gov.nasa.worldwind.util.measure.MeasureTool;
import gui.wwind.AOILayer;
import gui.wwind.FootprintsLayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class PolygonPanel extends javax.swing.JPanel {

    private WorldWindow wwd;
    private AOILayer aoi;
    private FootprintsLayer footprints;
    private MeasureTool mt;
    private boolean mtVisible = false;
    private final StringBuilder posList = new StringBuilder(200);

    public PolygonPanel() {
        initComponents();
    }

    @Override
    public void setEnabled(boolean enabled) {
        lblCoords.setEnabled(enabled);
        taCoords.setEnabled(enabled);
        bGraphSel.setEnabled(enabled);
//        bDraw.setEnabled(enabled);
    }

    public void linkTo(MeasureTool mTool, AOILayer aoi, FootprintsLayer footprints) {
        this.wwd = mTool.getWwd();
        this.aoi = aoi;
        this.footprints = footprints;
        this.mt = mTool;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCoords = new javax.swing.JLabel();
        bDraw = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCoords = new javax.swing.JTextArea();
        bGraphSel = new javax.swing.JButton();

        lblCoords.setText("Coordinates (deg.)");

        bDraw.setText("Draw");
        bDraw.setEnabled(false);
        bDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDrawActionPerformed(evt);
            }
        });

        taCoords.setColumns(10);
        taCoords.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        taCoords.setRows(3);
        jScrollPane2.setViewportView(taCoords);

        bGraphSel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_096_vector_path_polygon.png"))); // NOI18N
        bGraphSel.setText("Graphical Selection");
        bGraphSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGraphSelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCoords)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bGraphSel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(bDraw)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCoords)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bGraphSel)
                    .addComponent(bDraw))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDrawActionPerformed

    }//GEN-LAST:event_bDrawActionPerformed

    private void bGraphSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGraphSelActionPerformed
        if (mtVisible) {
            if (mt.isArmed()) {
                // edit
                mtVisible = true;
                mt.setArmed(false);
                bGraphSel.setText("Accept Selection");
            } else {
                //end
                mtVisible = false;
                mt.setArmed(false);
                finishGraphSel();
                taCoords.setEnabled(true);
                footprints.setHighlightingEnabled(true);
                bGraphSel.setText("Graphical Selection");
            }
        } else {
            if (!mt.isArmed()) {
                //start
                mt.setMeasureShapeType(MeasureTool.SHAPE_POLYGON);
                mt.setArmed(true);
                mtVisible = true;
                taCoords.setEnabled(false);
                footprints.setHighlightingEnabled(false);
                bGraphSel.setText("Edit Selection");
            }
        }
    }//GEN-LAST:event_bGraphSelActionPerformed

    private void finishGraphSel() {
        // make a copy of linebuilder pos
        ArrayList<Position> perimeter = new ArrayList<>();
        for (Position pos : mt.getPositions()) {
            perimeter.add(pos);
        }
        Iterator<Position> it = perimeter.iterator();
        if (!perimeter.isEmpty()) {
            // display coordinates
            posList.setLength(0);
            taCoords.setText("");
            while (it.hasNext()) {
                Position pos = it.next();
                final double lat = pos.getLatitude().getDegrees();
                final double lon = pos.getLongitude().getDegrees();
                taCoords.append(String.format(Locale.ENGLISH, "%.6f %.6f\n", lat, lon));
                posList.append(lat).append(' ').append(lon).append(' ');
            }
            taCoords.setCaretPosition(0);
            // add polygonal area of interest
            mt.clear();
            aoi.setSurfPoly(perimeter);
            wwd.redraw();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDraw;
    private javax.swing.JButton bGraphSel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCoords;
    private javax.swing.JTextArea taCoords;
    // End of variables declaration//GEN-END:variables

    public String getCoords() {
        return posList.toString();
    }
}
