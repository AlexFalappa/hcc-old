package net.falappa.wwind.layers;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.render.SurfaceQuad;
import gov.nasa.worldwind.render.SurfaceSector;
import gov.nasa.worldwind.render.SurfaceShape;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.Preferences;
import net.falappa.wwind.util.WWindUtils;

/**
 * A WorldWind layer (<tt>RenderableLayer</tt> implementation) managing a set of surface shapes (polgons, polylines, circles, sectors and
 * quads).
 * <p>
 * Each shape is identified by a literal name. All shapes inherit a set of layer wide attributes (color and opacity) that can be overridden
 * shape by shape and optionally reset.
 * <p>
 * The layer also manages shapes selection and highlighting with an annotation (one at a time). Selection listeners can be registered to be
 * notified of shape selection changes.
 * <p>
 * The layer offers other useful methods such as "flying to" a shape.
 * <p>
 * @author Alessandro Falappa
 */
public class SurfShapesLayer extends RenderableLayer implements SelectListener {

    private static final float HIGHL_INSIDE_OPACITY = 0.7f;
    private static final float NORM_INSIDE_OPACITY = 0.4f;

    /**
     * Event fired on shapes selection.
     */
    public static final String EVT_SELECT_SHAPES = "selectShapes";

    /**
     * Event fired on shapes deselection.
     */
    public static final String EVT_DESELECT_SHAPES = "deselectShapes";
    private static final String PROPERTY_SELECTION = "shapeSelection";
    private final BasicShapeAttributes attr = new BasicShapeAttributes();
    private final BasicShapeAttributes attrHigh = new BasicShapeAttributes();
    private final HashMap<String, SurfaceShape> shapesById = new HashMap<>();
    private final GlobeAnnotation popupAnnotation = new GlobeAnnotation("", Position.ZERO);
    private WorldWindow wwd;
    private SurfaceShape prevPopupShape;
    private String highlightEvent = SelectEvent.LEFT_CLICK;
    private boolean highlightingEnabled = true;

    /**
     * Initializing constructor.
     * <p>
     * @param name the name of this layer
     */
    public SurfShapesLayer(String name) {
        // properties of layer
        setName(name);
        setEnabled(true);
        // painting attributes for shapes
        attr.setOutlineMaterial(Material.ORANGE);
        attr.setOutlineWidth(1.5);
        Material intMat = new Material(Color.ORANGE.brighter().brighter());
        attr.setInteriorMaterial(intMat);
        attr.setInteriorOpacity(NORM_INSIDE_OPACITY);
        // painting attributes for hihglighted shapes
        attrHigh.setOutlineMaterial(Material.BLACK);
        attrHigh.setOutlineWidth(2);
        attrHigh.setInteriorMaterial(Material.WHITE);
        attrHigh.setInteriorOpacity(HIGHL_INSIDE_OPACITY);
        // popup annotation attributes
        AnnotationAttributes attrAnno = new AnnotationAttributes();
        attrAnno.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
        attrAnno.setFrameShape(AVKey.SHAPE_RECTANGLE);
        attrAnno.setCornerRadius(3);
        attrAnno.setDrawOffset(new Point(0, 8));
        attrAnno.setLeaderGapWidth(8);
        attrAnno.setTextColor(Color.BLACK);
        attrAnno.setBackgroundColor(new Color(1f, 1f, 1f, .85f));
        attrAnno.setBorderColor(new Color(0xababab));
        attrAnno.setInsets(new Insets(3, 3, 3, 3));
        attrAnno.setVisible(false);
        popupAnnotation.setAttributes(attrAnno);
        popupAnnotation.setAlwaysOnTop(true);
        addRenderable(popupAnnotation);
    }

    /**
     * Links the layer to a given WorldWindow.
     * <p>
     * @param wwd the worldwindow to attach to
     */
    public void linkTo(WorldWindow wwd) {
        this.wwd = wwd;
        wwd.addSelectListener(this);
    }

    /**
     * Detaches the layer from the WorldWindow.
     */
    public void detach() {
        wwd.removeSelectListener(this);
    }

    /**
     * Detaches the layer and clears all its shapes.
     */
    @Override
    public void dispose() {
        detach();
        super.dispose();
    }

    /**
     * Tells if shape highlighting on the configured mouse event is enabled.
     * <p>
     * @see #setHighlightEvent(java.lang.String)
     * @return true if enabled
     */
    public boolean isHighlightingEnabled() {
        return highlightingEnabled;
    }

    /**
     * Toggles shape highlighting on the configured mouse event.
     * <p>
     * @see #setHighlightEvent(java.lang.String)
     * @param highlightingEnabled true to enable, false otherwise
     */
    public void setHighlightingEnabled(boolean highlightingEnabled) {
        this.highlightingEnabled = highlightingEnabled;
        // hide popup and clear highlighed object when disabling
        if (!highlightingEnabled) {
            popupAnnotation.getAttributes().setVisible(false);
            if (prevPopupShape != null) {
                prevPopupShape.setHighlighted(false);
            }
            wwd.redraw();
        }
    }

