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
package gui.panels;

import java.awt.Color;
import main.App;
import net.falappa.wwind.widgets.WWindPanel;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class GeoAreaPanel extends javax.swing.JPanel {

//    private MeasureTool measTool;
    public static final Color COL_FILL = new Color(255, 255, 255, 63);
    public static final Color COL_BOUNDARY = new Color(255, 20, 0, 200);

    public GeoAreaPanel() {
        initComponents();
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chGeoEnable = new javax.swing.JCheckBox();
        cbOper = new javax.swing.JComboBox();
        cbPrimitive = new javax.swing.JComboBox();
        bDraw = new javax.swing.JButton();
        bCoords = new javax.swing.JButton();

        chGeoEnable.setText("Spatial constraints");
        chGeoEnable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chGeoEnableItemStateChanged(evt);
            }
        });

        cbOper.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Overlaps", "Contains", "Intersect", "Is contained" }));
        cbOper.setEnabled(false);

        cbPrimitive.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Polygon", "Circle", "Line String", "Point", "Lat Lon Range" }));
        cbPrimitive.setEnabled(false);
        cbPrimitive.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbPrimitiveItemStateChanged(evt);
            }
        });

        bDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_096_vector_path_polygon.png"))); // NOI18N
        bDraw.setText("Draw");
        bDraw.setEnabled(false);
        bDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDrawActionPerformed(evt);
            }
        });

        bCoords.setText("Coordinates");
        bCoords.setEnabled(false);
        bCoords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCoordsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chGeoEnable)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bCoords)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bDraw))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbOper, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPrimitive, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chGeoEnable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbOper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPrimitive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bDraw)
                    .addComponent(bCoords))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbPrimitiveItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbPrimitiveItemStateChanged
        switch (cbPrimitive.getSelectedIndex()) {
            case 0:
                App.frame.wwindPane.setEditMode(WWindPanel.EditModes.POLYGON);
                bDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_096_vector_path_polygon.png"))); // NOI18N
                break;
            case 1:
                App.frame.wwindPane.setEditMode(WWindPanel.EditModes.CIRCLE);
                bDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_095_vector_path_circle.png"))); // NOI18N
                break;
            case 2:
                App.frame.wwindPane.setEditMode(WWindPanel.EditModes.POLYLINE);
                bDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_097_vector_path_line.png"))); // NOI18N
                break;
            case 3:
                App.frame.wwindPane.setEditMode(WWindPanel.EditModes.POINT);
                bDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_185_screenshot.png"))); // NOI18N
                break;
            case 4:
                //TODO implement sector AOI
                // App.frame.wwindPane.setEditMode(WWindPanel.EditModes.RANGE);
                // bDraw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_099_vector_path_all.png"))); // NOI18N
                break;
        }
    }//GEN-LAST:event_cbPrimitiveItemStateChanged

    private void chGeoEnableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chGeoEnableItemStateChanged
        boolean flag = chGeoEnable.isSelected();
        cbOper.setEnabled(flag);
        cbPrimitive.setEnabled(flag);
        bDraw.setEnabled(flag);
        bCoords.setEnabled(flag);
    }//GEN-LAST:event_chGeoEnableItemStateChanged

    private void bDrawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDrawActionPerformed
        cbOper.setEnabled(App.frame.wwindPane.isEditing());
        cbPrimitive.setEnabled(App.frame.wwindPane.isEditing());
        bCoords.setEnabled(App.frame.wwindPane.isEditing());
        if (App.frame.wwindPane.isEditing()) {
            App.frame.wwindPane.stopEditing();
            App.frame.wwindPane.editShapeToAOI();
            App.frame.wwindPane.editShapeClear();
            bDraw.setText("Draw");
        } else {
            App.frame.wwindPane.startEditing();
            bDraw.setText("Done");
        }
    }//GEN-LAST:event_bDrawActionPerformed

    private void bCoordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCoordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bCoordsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCoords;
    private javax.swing.JButton bDraw;
    private javax.swing.JComboBox cbOper;
    private javax.swing.JComboBox cbPrimitive;
    private javax.swing.JCheckBox chGeoEnable;
    // End of variables declaration//GEN-END:variables

    public boolean constraintsEnabled() {
        return chGeoEnable.isSelected();
    }

    public int getOperator() {
        return cbOper.getSelectedIndex();
    }

    public int getPrimitive() {
        return cbPrimitive.getSelectedIndex();
    }

    public double getRangeLatMin() {
//        return pRangePane.getLatMin();
        return 0.0;
    }

    public double getRangeLatMax() {
//        return pRangePane.getLatMax();
        return 0.0;
    }

    public double getRangeLonMin() {
//        return pRangePane.getLonMin();
        return 0.0;
    }

    public double getRangeLonMax() {
//        return pRangePane.getLonMax();
        return 0.0;
    }

    public double getCircleCenterLat() {
//        return pCirclePane.getLat();
        return 0.0;
    }

    public double getCircleCenterLon() {
//        return pCirclePane.getLon();
        return 0.0;
    }

    public double getCircleRadius() {
//        return pCirclePane.getRadius();
        return 0.0;
    }

    public double getPointLat() {
//        return pPointPane.getLat();
        return 0.0;
    }

    public double getPointLon() {
//        return pPointPane.getLon();
        return 0.0;
    }

    public String getPolygonCoords() {
//        return pPolyPane.getCoords();
        return "0 0 1 0 1 1 0 1 0 0";
    }

    public String getPolylineCoords() {
//        return pLinePane.getCoords();
        return "0 0 1 0 1 1 0 1 0 0";
    }
}
