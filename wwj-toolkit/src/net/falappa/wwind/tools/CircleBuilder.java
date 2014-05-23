/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.falappa.wwind.tools;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVListImpl;
import gov.nasa.worldwind.event.PositionEvent;
import gov.nasa.worldwind.event.PositionListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Polyline;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

/**
 * A utility class to interactively build a circle.
 * <p>
 * @author Alessandro Falappa
 */
public class CircleBuilder extends AVListImpl {

    private final WorldWindow wwd;
    private boolean armed = false;
    private final ArrayList<Position> positions = new ArrayList<>(2);
    private final RenderableLayer layer;
    private final Polyline line;
    private boolean active = false;

    public CircleBuilder(final WorldWindow wwd, RenderableLayer lineLayer, Polyline polyline) {
        this.wwd = wwd;
        if (polyline != null) {
            this.line = polyline;
        } else {
            this.line = new Polyline();
            this.line.setFollowTerrain(true);
        }
        this.line.setPositions(this.positions);
        if (lineLayer != null) {
            this.layer = lineLayer;
        } else {
            this.layer = new RenderableLayer();
        }
        this.layer.addRenderable(this.line);
        this.wwd.getModel().getLayers().add(this.layer);
        setupWWdEvtHandlers();
    }

    private void setupWWdEvtHandlers() {
        this.wwd.getInputHandler().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (armed && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    if (armed && (mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                        active = true;
                        setCenterPos();
                    }
                    mouseEvent.consume();
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (armed && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    if (positions.size() == 1) {
                        setPerimeterPos();
                    }
                    active = false;
                    mouseEvent.consume();
                }
            }
        });
        this.wwd.getInputHandler().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                if (armed && (mouseEvent.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                    // Don't update the polyline here because the wwd current cursor position will not
                    // have been updated to reflect the current mouse position. Wait to update in the
                    // position listener, but consume the event so the view doesn't respond to it.
                    if (active) {
                        mouseEvent.consume();
                    }
                }
            }
        });
        this.wwd.addPositionListener(new PositionListener() {
            @Override
            public void moved(PositionEvent event) {
                if (!active) {
                    return;
                }
                if (positions.isEmpty()) {
                    setCenterPos();
                } else {
                    setPerimeterPos();
                }
            }
        });
    }

    public Position getCenter() {
        return positions.get(0);
    }

    public Position getPerimeterPos() {
        return positions.get(1);
    }

    public double getRadius() {
        return line.getLength();
    }

    /**
     * Removes all positions from the polyline.
     */
    public void clear() {
        while (this.positions.size() > 0) {
            this.removePosition();
        }
    }

    /**
     * Returns the layer holding the polyline being created.
     * <p>
     * @return the layer containing the polyline.
     */
    public RenderableLayer getLayer() {
        return this.layer;
    }

    /**
     * Identifies whether the line builder is armed.
     * <p>
     * @return true if armed, false if not armed.
     */
    public boolean isArmed() {
        return this.armed;
    }

    /**
     * Arms and disarms the line builder. When armed, the line builder monitors user input and builds the polyline in response to the
     * actions mentioned in the overview above. When disarmed, the line builder ignores all user input.
     * <p>
     * @param armed true to arm the line builder, false to disarm it.
     */
    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    private void setCenterPos() {
        Position curPos = this.wwd.getCurrentPosition();
        if (curPos == null) {
            return;
        }
        if (this.positions.size() == 1) {
            Position currentLastPosition = this.positions.get(0);
            this.positions.set(0, curPos);
            this.line.setPositions(this.positions);
            this.firePropertyChange("CircleBuilder.ReplaceRadiusPosition", currentLastPosition, curPos);
        } else {
            positions.clear();
            this.positions.add(curPos);
            this.line.setPositions(this.positions);
            this.firePropertyChange("CircleBuilder.AddCenterPosition", null, curPos);
        }
        this.wwd.redraw();
    }

    private void setPerimeterPos() {
        Position curPos = this.wwd.getCurrentPosition();
        if (curPos == null) {
            return;
        }
        if (this.positions.size() == 2) {
            Position currentLastPosition = this.positions.get(1);
            this.positions.set(1, curPos);
            this.line.setPositions(this.positions);
            this.firePropertyChange("CircleBuilder.ReplaceRadiusPosition", currentLastPosition, curPos);
        } else {
            this.positions.add(curPos);
            this.firePropertyChange("CircleBuilder.AddRadiusPosition", null, curPos);
        }
        this.wwd.redraw();
    }

    private void removePosition() {
        if (this.positions.isEmpty()) {
            return;
        }
        int siz = positions.size();
        if (siz > 1) {
            Position currentLastPosition = this.positions.get(1);
            this.positions.remove(1);
            this.firePropertyChange("CircleBuilder.RemoveRadius", currentLastPosition, null);
        }
        if (siz > 0) {
            Position currentLastPosition = this.positions.get(0);
            this.positions.remove(0);
            this.firePropertyChange("CircleBuilder.RemoveCenter", currentLastPosition, null);
        }
        this.line.setPositions(this.positions);
        this.wwd.redraw();
    }
}