    /**
     * Getter for the current highlighting mouse event.
     * <p>
     * @return one of the {@link SelectEvent} constants
     */
    public String getHighlightEvent() {
        return highlightEvent;
    }

    /**
     * Setter for the current highlighting mouse event.
     * <p>
     * @param highlightEvent one of the {@link SelectEvent} constants
     */
    public void setHighlightEvent(String highlightEvent) {
        this.highlightEvent = highlightEvent;
    }

    /**
     * Returns the current layer wide shape color.
     * <p>
     * @return the current color
     */
    public Color getColor() {
        return attr.getOutlineMaterial().getDiffuse();
    }

    /**
     * Set the layer wide shape color.
     * <p>
     * @param col the new color
     */
    public void setColor(Color col) {
        attr.setOutlineMaterial(new Material(col));
        attr.setInteriorMaterial(new Material(col.brighter().brighter()));
    }

    /**
     * Returns the current layer wide shape opacity.
     * <p>
     * @return the current opacity
     */
    @Override
    public double getOpacity() {
        return attr.getOutlineOpacity();
    }

    /**
     * Set the current layer wide shape opacity.
     * <p>
     * @param opacity the new opacity
     */
    @Override
    public void setOpacity(double opacity) {
        attr.setOutlineOpacity(opacity);
        attr.setInteriorOpacity(NORM_INSIDE_OPACITY * opacity);
    }

    /**
     * Returns the current highlighting color.
     * <p>
     * @return the current color
     */
    public Color getHighlightColor() {
        return attrHigh.getOutlineMaterial().getDiffuse();
    }

    /**
     * Set the current highlighting color.
     * <p>
     * @param col the new color
     */
    public void setHighlightColor(Color col) {
        attrHigh.setOutlineMaterial(new Material(col));
        attrHigh.setInteriorMaterial(new Material(col.brighter().brighter()));
    }

    /**
     * Returns the current highlighting opacity.
     * <p>
     * @return the current opacity
     */
    public double getHighlightOpacity() {
        return attrHigh.getOutlineOpacity();
    }

    /**
     * Set the current highlighting opacity.
     * <p>
     * @param opacity the new opacity
     */
    public void setHighlightOpacity(double opacity) {
        attrHigh.setOutlineOpacity(opacity);
        attrHigh.setInteriorOpacity(HIGHL_INSIDE_OPACITY * opacity);
    }

    /**
     *
     * @param lat
     * @param lon
     * @param rad
     * @param id
     */
    public void addSurfCircle(double lat, double lon, double rad, String id) {
        SurfaceCircle shape = new SurfaceCircle(attr, LatLon.fromDegrees(lat, lon), rad);
        shape.setValue(AVKey.HOVER_TEXT, id);
        shapesById.put(id, shape);
        addRenderable(shape);
    }

    /**
     *
     * @param coords
     * @param id
     */
    public void addSurfPoly(List<LatLon> coords, String id) {
        SurfacePolygon shape = new SurfacePolygon(coords);
        shape.setAttributes(attr);
        shape.setHighlightAttributes(attrHigh);
        if (id != null) {
            shape.setValue(AVKey.HOVER_TEXT, id);
        }
        shapesById.put(id, shape);
        addRenderable(shape);
    }

    /**
     *
     * @param lat
     * @param lon
     * @param w
     * @param h
     * @param id
     */
    public void addSurfQuad(double lat, double lon, double w, double h, String id) {
        SurfaceQuad shape = new SurfaceQuad(attr, LatLon.fromDegrees(lat, lon), w, h);
        shape.setValue(AVKey.HOVER_TEXT, id);
        addRenderable(shape);
    }

    /**
     *
     * @param minlat
     * @param minlon
     * @param maxlat
     * @param maxlon
     * @param id
     */
    public void addSurfSect(double minlat, double minlon, double maxlat, double maxlon, String id) {
        SurfaceSector shape = new SurfaceSector(attr, Sector.fromDegrees(minlat, maxlat, minlon, maxlon));
        shape.setPathType(AVKey.LINEAR);
        shapesById.put(id, shape);
        addRenderable(shape);
    }

    /**
     *
     * @param id
     * @return
     * @throws NoSuchShapeException
     */
    public SurfaceShape getSurfShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        return shapesById.get(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws NoSuchShapeException
     */
    public SurfacePolygon getSurfPoly(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        final SurfaceShape ret = shapesById.get(id);
        return ret instanceof SurfacePolygon ? (SurfacePolygon) ret : null;
    }

    /**
     *
     * @param id
     * @throws NoSuchShapeException
     */
    public void removeSurfShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        final SurfaceShape ret = shapesById.remove(id);
        removeRenderable(ret);
    }

