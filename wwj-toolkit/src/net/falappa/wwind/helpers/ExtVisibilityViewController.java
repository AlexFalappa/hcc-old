package net.falappa.wwind.helpers;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.geom.ExtentHolder;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.view.orbit.OrbitView;
import gov.nasa.worldwindx.examples.util.ExtentVisibilitySupport;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Taken from Worldwind examples.
 */
public class ExtVisibilityViewController {

    protected static final double SMOOTHING_FACTOR = 0.96;
    protected boolean enabled = true;
    protected WorldWindow wwd;
    protected ExtVisibilityViewAnimator animator;
    protected Iterable<?> objectsToTrack;

    public ExtVisibilityViewController(WorldWindow wwd) {
        this.wwd = wwd;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.animator != null) {
            this.animator.stop();
            this.animator = null;
        }
    }

    public Iterable<?> getObjectsToTrack() {
        return this.objectsToTrack;
    }

    public void setObjectsToTrack(Iterable<?> iterable) {
        this.objectsToTrack = iterable;
    }

    public boolean isSceneContained(View view) {
        ExtentVisibilitySupport vs = new ExtentVisibilitySupport();
        this.addExtents(vs);
        return vs.areExtentsContained(view);
    }

    public Vec4[] computeViewLookAtForScene(View view) {
        Globe globe = this.wwd.getModel().getGlobe();
        double ve = this.wwd.getSceneController().getVerticalExaggeration();
        ExtentVisibilitySupport vs = new ExtentVisibilitySupport();
        this.addExtents(vs);
        return vs.computeViewLookAtContainingExtents(globe, ve, view);
    }

    public Position computePositionFromPoint(Vec4 point) {
        return this.wwd.getModel().getGlobe().computePositionFromPoint(point);
    }

    public void gotoScene() {
        Vec4[] lookAtPoints = this.computeViewLookAtForScene(this.wwd.getView());
        if (lookAtPoints == null || lookAtPoints.length != 3) {
            return;
        }
        Position centerPos = this.wwd.getModel().getGlobe().computePositionFromPoint(lookAtPoints[1]);
        double zoom = lookAtPoints[0].distanceTo3(lookAtPoints[1]);
        this.wwd.getView().stopAnimations();
        this.wwd.getView().goTo(centerPos, zoom);
    }

    public void sceneChanged() {
        OrbitView view = (OrbitView) this.wwd.getView();
        if (!this.isEnabled()) {
            return;
        }
        if (this.isSceneContained(view)) {
            return;
        }
        if (this.animator == null || !this.animator.hasNext()) {
            this.animator = new ExtVisibilityViewAnimator(SMOOTHING_FACTOR, view, this);
            this.animator.start();
            view.stopAnimations();
            view.addAnimator(this.animator);
            view.firePropertyChange(AVKey.VIEW, null, view);
        }
    }

    protected void addExtents(ExtentVisibilitySupport vs) {
        // Compute screen extents for WWIcons which have feedback information from their IconRenderer.
        Iterable<?> iterable = this.getObjectsToTrack();
        if (iterable == null) {
            return;
        }
        ArrayList<ExtentHolder> extentHolders = new ArrayList<>();
        ArrayList<ExtentVisibilitySupport.ScreenExtent> screenExtents = new ArrayList<>();
        for (Object o : iterable) {
            if (o == null) {
                continue;
            }
            if (o instanceof ExtentHolder) {
                extentHolders.add((ExtentHolder) o);
            } else if (o instanceof AVList) {
                AVList avl = (AVList) o;
                Object b = avl.getValue(AVKey.FEEDBACK_ENABLED);
                if (b == null || !Boolean.TRUE.equals(b)) {
                    continue;
                }
                if (avl.getValue(AVKey.FEEDBACK_REFERENCE_POINT) != null) {
                    screenExtents.add(new ExtentVisibilitySupport.ScreenExtent(
                            (Vec4) avl.getValue(AVKey.FEEDBACK_REFERENCE_POINT),
                            (Rectangle) avl.getValue(AVKey.FEEDBACK_SCREEN_BOUNDS)));
                }
            }
        }
        if (!extentHolders.isEmpty()) {
            Globe globe = this.wwd.getModel().getGlobe();
            double ve = this.wwd.getSceneController().getVerticalExaggeration();
            vs.setExtents(ExtentVisibilitySupport.extentsFromExtentHolders(extentHolders, globe, ve));
        }
        if (!screenExtents.isEmpty()) {
            vs.setScreenExtents(screenExtents);
        }
    }
}
