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
package gui.dialogs;

import java.util.HashMap;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import main.App;

/**
 * HCC settings dialog.
 * <p>
 * @author Alessandro Falappa
 */
public class SettingsDialog extends javax.swing.JDialog {

    private final HashMap<String, String> lafs = new HashMap<>();
    private final DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

    public SettingsDialog(java.awt.Frame parent) {
        super(parent, true);
        // add to the combobox and the map all platform installed L&Fs
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            lafs.put(info.getName(), info.getClassName());
            dcbm.addElement(info.getName());
        }
        // add to the combobox and the map third party L&Fs
        lafs.put("JGoodies Plastic", "com.jgoodies.looks.plastic.PlasticLookAndFeel");
        dcbm.addElement("JGoodies Plastic");
        lafs.put("JGoodies Plastic XP", "com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
        dcbm.addElement("JGoodies Plastic XP");
        // build widgets
        initComponents();
        // select the current look and feel
        cbLF.setSelectedItem(UIManager.getLookAndFeel().getName());
    }

    /** This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbLF = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        bCancel = new javax.swing.JButton();
        bOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("HCC Settings");

        jLabel2.setText("Look & Feel");

        cbLF.setModel(dcbm);

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()-1f));
        jLabel3.setForeground(javax.swing.UIManager.getDefaults().getColor("CheckBoxMenuItem.acceleratorSelectionBackground"));
        jLabel3.setText("Requires restart of the application");

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        bOk.setText("OK");
        bOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancel))
                    .addGroup(contentPaneLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 9, Short.MAX_VALUE))
                            .addComponent(cbLF, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        contentPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bCancel, bOk});

        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbLF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancel)
                    .addComponent(bOk))
                .addContainerGap())
        );

        getContentPane().add(contentPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOkActionPerformed
        // get preferences API node
        Preferences prefs = Preferences.userRoot().node(App.PREF_ROOT);
        // store selected LAF class name
        prefs.put(App.PREF_LAFCLASS, lafs.get(cbLF.getSelectedItem().toString()));
        setVisible(false);
    }//GEN-LAST:event_bOkActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_bCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bOk;
    private javax.swing.JComboBox cbLF;
    private javax.swing.JPanel contentPane;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}