package net.falappa.wwind.widgets;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.RenderingExceptionListener;
import gov.nasa.worldwind.exception.WWAbsentRequirementException;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.util.measure.MeasureTool;
import gov.nasa.worldwind.util.measure.MeasureToolController;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.FlatOrbitView;
import gov.nasa.worldwindx.examples.util.StatusLayer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.falappa.wwind.layers.EditableMarkerLayer;
import net.falappa.wwind.layers.ShapeSelectionSource;
import net.falappa.wwind.layers.SingleMarkerLayer;
import net.falappa.wwind.layers.SingleSurfShapeLayer;
import net.falappa.wwind.layers.SurfShapeLayer;
import net.falappa.wwind.layers.SurfShapesLayer;
import net.falappa.wwind.posparser.LatLonParser;
import net.falappa.wwind.utils.WWindUtils;

/**
 * A base WorldWind panel with a bar containing a {@link FlyToPanel} and a {@link FlatRoundInLinePanel}.
 * <p>
 * Includes a configured {@link WorldWindowGLCanvas} with a base known layer definition (contained in an XML file).
 * <p>
 * Manages a Map of {@link SurfShapesLayer} (indexed by name), a {@link SingleSurfShapeLayer} and a {@link SingleMarkerLayer}.
 * <tt>SurfShapesLayer</tt>s can be added (if not already present) and removed, events are fired on addition/removal. The
 * {@link SingleSurfShapeLayer} and the {@link SingleMarkerLayer} are managed together to expose the concept of a single Area Of Interest.
 * <p>
 * Manages creation and modification of an editing shape (circle, polygon, polyline, point) that can afterwards be converted to an AOI.
 * Another editing shape can be controlled trough an editing toolbar.
 * <p>
 * It's also possible to add other WorldWind layers. An utility method aids the placement before the "PlaceNames" layer.
 * <p>
 * Offers fly to point and fly to area methods to animate going to a point on the Globe or bringing an area into view.
 * <p>
 * @author Alessandro Falappa
 */
public class WWindPanel extends javax.swing.JPanel {

    /**
     * Types of supported area of interest.
     */
    public static enum AoiShapes {

        POLYGON, CIRCLE, POLYLINE, POINT
    }

    /**
     * Types of supported edit shapes.
     */
    public static enum EditModes {

        POLYGON, CIRCLE, POLYLINE, POINT
    }
    public static final String EVENT_SURF_LAYER_ADDED = "WWindPanel.SurfShapeLayerAdded";
    public static final String EVENT_SURF_LAYER_REMOVED = "WWindPanel.SurfShapeLayerRemoved";
    private static final Logger logger = Logger.getLogger(WWindPanel.class.getName());
    private static final Color COLOR_EDIT = new Color(0, 200, 255, 200);
    private final SingleSurfShapeLayer aoi = new SingleSurfShapeLayer("Area of Interest");
    private final SingleMarkerLayer moi = new SingleMarkerLayer("Marker of interest");
    private final HashMap<String, SurfShapeLayer> shapeLayers = new HashMap<>();
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private boolean editing = false;
    private EditModes editMode = EditModes.POLYGON;
    private MeasureTool mt = null;
    private EditableMarkerLayer eml = null;
    private EditButtonsPanel editBtnsPanel = null;
    private LayerSettingsDialog layerSettingsDialog = null;
    private JButton bLayerSettings = null;

    static {
        // set the WorldWind layers configuration file property
        StringBuilder sb = new StringBuilder("/");
        sb.append(WWindPanel.class.getName().replace('.', '/')).append("Layers.xml");
        URL wwCfg = WWindPanel.class.getResource(sb.toString());
        logger.config(String.format("Setting WordlWind app config document to: %s", wwCfg.toString()));
        System.setProperty("gov.nasa.worldwind.app.config.document", wwCfg.toString());
    }

