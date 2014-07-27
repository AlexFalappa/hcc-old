/*
 * Copyright 2014 afalappa.
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
import gov.nasa.worldwind.globes.Earth;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.globes.FlatGlobe;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.SkyColorLayer;
import gov.nasa.worldwind.layers.SkyGradientLayer;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.FlatOrbitView;

/**
 * WorldWindow globe/map switcher in a small inline panel.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class FlatRoundInLinePanel extends javax.swing.JPanel {

    private Globe roundGlobe = new Earth();
    private FlatGlobe flatGlobe = new EarthFlat();
    private WorldWindow ww;

    /**
     * Default constructor.
     * <p>
     * The panel is initially disabled, must be linked to a WorldWindow to work.
     */
    public FlatRoundInLinePanel() {
        this(null);
    }

    /**
     * Initializing constructor.
     * <p>
     * Enables all panel functionalities.
     * <p>
     * @param ww the WorldWindow to link to
     */
    public FlatRoundInLinePanel(WorldWindow ww) {
        initComponents();
        if (ww != null) {
            setupGlobes(ww);
        }
    }

    private void setupGlobes(WorldWindow ww) {
        this.ww = ww;
        Globe globe = ww.getModel().getGlobe();
        if (globe instanceof FlatGlobe) {
            flatGlobe = (FlatGlobe) globe;
        } else {
            roundGlobe = globe;
        }
    }

    /**
     * Getter for the managed WorldWindow.
     * <p>
     * @return the current WorldWindow
     */
    public WorldWindow getWorldWindow() {
        return ww;
    }

    /**
     * Setter for the managed WorldWindow.
     * <p>
     * @param ww the new WorldWindow
     */
    public void setWorldWindow(WorldWindow ww) {
        if (ww == null) {
            throw new NullPointerException("Null worldwindow");
        }
        setupGlobes(ww);
        // enable widgets
        rbGlobe.setEnabled(true);
        rbMap.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rbGlobe = new javax.swing.JRadioButton();
        rbMap = new javax.swing.JRadioButton();
        cbProjection = new javax.swing.JComboBox();

        buttonGroup1.add(rbGlobe);
        rbGlobe.setSelected(true);
        rbGlobe.setText("Globe");
        rbGlobe.setEnabled(false);
        rbGlobe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbGlobeActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbMap);
        rbMap.setText("Map");
        rbMap.setEnabled(false);
        rbMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMapActionPerformed(evt);
            }
        });

        cbProjection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mercator", "Lat-Lon", "Sinusoidal", "Modified Sinusoidal" }));
        cbProjection.setEnabled(false);
        cbProjection.setLightWeightPopupEnabled(false);
        cbProjection.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProjectionItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rbGlobe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbMap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProjection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rbGlobe)
                .addComponent(rbMap)
                .addComponent(cbProjection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbGlobeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGlobeActionPerformed
        cbProjection.setEnabled(false);
        switchGlobe(false);
    }//GEN-LAST:event_rbGlobeActionPerformed

    private void rbMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMapActionPerformed
        cbProjection.setEnabled(true);
        switchGlobe(true);
    }//GEN-LAST:event_rbMapActionPerformed

    private void cbProjectionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProjectionItemStateChanged
        switch (cbProjection.getSelectedIndex()) {
            case 0:
                // mercator
                flatGlobe.setProjection(FlatGlobe.PROJECTION_MERCATOR);
                break;
            case 1:
                // equirectangular
                flatGlobe.setProjection(FlatGlobe.PROJECTION_LAT_LON);
                break;
            case 2:
                // sinusoidal
                flatGlobe.setProjection(FlatGlobe.PROJECTION_SINUSOIDAL);
                break;
            case 3:
                // modified sinusoidal
                flatGlobe.setProjection(FlatGlobe.PROJECTION_MODIFIED_SINUSOIDAL);
                break;
        }
        ww.redraw();
        }//GEN-LAST:event_cbProjectionItemStateChanged

    private void switchGlobe(boolean flat) {
        if (!flat) {
            // Switch to round globe
            ww.getModel().setGlobe(roundGlobe);
            // Switch to orbit view and update with current position
            FlatOrbitView flatOrbitView = (FlatOrbitView) ww.getView();
            BasicOrbitView orbitView = new BasicOrbitView();
            orbitView.setCenterPosition(flatOrbitView.getCenterPosition());
            orbitView.setZoom(flatOrbitView.getZoom());
            orbitView.setHeading(flatOrbitView.getHeading());
            orbitView.setPitch(flatOrbitView.getPitch());
            ww.setView(orbitView);
            // Change sky layer
            LayerList layers = ww.getModel().getLayers();
            for (int i = 0; i < layers.size(); i++) {
                if (layers.get(i) instanceof SkyColorLayer) {
                    layers.set(i, new SkyGradientLayer());
                }
            }
        } else {
            // Switch to flat globe
            ww.getModel().setGlobe(flatGlobe);
            cbProjectionItemStateChanged(null);
            // Switch to flat view and update with current position
            BasicOrbitView orbitView = (BasicOrbitView) ww.getView();
            FlatOrbitView flatOrbitView = new FlatOrbitView();
            flatOrbitView.setCenterPosition(orbitView.getCenterPosition());
            flatOrbitView.setZoom(orbitView.getZoom());
            flatOrbitView.setHeading(orbitView.getHeading());
            flatOrbitView.setPitch(orbitView.getPitch());
            ww.setView(flatOrbitView);
            // Change sky layer
            LayerList layers = ww.getModel().getLayers();
            for (int i = 0; i < layers.size(); i++) {
                if (layers.get(i) instanceof SkyGradientLayer) {
                    layers.set(i, new SkyColorLayer());
                }
            }
        }
        ww.redraw();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbProjection;
    private javax.swing.JRadioButton rbGlobe;
    private javax.swing.JRadioButton rbMap;
    // End of variables declaration//GEN-END:variables
}
