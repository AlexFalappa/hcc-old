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

import gov.nasa.worldwind.geom.Position;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import net.falappa.swing.TextPrompt;
import net.falappa.wwind.posparser.PositionParser;

/**
 * A panel allowing to enter a location and moving the view to the entered location.
 * <p>
 * The component also allows to go to the previous, next locations. The component enables after setting the controlled {@link WWindPanel}..
 *
 * @author Alessandro Falappa
 */
public class FlyToPanel extends javax.swing.JPanel {

    private WWindPanel wp;
    private Position curLoc = null;
    private final LinkedList<Position> prevLocs = new LinkedList<>();
    private final LinkedList<Position> nextLocs = new LinkedList<>();
    private final ArrayList<PositionParser> parsers = new ArrayList<>();
    private TextPrompt hint;

    /**
     * Default constructor.
     * <p>
     * The panel is initially disabled, must be linked to a WWindPanel to work.
     */
    public FlyToPanel() {
        initComponents();
        hint = new TextPrompt("Lat Lon", txLocation, TextPrompt.Show.FOCUS_LOST);
        hint.setForeground(Color.GRAY);
        hint.changeStyle(Font.ITALIC);
    }

    /**
     * Initializing constructor.
     *
     * @param wp the WWindPanel to link to
     */
    public FlyToPanel(WWindPanel wp) {
        this.wp = wp;
    }

    /**
     * Getter for the linked WWindPanel.
     *
     * @return the current WWindPanel
     */
    public WWindPanel getWWindPanel() {
        return wp;
    }

    /**
     * Setter for the linked WWindPanel.
     *
     * @param wp the new WWindPanel
     */
    public void setWWindPanel(WWindPanel wp) {
        if (wp == null) {
            throw new NullPointerException("Null WWindPanel");
        }
        this.wp = wp;
        // enable widgets
        jLabel1.setEnabled(true);
        txLocation.setEnabled(true);
        bGo.setEnabled(true);
    }

    /**
     * Adds a <tt>PositionParser</tt> to the list of parsers used to interpret location strings.
     *
     * @param parser an object implementing the {@link PositionParser} interface
     */
    public void addParser(PositionParser parser) {
        parsers.add(parser);
    }

    /**
     * Removes all position parsers.
     */
    public void removeAllParsers() {
        parsers.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        bGo = new javax.swing.JButton();
        txLocation = new javax.swing.JTextField();
        bPrevLoc = new javax.swing.JButton();
        bNextLoc = new javax.swing.JButton();

        jLabel1.setText("Fly to");
        jLabel1.setEnabled(false);

        bGo.setText("Go");
        bGo.setEnabled(false);
        bGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGoActionPerformed(evt);
            }
        });

        txLocation.setColumns(10);
        txLocation.setEnabled(false);
        txLocation.setMaximumSize(new java.awt.Dimension(200, 30));
        txLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txLocationActionPerformed(evt);
            }
        });

        bPrevLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/glyphicons_224_chevron-left.png"))); // NOI18N
        bPrevLoc.setToolTipText("Previous location");
        bPrevLoc.setEnabled(false);
        bPrevLoc.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bPrevLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrevLocActionPerformed(evt);
            }
        });

        bNextLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/falappa/wwind/widgets/img/glyphicons_223_chevron-right.png"))); // NOI18N
        bNextLoc.setToolTipText("Next location");
        bNextLoc.setEnabled(false);
        bNextLoc.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bNextLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNextLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txLocation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bGo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bPrevLoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bNextLoc)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bNextLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(bPrevLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bGo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void enablePrevNextButtons() {
        bPrevLoc.setEnabled(!prevLocs.isEmpty());
        bNextLoc.setEnabled(!nextLocs.isEmpty());
    }

    private Position parseLocation(String text) {
        // try all the parsers, return the position parsed by the first found
        for (PositionParser pars : parsers) {
            Position pos = pars.parseString(text);
            if (pos != null) {
                return pos;
            }
        }
        return null;
    }

    private void bGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGoActionPerformed
        Position pos = parseLocation(txLocation.getText());
        if (pos != null) {
            // pan to position
            wp.flyToPoint(pos);
            // store position in previous locations
            if (curLoc != null) {
                prevLocs.addFirst(curLoc);
            }
            curLoc = pos;
            enablePrevNextButtons();
        }
    }//GEN-LAST:event_bGoActionPerformed

    private void bPrevLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrevLocActionPerformed
        // current to next and previous location to current
        final Position prevPos = prevLocs.removeFirst();
        nextLocs.addFirst(curLoc);
        curLoc = prevPos;
        // fly to position
        wp.flyToPoint(curLoc);
        enablePrevNextButtons();
    }//GEN-LAST:event_bPrevLocActionPerformed

    private void bNextLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNextLocActionPerformed
        // current to prev and next location to current
        final Position nextLoc = nextLocs.removeFirst();
        prevLocs.addFirst(curLoc);
        curLoc = nextLoc;
        // fly to position
        wp.flyToPoint(curLoc);
        enablePrevNextButtons();
    }//GEN-LAST:event_bNextLocActionPerformed

    private void txLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txLocationActionPerformed
        bGoActionPerformed(null);
    }//GEN-LAST:event_txLocationActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bGo;
    private javax.swing.JButton bNextLoc;
    private javax.swing.JButton bPrevLoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txLocation;
    // End of variables declaration//GEN-END:variables
}
