/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.falappa.wwind.layers;

import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.render.SurfaceShape;
import java.awt.Color;

/**
 * A layer managing a set of named surface shapes.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public interface SurfShapeLayer extends Layer, ShapeHighlighting {

    /**
     * Returns the current color of all layer shapes.
     *
     * @return the current color
     */
    Color getColor();

    /**
     * Set the color of all layer shapes.
     *
     * @param col the new color
     */
    void setColor(Color col);

    /**
     * Accessor for a named surface shape.
     *
     * @param id the shape identifier
     * @return the requested shape
     * @throws NoSuchShapeException if no shape with the given name exists if no shape with the given name exists
     */
    SurfaceShape getSurfShape(String id) throws NoSuchShapeException;

    /**
     * Removes the surface shape of the given name.
     *
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void removeSurfShape(String id) throws NoSuchShapeException;

    /**
     * Removes all the surface shapes of the layer.
     */
    void removeAllShapes();

    /**
     * Get the total number of shapes in the layer.
     *
     * @return the number of shapes
     */
    int getNumShapes();

    /**
     * Sets the color and opacity of the surface shape with the given name.
     * <p>
     * The color and opacity becomes independent from those of the layer.
     *
     * @param id the shape identifier
     * @param col new shape color
     * @param opacity new shape opacity
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void setSurfShapeColor(String id, Color col, double opacity) throws NoSuchShapeException;

    /**
     * Reset the color and opacity of the surface shape with the given name to those of the layer.
     *
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void resetSurfShapeColor(String id) throws NoSuchShapeException;

    /**
     * Reset the color and opacity of all surface shapes to those of the layer.
     */
    public void resetAllSurfShapeColors();

    /**
     * Toggles the visibility of the surface shape with the given name.
     *
     * @param id the shape identifier
     * @param flag true to show, false to hide
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void setSurfShapeVisible(String id, boolean flag) throws NoSuchShapeException;

    /**
     * Animates the map bringing the surface shape with the given name into view and highlights it.
     *
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void flyToHiglhlightShape(String id) throws NoSuchShapeException;

    /**
     * Animates the map bringing the surface shape with the given name into view.
     *
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    void flyToShape(String id) throws NoSuchShapeException;

}