    /**
     * Sets useful system properties for tweaking Java2D, Swing and JOGL behaviours to better fit WorldWind.
     * <p>
     * It's recommended to call this method as soon as possible in the main class.
     */
    public static void setupPropsForWWind() {
        // global JOGL properties
        System.setProperty("jogl.disable.opengles", "true");
        logger.config("Disabled lookup for OpenGL/ES");
        // indicate preferred JAXP XML parser to avoid problems in WorldWind when other parsers are available
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        // set other global properties depending on the OS
        if (Configuration.isMacOS()) {
            // offset the lower right resize grab handle (avoid overlapping)
            System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
            logger.config("Set Java2D and AWT hints for MacOS-X platform");
        } else if (Configuration.isWindowsOS()) {
            // prevents flashing during window resizing
            System.setProperty("sun.awt.noerasebackground", "true");
            // disable Java2D DirectDraw acceleration
            System.setProperty("sun.java2d.noddraw", "true");
            logger.config("Set Java2D and AWT hints for Windows platform");
        } else if (Configuration.isLinuxOS()) {
            // disable Java2D OpenGL acceleration
            System.setProperty("sun.java2d.opengl", "false");
            // prevents flashing during window resizing
            System.setProperty("sun.awt.noerasebackground", "true");
            logger.config("Set Java2D and AWT hints for Linux platform");
        }
    }

    /**
     * Default constructor.
     */
    public WWindPanel() {
        initComponents();
        setupWorldWind();
        flyToPanel.setWWindPanel(this);
        flyToPanel.addParser(new LatLonParser());
        globeSwitcher.setWorldWindow(wwCanvas);
    }

    /**
     * Package accessible getter to the inner <code>WorldWindowGLCanvas</code>.
     * <p>
     * @return the inner preconfigured <code>WorldWindowGLCanvas</code>
     */
    WorldWindowGLCanvas getWWCanvas() {
        return wwCanvas;
    }

    /**
     * Specifies if the bar should be on the bottom of the panel.
     * <p>
     * @param flag true to place the bar on the bottom
     */
    public void setBottomBar(boolean flag) {
        remove(pTop);
        add(pTop, java.awt.BorderLayout.PAGE_END);
    }

    /**
     * Getter for the edit shape color.
     * <p>
     * @return the current color
     */
    public Color getEditColor() {
        mtInit();
        return mt.getLineColor();
    }

    /**
     * Setter for the edit shape color.
     * <p>
     * @param editColor the new color
     */
    public void setEditColor(Color editColor) {
        mtInit();
        mt.setLineColor(editColor);
        eml.setColor(editColor);
    }

    /**
     * Tells if during edit shape creation the user can use a click-and-drag gesture.
     * <p>
     * When enabled the user can click, drag and then release to fix a control point position. Note: when click-and-drag is enabled the
     * first polyline and polygon segment must be drawn clicking and dragging, otherwise two starting point will be created.
     * <p>
     * @return true if click-and-drag is currently enabled
     */
    public boolean isEditRubberbanding() {
        mtInit();
        return mt.getController().isUseRubberBand();
    }

    /**
     * Sets if during edit shape creation the user can use a click-and-drag gesture.
     * <p>
     * @param flag true to enable click-and-drag
     */
    public void setEditRubberbanding(boolean flag) {
        mtInit();
        mt.getController().setUseRubberBand(flag);
    }

    /**
     * Getter for the current edit mode.
     * <p>
     * It is set to {@link EditModes#POLYGON} by default.
     * <p>
     * @return one of the EditModes enumerated value
     */
    public EditModes getEditMode() {
        return editMode;
    }

    /**
     * Setter for the current edit mode.
     * <p>
     * The new mode takes effect at next start of editing.
     * <p>
     * @param mode the new mode as EditModes enumerated value
     */
    public void setEditMode(EditModes mode) {
        editMode = mode;
    }

