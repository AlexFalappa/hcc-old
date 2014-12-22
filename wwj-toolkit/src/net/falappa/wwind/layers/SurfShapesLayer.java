package net.falappa.wwind.layers;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.SelectEvent;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.Preferences;
import net.falappa.wwind.utils.WWindUtils;

/**
 * A WorldWind layer (<tt>RenderableLayer</tt> implementation) managing a set of surface shapes (polygons, polylines, circles, sectors and
 * quads).
 * <p>
 * Each shape is identified by a literal name. All shapes inherit a set of layer wide attributes (color and opacity) that can be overridden
 * shape by shape and optionally reset.
 * <p>
 * The layer also manages shapes mouse click selection and highlighting with an annotation (one at a time). Selection listeners can be
 * registered to be notified of shape selection changes.
 * <p>
 * The layer offers other useful methods such as "flying to" and programatically highlighting a shape.
 *
 * @author Alessandro Falappa
 */
public class SurfShapesLayer extends RenderableLayer implements ShapeSelectionSource, SurfShapeLayer {

    private static final float HIGHL_INSIDE_OPACITY = 0.7f;
    private static final float NORM_INSIDE_OPACITY = 0.4f;

    private static final String PROPERTY_SELECTION = "shapeSelection";
    private final BasicShapeAttributes attr = new BasicShapeAttributes();
    private final BasicShapeAttributes attrHigh = new BasicShapeAttributes();
    private final HashMap<String, SurfaceShape> shapesById = new HashMap<>();
    private final GlobeAnnotation popupAnnotation = new GlobeAnnotation("", Position.ZERO);
    private WorldWindow wwd;
    private SurfaceShape prevPopupShape;
    private String highlightEvent = SelectEvent.LEFT_CLICK;
    private boolean highlightingEnabled = true;
    private boolean showAnnotation = true;

    /**
     * Initializing constructor.
     *
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

    @Override
    public void linkTo(WorldWindow wwd) {
        this.wwd = wwd;
        wwd.addSelectListener(this);
    }

    @Override
    public void detach() {
        wwd.removeSelectListener(this);
    }

    @Override
    public void dispose() {
        detach();
        super.dispose();
    }

    @Override
    public boolean isHighlightingEnabled() {
        return highlightingEnabled;
    }

    @Override
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

    @Override
    public boolean isShowAnnotation() {
        return showAnnotation;
    }

    @Override
    public void setShowAnnotation(boolean showAnnotation) {
        this.showAnnotation = showAnnotation;
        if (!showAnnotation) {
            popupAnnotation.getAttributes().setVisible(false);
            wwd.redraw();
        }
    }

    @Override
    public String getHighlightEvent() {
        return highlightEvent;
    }

    @Override
    public void setHighlightEvent(String highlightEvent) {
        if (highlightEvent.equals(SelectEvent.LEFT_CLICK) || highlightEvent.equals(SelectEvent.LEFT_DOUBLE_CLICK) || highlightEvent.equals(
                SelectEvent.RIGHT_CLICK)) {
            this.highlightEvent = highlightEvent;
        } else {
            throw new IllegalArgumentException("Unsupported select event for highlighting!");
        }
    }

    @Override
    public Color getColor() {
        return attr.getOutlineMaterial().getDiffuse();
    }

    @Override
    public void setColor(Color col) {
        attr.setOutlineMaterial(new Material(col));
        attr.setInteriorMaterial(new Material(col.brighter().brighter()));
    }

    @Override
    public double getOpacity() {
        return attr.getOutlineOpacity();
    }

    @Override
    public void setOpacity(double opacity) {
        attr.setOutlineOpacity(opacity);
        attr.setInteriorOpacity(NORM_INSIDE_OPACITY * opacity);
    }

    @Override
    public Color getHighlightColor() {
        return attrHigh.getOutlineMaterial().getDiffuse();
    }

    @Override
    public void setHighlightColor(Color col) {
        attrHigh.setOutlineMaterial(new Material(col));
        attrHigh.setInteriorMaterial(new Material(col.brighter().brighter()));
    }

    @Override
    public double getHighlightOpacity() {
        return attrHigh.getOutlineOpacity();
    }

    @Override
    public void setHighlightOpacity(double opacity) {
        attrHigh.setOutlineOpacity(opacity);
        attrHigh.setInteriorOpacity(HIGHL_INSIDE_OPACITY * opacity);
    }

    /**
     * Adds or substitutes a named circular surface shape.
     *
     * @param lat center latitude in decimal degrees
     * @param lon center longitude in decimal degrees
     * @param rad radius in meters
     * @param id the shape identifier
     */
    public void addSurfCircle(double lat, double lon, double rad, String id) {
        SurfaceCircle shape = new SurfaceCircle(attr, LatLon.fromDegrees(lat, lon), rad);
        if (id != null) {
            shape.setValue(AVKey.HOVER_TEXT, id);
        }
        //set this layer as custom property of the shape
        shape.setValue(AVKey.LAYER, this);
        SurfaceShape old = shapesById.put(id, shape);
        if (old != null) {
            removeRenderable(old);
        }
        addRenderable(shape);
    }

