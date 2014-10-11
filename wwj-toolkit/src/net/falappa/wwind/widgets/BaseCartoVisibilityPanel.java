package net.falappa.wwind.widgets;

import net.falappa.wwind.utils.ToggleVisibilityAction;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.Action;
import javax.swing.JCheckBox;

/**
 * A panel listing and controlling the visibility of the default base cartoghraphy layers in a {@link WWindPanel} (Place Names, Political
 * Boundaries, Blue Marble Image, Blue Marble WMS and Microsoft Bing Map).
 * <p>
 * Layers are listed in the order they are added.
 * <p>
 * @author Alessandro Falappa
 */
public class BaseCartoVisibilityPanel extends javax.swing.JPanel {

    private static final String PREFNODE_BASECARTO = "base-carto";

    /**
     * Default constructor.
     */
    public BaseCartoVisibilityPanel() {
        initComponents();
    }

    /**
     * Attach this component to the given {@link WWindPanel}.
     * <p>
     * @param wwp the WWindPanel to attach to
     */
    public void linkTo(WWindPanel wwp) {
        final WorldWindowGLCanvas wwCanvas = wwp.getWWCanvas();
        LayerList layers = wwCanvas.getModel().getLayers();
        Layer layer = layers.getLayerByName("Place Names");
        link(chPlaceNames, wwCanvas, layer);
        layer = layers.getLayerByName("Political Boundaries");
        link(chBoundaries, wwCanvas, layer);
        layer = layers.getLayerByName("NASA Blue Marble Image");
        link(chBMImage, wwCanvas, layer);
        layer = layers.getLayerByName("Blue Marble (WMS) 2004");
        link(chBMWMS, wwCanvas, layer);
        layer = layers.getLayerByName("Bing Imagery");
        link(chBing, wwCanvas, layer);
    }

    /**
     * Store visibility state under the given preferences node.
     * <p>
     * @param prefs a {@link Preferences} node to write under
     */
    public void storePrefs(Preferences prefs) {
        // create a subnode for view settings
        Preferences vnode = prefs.node(PREFNODE_BASECARTO);
        // store layer enablement
        putChbInPrefs(chBMImage, vnode);
        putChbInPrefs(chBMWMS, vnode);
        putChbInPrefs(chBing, vnode);
        putChbInPrefs(chBoundaries, vnode);
        putChbInPrefs(chPlaceNames, vnode);
    }

    /**
     * Loads visibility state from the given preferences node and sets it on the linked WWindPanel.
     * <p>
     * @param prefs a {@link Preferences} node to load from under
     */
    public void loadPrefs(Preferences prefs) {
        // get view settings subnode
        Preferences vnode = prefs.node(PREFNODE_BASECARTO);
        // load layer enablement
        getChbFromPrefs(chBMImage, vnode, true);
        getChbFromPrefs(chBMWMS, vnode, true);
        getChbFromPrefs(chBing, vnode, false);
        getChbFromPrefs(chBoundaries, vnode, false);
        getChbFromPrefs(chPlaceNames, vnode, false);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chPlaceNames = new javax.swing.JCheckBox();
        chBoundaries = new javax.swing.JCheckBox();
        chBMImage = new javax.swing.JCheckBox();
        chBMWMS = new javax.swing.JCheckBox();
        chBing = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Base Layers"));

        chPlaceNames.setText("Place Names");

        chBoundaries.setText("Political Boundaries");

        chBMImage.setText("BlueMarble Image");

        chBMWMS.setText("BlueMarble WMS");

        chBing.setText("Bing");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chBMImage)
            .addComponent(chBMWMS)
            .addComponent(chBoundaries)
            .addComponent(chPlaceNames)
            .addComponent(chBing)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(chPlaceNames)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBoundaries)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBMImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBMWMS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chBing))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chBMImage;
    private javax.swing.JCheckBox chBMWMS;
    private javax.swing.JCheckBox chBing;
    private javax.swing.JCheckBox chBoundaries;
    private javax.swing.JCheckBox chPlaceNames;
    // End of variables declaration//GEN-END:variables

    private void link(JCheckBox jcb, WorldWindow wwd, Layer layer) {
        if (layer != null) {
            jcb.setSelected(layer.isEnabled());
            jcb.setAction(new ToggleVisibilityAction(layer, wwd));
        } else {
            jcb.setEnabled(false);
        }
    }

    private void putChbInPrefs(JCheckBox chb, Preferences vnode) {
        vnode.putBoolean(chb.getText(), chb.isSelected());
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
