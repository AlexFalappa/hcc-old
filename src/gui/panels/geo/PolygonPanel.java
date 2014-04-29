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

import java.awt.Component;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.DefaultListModel;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwindx.examples.LineBuilder;
import gui.wwind.AOILayer;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class PolygonPanel extends javax.swing.JPanel {

    private LineBuilder lineBuilder;
    private WorldWindow wwd;
    private AOILayer aoi;
    private final DefaultListModel<String> dlm = new DefaultListModel<>();
    private final StringBuilder posList = new StringBuilder(200);

    public PolygonPanel() {
        initComponents();
    }

    @Override
    public void setEnabled(boolean enabled) {
        lblCoords.setEnabled(enabled);
        taCoords.setEnabled(enabled);
        bGraphSel.setEnabled(enabled);
        bDraw.setEnabled(enabled);
    }

    public void linkTo(WorldWindow wwd, AOILayer aoi) {
        this.wwd = wwd;
        this.aoi = aoi;
        lineBuilder = new LineBuilder(wwd, aoi, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCoords = new javax.swing.JLabel();
        bGraphSel = new javax.swing.JButton();
        bDraw = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCoords = new javax.swing.JTextArea();

        lblCoords.setText("Coordinates (deg.)");

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

        taCoords.setColumns(10);
        taCoords.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        taCoords.setRows(3);
        jScrollPane2.setViewportView(taCoords);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCoords)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bGraphSel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bGraphSel)
                    .addComponent(bDraw))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bGraphSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGraphSelActionPerformed
        if (lineBuilder.isArmed()) {
            lineBuilder.setArmed(false);
            bGraphSel.setText("Graphical Selection");
            ((Component) wwd).setCursor(Cursor.getDefaultCursor());
            finishGraphSel();
        } else {
            lineBuilder.setArmed(true);
            bGraphSel.setText("Close & Accept");
            ((Component) wwd).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }//GEN-LAST:event_bGraphSelActionPerformed

    private void bDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDrawActionPerformed

    }//GEN-LAST:event_bDrawActionPerformed

    private void finishGraphSel() {
        // make a copy of linebuilder pos
        ArrayList<Position> perimeter = new ArrayList<>();
        for (Position pos : lineBuilder.getLine().getPositions()) {
            perimeter.add(pos);
        }
        // clear line and stringbuilder
        lineBuilder.clear();
        posList.setLength(0);
        // display coordinates
        Iterator<Position> it = perimeter.iterator();
        Position first = it.next();
        dlm.removeAllElements();
        final double latitu = first.getLatitude().getDegrees();
        final double longitu = first.getLongitude().getDegrees();
        taCoords.append(String.format(Locale.ENGLISH, "%.6f %.6f\n", latitu, longitu));
        posList.append(latitu).append(' ').append(longitu).append(' ');
        while (it.hasNext()) {
            Position pos = it.next();
            final double lat = pos.getLatitude().getDegrees();
            final double lon = pos.getLongitude().getDegrees();
            taCoords.append(String.format(Locale.ENGLISH, "%.6f %.6f\n", lat, lon));
            posList.append(lat).append(' ').append(lon).append(' ');
        }
        taCoords.append(String.format(Locale.ENGLISH, "%.6f %.6f", latitu, longitu));
        posList.append(latitu).append(' ').append(longitu);
        // add polygonal area of interest
        aoi.setSurfPoly(perimeter);
        wwd.redraw();
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
