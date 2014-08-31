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

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JSpinner;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class CircleDialog extends javax.swing.JDialog {

    private boolean okPressed = false;

    public CircleDialog(java.awt.Frame parent) {
        super(parent);
        initComponents();
        // set spinners locale to english (to get point as decimal separator)
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        ((JSpinner.NumberEditor) spCenterLat.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spCenterLon.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
        ((JSpinner.NumberEditor) spRadius.getEditor()).getFormat().setDecimalFormatSymbols(decimalFormatSymbols);
    }

    /** This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lCenterLat = new javax.swing.JLabel();
        lCenterLon = new javax.swing.JLabel();
        lRadius = new javax.swing.JLabel();
        spCenterLat = new javax.swing.JSpinner();
        spCenterLon = new javax.swing.JSpinner();
        spRadius = new javax.swing.JSpinner();
        lUom1 = new javax.swing.JLabel();
        lUom2 = new javax.swing.JLabel();
        lUom3 = new javax.swing.JLabel();
        bOk = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Circle coordinates");

        lCenterLat.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lCenterLat.setText("Center lat");

        lCenterLon.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lCenterLon.setText("Center lon");

        lRadius.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lRadius.setText("Radius");

        spCenterLat.setModel(new javax.swing.SpinnerNumberModel(0.0d, -90.0d, 90.0d, 1.0d));

        spCenterLon.setModel(new javax.swing.SpinnerNumberModel(0.0d, -180.0d, 180.0d, 1.0d));

        spRadius.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(500.0d)));

        lUom1.setFont(lUom1.getFont().deriveFont(lUom1.getFont().getSize()-1f));
        lUom1.setText("mt.");

        lUom2.setFont(lUom2.getFont().deriveFont(lUom2.getFont().getSize()-1f));
        lUom2.setText("deg.");

        lUom3.setFont(lUom3.getFont().deriveFont(lUom3.getFont().getSize()-1f));
        lUom3.setText("deg.");

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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lCenterLat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spCenterLat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lUom3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lCenterLon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spCenterLon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lUom2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lRadius)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lUom1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bCancel)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bCancel, bOk});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lCenterLat, lCenterLon, lRadius});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCenterLat)
                    .addComponent(spCenterLat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCenterLon)
                    .addComponent(spCenterLon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lRadius)
                    .addComponent(spRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lUom1))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bCancel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bOk, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    private void bOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOkActionPerformed
        okPressed = true;
        setVisible(false);
    }//GEN-LAST:event_bOkActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bOk;
    private javax.swing.JLabel lCenterLat;
    private javax.swing.JLabel lCenterLon;
    private javax.swing.JLabel lRadius;
    private javax.swing.JLabel lUom1;
    private javax.swing.JLabel lUom2;
    private javax.swing.JLabel lUom3;
    private javax.swing.JSpinner spCenterLat;
    private javax.swing.JSpinner spCenterLon;
    private javax.swing.JSpinner spRadius;
    // End of variables declaration//GEN-END:variables
}
