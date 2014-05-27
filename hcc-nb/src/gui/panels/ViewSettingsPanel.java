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

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwindx.examples.util.ExtentVisibilitySupport;
import gui.wwind.AOILayer;
import gui.wwind.FootprintsLayer;
import gui.wwind.MOILayer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import main.App;

/**
 * Globe view settings and other view manipulations.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class ViewSettingsPanel extends javax.swing.JPanel {

    private int currFtpIndex = -1;
    private FootprintsLayer footprints;
    private AOILayer aois;
    private MOILayer mois;
    private ExtentVisibilitySupport extVisSupport;

    public ViewSettingsPanel() {
        initComponents();
    }

    public void linkTo(WorldWindow wwd) {
        LayerList layers = wwd.getModel().getLayers();
        List<Layer> layersByClass = layers.getLayersByClass(MOILayer.class);
        mois = (MOILayer) layersByClass.get(0);
        layersByClass = layers.getLayersByClass(FootprintsLayer.class);
        footprints = (FootprintsLayer) layersByClass.get(0);
        link(chFootprints, wwd, footprints);
        layersByClass = layers.getLayersByClass(AOILayer.class);
        aois = (AOILayer) layersByClass.get(0);
        link(chAoi, wwd, aois);
        Layer layer = layers.getLayerByName("Scale bar");
        link(chScale, wwd, layer);
        layer = layers.getLayerByName("Compass");
        link(chCompass, wwd, layer);
        layer = layers.getLayerByName("World Map");
        link(chMiniMap, wwd, layer);
        layer = layers.getLayerByName("View Controls");
        link(chViewContrl, wwd, layer);
        layer = layers.getLayerByName("Lat-Lon Graticule");
        link(chGraticule, wwd, layer);
        layer = layers.getLayerByName("Place Names");
        link(chPlaceNames, wwd, layer);
        layer = layers.getLayerByName("Political Boundaries");
        link(chBoundaries, wwd, layer);
        layer = layers.getLayerByName("NASA Blue Marble Image");
        link(chBMImage, wwd, layer);
        layer = layers.getLayerByName("Blue Marble (WMS) 2004");
        link(chBMWMS, wwd, layer);
        layer = layers.getLayerByName("MS Virtual Earth Aerial");
        link(chMsVirtEarth, wwd, layer);
        layer = layers.getLayerByName("Bing Imagery");
        link(chBing, wwd, layer);
    }

    public void reset() {
        currFtpIndex = -1;
        taRecDetails.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        chAoi = new javax.swing.JCheckBox();
        chFootprints = new javax.swing.JCheckBox();
        chPlaceNames = new javax.swing.JCheckBox();
        chBoundaries = new javax.swing.JCheckBox();
        chBMImage = new javax.swing.JCheckBox();
        chBMWMS = new javax.swing.JCheckBox();
        chMsVirtEarth = new javax.swing.JCheckBox();
        chBing = new javax.swing.JCheckBox();
        ccbAoi = new net.falappa.swing.combobox.colorbox.ColorComboBox();
        ccbFootprints = new net.falappa.swing.combobox.colorbox.ColorComboBox();
        jPanel1 = new javax.swing.JPanel();
        chMiniMap = new javax.swing.JCheckBox();
        chCompass = new javax.swing.JCheckBox();
        chScale = new javax.swing.JCheckBox();
        chViewContrl = new javax.swing.JCheckBox();
        chGraticule = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bPrev = new javax.swing.JButton();
        bNext = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taRecDetails = new javax.swing.JTextArea();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Layers"));

        chAoi.setText("Area of Interest");
        chAoi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chAoiItemStateChanged(evt);
            }
        });

        chFootprints.setText("Footprints");
        chFootprints.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chFootprintsItemStateChanged(evt);
            }
        });

        chPlaceNames.setText("Place Names");

        chBoundaries.setText("Political Boundaries");

        chBMImage.setText("BlueMarble Image");

        chBMWMS.setText("BlueMarble WMS");

        chMsVirtEarth.setText("MS Virtual Earth");

        chBing.setText("Bing");

        ccbAoi.setSelectedIndex(10);
        ccbAoi.setEnabled(false);
        ccbAoi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ccbAoiItemStateChanged(evt);
            }
        });

        ccbFootprints.setSelectedIndex(8);
        ccbFootprints.setEnabled(false);
        ccbFootprints.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ccbFootprintsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chAoi)
                            .addComponent(chFootprints)
                            .addComponent(chBMImage)
                            .addComponent(chBMWMS)
                            .addComponent(chMsVirtEarth)
                            .addComponent(chBoundaries)
                            .addComponent(chPlaceNames)
                            .addComponent(chBing))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ccbFootprints, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ccbAoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chAoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ccbAoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chFootprints)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ccbFootprints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chPlaceNames)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBoundaries)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBMImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBMWMS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chMsVirtEarth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBing)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Visual aids"));

        chMiniMap.setText("World Map");

        chCompass.setText("Compass");

        chScale.setText("Scale");

        chViewContrl.setText("View Controls");

        chGraticule.setText("Graticule");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chCompass)
                    .addComponent(chScale)
                    .addComponent(chViewContrl)
                    .addComponent(chGraticule)
                    .addComponent(chMiniMap))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chMiniMap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chCompass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chScale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chViewContrl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chGraticule)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Results navigation");

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        bPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_224_chevron-left.png"))); // NOI18N
        bPrev.setText("Previous");
        bPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrevActionPerformed(evt);
            }
        });
        jPanel3.add(bPrev);

        bNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_223_chevron-right.png"))); // NOI18N
        bNext.setText("Next");
        bNext.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        bNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNextActionPerformed(evt);
            }
        });
        jPanel3.add(bNext);

        taRecDetails.setEditable(false);
        taRecDetails.setColumns(10);
        taRecDetails.setRows(5);
        taRecDetails.setTabSize(4);
        taRecDetails.setEnabled(false);
        jScrollPane1.setViewportView(taRecDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrevActionPerformed
        final int numRenderables = App.frame.footprints.getNumRenderables();
        if (numRenderables > 0) {
            currFtpIndex--;
            if (currFtpIndex < 0) {
                currFtpIndex = numRenderables - 1;
            }
            // put the footprint into view
            SurfacePolygon poly = flyToCurrFootprint();
            // set details
            updateRecDetails(poly);
        }
    }//GEN-LAST:event_bPrevActionPerformed

    private void bNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextActionPerformed
        final int numRenderables = App.frame.footprints.getNumRenderables();
        if (numRenderables > 0) {
            currFtpIndex++;
            if (currFtpIndex == numRenderables) {
                currFtpIndex = 0;
            }
            // put the footprint into view
            SurfacePolygon poly = flyToCurrFootprint();
            // set details
            updateRecDetails(poly);
        }
    }//GEN-LAST:event_bNextActionPerformed

    private void ccbAoiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ccbAoiItemStateChanged
        final Color newColor = ccbAoi.getSelectedColor();
        aois.setColor(newColor);
        mois.setColor(newColor);
        if (App.frame != null) {
            App.frame.wwindPane.redraw();
        }
    }//GEN-LAST:event_ccbAoiItemStateChanged

    private void ccbFootprintsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ccbFootprintsItemStateChanged
        footprints.setColor(ccbFootprints.getSelectedColor());
        if (App.frame != null) {
            App.frame.wwindPane.redraw();
        }
    }//GEN-LAST:event_ccbFootprintsItemStateChanged

    private void chAoiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chAoiItemStateChanged
        ccbAoi.setEnabled(chAoi.isSelected());
    }//GEN-LAST:event_chAoiItemStateChanged

    private void chFootprintsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chFootprintsItemStateChanged
        ccbFootprints.setEnabled(chFootprints.isSelected());
    }//GEN-LAST:event_chFootprintsItemStateChanged

    private SurfacePolygon flyToCurrFootprint() {
        SurfacePolygon poly = App.frame.footprints.getFootPoly(currFtpIndex);
        App.frame.flyToSector(poly);
        return poly;
    }

    private void updateRecDetails(SurfacePolygon poly) {
        // TODO display also sensing start-stop times
        taRecDetails.setText(String.format("Record: %d\nId: %s\n", currFtpIndex, poly.getValue(AVKey.HOVER_TEXT)));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bNext;
    private javax.swing.JButton bPrev;
    private net.falappa.swing.combobox.colorbox.ColorComboBox ccbAoi;
    private net.falappa.swing.combobox.colorbox.ColorComboBox ccbFootprints;
    private javax.swing.JCheckBox chAoi;
    private javax.swing.JCheckBox chBMImage;
    private javax.swing.JCheckBox chBMWMS;
    private javax.swing.JCheckBox chBing;
    private javax.swing.JCheckBox chBoundaries;
    private javax.swing.JCheckBox chCompass;
    private javax.swing.JCheckBox chFootprints;
    private javax.swing.JCheckBox chGraticule;
    private javax.swing.JCheckBox chMiniMap;
    private javax.swing.JCheckBox chMsVirtEarth;
    private javax.swing.JCheckBox chPlaceNames;
    private javax.swing.JCheckBox chScale;
    private javax.swing.JCheckBox chViewContrl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taRecDetails;
    // End of variables declaration//GEN-END:variables

    private void link(JCheckBox jcb, WorldWindow wwd, Layer layer) {
        if (layer != null) {
            jcb.setSelected(layer.isEnabled());
            jcb.setAction(new ToggleAction(layer, wwd));
        } else {
            jcb.setEnabled(false);
        }
    }

    public void storePrefs(Preferences prefs) {
        // create a subnode for view settings
        Preferences vnode = prefs.node("view");
        // store layer enablement
        putChbInPrefs(chAoi, vnode);
        putChbInPrefs(chBMImage, vnode);
        putChbInPrefs(chBMWMS, vnode);
        putChbInPrefs(chBing, vnode);
        putChbInPrefs(chBoundaries, vnode);
        putChbInPrefs(chCompass, vnode);
        putChbInPrefs(chFootprints, vnode);
        putChbInPrefs(chGraticule, vnode);
        putChbInPrefs(chMiniMap, vnode);
        putChbInPrefs(chMsVirtEarth, vnode);
        putChbInPrefs(chPlaceNames, vnode);
        putChbInPrefs(chScale, vnode);
        putChbInPrefs(chViewContrl, vnode);
        // store layers color
        vnode.putInt("AoiColor", ccbAoi.getSelectedColor().getRGB());
        vnode.putInt("FootpColor", ccbFootprints.getSelectedColor().getRGB());
    }

    private void putChbInPrefs(JCheckBox chb, Preferences vnode) {
        vnode.putBoolean(chb.getText(), chb.isSelected());
    }

    public void loadPrefs(Preferences prefs) {
        // get view settings subnode
        Preferences vnode = prefs.node("view");
        // load layer enablement
        getChbFromPrefs(chAoi, vnode, true);
        getChbFromPrefs(chBMImage, vnode, true);
        getChbFromPrefs(chBMWMS, vnode, true);
        getChbFromPrefs(chBing, vnode, false);
        getChbFromPrefs(chBoundaries, vnode, false);
        getChbFromPrefs(chCompass, vnode, false);
        getChbFromPrefs(chFootprints, vnode, true);
        getChbFromPrefs(chGraticule, vnode, false);
        getChbFromPrefs(chMiniMap, vnode, false);
        getChbFromPrefs(chMsVirtEarth, vnode, false);
        getChbFromPrefs(chPlaceNames, vnode, false);
        getChbFromPrefs(chScale, vnode, true);
        getChbFromPrefs(chViewContrl, vnode, true);
        // load layers color
        ccbAoi.setSelectedColor(new Color(vnode.getInt("AoiColor", Color.red.getRGB())));
        ccbFootprints.setSelectedColor(new Color(vnode.getInt("FootpColor", Color.orange.getRGB())));
    }

    private void getChbFromPrefs(JCheckBox chb, Preferences vnode, boolean flag) {
        chb.setSelected(vnode.getBoolean(chb.getText(), flag));
        // force action firing
        final Action act = chb.getAction();
        if (act != null) {
            act.actionPerformed(new ActionEvent(chb, 1, "initial"));
        }
    }

    private class ToggleAction extends AbstractAction {

        private final WorldWindow wwd;
        private final Layer layer;

        public ToggleAction(Layer layer, WorldWindow wwd) {
            super(layer.getName());
            this.wwd = wwd;
            this.layer = layer;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            boolean toggle = ((JCheckBox) actionEvent.getSource()).isSelected();
            this.layer.setEnabled(toggle);
            wwd.redraw();
        }
    }
}
