package net.falappa.wwind.widgets;

import gov.nasa.worldwind.WorldWindow;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.falappa.wwind.layers.SurfShapeLayer;

/**
 * A panel listing and controlling the visibility of {@link SurfShapesLayer} objects managed by a {@link WWindPanel}.
 * <p>
 * Layers are listed in the order they are added.
 *
 * @author Alessandro Falappa
 */
public class SurfShapeLayersVisibilityPanel extends javax.swing.JPanel implements PropertyChangeListener {

    /**
     * Default constructor.
     */
    public SurfShapeLayersVisibilityPanel() {
        initComponents();
    }

    /**
     * Attach this component to the given {@link WWindPanel}.
     *
     * @param wwp the WWindPanel to attach to
     */
    public void linkTo(WWindPanel wwp) {
        // add existing surface shape layers
        for (SurfShapeLayer sl : wwp.getAllSurfShapeLayers()) {
            addSurfShapeLayer(sl, wwp.getWWCanvas());
        }
        // add a listener for future additions/removal
        wwp.addSurfShapeListener(this);
    }

    /**
     * Property change listener implementation.
     * <p>
     * Listens to {@link SurfShapesLayer} additions/removals
     *
     * @param evt property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase(WWindPanel.EVENT_SURF_LAYER_ADDED)) {
            WWindPanel wwp = (WWindPanel) evt.getSource();
            addSurfShapeLayer((SurfShapeLayer) evt.getNewValue(), wwp.getWWCanvas());
        }
        if (evt.getPropertyName().equalsIgnoreCase(WWindPanel.EVENT_SURF_LAYER_REMOVED)) {
            removeSurfShapeLayer((SurfShapeLayer) evt.getOldValue());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroller = new javax.swing.JScrollPane();
        pLayers = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        scroller.setBorder(javax.swing.BorderFactory.createTitledBorder("Layers"));

        pLayers.setLayout(new javax.swing.BoxLayout(pLayers, javax.swing.BoxLayout.PAGE_AXIS));
        scroller.setViewportView(pLayers);

        add(scroller, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pLayers;
    private javax.swing.JScrollPane scroller;
    // End of variables declaration//GEN-END:variables

    private void addSurfShapeLayer(SurfShapeLayer sl, WorldWindow wwd) {
        if (sl == null) {
            return;
        }
        // add a LayerPropPanel with the layer name
        LayerPropsPanel paneLprops = new LayerPropsPanel(wwd, sl);
        paneLprops.setName(sl.getName());
        pLayers.add(paneLprops);
        // relayout component
        pLayers.revalidate();
        scroller.repaint();
    }

    private void removeSurfShapeLayer(SurfShapeLayer sl) {
        // remove component whose name equals the layer name (checbox and strut)
        for (Component cmp : pLayers.getComponents()) {
            if (cmp.getName().equalsIgnoreCase(sl.getName())) {
                pLayers.remove(cmp);
            }
        }
        // relayout component
        pLayers.revalidate();
        scroller.repaint();
    }
}
