package net.falappa.wwind.helpers;

import gov.nasa.worldwind.Movable;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.MarkerAttributes;

/**
 * A marker implementing the Movable interface to allow dragging itself.
 *
 * @author Alessandro Falappa
 */
public class DraggableMarker extends BasicMarker implements Movable {

    /**
     * Initializing constructor.
     *
     * @param position the marker position
     * @param attrs marker attributes
     */
    public DraggableMarker(Position position, MarkerAttributes attrs) {
        super(position, attrs);
    }

    /**
     * Initializing constructor.
     *
     * @param position the marker position
     * @param attrs marker attributes
     * @param heading north heading angle
     */
    public DraggableMarker(Position position, MarkerAttributes attrs, Angle heading) {
        super(position, attrs, heading);
    }

    /**
     * Getter for the marker position.
     *
     * @return current position
     */
    @Override
    public Position getReferencePosition() {
        return position;
    }

    /**
     * Recalculate marker position as moved by a delta-position.
     *
     * @see Movable
     * @param position delta-position to add
     */
    @Override
    public void move(Position position) {
        this.position.add(position);
    }

    /**
     * Place marker to a new position.
     *
     * @see Movable
     * @param position the new position
     */
    @Override
    public void moveTo(Position position) {
        this.position = position;
    }

}
