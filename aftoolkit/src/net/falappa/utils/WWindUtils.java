package net.falappa.utils;

import gov.nasa.worldwind.geom.LatLon;
import java.util.ArrayList;
import java.util.List;

/**
 * WorldWind related utility methods.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public final class WWindUtils {

    /**
     * Pivate to prevent instantiation
     */
    private WWindUtils() {
    }

    /**
     * Parses a string of Lat Lon coordinates in degrees and creates a list of
     * <code>LatLon</code> objects.
     *
     * @param posList a GML posList coordinates string (lat lon pairs)
     * @return a List of LatLon objects
     */
    public static List<LatLon> posList2LatLonList(String posList) {
        List<LatLon> ret = new ArrayList<>();
        String[] coords = posList.split("\\s");
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
}