    /**
     * Adds or substitutes a named polygonal surface shape.
     *
     * @param coords the points of the polygon outer boundary
     * @param id the shape identifier
     */
    public void addSurfPoly(List<LatLon> coords, String id) {
        SurfacePolygon shape = new SurfacePolygon(coords);
        shape.setAttributes(attr);
        shape.setHighlightAttributes(attrHigh);
        if (id != null) {
            shape.setValue(AVKey.HOVER_TEXT, id);
        }
        //set this layer as custom property of the shape
        shape.setValue(AVKey.LAYER, this);
        SurfaceShape old = shapesById.put(id, shape);
        if (old != null) {
            removeRenderable(old);
        }
        addRenderable(shape);
    }

    /**
     * Adds or substitutes a named regular quadrilateral surface shape.
     *
     * @param lat center latitude in decimal degrees
     * @param lon center longitude in decimal degrees
     * @param w width in meters
     * @param h height in meters
     * @param id the shape identifier
     */
    public void addSurfQuad(double lat, double lon, double w, double h, String id) {
        SurfaceQuad shape = new SurfaceQuad(attr, LatLon.fromDegrees(lat, lon), w, h);
        shape.setHighlightAttributes(attrHigh);
        if (id != null) {
            shape.setValue(AVKey.HOVER_TEXT, id);
        }
        //set this layer as custom property of the shape
        shape.setValue(AVKey.LAYER, this);
        SurfaceShape old = shapesById.put(id, shape);
        if (old != null) {
            removeRenderable(old);
        }
        addRenderable(shape);
    }

    /**
     * Adds or substitutes a named lat lon range (sector) surface shape.
     *
     * @param minlat minimum latitude in decimal degrees
     * @param minlon minimum longitude in decimal degrees
     * @param maxlat maximum latitude in decimal degrees
     * @param maxlon maximum longitude in decimal degrees
     * @param id the shape identifier
     */
    public void addSurfSect(double minlat, double minlon, double maxlat, double maxlon, String id) {
        SurfaceSector shape = new SurfaceSector(attr, Sector.fromDegrees(minlat, maxlat, minlon, maxlon));
        shape.setPathType(AVKey.LINEAR);
        shape.setHighlightAttributes(attrHigh);
        if (id != null) {
            shape.setValue(AVKey.HOVER_TEXT, id);
        }
        //set this layer as custom property of the shape
        shape.setValue(AVKey.LAYER, this);
        SurfaceShape old = shapesById.put(id, shape);
        if (old != null) {
            removeRenderable(old);
        }
        addRenderable(shape);
    }

