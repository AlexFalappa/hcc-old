package net.falappa.wwind.widgets;

import net.falappa.wwind.utils.ToggleVisibilityAction;
import gov.nasa.worldwind.WorldWindow;
import javax.swing.JSlider;
import net.falappa.wwind.layers.SurfShapeLayer;

/**
 *
 * @author Alessandro Falappa
 */
public class LayerPropsPanel extends javax.swing.JPanel {

    WorldWindow wwd;
    SurfShapeLayer sl;

    public LayerPropsPanel(WorldWindow wwd, SurfShapeLayer layer) {
        initComponents();
        this.sl = layer;
        this.wwd = wwd;
        chLayerName.setSelected(layer.isEnabled());
        chLayerName.setAction(new ToggleVisibilityAction(layer, wwd));
        ccbColor.setSelectedColor(layer.getColor());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chLayerName = new javax.swing.JCheckBox();
        ccbColor = new net.falappa.swing.combobox.colorbox.ColorComboBox();
        slOpacity = new javax.swing.JSlider();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 9, 3));

        chLayerName.setText("layer name");

        ccbColor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ccbColorItemStateChanged(evt);
            }
        });

        slOpacity.setMinimum(10);
        slOpacity.setValue(100);
        slOpacity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slOpacityStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(chLayerName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(ccbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(slOpacity, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chLayerName)
                    .addComponent(ccbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slOpacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ccbColorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ccbColorItemStateChanged
        sl.setColor(ccbColor.getSelectedColor());
        wwd.redraw();
    }//GEN-LAST:event_ccbColorItemStateChanged

    private void slOpacityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slOpacityStateChanged
        JSlider source = (JSlider) evt.getSource();
        double val = ((double) source.getValue()) / ((double) source.getMaximum());
        sl.setOpacity(val);
        wwd.redraw();
    }//GEN-LAST:event_slOpacityStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.falappa.swing.combobox.colorbox.ColorComboBox ccbColor;
    private javax.swing.JCheckBox chLayerName;
    private javax.swing.JSlider slOpacity;
    // End of variables declaration//GEN-END:variables
}
