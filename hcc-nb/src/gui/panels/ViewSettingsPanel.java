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

import java.util.ArrayList;
import java.util.prefs.Preferences;
import net.falappa.wwind.layers.NoSuchShapeException;
import net.falappa.wwind.layers.SurfShapeLayer;
import net.falappa.wwind.widgets.WWindPanel;

/**
 * Globe view settings and other view manipulations.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class ViewSettingsPanel extends javax.swing.JPanel {

    private int currPidIndex = -1;
    private ArrayList<String> prodIds;
    private WWindPanel wwp;

    public ViewSettingsPanel() {
        initComponents();
    }

    public void linkTo(WWindPanel wwp) {
        this.wwp = wwp;
        baseVisibility.linkTo(wwp);
        visAidsVisibility.linkTo(wwp);
        sslVisibility.linkTo(wwp);
    }

    public void storePrefs(Preferences prefs) {
        // create a subnode for view settings
        Preferences vnode = prefs.node("view");
        // store cartography and visual aids visibility
        baseVisibility.storePrefs(vnode);
        visAidsVisibility.storePrefs(vnode);
    }

    public void loadPrefs(Preferences prefs) {
        // get view settings subnode
        Preferences vnode = prefs.node("view");
        // load cartography and visual aids visibility
        baseVisibility.loadPrefs(vnode);
        visAidsVisibility.loadPrefs(vnode);
    }

    public void setProductIds(ArrayList<String> prodIds) {
        this.prodIds = prodIds;
        currPidIndex = -1;
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
        //select previous product id
        if (--currPidIndex < 0) {
            currPidIndex = prodIds.size() - 1;
        }
        flyToCurrProdid();
    }//GEN-LAST:event_bPrevActionPerformed

    private void bNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextActionPerformed
        //select next product id
        currPidIndex = (++currPidIndex) % prodIds.size();
        flyToCurrProdid();
    }//GEN-LAST:event_bNextActionPerformed

    private void flyToCurrProdid() {
        final String pid = prodIds.get(currPidIndex);
        // put the surface shape into view
        for (SurfShapeLayer ssl : wwp.getAllSurfShapeLayers()) {
            try {
                ssl.flyToHiglhlightShape(pid);
                // TODO display also sensing start-stop times
                taRecDetails.setText(String.format("Record: %d\nId: %s\n", currPidIndex, pid));
                break;
            } catch (NoSuchShapeException ex) {
                //ignored will cycle
            }
        }
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
}