    /**
     *
     * @param id
     * @param col
     * @param opacity
     * @throws NoSuchShapeException
     */
    public void setSurfShapeColor(String id, Color col, double opacity) throws NoSuchShapeException {
        BasicShapeAttributes newAttr = new BasicShapeAttributes(attr);
        newAttr.setOutlineMaterial(new Material(col));
        newAttr.setInteriorMaterial(new Material(col.brighter().brighter()));
        newAttr.setOutlineOpacity(opacity);
        newAttr.setInteriorOpacity(NORM_INSIDE_OPACITY * opacity);
        SurfaceShape shape = getSurfShape(id);
        shape.setAttributes(newAttr);
    }

    /**
     *
     * @param id
     * @throws NoSuchShapeException
     */
    public void resetSurfShapeColor(String id) throws NoSuchShapeException {
        SurfaceShape shape = getSurfShape(id);
        shape.setAttributes(attr);
    }

    /**
     *
     * @param id
     * @param flag
     * @throws NoSuchShapeException
     */
    public void setSurfShapeVisible(String id, boolean flag) throws NoSuchShapeException {
        SurfaceShape shape = getSurfShape(id);
        shape.setVisible(flag);
    }

    /**
     *
     * @param id
     * @throws NoSuchShapeException
     */
    public void flyToHiglhlightShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        final SurfaceShape shape = shapesById.get(id);
        internalFlyTo(shape);
        internalHighlight(shape, true);
    }

    /**
     *
     * @param id
     * @throws NoSuchShapeException
     */
    public void flyToShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        internalFlyTo(shapesById.get(id));
    }

    private void internalFlyTo(SurfaceShape shape) {
        Sector sector = Sector.boundingSector(shape.getLocations(wwd.getModel().getGlobe()));
        WWindUtils.flyToSector(wwd, sector);
    }

    /**
     *
     * @param id
     * @throws NoSuchShapeException
     */
    public void highlightShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        internalHighlight(shapesById.get(id), true);
    }

    private void internalHighlight(SurfaceShape shape, boolean noDeselect) {
        if (!highlightingEnabled) {
            return;
        }
        String shpId = (String) shape.getValue(AVKey.HOVER_TEXT);
        // if annotation visible and same shape
        if (popupAnnotation.getAttributes().isVisible() && shape.equals(prevPopupShape)) {
            if (!noDeselect) {
                // hide annotation and de highlight
                popupAnnotation.getAttributes().setVisible(false);
                shape.setHighlighted(false);
                // forget previous highlighted shape and fire deselection event
                prevPopupShape = null;
                firePropertyChange(new PropertyChangeEvent(this, PROPERTY_SELECTION, shpId, null));
            }
        } else {
            // find shape centroid
            final Sector boundingSector = Sector.boundingSector(shape.getLocations(wwd.getModel().getGlobe()));
            Position centroid = new Position(boundingSector.getCentroid(), 0d);
            // prepare and show annotation
            popupAnnotation.setText(shpId);
            popupAnnotation.setPosition(centroid);
            popupAnnotation.getAttributes().setVisible(true);
            // highlight shape
            shape.setHighlighted(true);
            if (prevPopupShape != null) {
                // de highlight previous shape and fire deselection event
                prevPopupShape.setHighlighted(false);
                firePropertyChange(new PropertyChangeEvent(this, PROPERTY_SELECTION, prevPopupShape.getValue(AVKey.HOVER_TEXT), shpId));
            } else {
                // fire event only
                firePropertyChange(new PropertyChangeEvent(this, PROPERTY_SELECTION, null, shpId));
            }
            // remember shape
            prevPopupShape = shape;
        }
    }

    /**
     *
     */
    @Override
    public void removeAllRenderables() {
        super.removeAllRenderables();
        shapesById.clear();
        // re-add the hidden notation
        popupAnnotation.getAttributes().setVisible(false);
        addRenderable(popupAnnotation);
    }

    /**
     * Implementation of WorldWind selection API.
     * <p>
     * @param event selection event
     */
    @Override
    public void selected(SelectEvent event) {
        // short circuit exit if highlighting is not enabled
        if (!highlightingEnabled) {
            return;
        }
        if (event.getEventAction().equals(highlightEvent)) {
            final Object topObject = event.getTopObject();
            if (topObject instanceof SurfaceShape) {
                SurfaceShape shape = (SurfaceShape) topObject;
                if (shapesById.containsValue(shape)) {
                    internalHighlight(shape, false);
                }
                wwd.redraw();
            }
        }
    }

    /**
     *
     * @param listener
     */
    public void addShapeSelectionListener(PropertyChangeListener listener) {
        getChangeSupport().addPropertyChangeListener(PROPERTY_SELECTION, listener);
    }

    /**
     *
     * @param listener
     */
    public void removeShapeSelectionListener(PropertyChangeListener listener) {
        getChangeSupport().removePropertyChangeListener(PROPERTY_SELECTION, listener);
    }

    /**
     *
     * @return
     */
    public PropertyChangeListener[] getShapeSelectionListeners() {
        return getChangeSupport().getPropertyChangeListeners(PROPERTY_SELECTION);
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

}