    /**
     * Starts editing.
     * <p>
     * After this method is called mouse input is "captured" for editing. Call {@link #stopEditing()} to end an editing session. Previous
     * editing shapes are cleared. Edit mode changing does not take effect after editing has been started.
     */
    public void startEditing() {
        mtInit();
        editing = true;
        mt.clear();
        switch (editMode) {
            case CIRCLE:
                eml.setEditing(false);
                mt.setMeasureShapeType(MeasureTool.SHAPE_CIRCLE);
                mt.setArmed(true);
                break;
            case POINT:
                eml.setEditing(true);
                break;
            case POLYGON:
                eml.setEditing(false);
                mt.setMeasureShapeType(MeasureTool.SHAPE_POLYGON);
                mt.setArmed(true);
                break;
            case POLYLINE:
                eml.setEditing(false);
                mt.setMeasureShapeType(MeasureTool.SHAPE_PATH);
                mt.setArmed(true);
                break;
        }
    }

    /**
     * Tells if editing mode is currently active.
     * <p>
     * @return true if editing, false otherwise
     */
    public boolean isEditing() {
        return editing;
    }

    /**
     * Ends editing.
     * <p>
     * After this method is called user input manipulates the view. The editing shape is not cleared.
     */
    public void stopEditing() {
        mt.setArmed(false);
        eml.setEditing(false);
        editing = false;
    }

    /**
     * Tells if an editing shape has been defined.
     * <p>
     * @return true if shape present
     */
    public boolean hasEditShape() {
        if (editMode == EditModes.POINT) {
            return eml.isPositionSet();
        } else if (editMode == EditModes.CIRCLE) {
            return mt != null && !mt.getPositions().isEmpty();
        } else {
            return mt != null && !mt.isArmed() && !mt.getPositions().isEmpty();
        }
    }

    /**
     * Clears a previous editing shape.
     * <p>
     * Can be called also while editing.
     */
    public void editShapeClear() {
        mt.clear();
        eml.clear();
    }

    /**
     * Transfers the editing shape to the Area of Interest or Marker of Interest layers.
     * <p>
     * Implicitly ends editing.
     */
    public void editShapeToAOI() {
        stopEditing();
        if (!hasEditShape()) {
            return;
        }
        if (eml.isPositionSet()) {
            aoi.clear();
            moi.setPosition(eml.getPosition());
        } else {
            moi.clear();
            switch (mt.getMeasureShapeType()) {
                case MeasureTool.SHAPE_CIRCLE:
                    aoi.setSurfCircle(mt.getCenterPosition(), mt.getWidth() / 2);
                    break;
                case MeasureTool.SHAPE_PATH:
                    // note: coords list must be copied!!
                    aoi.setSurfLine(new ArrayList<>(mt.getPositions()));
                    break;
                case MeasureTool.SHAPE_POLYGON:
                    // note: coords list must be copied!!
                    aoi.setSurfPoly(new ArrayList<>(mt.getPositions()));
                    break;
            }
        }
    }

    /**
     * Adds a listener which gets notified of all editing actions.
     * <p>
     * @param listener a PropertyChangeListener object receiving all editing notifications
     */
    public void addEditListener(PropertyChangeListener listener) {
        mtInit();
        mt.addPropertyChangeListener(listener);
        eml.addPropertyChangeListener(listener);
    }

    /**
     * Removes a previously added editing action listener.
     * <p>
     * @param listener a PropertyChangeListener object previously added
     */
    public void removeEditListener(PropertyChangeListener listener) {
        mtInit();
        mt.removePropertyChangeListener(listener);
        eml.removePropertyChangeListener(listener);
    }

    /**
     * Adds a listener which gets notified of actions on managed {@link SurfShapesLayer} objects.
     * <p>
     * @param listener a PropertyChangeListener object
     */
    public void addSurfShapeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a previously added {@link SurfShapesLayer} listener.
     * <p>
     * @param listener a PropertyChangeListener object previously added
     */
    public void removeSurfShapeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Shows/hides the editing toolbar.
     * <p>
     * Editing toolbar is hidden by default.
     * <p>
     * @param flag true to show editing toolbar
     */
    public void setEditBarVisible(boolean flag) {
        // lazyly construct and place on the toolbar the EditButtonsPanel component
        if (editBtnsPanel == null) {
            editBtnsPanel = new EditButtonsPanel();
            editBtnsPanel.setWorldWindow(wwCanvas);
            pTop.add(editBtnsPanel, 2);
            pTop.add(Box.createHorizontalStrut(12), 3);
        }
        editBtnsPanel.setVisible(flag);
        pTop.revalidate();
    }