    @Override
    public SurfaceShape getSurfShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        return shapesById.get(id);
    }

    /**
     * Accessor for a named polygonal surface shape.
     *
     * @param id the shape identifier
     * @return the requested shape or null if the shape was not a polygon
     * @throws NoSuchShapeException if no shape with the given name exists
     */
    public SurfacePolygon getSurfPoly(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        final SurfaceShape ret = shapesById.get(id);
        return ret instanceof SurfacePolygon ? (SurfacePolygon) ret : null;
    }

    @Override
    public void removeSurfShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        final SurfaceShape ret = shapesById.remove(id);
        removeRenderable(ret);
    }

    @Override
    public int getNumShapes() {
        return getNumRenderables();
    }

    @Override
    public void setSurfShapeColor(String id, Color col, double opacity) throws NoSuchShapeException {
        SurfaceShape shape = getSurfShape(id);
        BasicShapeAttributes newAttr = new BasicShapeAttributes(attr);
        newAttr.setOutlineMaterial(new Material(col));
        newAttr.setInteriorMaterial(new Material(col.brighter().brighter()));
        newAttr.setOutlineOpacity(opacity);
        newAttr.setInteriorOpacity(NORM_INSIDE_OPACITY * opacity);
        shape.setAttributes(newAttr);
    }

    @Override
    public void resetSurfShapeColor(String id) throws NoSuchShapeException {
        SurfaceShape shape = getSurfShape(id);
        shape.setAttributes(attr);
    }

    @Override
    public void resetAllSurfShapeColors() {
        for (SurfaceShape shp : shapesById.values()) {
            shp.setAttributes(attr);
        }
    }

    @Override
    public void setSurfShapeVisible(String id, boolean flag) throws NoSuchShapeException {
        SurfaceShape shape = getSurfShape(id);
        shape.setVisible(flag);
    }

    @Override
    public void flyToHiglhlightShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        final SurfaceShape shape = shapesById.get(id);
        WWindUtils.flyToObjects(wwd, Arrays.asList(shape));
        internalHighlight(shape, true);
    }

    @Override
    public void flyToShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        WWindUtils.flyToObjects(wwd, Arrays.asList(shapesById.get(id)));
    }

    @Override
    public void highlightShape(String id) throws NoSuchShapeException {
        if (!shapesById.containsKey(id)) {
            throw new NoSuchShapeException(String.format("No such shape: %s", id));
        }
        internalHighlight(shapesById.get(id), true);
    }

    @Override
    public void removeAllShapes() {
        super.removeAllRenderables();
        shapesById.clear();
        // re-add the hidden notation
        popupAnnotation.getAttributes().setVisible(false);
        addRenderable(popupAnnotation);
    }

    @Override
    public void removeAllRenderables() {
        throw new IllegalStateException("Use removeAllShapes() method instead!");
    }

    /**
     * Implementation of WorldWind selection API.
     *
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
                final Object layer = shape.getValue(AVKey.LAYER);
                if (layer instanceof SurfShapesLayer) {
                    SurfShapesLayer sl = (SurfShapesLayer) layer;
                    if (sl.getName().equals(getName())) {
                    internalHighlight(shape, false);
                }
                wwd.redraw();
                }
            }
        }
    }

    @Override
    public void addShapeSelectionListener(PropertyChangeListener listener) {
        getChangeSupport().addPropertyChangeListener(PROPERTY_SELECTION, listener);
    }

    @Override
    public void removeShapeSelectionListener(PropertyChangeListener listener) {
        getChangeSupport().removePropertyChangeListener(PROPERTY_SELECTION, listener);
    }

    @Override
    public PropertyChangeListener[] getShapeSelectionListeners() {
        return getChangeSupport().getPropertyChangeListeners(PROPERTY_SELECTION);
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

    // manages change of attributes for highlighting, annotation bubble toggle, event firing
    private void internalHighlight(SurfaceShape shape, boolean noDeselect) {
        if (!highlightingEnabled) {
            return;
        }
        String shpId = (String) shape.getValue(AVKey.HOVER_TEXT);
        // if annotation visible and same shape
        if (shape.equals(prevPopupShape) && shape.isHighlighted()) {
            if (!noDeselect) {
                // hide annotation and de-highlight
                popupAnnotation.getAttributes().setVisible(false);
                shape.setHighlighted(false);
                // forget previous highlighted shape and fire deselection event
                prevPopupShape = null;
                firePropertyChange(new PropertyChangeEvent(this, PROPERTY_SELECTION, shpId, null));
            }
        } else {
            if (showAnnotation) {
                // find shape centroid
                final Sector boundingSector = Sector.boundingSector(shape.getLocations(wwd.getModel().getGlobe()));
                Position centroid = new Position(boundingSector.getCentroid(), 0d);
                // prepare and show annotation
                popupAnnotation.setText(shpId);
                popupAnnotation.setPosition(centroid);
                popupAnnotation.getAttributes().setVisible(true);
            }
            // highlight shape
            shape.setHighlighted(true);
            if (prevPopupShape != null) {
                // de-highlight previous shape and fire deselection event
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
}
