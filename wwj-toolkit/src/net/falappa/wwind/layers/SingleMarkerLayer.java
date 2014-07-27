package net.falappa.wwind.layers;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AbstractLayer;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;
import gov.nasa.worldwind.render.markers.Marker;
import gov.nasa.worldwind.render.markers.MarkerRenderer;
import gov.nasa.worldwind.terrain.SectorGeometryList;
import java.awt.Color;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import net.falappa.wwind.util.WWindUtils;

/**
 * Layer containing a single spherical marker.
 * <p>
 * The layer is not pickable and is meant to be altered programmatically only (does not offer support for user interaction). Some parts of
 * the implementation has been taken from WorldWind MarkerLayer class.
 * <p>
 * @author Alessandro Falappa
 */
public class SingleMarkerLayer extends AbstractLayer {

    private final MarkerRenderer markerRenderer = new MarkerRenderer();
    private final BasicMarkerAttributes attr = new BasicMarkerAttributes();
    private final ArrayList<Marker> mrkrs = new ArrayList<>(1);

    /**
     * Initializing constructor.
     * <p>
     * @param name the name of this layer
     */
    public SingleMarkerLayer(String name) {
        // properties of layer
        setName(name);
        setPickEnabled(false);
        // keep markers on top of terrain
        markerRenderer.setOverrideMarkerElevation(true);
        // painting attributes for markers
        attr.setShapeType(BasicMarkerShape.SPHERE);
        attr.setMaterial(Material.RED);
        attr.setMarkerPixels(8);
        attr.setMinMarkerSize(3);
    }

    /**
     * Getter for the marker color.
     * <p>
     * @return the current color
     */
    public Color getColor() {
        return attr.getMaterial().getDiffuse();
    }

    /**
     * Setter for the marker color.
     * <p>
     * @param col the new color
     */
    public void setColor(Color col) {
        attr.setMaterial(new Material(col));
    }

    /**
     * Setter for the marker position.
     * <p>
     * @param coords the new position
     */
    public void setPosition(Position coords) {
        if (mrkrs.size() == 1) {
            mrkrs.get(0).setPosition(coords);
        } else {
            Marker marker = new BasicMarker(coords, attr);
            mrkrs.add(marker);
        }
    }

    /**
     * Getter for the marker position.
     * <p>
     * @return the current position or null if no position has ever been set (no marker)
     */
    public Position getPosition() {
        if (isPositionSet()) {
            return mrkrs.get(0).getPosition();
        }
        return null;
    }

    /**
     * Clear the current marker position (hides the marker).
     */
    public void clear() {
        mrkrs.clear();
    }

    /**
     * Tells if the marker has ever been set.
     * <p>
     * @return true if the marker has been positioned, false otherwise
     */
    public boolean isPositionSet() {
        return !mrkrs.isEmpty();
    }

    /**
     * Load SurfShapesLayer preferences from Java Preferences API nodes.
     * <p>
     * @param baseNode the root node under which to look for this class own node
     */
    public void loadPrefs(Preferences baseNode) {
        // TODO caricamento da preferences
    }

    /**
     * Store SurfShapesLayer preferences using Java Preferences API nodes.
     * <p>
     * @param baseNode the root node under which to store this class own node
     */
    public void storePrefs(Preferences baseNode) {
        // TODO memorizzazione preferences
    }

    @Override
    protected void doRender(DrawContext dc) {
        draw(dc, null);
    }

    @Override
    protected void doPick(DrawContext dc, java.awt.Point pickPoint) {
        draw(dc, pickPoint);
    }

    // taken from WorldWind MarkerLayer
    protected void draw(DrawContext dc, java.awt.Point pickPoint) {
        if (mrkrs.isEmpty()) {
            return;
        }
        if (dc.getVisibleSector() == null) {
            return;
        }
        SectorGeometryList geos = dc.getSurfaceGeometry();
        if (geos == null) {
            return;
        }
        markerRenderer.render(dc, mrkrs);
    }

    /**
     * Animate a given map bringing the current marker into view.
     * <p>
     * Does nothing if no position has been set.
     * <p>
     * @param wwd the <tt>WorldWindow</tt> to animate
     */
    public void flyToMOI(WorldWindow wwd) {
        if (isPositionSet()) {
            final Position pos = new Position(mrkrs.get(0).getPosition(), 100e3);
            WWindUtils.flyToPoint(wwd, pos);
        }
    }
}
