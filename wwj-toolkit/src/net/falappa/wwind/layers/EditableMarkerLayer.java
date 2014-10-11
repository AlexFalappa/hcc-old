package net.falappa.wwind.layers;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AbstractLayer;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;
import gov.nasa.worldwind.render.markers.Marker;
import gov.nasa.worldwind.render.markers.MarkerRenderer;
import gov.nasa.worldwind.terrain.SectorGeometryList;
import gov.nasa.worldwind.util.BasicDragger;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import net.falappa.wwind.helpers.DraggableMarker;

/**
 * A layer for editing a single marker placement.
 * <p>
 * Fires property change events during editing.
 *
 * @author Alessandro Falappa
 */
public class EditableMarkerLayer extends AbstractLayer {

    /**
     * Event fired on layer clearing.
     */
    public static final String EVENT_CLEAR = "EditableMarkerLayer.Clear";

    /**
     * Event fired after marker position has been set after clearing.
     */
    public static final String EVENT_POSITION_SET = "EditableMarkerLayer.SetPosition";

    /**
     * Event fired after existing marker position has been changed.
     */
    public static final String EVENT_POSITION_CHANGED = "EditableMarkerLayer.ChangePosition";

    /**
     * Event fired after an editing session has been started.
     */
    public static final String EVENT_EDITING_START = "EditableMarkerLayer.EditStart";

    /**
     * Event fired after an editing session has been ended.
     */
    public static final String EVENT_EDITING_STOP = "EditableMarkerLayer.EditStop";
    private final MarkerRenderer markerRenderer = new MarkerRenderer();
    private final BasicMarkerAttributes attr = new BasicMarkerAttributes();
    private final ArrayList<Marker> mrkrs = new ArrayList<>(1);
    private final WorldWindow wwd;
    private final BasicDragger bd;
    private final MouseAdapter maPointSel = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent mev) {
            if (mev.getButton() == MouseEvent.BUTTON1) {
                Position curPos = wwd.getCurrentPosition();
                setPosition(curPos);
                wwd.redraw();
                mev.consume();
            }
        }
    };

    /**
     * Default constructor.
     * <p>
     * @param wwd WorldWindow to link to
     * @param name layer name
     */
    public EditableMarkerLayer(WorldWindow wwd, String name) {
        // properties of layer
        setName(name);
        // keep markers on top of terrain
        markerRenderer.setOverrideMarkerElevation(true);
        // painting attributes for markers
        attr.setShapeType(BasicMarkerShape.SPHERE);
        attr.setMaterial(Material.RED);
        attr.setMarkerPixels(8);
        attr.setMinMarkerSize(3);
        this.wwd = wwd;
        // add the dragger
        bd = new BasicDragger(this.wwd, true);
//        wwd.addSelectListener(bd);
    }

    /**
     * Detaches layer from the WorldWindow.
     */
    @Override
    public void dispose() {
        wwd.removeSelectListener(bd);
        wwd.getInputHandler().removeMouseListener(maPointSel);
    }

    /**
     * Getter for the marker color.
     *
     * @return the current color
     */
    public Color getColor() {
        return attr.getMaterial().getDiffuse();
    }

    /**
     * Setter for the marker color.
     *
     * @param col the new color
     */
    public void setColor(Color col) {
        attr.setMaterial(new Material(col));
    }

    /**
     * Setter for the marker position.
     *
     * @param coords the new position
     */
    public void setPosition(Position coords) {
        if (mrkrs.size() == 1) {
            Position prevPos = getPosition();
            mrkrs.get(0).setPosition(coords);
            getChangeSupport().firePropertyChange(EVENT_POSITION_CHANGED, prevPos, getPosition());
        } else {
            Marker marker = new DraggableMarker(coords, attr);
            mrkrs.add(marker);
            getChangeSupport().firePropertyChange(EVENT_POSITION_SET, null, getPosition());
        }
    }

    /**
     * Getter for the marker position.
     *
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
        wwd.removeSelectListener(bd);
        mrkrs.clear();
        getChangeSupport().firePropertyChange(EVENT_CLEAR, getPosition(), null);
    }

    /**
     * Tells if the marker has ever been set.
     *
     * @return true if the marker has been positioned, false otherwise
     */
    public boolean isPositionSet() {
        return !mrkrs.isEmpty();
    }

    /**
     * Toggles between editing mode and normal mode.
     *
     * While in editing mode user clicks create/place the marker, while user drags move it.
     *
     * @param flag true to enable editing mode, false otherwise
     */
    public void setEditing(boolean flag) {
        if (flag) {
            wwd.addSelectListener(bd);
            wwd.getInputHandler().addMouseListener(maPointSel);
            getChangeSupport().firePropertyChange(EVENT_EDITING_START, null, null);
        } else {
            wwd.getInputHandler().removeMouseListener(maPointSel);
            getChangeSupport().firePropertyChange(EVENT_EDITING_STOP, null, null);
        }
    }

    /**
     * Load SurfShapesLayer preferences from Java Preferences API nodes.
     *
     * @param baseNode the root node under which to look for this class own node
     */
    public void loadPrefs(Preferences baseNode) {
        // TODO caricamento da preferences
    }

    /**
     * Store SurfShapesLayer preferences using Java Preferences API nodes.
     *
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

}
