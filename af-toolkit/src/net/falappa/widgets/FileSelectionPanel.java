/*
 * Copyright 2014 Alessandro Falappa.
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
package net.falappa.widgets;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Alessandro Falappa
 */
public class FileSelectionPanel extends javax.swing.JPanel {

    private File selectedFile = null;
    private static final JFileChooser fc = new JFileChooser();

    static {
        // set some filechooser properties
        fc.setApproveButtonText("Choose");
        fc.setDialogTitle("Choose a file");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public FileSelectionPanel() {
        initComponents();
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
        txFile.setText(selectedFile.getAbsolutePath());
    }

    /** This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bChoose = new javax.swing.JButton();
        txFile = new javax.swing.JTextField();

        bChoose.setText("...");
        bChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bChooseActionPerformed(evt);
            }
        });

        txFile.setEditable(false);
        txFile.setColumns(20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bChoose))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(bChoose))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bChooseActionPerformed
        if (selectedFile != null) {
            // set initial directory based on currently selected file
            fc.setCurrentDirectory(selectedFile.getParentFile());
        }
        // show the dialog centered on the parent widget
        if (fc.showDialog(this.getParent(), null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            // set or clear the textfield
            if (selectedFile != null) {
                txFile.setText(selectedFile.getAbsolutePath());
            } else {
                txFile.setText("");
            }
        }
    }//GEN-LAST:event_bChooseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bChoose;
    private javax.swing.JTextField txFile;
    // End of variables declaration//GEN-END:variables
}
