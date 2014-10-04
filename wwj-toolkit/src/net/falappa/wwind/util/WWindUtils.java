package net.falappa.wwind.util;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.FlatOrbitView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.falappa.wwind.layers.SurfShapesLayer;
import net.falappa.wwind.widgets.WWindPanel;

/**
 * WorldWind related utility methods.
 * <p>
 * @author Alessandro Falappa
 */
public final class WWindUtils {

    /**
     * Pivate to prevent instantiation
     */
    private WWindUtils() {
    }

    /**
     * Parses a string of Lat Lon coordinates in degrees and creates a list of <code>LatLon</code> objects.
     * <p>
     * @param posList a GML posList coordinates string (lat lon pairs)
     * @return a List of LatLon objects
     */
    public static List<LatLon> posList2LatLonList(String posList) {
        List<LatLon> ret = new ArrayList<>();
        String[] coords = posList.split("\\s+");
        if (coords.length % 2 != 0) {
            throw new IllegalArgumentException("Odd number of coordinates in given posList");
        }
        for (int i = 0; i < coords.length; i += 2) {
            double lat = Double.valueOf(coords[i]);
            double lon = Double.valueOf(coords[i + 1]);
            ret.add(LatLon.fromDegrees(lat, lon));
        }
        return ret;
    }

    /**
     * Converts a list of <code>LatLon</code> derived objects into a string of Lat Lon coordinates in degrees.
     * <p>
     * @param latlonList a List of LatLon derived objects
     * @return a GML posList coordinates string (lat lon pairs)
     */
    public static String latLonList2PosList(Iterable<? extends LatLon> latlonList) {
        StringBuilder sb = new StringBuilder();
        for (LatLon ll : latlonList) {
            sb.append(String.format(Locale.ENGLISH, "%f %f ", ll.latitude.degrees, ll.longitude.degrees));
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Add some random polygons to the {@link SurfShapesLayer} of the given name.
     * <p>
     * The polygons will have an od generated from the given prefix and an appended counter. The polygons will be generated over Europe.
     * <p>
     * @param wwp the WWindPanel holding the layers
     * @param lname the SurfShapesLayer to add to
     * @param idPrefix the shape id prefix
     */
    public static void randPolys(WWindPanel wwp, String lname, String idPrefix) {
        List<LatLon> positions;
        SurfShapesLayer ssl = (SurfShapesLayer) wwp.getSurfShapeLayer(lname);
        for (int i = 0; i < 20; i++) {
            positions = new ArrayList<>();
            double btmLat = 10 + Math.random() * 50;
            double btmLon = Math.random() * 70;
            double w = 2 + Math.random() * 3;
            double h = 2 + Math.random() * 3;
            positions.add(LatLon.fromDegrees(btmLat, btmLon));
            positions.add(LatLon.fromDegrees(btmLat, btmLon + w));
            positions.add(LatLon.fromDegrees(btmLat + h, btmLon + w));
            positions.add(LatLon.fromDegrees(btmLat + h, btmLon));
            ssl.addSurfPoly(positions, String.format("%s%d", idPrefix, i));
        }
        wwp.redraw();
    }

    /**
     * Animates the map, in a sort of fligth, to bring the given area into view.
     * <p>
     * @param wwd the WorldWindow reference for calculations
     * @param sector the lat lon range sector to fly to
     */
    public static void flyToSector(WorldWindow wwd, Sector sector) {
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
        if (wwd.getView() instanceof BasicOrbitView) {
            BasicOrbitView view = (BasicOrbitView) wwd.getView();
            view.addPanToAnimator(pos, Angle.ZERO, Angle.ZERO, altitude);
        } else if (wwd.getView() instanceof FlatOrbitView) {
            FlatOrbitView fview = (FlatOrbitView) wwd.getView();
            fview.addPanToAnimator(pos, Angle.ZERO, Angle.ZERO, altitude);
        }
    }

    /**
     * Animates the map, in a sort of fligth, to bring the given position into view.
     * <p>
     * @param wwd
     * @param position the coordinates and elevation to fly to
     */
    public static void flyToPoint(WorldWindow wwd, Position position) {
        if (wwd.getView() instanceof BasicOrbitView) {
            BasicOrbitView view = (BasicOrbitView) wwd.getView();
            view.addPanToAnimator(position, Angle.ZERO, Angle.ZERO, position.getAltitude());
        } else if (wwd.getView() instanceof FlatOrbitView) {
            FlatOrbitView fview = (FlatOrbitView) wwd.getView();
            fview.addPanToAnimator(position, Angle.ZERO, Angle.ZERO, position.getAltitude());
        }
    }

}
