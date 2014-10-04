/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.falappa.wwind.layers;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.render.SurfaceShape;
import java.awt.Color;

/**
 * A
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public interface SurfShapeLayer {

    /**
     * Links the layer to a given WorldWindow.
     * <p>
     * @param wwd the worldwindow to attach to
     */
    void linkTo(WorldWindow wwd);

    /**
     * Detaches the layer from the WorldWindow.
     */
    void detach();

    /**
     * Detaches the layer and clears all its shapes.
     */
    void dispose();

    /**
     * Returns the current color of all layer shapes.
     * <p>
     * @return the current color
     */
    Color getColor();

    /**
     * Set the color of all layer shapes.
     * <p>
     * @param col the new color
     */
    void setColor(Color col);

    /**
     * Returns the current opacity of all layer shapes.
     * <p>
     * @return the current opacity
     */
    double getOpacity();

    /**
     * Set the opacity of all layer shapes.
     * <p>
     * @param opacity the new opacity
     */
    void setOpacity(double opacity);

    /**
     * Accessor for a named surface shape.
     * <p>
     * @param id the shape identifier
     * @return the requested shape
     * @throws NoSuchShapeException if no shape with the given name exists if no shape with the given name exists
     */
    SurfaceShape getSurfShape(String id) throws NoSuchShapeException;

    /**
     * Removes the surface shape of the given name.
     * <p>
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void removeSurfShape(String id) throws NoSuchShapeException;

    /**
     * Removes all the surface shapes of the layer.
     */
    void removeAllRenderables();

    /**
     * Sets the color and opacity of the surface shape with the given name.
     * <p>
     * The color and opacity becomes independent from those of the layer.
     * <p>
     * @param id the shape identifier
     * @param col new shape color
     * @param opacity new shape opacity
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void setSurfShapeColor(String id, Color col, double opacity) throws NoSuchShapeException;

    /**
     * Reset the color and opacity of the surface shape with the given name to those of the layer.
     * <p>
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void resetSurfShapeColor(String id) throws NoSuchShapeException;

    /**
     * Toggles the visibility of the surface shape with the given name.
     * <p>
     * @param id the shape identifier
     * @param flag true to show, false to hide
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void setSurfShapeVisible(String id, boolean flag) throws NoSuchShapeException;

    /**
     * Animates the map bringing the surface shape with the given name into view and highlights it.
     * <p>
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void flyToHiglhlightShape(String id) throws NoSuchShapeException;

    /**
     * Animates the map bringing the surface shape with the given name into view.
     * <p>
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void flyToShape(String id) throws NoSuchShapeException;

}
