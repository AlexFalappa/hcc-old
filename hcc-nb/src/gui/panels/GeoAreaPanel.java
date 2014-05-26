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

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.util.measure.MeasureTool;
import gov.nasa.worldwind.util.measure.MeasureToolController;
import gui.wwind.AOILayer;
import gui.wwind.MOILayer;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import main.App;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class GeoAreaPanel extends javax.swing.JPanel {

    private MeasureTool measTool;
    public static final Color COL_FILL = new Color(255, 255, 255, 63);
    public static final Color COL_BOUNDARY = new Color(255, 20, 0, 200);

    public GeoAreaPanel() {
        initComponents();
    }

    public void linkTo(final WorldWindowGLCanvas wwCanvas, AOILayer aois, MOILayer mois) {
        // create and setup the measure tool
        measTool = new MeasureTool(wwCanvas);
        measTool.setController(new MeasureToolController());
        // set some attributes of the measure tool
        measTool.setShowAnnotation(false);
        measTool.setFollowTerrain(true);
        measTool.setFillColor(COL_FILL);
        measTool.setLineColor(COL_BOUNDARY);
        measTool.getControlPointsAttributes().setBackgroundColor(COL_BOUNDARY);
        measTool.getLayer().setPickEnabled(false);
        measTool.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MeasureTool.EVENT_ARMED)) {
                    if (measTool.isArmed()) {
                        ((Component) wwCanvas).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    } else {
                        ((Component) wwCanvas).setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        });
        // link the various panes
        pPolyPane.linkTo(measTool, aois);
        pLinePane.linkTo(measTool, aois);
        pCirclePane.linkTo(measTool, aois);
        pPointPane.linkTo(wwCanvas, mois);
        pRangePane.linkTo(wwCanvas);
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
        pCards = new javax.swing.JPanel();
        pPolyPane = new gui.panels.geo.PolygonPanel();
        pCirclePane = new gui.panels.geo.CirclePanel();
        pLinePane = new gui.panels.geo.LineStringPanel();
        pPointPane = new gui.panels.geo.PointPanel();
        pRangePane = new gui.panels.geo.LonLatRangePanel();

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

        pCards.setLayout(new java.awt.CardLayout(1, 1));

        pPolyPane.setEnabled(false);
        pCards.add(pPolyPane, "Polygon");
        pCards.add(pCirclePane, "Circle");

        pLinePane.setEnabled(false);
        pCards.add(pLinePane, "Line String");

        pPointPane.setEnabled(false);
        pCards.add(pPointPane, "Point");

        pRangePane.setEnabled(false);
        pCards.add(pRangePane, "Lat Lon Range");

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
                            .addComponent(pCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbOper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPrimitive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                .addComponent(pCards, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbPrimitiveItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbPrimitiveItemStateChanged
        CardLayout layout = (CardLayout) pCards.getLayout();
        layout.show(pCards, (String) cbPrimitive.getSelectedItem());
        App.frame.mois.setEnabled(cbPrimitive.getSelectedIndex() == 3);
        App.frame.aois.setEnabled(cbPrimitive.getSelectedIndex() != 3);
    }//GEN-LAST:event_cbPrimitiveItemStateChanged

    private void chGeoEnableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chGeoEnableItemStateChanged
        boolean flag = chGeoEnable.isSelected();
        cbOper.setEnabled(flag);
        cbPrimitive.setEnabled(flag);
        pCirclePane.setEnabled(flag);
        pLinePane.setEnabled(flag);
        pRangePane.setEnabled(flag);
        pPointPane.setEnabled(flag);
        pPolyPane.setEnabled(flag);
    }//GEN-LAST:event_chGeoEnableItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbOper;
    private javax.swing.JComboBox cbPrimitive;
    private javax.swing.JCheckBox chGeoEnable;
    private javax.swing.JPanel pCards;
    private gui.panels.geo.CirclePanel pCirclePane;
    private gui.panels.geo.LineStringPanel pLinePane;
    private gui.panels.geo.PointPanel pPointPane;
    private gui.panels.geo.PolygonPanel pPolyPane;
    private gui.panels.geo.LonLatRangePanel pRangePane;
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
        return pRangePane.getLatMin();
    }

    public double getRangeLatMax() {
        return pRangePane.getLatMax();
    }

    public double getRangeLonMin() {
        return pRangePane.getLonMin();
    }

    public double getRangeLonMax() {
        return pRangePane.getLonMax();
    }

    public double getCircleCenterLat() {
        return pCirclePane.getLat();
    }

    public double getCircleCenterLon() {
        return pCirclePane.getLon();
    }

    public double getCircleRadius() {
        return pCirclePane.getRadius();
    }

    public double getPointLat() {
        return pPointPane.getLat();
    }

    public double getPointLon() {
        return pPointPane.getLon();
    }

    public String getPolygonCoords() {
        return pPolyPane.getCoords();
    }

    public String getPolylineCoords() {
        return pLinePane.getCoords();
    }
}
