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

import gov.nasa.worldwind.render.SurfacePolygon;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.Action;
import javax.swing.JCheckBox;
import net.falappa.wwind.widgets.WWindPanel;

/**
 * Globe view settings and other view manipulations.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class ViewSettingsPanel extends javax.swing.JPanel {

//    private int currFtpIndex = -1;
//    private FootprintsLayer footprints;
//    private AOILayer aois;
//    private MOILayer mois;
//    private ExtentVisibilitySupport extVisSupport;
    public ViewSettingsPanel() {
        initComponents();
    }

    public void linkTo(WWindPanel wwp) {
        baseVisibility.linkTo(wwp);
        visAidsVisibility.linkTo(wwp);
        sslVisibility.linkTo(wwp);
        /*
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
         */
    }

    public void reset() {
        //currFtpIndex = -1;
        taRecDetails.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bPrev = new javax.swing.JButton();
        bNext = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taRecDetails = new javax.swing.JTextArea();
        visAidsVisibility = new net.falappa.wwind.widgets.VisualAidsVisibilityPanel();
        baseVisibility = new net.falappa.wwind.widgets.BaseCartoVisibilityPanel();
        sslVisibility = new net.falappa.wwind.widgets.SurfShapeLayersVisibilityPanel();

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sslVisibility, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(visAidsVisibility, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(baseVisibility, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sslVisibility, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(baseVisibility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(visAidsVisibility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrevActionPerformed
//        final int numRenderables = footprints.getNumRenderables();
//        if (numRenderables > 0) {
//            currFtpIndex--;
//            if (currFtpIndex < 0) {
//                currFtpIndex = numRenderables - 1;
//            }
//            // put the footprint into view
//            SurfacePolygon poly = flyToCurrFootprint();
//            // set details
//            updateRecDetails(poly);
//        }
    }//GEN-LAST:event_bPrevActionPerformed

    private void bNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextActionPerformed
//        final int numRenderables = footprints.getNumRenderables();
//        if (numRenderables > 0) {
//            currFtpIndex++;
//            if (currFtpIndex == numRenderables) {
//                currFtpIndex = 0;
//            }
//            // put the footprint into view
//            SurfacePolygon poly = flyToCurrFootprint();
//            // set details
//            updateRecDetails(poly);
//        }
    }//GEN-LAST:event_bNextActionPerformed

    private SurfacePolygon flyToCurrFootprint() {
//        SurfacePolygon poly = footprints.getPoly(currFtpIndex);
//        footprints.flyToShape(poly);
//        return poly;
        return null;
    }

    private void updateRecDetails(SurfacePolygon poly) {
        // TODO display also sensing start-stop times
        //taRecDetails.setText(String.format("Record: %d\nId: %s\n", currFtpIndex, poly.getValue(AVKey.HOVER_TEXT)));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bNext;
    private javax.swing.JButton bPrev;
    private net.falappa.wwind.widgets.BaseCartoVisibilityPanel baseVisibility;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private net.falappa.wwind.widgets.SurfShapeLayersVisibilityPanel sslVisibility;
    private javax.swing.JTextArea taRecDetails;
    private net.falappa.wwind.widgets.VisualAidsVisibilityPanel visAidsVisibility;
    // End of variables declaration//GEN-END:variables

    public void storePrefs(Preferences prefs) {
        // create a subnode for view settings
        Preferences vnode = prefs.node("view");
        // store layer enablement
//        putChbInPrefs(chAoi, vnode);
//        putChbInPrefs(chBMImage, vnode);
//        putChbInPrefs(chBMWMS, vnode);
//        putChbInPrefs(chBing, vnode);
//        putChbInPrefs(chBoundaries, vnode);
//        putChbInPrefs(chCompass, vnode);
//        putChbInPrefs(chFootprints, vnode);
//        putChbInPrefs(chGraticule, vnode);
//        putChbInPrefs(chMiniMap, vnode);
//        putChbInPrefs(chMsVirtEarth, vnode);
//        putChbInPrefs(chPlaceNames, vnode);
//        putChbInPrefs(chScale, vnode);
//        putChbInPrefs(chViewContrl, vnode);
        // store layers color
//        vnode.putInt("AoiColor", ccbAoi.getSelectedColor().getRGB());
//        vnode.putInt("FootpColor", ccbFootprints.getSelectedColor().getRGB());
    }

    private void putChbInPrefs(JCheckBox chb, Preferences vnode) {
        vnode.putBoolean(chb.getText(), chb.isSelected());
    }

    public void loadPrefs(Preferences prefs) {
        // get view settings subnode
        Preferences vnode = prefs.node("view");
        // load layer enablement
//        getChbFromPrefs(chAoi, vnode, true);
//        getChbFromPrefs(chBMImage, vnode, true);
//        getChbFromPrefs(chBMWMS, vnode, true);
//        getChbFromPrefs(chBing, vnode, false);
//        getChbFromPrefs(chBoundaries, vnode, false);
//        getChbFromPrefs(chCompass, vnode, false);
//        getChbFromPrefs(chFootprints, vnode, true);
//        getChbFromPrefs(chGraticule, vnode, false);
//        getChbFromPrefs(chMiniMap, vnode, false);
//        getChbFromPrefs(chMsVirtEarth, vnode, false);
//        getChbFromPrefs(chPlaceNames, vnode, false);
//        getChbFromPrefs(chScale, vnode, true);
//        getChbFromPrefs(chViewContrl, vnode, true);
        // load layers color
//        ccbAoi.setSelectedColor(new Color(vnode.getInt("AoiColor", Color.red.getRGB())));
//        ccbFootprints.setSelectedColor(new Color(vnode.getInt("FootpColor", Color.orange.getRGB())));
    }

    private void getChbFromPrefs(JCheckBox chb, Preferences vnode, boolean flag) {
        chb.setSelected(vnode.getBoolean(chb.getText(), flag));
        // force action firing
        final Action act = chb.getAction();
        if (act != null) {
            act.actionPerformed(new ActionEvent(chb, 1, "initial"));
        }
    }
}
