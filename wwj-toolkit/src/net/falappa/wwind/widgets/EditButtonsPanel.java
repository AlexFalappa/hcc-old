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
package net.falappa.wwind.widgets;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.util.measure.MeasureTool;
import gov.nasa.worldwind.util.measure.MeasureToolController;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.falappa.wwind.layers.EditableMarkerLayer;

/**
 * Panel to draw shapes on a WorldWind map.
 * <p>
 * Controls an internal MeasureTool linked to a WorldWindow. The MeasureTool can also be an external, preconfigured, one.
 * <p>
 * Controls an internal EditableMarkerLayer linked to a WorldWindow.
 * <p>
 * @author Alessandro Falappa
 */
public class EditButtonsPanel extends javax.swing.JPanel {

    private static final Color COLOR_EDIT = new Color(255, 204, 102, 200);
    private WorldWindow ww;
    private MeasureTool mt;
    private EditableMarkerLayer eml;

    public EditButtonsPanel() {
        initComponents();
    }

    public WorldWindow getWorldWindow() {
        return ww;
    }

    /**
     * Setter for the {@link WorldWindow} and {@link MeasureTool}.
     * <p>
     * @param wwindow the WorldWindow
     * @param mt the external MeasureTool
     */
    public void setWorldWindow(WorldWindow wwindow, MeasureTool mt) {
        if (wwindow == null) {
            throw new NullPointerException("Null WorldWindow!");
        }
        this.ww = wwindow;
        if (mt == null) {
            throw new NullPointerException("Null MeasureTool!");
        }
        if (!mt.getWwd().equals(wwindow)) {
            throw new IllegalArgumentException("MeasureTool refers to another WorldWindow!");
        }
        this.mt = mt;
        postWwdInit();
    }

