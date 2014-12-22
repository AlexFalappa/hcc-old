/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.falappa.wwind.layers;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.event.SelectListener;
import java.beans.PropertyChangeListener;

/**
 * Source of shape selection events.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public interface ShapeSelectionSource extends SelectListener {

    /**
     * Event fired on shapes selection.
     */
    public static final String EVT_SELECT_SHAPES = "selectShapes";
    /**
     * Event fired on shapes deselection.
     */
    public static final String EVT_DESELECT_SHAPES = "deselectShapes";

    /**
     * Links the layer to a given WorldWindow for receiving selection events.
     *
     * @param wwd the worldwindow to attach to
     */
    void linkTo(WorldWindow wwd);

    /**
     * Detaches the layer from the WorldWindow with regard to event processing.
     */
    void detach();

    /**
     * Add a property change listener that will be notified of "shape selection" (highlighting) events.
     *
     * @param listener the property change listener
     */
    void addShapeSelectionListener(PropertyChangeListener listener);

    /**
     * Removes a previously added property change listener.
     *
     * @param listener the property change listener
     */
    void removeShapeSelectionListener(PropertyChangeListener listener);

    /**
     * Gets a list of currently registered shape selection property change listeners
     *
     * @return a possibly empty array of property change listeners
     */
    PropertyChangeListener[] getShapeSelectionListeners();

}
