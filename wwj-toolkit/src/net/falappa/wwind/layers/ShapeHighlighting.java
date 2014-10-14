package net.falappa.wwind.layers;

import gov.nasa.worldwind.event.SelectEvent;
import java.awt.Color;

/**
 * Represent a shape highlighting capability.
 *
 * @author Alessandro Falappa
 */
public interface ShapeHighlighting {

    /**
     * Tells if shape highlighting on the configured mouse event is enabled.
     *
     * @see #setHighlightEvent(java.lang.String)
     * @return true if enabled
     */
    public boolean isHighlightingEnabled();

    /**
     * Toggles shape highlighting on the configured mouse event.
     *
     * @see #setHighlightEvent(java.lang.String)
     * @param highlightingEnabled true to enable, false otherwise
     */
    public void setHighlightingEnabled(boolean highlightingEnabled);

    /**
     * Tells if an annotation is shown on the higlighted shape.
     *
     * @return true if annotation shown
     */
    public boolean isShowAnnotation();

    /**
     * Asks for showing an annotation when highlighting.
     * <p>
     * The annotation content is implementation defined.
     *
     * @param showAnnotation true if annotation should be shown
     */
    public void setShowAnnotation(boolean showAnnotation);

    /**
     * Getter for the current highlighting mouse event.
     *
     * @return one of the {@link SelectEvent} mouse clicking constants
     */
    public String getHighlightEvent();

    /**
     * Setter for the current highlighting mouse click event.
     *
     * @param highlightEvent one of {@link SelectEvent#LEFT_CLICK},{@link SelectEvent#LEFT_DOUBLE_CLICK} or {@link SelectEvent#RIGHT_CLICK}
     * constants
     */
    public void setHighlightEvent(String highlightEvent);

    /**
     * Returns the current highlighting color.
     *
     * @return the current color
     */
    public Color getHighlightColor();

    /**
     * Set the current highlighting color.
     *
     * @param col the new color
     */
    public void setHighlightColor(Color col);

    /**
     * Returns the current highlighting opacity.
     *
     * @return the current opacity
     */
    public double getHighlightOpacity();

    /**
     * Set the current highlighting opacity.
     *
     * @param opacity the new opacity
     */
    public void setHighlightOpacity(double opacity);

    /**
     * Highlights the surface shape with the given name.
     *
     * @param id the shape identifier
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    public void highlightShape(String id) throws NoSuchShapeException;

}