    /**
     * Shows/hides the layer settings button.
     * <p>
     * Layer settings button is hidden by default.
     * <p>
     * @param flag true to show the button
     */
    public void setLayerSettingsButtonVisible(boolean flag) {
        // lazily construct the dialog
        if (layerSettingsDialog == null) {
            layerSettingsDialog = new LayerSettingsDialog((Frame) SwingUtilities.getAncestorOfClass(javax.swing.JFrame.class, this));
            layerSettingsDialog.linkTo(this);
            layerSettingsDialog.pack();
            layerSettingsDialog.setLocationRelativeTo(this);
        }
        // lazily construct the button and place it on the toolbar
        if (bLayerSettings == null) {
            bLayerSettings = new JButton();
            bLayerSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                    "/com/telespazio/wwind/widgets/img/glyphicons_114_list.png"))); // NOI18N
            bLayerSettings.setToolTipText("Layer settings");
            bLayerSettings.setMargin(new java.awt.Insets(0, 0, 0, 0));
            bLayerSettings.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    layerSettingsDialog.setVisible(true);
                }
            });
            pTop.add(Box.createHorizontalStrut(6), pTop.getComponentCount());
            pTop.add(bLayerSettings, pTop.getComponentCount());
        }
        bLayerSettings.setVisible(flag);
        pTop.revalidate();
    }

    /**
     * Tells if editing toolbar is shown.
     * <p>
     * @return true if editing functions enabled
     */
    public boolean isEditBarVisible() {
        return editBtnsPanel != null && editBtnsPanel.isVisible();
    }

    /**
     * Adds a generic WorldWind layer to the map.
     * <p>
     * The layer is added on top of the other layers.
     * <p>
     * @param layer the layer to add
     */
    public void addLayer(Layer layer) {
        wwCanvas.getModel().getLayers().add(layer);
    }

    /**
     * Removes a generic WorldWind layer from the map.
     * <p>
     * @param layer the layer to remove
     */
    public void removeLayer(Layer layer) {
        wwCanvas.getModel().getLayers().remove(layer);
    }

    /**
     * Adds a SurfShapeLayer to the map and to the map of managed layers.
     * <p>
     * The layer is added if not already present in the map. The layer is linked to the map for event processing (e.g. selection)
     * <p>
     * @param slayer the layer to add
     */
    public void addSurfShapeLayer(SurfShapeLayer slayer) {
        final String layerName = slayer.getName();
        if (!shapeLayers.containsKey(layerName)) {
            //link layer for selection processing
            if (slayer instanceof ShapeSelectionSource) {
                ((ShapeSelectionSource) slayer).linkTo(wwCanvas);
            }
            shapeLayers.put(layerName, slayer);
            insertBeforePlacenames(slayer);
            changeSupport.firePropertyChange(EVENT_SURF_LAYER_ADDED, null, slayer);
        }
    }

    /**
     * Checks if a named SurfShapeLayer is present in the managed set.
     * <p>
     * @param name the name of the layer
     * @return true if a layer is present, false otherwise
     */
    public boolean hasSurfShapeLayer(String name) {
        return shapeLayers.containsKey(name);
    }

    /**
     * Getter for a named SurfShapeLayer.
     * <p>
     * @param name the name of the layer
     * @return the layer object reference or null if not present
     */
    public SurfShapeLayer getSurfShapeLayer(String name) {
        return shapeLayers.get(name);
    }

    /**
     * Removes the named SurfShapeLayer from the map.
     * <p>
     * The layer content is left untouched. Before removal the layer is correctly detached from the WorldWind map.
     * <p>
     * @param name the name of the layer
     */
    public void removeSurfShapeLayer(String name) {
        SurfShapeLayer removedLayer = shapeLayers.remove(name);
        if (removedLayer != null) {
            // detach the layer listeners from the WorldWindow
            if (removedLayer instanceof ShapeSelectionSource) {
                ((ShapeSelectionSource) removedLayer).detach();
            }
            // remove the layer from the map layer list
            wwCanvas.getModel().getLayers().remove(removedLayer);
            changeSupport.firePropertyChange(EVENT_SURF_LAYER_REMOVED, removedLayer, null);
        }
    }

    /**
     * Getter for all the registered SurfShapeLayers.
     * <p>
     * @return a reference to the layers map values (as a Collection object) see {@link Map#values()}.
     */
    public Collection<SurfShapeLayer> getAllSurfShapeLayers() {
        return shapeLayers.values();
    }

    /**
     * Removes all SurfaceShapeLayer layers from the map.
     */
    public void removeAllSurfShapeLayers() {
        for (Iterator<Map.Entry<String, SurfShapeLayer>> it = shapeLayers.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, SurfShapeLayer> entry = it.next();
            entry.getValue().dispose();
            wwCanvas.getModel().getLayers().remove(entry.getValue());
            changeSupport.firePropertyChange(EVENT_SURF_LAYER_REMOVED, entry.getValue(), null);
            it.remove();
        }
    }

    /**
     * Toggles the visibility on the map of the given named SurfaceShapeLayer.
     * <p>
     * The layer is still present on the map.
     * <p>
     * @param name the layer name
     * @param flag true if visible false otherwise
     */
    public void setSurfShapeLayerVisible(String name, boolean flag) {
        if (shapeLayers.containsKey(name)) {
            shapeLayers.get(name).setEnabled(flag);
        }
    }

    /**
     * Tells if an Area Of Interest has been set
     * <p>
     * @return true if an area of interest exists
     */
    public boolean hasAOI() {
        return moi.isPositionSet() || aoi.hasShape();
    }

    /**
     * Getter for the current Area Of Interest shape type.
     * <p>
     * @return an {@link AoiShapes} enumerated value or null if no area of interest
     */
    public AoiShapes getAOIType() {
        if (moi.isPositionSet()) {
            return AoiShapes.POINT;
        } else {
            Renderable shape = aoi.getShape();
            if (shape instanceof SurfacePolygon) {
                return AoiShapes.POLYGON;
            } else if (shape instanceof SurfaceCircle) {
                return AoiShapes.CIRCLE;
            } else if (shape instanceof Path) {
                return AoiShapes.POLYLINE;
            }
        }
        return null;
    }

    /**
     * Accesses the current Area Of Interest coordinates.
     * <p>
     * Can be called when the AOI type is either {@link AoiShapes#POLYGON} or {@link AoiShapes#POLYGON}.
     * <p>
     * @return an iterable on the coordinates or null if not applicable
     */
    public Iterable<? extends LatLon> getAOICoordinates() {
        Renderable shape = aoi.getShape();
        if (shape instanceof SurfacePolygon) {
            SurfacePolygon poly = (SurfacePolygon) shape;
            return poly.getOuterBoundary();
        }
        if (shape instanceof Path) {
            Path path = (Path) shape;
            return path.getPositions();
        }
        return null;
    }

    /**
     * Accesses the current Area Of Interest center/position.
     * <p>
     * Can be called when the AOI type is either {@link AoiShapes#CIRCLE} or {@link AoiShapes#POINT}.
     * <p>
     * @return the center/positon as a LatLon object or null if not applicable
     */
    public LatLon getAOICenter() {
        if (moi.isPositionSet()) {
            return moi.getPosition();
        } else {
            Renderable shape = aoi.getShape();
            if (shape instanceof SurfaceCircle) {
                SurfaceCircle circle = (SurfaceCircle) shape;
                return circle.getCenter();
            }
        }
        return null;
    }

    /**
     * Accesses the current Area Of Interest radius.
     * <p>
     * Can be called when the AOI type is {@link AoiShapes#CIRCLE}.
     * <p>
     * @return the radius in meters or null if not applicable
     */
    public Double getAOIRadius() {
        Renderable shape = aoi.getShape();
        if (shape instanceof SurfaceCircle) {
            SurfaceCircle circle = (SurfaceCircle) shape;
            return circle.getRadius();
        }
        return null;
    }

    /**
     * Clears the current Area Of Interest.
     */
    public void clearAOI() {
        aoi.clear();
        moi.clear();
    }

    /**
     * Defines a circular Area Of Interest.
     * <p>
     * @param center circle center as Position object
     * @param radius circle radius in meters
     */
    public void setAOICircle(Position center, double radius) {
        aoi.setSurfCircle(center, radius);
        moi.clear();
    }

    /**
     * Defines a point Area Of Interest.
     * <p>
     * @param pos location as Position object
     */
    public void setAOIPoint(Position pos) {
        moi.setPosition(pos);
        aoi.clear();
    }

    /**
     * Defines a geodetic poligonal Area Of Interest.
     * <p>
     * @param points polygon points as Iterable of Position objects
     */
    public void setAOIPolygon(Iterable<? extends LatLon> points) {
        ArrayList<LatLon> pointsCopy = new ArrayList<>();
        for (LatLon p : points) {
            pointsCopy.add(p);
        }
        aoi.setSurfPoly(pointsCopy);
        moi.clear();
    }

    /**
     * Defines a geodetic multi segment Area Of Interest.
     * <p>
     * @param points polyline points as Iterable of Position objects
     */
    public void setAOIPolyline(Iterable<? extends LatLon> points) {
        ArrayList<Position> pointsCopy = new ArrayList<>();
        for (LatLon p : points) {
            pointsCopy.add(new Position(p, 0));
        }
        aoi.setSurfLine(pointsCopy);
        moi.clear();
    }

    /**
     * Setter for Area Of Interest Color.
     * <p>
     * @param col the new color
     */
    public void setAOIColor(Color col) {
        aoi.setColor(col);
        moi.setColor(col);
    }

    /**
     * Getter for Area Of Interest Color.
     * <p>
     * @return the current color
     */
    public Color getAOIColor() {
        return aoi.getColor();
    }

    /**
     * Insert the layer into the layer list just before the placenames.
     * <p>
     * @param layer
     */
    public void insertBeforePlacenames(Layer layer) {
        int pos = 0;
        LayerList layers = wwCanvas.getModel().getLayers();
        for (Layer l : layers) {
            if (l instanceof PlaceNameLayer) {
                pos = layers.indexOf(l);
            }
        }
        layers.add(pos, layer);
    }

    /**
     * Asks for an asynchronous redraw of the map.
     */
    public void redraw() {
        wwCanvas.redraw();
    }

    /**
     * Instantly moves the point of view of the map as looking to the given lat lon coordinates from the given altitude.
     * <p>
     * @param position the coordinates and elevation of the eye
     */
    public void eyeToPoint(Position position) {
        wwCanvas.getView().setEyePosition(position);
        wwCanvas.redraw();
    }

    /**
     * Instantly moves the point of view of the map to the given altitude.
     * <p>
     * @param altitude the desired altitude in meters
     */
    public void eyeToAltitude(double altitude) {
        if (wwCanvas.getView() instanceof BasicOrbitView) {
            BasicOrbitView oview = (BasicOrbitView) wwCanvas.getView();
            oview.setZoom(altitude);
        } else if (wwCanvas.getView() instanceof FlatOrbitView) {
            FlatOrbitView fview = (FlatOrbitView) wwCanvas.getView();
            fview.setZoom(altitude);
        }
        wwCanvas.redraw();
    }

    /**
     * Instantly zooms in or out according to the given factor.
     * <p>
     * Recalculates the current eye altitude according to the formula: <tt>current_altitude + current_altitude * zFact</tt>
     * <p>
     * @param zFact the zoom factor, positive values zoom out while negative ones zoom in
     */
    public void eyeZoomAltitude(double zFact) {
        if (wwCanvas.getView() instanceof BasicOrbitView) {
            BasicOrbitView oview = (BasicOrbitView) wwCanvas.getView();
            final double currZoom = oview.getZoom();
            oview.setZoom(currZoom + currZoom * zFact);
        } else if (wwCanvas.getView() instanceof FlatOrbitView) {
            FlatOrbitView fview = (FlatOrbitView) wwCanvas.getView();
            final double currZoom = fview.getZoom();
            fview.setZoom(currZoom + currZoom * zFact);
        }
        wwCanvas.redraw();
    }

    /**
     * Animates the map, in a sort of fligth, to bring the given position into view.
     * <p>
     * @param position the coordinates and elevation to fly to
     */
    public void flyToPoint(Position position) {
        WWindUtils.flyToPoint(wwCanvas, position);
    }

    /**
     * Animates the map, in a sort of fligth, to bring the given area into view.
     * <p>
     * @param sector the lat lon range sector to fly to
     */
    public void flyToSector(Sector sector) {
        WWindUtils.flyToSector(wwCanvas, sector);
    }

    /**
     * Animates the map, in a sort of fligth, to bring the current area of interest into view.
     * <p>
     * Does nothing if there's no AOI defined.
     */
    public void flyToAOI() {
        if (hasAOI()) {
            if (getAOIType() == AoiShapes.POINT) {
                moi.flyToMOI(wwCanvas);
            } else {
                aoi.flyToAOI(wwCanvas);
            }
        }
    }

    /**
     * Animates the map, in a sort of fligth, to bring the current edit shape into view.
     * <p>
     * Does nothing if there's no edit shape defined.
     */
    public void flyToEditShape() {
        if (hasEditShape()) {
            if (editMode == EditModes.POLYLINE) {
                WWindUtils.flyToObjects(wwCanvas, Arrays.asList(mt.getLine()));
            } else if (editMode == EditModes.POINT) {
                Position pos = new Position(eml.getPosition(), 1_000_000);
                WWindUtils.flyToPoint(wwCanvas, pos);
            } else {
                WWindUtils.flyToObjects(wwCanvas, Arrays.asList(mt.getSurfaceShape()));
            }
        }
    }

    /**
     * Animates the map, in a sort of fligth, to bring the given WorldWind objects into view.
     * <p>
     * Delegates to {@link WWindUtils#flyToObjects(gov.nasa.worldwind.WorldWindow, java.lang.Iterable)}.
     *
     * @param itrs a collection of objects
     */
    public void flyToObjects(Iterable<?> itrs) {
        WWindUtils.flyToObjects(wwCanvas, itrs);
    }

    /**
     * Load WWindPanel preferences from Java Preferences API nodes.
     * <p>
     * @param baseNode the root node under which to look for this class own node
     */
    public void loadPrefs(Preferences baseNode) {
        // TODO caricamento da preferences
        // TODO delegare anche ai layers??
    }

    /**
     * Store WWindPanel preferences using Java Preferences API nodes.
     * <p>
     * @param baseNode the root node under which to store this class own node
     */
    public void storePrefs(Preferences baseNode) {
        // TODO memorizzazione preferences
        // TODO delegare anche ai layers??
    }

    /**
     * Debug method to print the current layer list (with class names).
     */
    public void dumpLayerList() {
        int pos = 1;
        LayerList layers = wwCanvas.getModel().getLayers();
        StringBuilder sb = new StringBuilder("Current layer list:\n");
        for (Layer l : layers) {
            sb.append(String.format("%2d: %s [%s]\n", pos++, l.getName(), l.getClass().getName()));
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Layers", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Toggles a crosshair mouse cursor over the map.
     * <p>
     * @param flag true if cursor should be changed to crosshair false to revert it back to arrow
     */
    public void setCrosshair(boolean flag) {
        if (flag) {
            ((Component) wwCanvas).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            ((Component) wwCanvas).setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pTop = new javax.swing.JPanel();
        flyToPanel = new net.falappa.wwind.widgets.FlyToPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        globeSwitcher = new net.falappa.wwind.widgets.FlatRoundInLinePanel();
        wwCanvas = new gov.nasa.worldwind.awt.WorldWindowGLCanvas();

        setLayout(new java.awt.BorderLayout());

        pTop.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 2, 3, 2));
        pTop.setLayout(new javax.swing.BoxLayout(pTop, javax.swing.BoxLayout.LINE_AXIS));
        pTop.add(flyToPanel);
        pTop.add(filler1);
        pTop.add(globeSwitcher);

        add(pTop, java.awt.BorderLayout.PAGE_START);
        add(wwCanvas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.Box.Filler filler1;
    private net.falappa.wwind.widgets.FlyToPanel flyToPanel;
    private net.falappa.wwind.widgets.FlatRoundInLinePanel globeSwitcher;
    private javax.swing.JPanel pTop;
    private gov.nasa.worldwind.awt.WorldWindowGLCanvas wwCanvas;
    // End of variables declaration//GEN-END:variables

    private void setupWorldWind() {
        BasicModel model = new BasicModel();
        wwCanvas.setModel(model);
        wwCanvas.setSize(400, 300);
        // Register a rendering exception listener
        wwCanvas.addRenderingExceptionListener(new RenderingExceptionListener() {
            @Override
            public void exceptionThrown(Throwable t) {
                if (t instanceof WWAbsentRequirementException) {
                    StringBuilder message = new StringBuilder("Computer does not meet minimum graphics requirements.\n");
                    message.append("Please install up-to-date graphics driver and try again.\n");
                    message.append("Reason: ").append(t.getMessage());
                    message.append("\nThis program will end when you press OK.");
                    JOptionPane.showMessageDialog(null, message.toString(), "Unable to Start Program", JOptionPane.ERROR_MESSAGE);
                    System.exit(-1);
                } else {
                    JOptionPane.showMessageDialog(null, "WorldWind library rendering problem!\n" + t.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    t.printStackTrace(System.err);
                }
            }
        });
        LayerList layers = model.getLayers();
        // add the area of interest layer
        insertBeforePlacenames(aoi);
        // add the marker of interest layer
        insertBeforePlacenames(moi);
        // add a StatusLayer
        StatusLayer slayer = new StatusLayer();
        slayer.setEventSource(wwCanvas);
        slayer.setDefaultFont(UIManager.getFont("Label.font"));
        layers.add(slayer);
        // add a view controls layer and register a controller for it.
        ViewControlsLayer viewControlsLayer = new ViewControlsLayer();
        layers.add(viewControlsLayer);
        wwCanvas.addSelectListener(new ViewControlsSelectListener(wwCanvas, viewControlsLayer));
    }

    // lazily construct the MeasureTool and the EditableMarkerLayer
    private void mtInit() {
        if (mt == null) {
            // create and setup the measure tool
            mt = new MeasureTool(wwCanvas);
            // set some attributes of the measure tool
            final MeasureToolController mtController = new MeasureToolController();
            mtController.setUseRubberBand(false);
            mt.setController(mtController);
            mt.setMeasureShapeType(MeasureTool.SHAPE_POLYGON);
            mt.setShowAnnotation(false);
            mt.setFollowTerrain(true);
            mt.setFillColor(new Color(255, 255, 255, 63));
            mt.setLineColor(COLOR_EDIT);
            mt.getControlPointsAttributes().setBackgroundColor(new Color(0, 200, 255, 200));
            mt.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals(MeasureTool.EVENT_ARMED)) {
                        setCrosshair(mt.isArmed());
                    }
                }
            });
            // create the EditableMarkerLayer for marker editing
            eml = new EditableMarkerLayer(wwCanvas, "EditableMarkerLayer");
            eml.setColor(COLOR_EDIT);
            eml.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals(EditableMarkerLayer.EVENT_EDITING_START)) {
                        setCrosshair(true);
                    }
                    if (evt.getPropertyName().equals(EditableMarkerLayer.EVENT_POSITION_SET)) {
                        setCrosshair(false);
                        eml.setEditing(false);
                    }
                }
            });
            wwCanvas.getModel().getLayers().add(eml);
        }
    }
}
