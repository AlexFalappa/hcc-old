package net.falappa.wwind.utils;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layers.Layer;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;

/**
 * A Swing {@link Action} to toggle WorldWind layers visibility.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class ToggleVisibilityAction extends AbstractAction {

    private final WorldWindow wwd;
    private final Layer layer;

    /**
     * Initializing constructor.
     *
     * @param layer the WorldWind layer to hide/show
     * @param wwd the WorldWindow to redraw
     */
    public ToggleVisibilityAction(Layer layer, WorldWindow wwd) {
        super(layer.getName());
        this.wwd = wwd;
        this.layer = layer;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        boolean toggle = ((JCheckBox) actionEvent.getSource()).isSelected();
        this.layer.setEnabled(toggle);
        wwd.redraw();
    }
}