    /**
     * Setter for the {@link WorldWindow}.
     * <p>
     * @param wwindow the WorldWindow
     */
    public void setWorldWindow(WorldWindow wwindow) {
        if (wwindow == null) {
            throw new NullPointerException("Null WorldWindow");
        }
        this.ww = wwindow;
        // create and setup the measure tool
        this.mt = new MeasureTool(wwindow);
        this.mt.setController(new MeasureToolController());
        // set some attributes of the measure tool
        this.mt.setShowAnnotation(false);
        this.mt.setFollowTerrain(true);
        this.mt.setFillColor(new Color(255, 255, 255, 63));
        this.mt.setLineColor(COLOR_EDIT);
        this.mt.getControlPointsAttributes().setBackgroundColor(COLOR_EDIT);
        this.mt.getControlPointsAttributes().setTextColor(Color.YELLOW);
        this.mt.getControlPointsAttributes().setHighlightScale(1.2);
        this.mt.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MeasureTool.EVENT_ARMED)) {
                    setCrosshair(mt.isArmed());
                    if (!mt.isArmed()) {
                        tbSelectTool.setSelected(true);
                    }
                }
            }
        });
        postWwdInit();
    }

    private void postWwdInit() {
        // add editable marker layer to worldwindow
        this.eml = new EditableMarkerLayer(ww, "EditableMarkerLayer");
        this.eml.setColor(COLOR_EDIT);
        this.eml.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(EditableMarkerLayer.EVENT_EDITING_START)) {
                    setCrosshair(true);
                }
                if (evt.getPropertyName().equals(EditableMarkerLayer.EVENT_POSITION_SET)) {
                    setCrosshair(false);
                    tbSelectTool.setSelected(true);
                    tbSelectToolActionPerformed(null);
                }
            }
        });
        this.ww.getModel().getLayers().add(eml);
        // enable widgets
        tbCircleTool.setEnabled(true);
        tbLineTool.setEnabled(true);
        tbPointTool.setEnabled(true);
        tbPolyTool.setEnabled(true);
        tbSelectTool.setEnabled(true);
    }

    public void dispose() {
        mt.dispose();
        ww.getModel().getLayers().remove(eml);
        eml.dispose();
    }

    /**
     * Getter for the edit shape fill color.
     * <p>
     * @return the current color
     */
    public Color getColorFill() {
        return mt.getFillColor();
    }

    /**
     * Setter for the edit shape fill color.
     * <p>
     * @param colorFill the new color
     */
    public void setColorFill(Color colorFill) {
        this.mt.setFillColor(colorFill);
    }

    /**
     * Getter for the edit shape outline color.
     * <p>
     * @return the current color
     */
    public Color getColorBoundary() {
        return mt.getLineColor();
    }

    /**
     * Getter for the edit shape outline color.
     * <p>
     * @param colorBoundary the new color
     */
    public void setColorBoundary(Color colorBoundary) {
        this.mt.setLineColor(colorBoundary);
        this.eml.setColor(colorBoundary);
    }

    /**
     * Getter for the edit shape control points color.
     * <p>
     * @return the current color
     */
    public Color getColorCtrlPoints() {
        return mt.getControlPointsAttributes().getBackgroundColor();
    }

    /**
     * setter for the edit shape control points color.
     * <p>
     * @param colorCtrlPoints the new color
     */
    public void setColorCtrlPoints(Color colorCtrlPoints) {
        this.mt.getControlPointsAttributes().setBackgroundColor(colorCtrlPoints);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgTools = new javax.swing.ButtonGroup();
        tbPolyTool = new javax.swing.JToggleButton();
        tbCircleTool = new javax.swing.JToggleButton();
        tbLineTool = new javax.swing.JToggleButton();
        tbPointTool = new javax.swing.JToggleButton();
        tbSelectTool = new javax.swing.JToggleButton();
        bClear = new javax.swing.JButton();

        bgTools.add(tbPolyTool);
        tbPolyTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/glyphicons_096_vector_path_polygon.png"))); // NOI18N
        tbPolyTool.setEnabled(false);
        tbPolyTool.setMargin(new java.awt.Insets(0, 0, 0, 0));
        tbPolyTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbPolyToolActionPerformed(evt);
            }
        });

        bgTools.add(tbCircleTool);
        tbCircleTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/glyphicons_095_vector_path_circle.png"))); // NOI18N
        tbCircleTool.setEnabled(false);
        tbCircleTool.setMargin(new java.awt.Insets(0, 0, 0, 0));
        tbCircleTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbCircleToolActionPerformed(evt);
            }
        });

        bgTools.add(tbLineTool);
        tbLineTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/glyphicons_097_vector_path_line.png"))); // NOI18N
        tbLineTool.setEnabled(false);
        tbLineTool.setMargin(new java.awt.Insets(0, 0, 0, 0));
        tbLineTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbLineToolActionPerformed(evt);
            }
        });

        bgTools.add(tbPointTool);
        tbPointTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/glyphicons_185_screenshot.png"))); // NOI18N
        tbPointTool.setEnabled(false);
        tbPointTool.setMargin(new java.awt.Insets(0, 0, 0, 0));
        tbPointTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbPointToolActionPerformed(evt);
            }
        });

        bgTools.add(tbSelectTool);
        tbSelectTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/mouse-arrow.png"))); // NOI18N
        tbSelectTool.setSelected(true);
        tbSelectTool.setEnabled(false);
        tbSelectTool.setMargin(new java.awt.Insets(0, 0, 0, 0));
        tbSelectTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbSelectToolActionPerformed(evt);
            }
        });

        bClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/glyphicons_197_remove.png"))); // NOI18N
        bClear.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbSelectTool)
                .addGap(0, 0, 0)
                .addComponent(tbPolyTool)
                .addGap(0, 0, 0)
                .addComponent(tbCircleTool)
                .addGap(0, 0, 0)
                .addComponent(tbLineTool)
                .addGap(0, 0, 0)
                .addComponent(tbPointTool)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bClear)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbSelectTool)
            .addComponent(tbPolyTool)
            .addComponent(tbCircleTool)
            .addComponent(tbLineTool)
            .addComponent(tbPointTool)
            .addComponent(bClear)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbSelectToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbSelectToolActionPerformed
        eml.setEditing(false);
        mt.setArmed(false);
    }//GEN-LAST:event_tbSelectToolActionPerformed

    private void tbPolyToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbPolyToolActionPerformed
        eml.setEditing(false);
        eml.clear();
        mt.setMeasureShapeType(MeasureTool.SHAPE_POLYGON);
        mt.setArmed(true);
    }//GEN-LAST:event_tbPolyToolActionPerformed

    private void tbCircleToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbCircleToolActionPerformed
        eml.setEditing(false);
        eml.clear();
        mt.setMeasureShapeType(MeasureTool.SHAPE_CIRCLE);
        mt.setArmed(true);
    }//GEN-LAST:event_tbCircleToolActionPerformed

    private void tbLineToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbLineToolActionPerformed
        eml.setEditing(false);
        eml.clear();
        mt.setMeasureShapeType(MeasureTool.SHAPE_PATH);
        mt.setArmed(true);
    }//GEN-LAST:event_tbLineToolActionPerformed

    private void tbPointToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbPointToolActionPerformed
        mt.clear();
        mt.setArmed(false);
        eml.setEditing(true);
    }//GEN-LAST:event_tbPointToolActionPerformed

    private void bClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearActionPerformed
        eml.setEditing(false);
        mt.setArmed(false);
        mt.clear();
        eml.clear();
        ww.redraw();
    }//GEN-LAST:event_bClearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClear;
    private javax.swing.ButtonGroup bgTools;
    private javax.swing.JToggleButton tbCircleTool;
    private javax.swing.JToggleButton tbLineTool;
    private javax.swing.JToggleButton tbPointTool;
    private javax.swing.JToggleButton tbPolyTool;
    private javax.swing.JToggleButton tbSelectTool;
    // End of variables declaration//GEN-END:variables

    private void setCrosshair(boolean flag) {
        if (flag) {
            ((Component) ww).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            ((Component) ww).setCursor(Cursor.getDefaultCursor());
        }
    }
}
