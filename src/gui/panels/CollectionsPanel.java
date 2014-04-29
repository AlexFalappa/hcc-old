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

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class CollectionsPanel extends javax.swing.JPanel {

    DefaultTableModel dtm = new DefaultTableModel(0, 1);

    /**
     * Creates new form CollectionsPanel
     */
    public CollectionsPanel() {
        dtm.setColumnIdentifiers(new String[]{"Collections"});
        initComponents();
        tblColls.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                bDel.setEnabled(tblColls.getSelectedRow() >= 0);
            }
        });
    }

    public boolean collectionSelected() {
        return tblColls.getSelectedRowCount() > 0;
    }

    public String[] getSelectedCollections() {
        String[] ret = null;
        final int numSelRows = tblColls.getSelectedRowCount();
        if (numSelRows > 0) {
            ret = new String[numSelRows];
            int[] selRows = tblColls.getSelectedRows();
            for (int idx = 0; idx < selRows.length; idx++) {
                ret[idx] = (String) dtm.getValueAt(selRows[idx], 0);
            }
        }
        return ret;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        bAdd = new javax.swing.JButton();
        bDel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblColls = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Collections");

        bAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_432_plus.png"))); // NOI18N
        bAdd.setToolTipText("Add collection name");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });

        bDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_433_minus.png"))); // NOI18N
        bDel.setToolTipText("Remove selected collection name");
        bDel.setEnabled(false);
        bDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelActionPerformed(evt);
            }
        });

        tblColls.setModel(dtm);
        tblColls.setFillsViewportHeight(true);
        tblColls.setRowHeight(18);
        tblColls.setTableHeader(null);
        jScrollPane2.setViewportView(tblColls);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_194_circle_question_mark.png"))); // NOI18N
        jButton1.setToolTipText("Discovery collections trough GetCapabilities");
        jButton1.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(bAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bDel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(bAdd)
                    .addComponent(bDel)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        String s = new String("collection");
        dtm.addRow(new String[]{s});
    }//GEN-LAST:event_bAddActionPerformed

    private void bDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelActionPerformed
        final int selectedRow = tblColls.getSelectedRow();
        if (selectedRow >= 0) {
            dtm.removeRow(selectedRow);
        }
    }//GEN-LAST:event_bDelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bDel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblColls;
    // End of variables declaration//GEN-END:variables
}
