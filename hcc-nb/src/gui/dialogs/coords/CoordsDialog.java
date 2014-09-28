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
package gui.dialogs.coords;

import gov.nasa.worldwind.geom.LatLon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import net.falappa.utils.GisUtils;
import net.falappa.wwind.util.WWindUtils;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class CoordsDialog extends javax.swing.JDialog {

    private boolean okPressed = false;
    private boolean closed = false;
    private final Timer labelTimer = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lErrMexs.setText("");
        }
    });

    public CoordsDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        labelTimer.setRepeats(false);
    }

    public boolean isOkPressed() {
        return okPressed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getCoordsString() {
        return taCoords.getText();
    }

    public List<LatLon> getCoords() {
        return WWindUtils.posList2LatLonList(taCoords.getText());
    }

    /** This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lCoords = new javax.swing.JLabel();
        lErrMexs = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCoords = new javax.swing.JTextArea();
        lExplain = new javax.swing.JLabel();
        bOk = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lCoords.setText("Coordinates");

        lErrMexs.setForeground(new java.awt.Color(204, 0, 0));

        taCoords.setColumns(10);
        taCoords.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        taCoords.setRows(3);
        jScrollPane2.setViewportView(taCoords);

        lExplain.setFont(lExplain.getFont().deriveFont(lExplain.getFont().getSize()-2f));
        lExplain.setText("Space separated lat lon pairs in decimal degrees");

        bOk.setText("OK");
        bOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOkActionPerformed(evt);
            }
        });

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lErrMexs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancel))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lCoords)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lExplain, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bCancel, bOk});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lCoords)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lExplain)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bCancel, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(bOk, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(lErrMexs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOkActionPerformed
        final String errMex = GisUtils.checkIsPolySeq(taCoords.getText(), closed);
        // correctness check of coordinates
        if (errMex != null) {
            lErrMexs.setText(errMex);
            labelTimer.start();
        } else {
            okPressed = true;
            setVisible(false);
        }
    }//GEN-LAST:event_bOkActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bOk;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lCoords;
    private javax.swing.JLabel lErrMexs;
    private javax.swing.JLabel lExplain;
    private javax.swing.JTextArea taCoords;
    // End of variables declaration//GEN-END:variables
}