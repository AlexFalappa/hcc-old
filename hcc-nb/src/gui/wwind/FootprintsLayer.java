/*
 * Copyright 2014 Alessandro Falappa <alex.falappa@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gui.wwind;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.Polygon;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.render.SurfaceQuad;
import gov.nasa.worldwind.render.SurfaceSector;
import gov.nasa.worldwind.render.SurfaceShape;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import net.falappa.wwind.util.HighlightControllerPub;

public class FootprintsLayer extends RenderableLayer implements SelectListener {

    private final BasicShapeAttributes attr = new BasicShapeAttributes();
    private final BasicShapeAttributes attrHigh = new BasicShapeAttributes();
    private final ArrayList<SurfaceShape> footprints = new ArrayList<>();
    private final GlobeAnnotation popupAnnotation = new GlobeAnnotation("", Position.ZERO);
    private WorldWindow wwd;
    private HighlightControllerPub highlighter;
    private SurfaceShape prevPopupShape;
    private String highlightEvent = SelectEvent.LEFT_CLICK;

    public FootprintsLayer() {
        // properties of layer
        setName("Footprints");
        setEnabled(true);
        // painting attributes for footprints
        attr.setOutlineMaterial(Material.ORANGE);
        attr.setOutlineWidth(1.5);
        attr.setInteriorMaterial(Material.ORANGE);
        attr.setInteriorOpacity(0.4f);
        // painting attributes for hihglighted footprints
        attrHigh.setOutlineMaterial(Material.BLACK);
        attrHigh.setOutlineWidth(2);
        attrHigh.setInteriorMaterial(Material.WHITE);
        attrHigh.setInteriorOpacity(0.7f);
        // popup annotation attributes
        AnnotationAttributes defaultAttributes = new AnnotationAttributes();
        defaultAttributes.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
        defaultAttributes.setFrameShape(AVKey.SHAPE_RECTANGLE);
        defaultAttributes.setCornerRadius(3);
        defaultAttributes.setDrawOffset(new Point(0, 8));
        defaultAttributes.setLeaderGapWidth(8);
        defaultAttributes.setTextColor(Color.BLACK);
        defaultAttributes.setBackgroundColor(new Color(1f, 1f, 1f, .85f));
        defaultAttributes.setBorderColor(new Color(0xababab));
        defaultAttributes.setInsets(new Insets(3, 3, 3, 3));
        defaultAttributes.setVisible(false);
        popupAnnotation.setAttributes(defaultAttributes);
        popupAnnotation.setAlwaysOnTop(true);
        addRenderable(popupAnnotation);
    }

    public void linkTo(WorldWindow wwd) {
        this.wwd = wwd;
        highlighter = new HighlightControllerPub(wwd, highlightEvent);
        wwd.addSelectListener(this);
    }

    public boolean isHighlightingEnabled() {
        return highlighter.isEnabled();
    }

    public void setHighlightingEnabled(boolean highlightingEnabled) {
        highlighter.setEnabled(highlightingEnabled);
        // hide popup and clear highlighed object when disabling
        if (!highlightingEnabled) {
            popupAnnotation.getAttributes().setVisible(false);
            highlighter.highlight(null);
            wwd.redraw();
        }
    }

    public Color getHighlightColor() {
        return attrHigh.getOutlineMaterial().getDiffuse();
    }

    public String getHighlightEvent() {
        return highlightEvent;
    }

    public void setHighlightEvent(String highlightEvent) {
        this.highlightEvent = highlightEvent;
        highlighter = new HighlightControllerPub(wwd, highlightEvent);
    }

    public Color getColor() {
        return attr.getOutlineMaterial().getDiffuse();
    }

    public void setColor(Color col) {
        attr.setOutlineMaterial(new Material(col));
        attr.setInteriorMaterial(new Material(col.brighter().brighter()));
    }

    public void setHighlightColor(Color col) {
        attrHigh.setOutlineMaterial(new Material(col));
        attrHigh.setInteriorMaterial(new Material(col.brighter().brighter()));
    }

    public void addSurfCircle(double lat, double lon, double rad, String tooltip) {
        SurfaceCircle shape = new SurfaceCircle(attr, LatLon.fromDegrees(lat, lon), rad);
        shape.setValue(AVKey.HOVER_TEXT, tooltip);
        addRenderable(shape);
    }

    public void addSurfPoly(List<LatLon> coords) {
        addSurfPoly(coords, null);
    }

    public void addSurfPoly(List<LatLon> coords, String tooltip) {
        SurfacePolygon shape = new SurfacePolygon(coords);
        shape.setAttributes(attr);
        shape.setHighlightAttributes(attrHigh);
        if (tooltip != null) {
            shape.setValue(AVKey.HOVER_TEXT, tooltip);
        }
        addRenderable(shape);
        footprints.add(shape);
    }

    public void addSurfQuad(double lat, double lon, double w, double h, String tooltip) {
        SurfaceQuad shape = new SurfaceQuad(attr, LatLon.fromDegrees(lat, lon), w, h);
        shape.setValue(AVKey.HOVER_TEXT, tooltip);
        addRenderable(shape);
    }

    public void addSurfSect(double minlat, double minlon, double maxlat, double maxlon) {
        SurfaceSector shape = new SurfaceSector(attr,
                Sector.fromDegrees(minlat, maxlat, minlon, maxlon));
        shape.setPathType(AVKey.LINEAR);
        addRenderable(shape);
    }

    public void addPath(List<Position> coords, String tooltip) {
        Path shape = new Path(coords);
        shape.setAttributes(attr);
        shape.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        shape.setPathType(AVKey.GREAT_CIRCLE);
        shape.setFollowTerrain(true);
        shape.setTerrainConformance(40);
        if (tooltip != null) {
            shape.setValue(AVKey.HOVER_TEXT, "hover: " + tooltip);
            shape.setValue(AVKey.ROLLOVER_TEXT, "rollover: " + tooltip);
        }
        addRenderable(shape);
    }

    public void addPoly(List<Position> coords, String tooltip) {
        Polygon shape = new Polygon(coords);
        shape.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        shape.setAttributes(attr);
        if (tooltip != null) {
            shape.setValue(AVKey.HOVER_TEXT, "hover: " + tooltip);
            shape.setValue(AVKey.ROLLOVER_TEXT, "rollover: " + tooltip);
        }
        addRenderable(shape);
    }

    public SurfaceShape getShape(int idx) {
        return footprints.get(idx);
    }

    public SurfacePolygon getPoly(int idx) {
        final SurfaceShape ret = footprints.get(idx);
        return ret instanceof SurfacePolygon ? (SurfacePolygon) ret : null;
    }

    public void flyToShape(int idx) {
        flyToShape(footprints.get(idx));
    }

    public void flyToShape(SurfaceShape shape) {
        Sector sector = Sector.boundingSector(shape.getLocations(wwd.getModel().getGlobe()));
        double delta_x = sector.getDeltaLonRadians();
        double delta_y = sector.getDeltaLatRadians();
        double earthRadius = wwd.getModel().getGlobe().getRadius();
        double horizDistance = earthRadius * delta_x;
        double vertDistance = earthRadius * delta_y;
        // Form a triangle consisting of the longest distance on the ground and the ray from the eye to the center point
        // The ray from the eye to the midpoint on the ground bisects the FOV
        double distance = Math.max(horizDistance, vertDistance) / 2;
        double altitude = distance / Math.tan(wwd.getView().getFieldOfView().radians / 2);
        // double the altitude to leave some space around
        altitude *= 2;
        // fly to the calculated position
        Position pos = new Position(sector.getCentroid(), altitude);
        BasicOrbitView view = (BasicOrbitView) wwd.getView();
        view.addPanToAnimator(pos, Angle.ZERO, Angle.ZERO, altitude);
        highlighter.highlight(shape);
    }

    public void highlight(SurfaceShape shape) {
        if (highlighter.isEnabled()) {
            highlighter.highlight(shape);
        }
    }

    @Override
    public void removeAllRenderables() {
        super.removeAllRenderables();
        footprints.clear();
        // re-add the hidden allnotation
        popupAnnotation.getAttributes().setVisible(false);
        addRenderable(popupAnnotation);
    }

    // SelectListener interface
    @Override
    public void selected(SelectEvent event) {
        // short circuit exit if highlighting is not enabled
        if (!highlighter.isEnabled()) {
            return;
        }
        if (event.isLeftClick()) {
            final Object topObject = event.getTopObject();
            if (topObject instanceof SurfaceShape) {
                SurfaceShape shape = (SurfaceShape) topObject;
                final Sector boundingSector = Sector.boundingSector(shape.getLocations(wwd.getModel().getGlobe()));
                Position centroid = new Position(boundingSector.getCentroid(), 0d);
                if (popupAnnotation.getAttributes().isVisible() && shape.equals(prevPopupShape)) {
                    popupAnnotation.getAttributes().setVisible(false);
                } else {
                    String ht = (String) shape.getValue(AVKey.HOVER_TEXT);
                    popupAnnotation.setText(ht);
                    popupAnnotation.setPosition(centroid);
                    popupAnnotation.getAttributes().setVisible(true);
                    prevPopupShape = shape;
                    wwd.redraw();
                }
            }
        }
    }

}
