/**
 * HMA Layer system Copyright 2011 Telespazio S.p.A.
 * <p>
 * author Alessandro Falappa created 02/set/2011
 * <p>
 * $LastChangedBy: afalappa $ $LastChangedDate: 2012-06-08 09:03:05 +0200 (ven, 08 giu 2012) $
 */
package net.falappa.utils;

import java.awt.geom.Point2D;

/**
 * Utility class for working with GIS related stuff.
 * <p>
 * Includes static methods for manipulating and converting lists of coordinates, a geodetic distance routine, etc.
 */
public final class GisUtils {

    /**
     * Private constructor to prevent instantiation
     */
    private GisUtils() {
    }

    /**
     * Convert the list of lon lat coordinates pairs from a geometry expressed in WKT into a list of lat lon pairs suitable for GML 3.1.1
     * &lt;gml:posList&gt; tag.
     * <p>
     * Examples:
     * <ul>
     * <li>"POINT(1 2)" is converted to "2 1"</li>
     * <li>"POLYGON((1 2,3 4,5 6,7 8,1 2))" is converted to "2 1 4 3 6 5 8 7 2 1"</li>
     * </ul>
     *
     * <b>Note:</b> it deals only with simple cases, does not handle multi primitives well nor polygons with holes.
     * <p>
     * @param wkt the geometry in WKT form
     * @return a GML 3.1.1 lon lat pos list string
     */
    public static String posListFromWKT(String wkt) {
        assert wkt != null;
        // remove whitespace and parentheses
        String ret = wkt.trim();
        ret = ret.replaceAll("\\(", "");//StringUtils.remove(ret, '(');
        ret = ret.replaceAll("\\)", "");//StringUtils.remove(ret, ')');
        // remove primitive name
        ret = ret.replaceFirst("POINT", "");//StringUtils.removeStart(ret, "POINT");
        ret = ret.replaceFirst("LINESTRING", "");//StringUtils.removeStart(ret, "LINESTRING");
        ret = ret.replaceFirst("MULTILINESTRING", "");//StringUtils.removeStart(ret, "MULTILINESTRING");
        ret = ret.replaceFirst("POLYGON", "");//StringUtils.removeStart(ret, "POLYGON");
        ret = ret.replaceFirst("MULTIPOLYGON", "");//StringUtils.removeStart(ret, "MULTIPOLYGON");
        // replace commas with spaces and split single coordinates
        String[] coords = ret.split(",");//StringUtils.split(ret.replace(',', ' '));
        // check there is an even number of coordinates
        assert coords.length % 2 == 0;
        // rebuild string inverting the coordinates
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coords.length; i += 2) {
            sb.append(coords[i + 1]).append(' ').append(coords[i]);
            if (i != coords.length - 2) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * Convert the list of lat lon coordinates pairs from a GML 3.1.1 &lt;gml:posList&gt; tag into a list of lon lat pairs suitable for
     * geometry expressed in WKT. The order of coordinate in pairs is thus reversed.
     * <p>
     * Examples:
     * <ul>
     * <li>"1 2" is converted to "2 1"</li>
     * <li>"1 2 3 4 5 6 7 8 1 2" is converted to "2 1,4 3,6 5,8 7,2 1"</li>
     * </ul>
     * <p>
     * @param posList a GML 3.1.1 lat lon pos list string
     * @return a lon lat coordinates list to use in WKT geometries
     */
    public static String WKTfromPosList(String posList) {
        // split single coordinates
        String[] coords = posList.split("\\s");//StringUtils.split(posList);
        // check there is an even number of coordinates
        assert coords.length % 2 == 0;
        // rebuild string separating pairs with a comma
        StringBuilder sb = new StringBuilder(posList.length());
        for (int i = 0; i < coords.length; i += 2) {
            sb.append(coords[i + 1]).append(' ').append(coords[i]);
            if (i != coords.length - 2) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    /**
     * Convert the list of lon lat coordinates pairs from the format used in COSMO Interoperability Protocol into a list of lat lon pairs
     * suitable for GML 3.1.1 &lt;gml:posList&gt; tag.
     * <p>
     * Examples:<br>
     * <ul>
     * <li>"[178:45;-170:45;-170:-45;178:-45;178:45]" is converted to "45 178 45 -170 -45 -170 -45 178 45 178"</li>
     * <li>"[6:5]" is converted to "5 6"</li>
     * </ul>
     * <p>
     * @param coordList the polygon coordinates in COSMO interoperability protocol form
     * @return a GML 3.1.1 pos list string
     */
    public static String posListFromCosmoCoordList(String coordList) {
        // remove whitespace and brackets
        String tmp = coordList.trim();
        tmp = tmp.replaceAll("\\[", "");//StringUtils.removeStart(tmp, "[");
        tmp = tmp.replaceAll("\\]", "");//StringUtils.removeEnd(tmp, "]");
        // replace colons and semicolons with spaces
        tmp = tmp.replace(':', ' ').replace(';', ' ');//StringUtils.replaceChars(tmp, ":;", "  ");
        // split single coordinates
        String[] coords = tmp.split("\\s");//StringUtils.split(tmp);
        // check there is an even number of coordinates
        assert coords.length % 2 == 0;
        // rebuild string inverting the coordinates
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coords.length; i += 2) {
            sb.append(coords[i + 1]).append(' ').append(coords[i]);
            if (i != coords.length - 2) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /**
     * Convert the list of lat lon coordinates pairs from a list of pairs suitable for GML 3.1.1 &lt;gml:posList&gt; tag into the format
     * used in COSMO Interoperability Protocol (lon lat).
     * <p>
     * Examples:<br>
     * <ul>
     * <li>"178 45 -170 45 -170 -45 178 -45 178 45" is converted to "[45:178;45:-170;-45:-170;-45:178;45:178]"</li>
     * <li>"5 6" is converted to "[6:5]"</li>
     * </ul>
     * <p>
     * @param posList a GML 3.1.1 lat lon pos list string
     * @return the polygon lon lat coordinates in COSMO interoperability protocol form
     */
    public static String CosmoCoordListFromPosList(String posList) {
        // split the posList at space boundaries
        String[] coords = posList.split("\\s");//StringUtils.split(posList);
        // check there is an even number of coordinates
        assert coords.length % 2 == 0;
        // invert lat lon, separate the pairs with';' and the coordinates with ':'
        StringBuilder sb = new StringBuilder("[");
        sb.append(coords[1]).append(':').append(coords[0]);
        for (int i = 2; i < coords.length; i += 2) {
            sb.append(';').append(coords[i + 1]).append(':').append(coords[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Approximate a circle of the given center and radius with a 40 sided regular polygon.
     * <p>
     * Returned geometry is in WKT string format. Calculations are performed on the WGS84 geodetic spheroid.
     * <p>
     * @param lon    Center longitude
     * @param lat    Center latitude
     * @param radius Radius, in meters
     * @return The polygon in WKT string
     */
    public static String circleToPolygonWKT(double lon, double lat,
            double radius) {
        Point2D.Double center = new Point2D.Double(lon, lat);
        StringBuilder wkt = new StringBuilder(200);
        wkt.append("POLYGON ((");
        // add the first point (0° bearing or heading north)
        Point2D.Double point0 = directVincenty(center, 0, radius);
        // repeat calculation increasing bearing by 360° / 40 = 9° steps
        wkt.append(point0.x).append(" ").append(point0.y).append(',');
        for (int alfa = 9; alfa < 360; alfa += 9) {
            Point2D.Double point = directVincenty(center, alfa, radius);
            wkt.append(point.x).append(" ").append(point.y).append(',');
        }
        // repeat the first point to close polygon
        wkt.append(point0.x).append(" ").append(point0.y);
        wkt.append("))");
        return wkt.toString();
    }

    /**
     * Calculate a destination lat/long point given start point lat/long (in decimal degrees), bearing angle (decimal degrees) and a
     * distance (meters).
     * <p>
     * The code employs the Vincenty direct formula from T.Vincenty, "Direct and Inverse Solutions of Geodesics on the Ellipsoid with
     * application of nested equations", Survey Review, vol XXII no 176, 1975 http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf
     * <p>
     * See http://www.movable-type.co.uk/scripts/latlong-vincenty-direct.html for explanation and sample web application.
     * <p>
     * @param point
     * @param brng
     * @param distance
     * @return
     */
    public static Point2D.Double directVincenty(Point2D.Double point,
            double brng, double distance) {
        // WGS-84 ellipsoid parameters
        double a = 6378137, b = 6356752.3142, f = 1 / 298.257223563;
        double s = distance;
        double alpha1 = brng * Math.PI / 180;
        double sinAlpha1 = Math.sin(alpha1);
        double cosAlpha1 = Math.cos(alpha1);
        double tanU1 = (1 - f) * Math.tan(point.y * Math.PI / 180);
        double cosU1 = 1 / Math.sqrt((1 + tanU1 * tanU1)), sinU1 = tanU1
                * cosU1;
        double sigma1 = Math.atan2(tanU1, cosAlpha1);
        double sinAlpha = cosU1 * sinAlpha1;
        double cosSqAlpha = 1 - sinAlpha * sinAlpha;
        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384
                * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double cos2SigmaM = Double.NaN;
        double sinSigma = Double.NaN;
        double cosSigma = Double.NaN;
        double sigma = s / (b * A), sigmaP = 2 * Math.PI;
        while (Math.abs(sigma - sigmaP) > 1e-12) {
            cos2SigmaM = Math.cos(2 * sigma1 + sigma);
            sinSigma = Math.sin(sigma);
            cosSigma = Math.cos(sigma);
            double deltaSigma = B
                    * sinSigma
                    * (cos2SigmaM + B
                    / 4
                    * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B
                    / 6
                    * cos2SigmaM
                    * (-3 + 4 * sinSigma * sinSigma)
                    * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
            sigmaP = sigma;
            sigma = s / (b * A) + deltaSigma;
        }
        double tmp = sinU1 * sinSigma - cosU1 * cosSigma * cosAlpha1;
        double lat2 = Math.atan2(sinU1 * cosSigma + cosU1 * sinSigma
                * cosAlpha1,
                (1 - f) * Math.sqrt(sinAlpha * sinAlpha + tmp * tmp));
        double lambda = Math.atan2(sinSigma * sinAlpha1, cosU1 * cosSigma
                - sinU1 * sinSigma * cosAlpha1);
        double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
        double L = lambda
                - (1 - C)
                * f
                * sinAlpha
                * (sigma + C
                * sinSigma
                * (cos2SigmaM + C * cosSigma
                * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        // var revAz = Math.atan2(sinAlpha, -tmp); // final bearing
        return new Point2D.Double(point.x + (L * 180 / Math.PI), lat2 * 180
                / Math.PI);
    }

    /**
     * Computes the geodetic bounding box of a circle of the given center and radius and returns it as a WKT bounding object string.
     * <p>
     * Calculations are performed on the WGS84 spheroid using the Vincenty formula.
     * <p>
     * @param centerLat latitude of the circle center in decimal degrees
     * @param centerLon longitude of the circle center in decimal degrees
     * @param radius    circle radius in meters
     * @return the bounding box in WKT format
     */
    public static String circleBBox(double centerLat, double centerLon,
            double radius) {
        Point2D.Double center = new Point2D.Double(centerLon, centerLat);
        StringBuilder wkt = new StringBuilder("BOX(");
        // calculate the leftmost circle point (270° bearing or heading west)
        Point2D.Double point = directVincenty(center, 270, radius);
        wkt.append(point.x).append(' ');
        // calculate the bottom-most circle point (180° bearing or heading
        // south)
        point = directVincenty(center, 180, radius);
        wkt.append(point.y).append(',');
        // calculate the rightmost circle point (90° bearing or heading east)
        point = directVincenty(center, 90, radius);
        wkt.append(point.x).append(' ');
        // calculate the topmost circle point (0° bearing or heading north)
        point = directVincenty(center, 0, radius);
        wkt.append(point.y).append(")");
        return wkt.toString();
    }

    /**
     * Returns a WKT polygon string representing a rectangle from the given lower left and upper right corners.
     * <p>
     * @param lowLeft string containing the lower left corner longitude and latitude separated by whitespace
     * @param upRight string containing the upper right corner longitude and latitude separated by whitespace
     * @return a WKT polygon string
     */
    public static String bboxAsPolygon(String lowLeft, String upRight) {
        String[] tmp = lowLeft.split("\\s");
        if (tmp.length != 2) {
            throw new IllegalArgumentException("Lower left corner is not valid: " + lowLeft);
        }
        double llLon = Double.valueOf(tmp[0]);
        double llLat = Double.valueOf(tmp[1]);
        tmp = upRight.split("\\s");
        if (tmp.length != 2) {
            throw new IllegalArgumentException("Upper right corner is not valid: " + upRight);
        }
        double urLon = Double.valueOf(tmp[0]);
        double urLat = Double.valueOf(tmp[1]);
        return bboxAsPolygon(llLon, llLat, urLon, urLat);
    }

    /**
     * Returns a WKT polygon string representing a rectangle from the given lower left and upper right corners.
     * <p>
     * @param lowLeftLon lower left corner longitude
     * @param lowLeftLat lower left corner latitude
     * @param upRightLon upper right corner longitude
     * @param upRightLat upper right corner latitude
     * @return a WKT polygon string
     */
    public static String bboxAsPolygon(double lowLeftLon, double lowLeftLat,
            double upRightLon, double upRightLat) {
        StringBuilder sb = new StringBuilder("POLYGON((");
        sb.append(lowLeftLon).append(' ').append(lowLeftLat).append(',');
        sb.append(upRightLon).append(' ').append(lowLeftLat).append(',');
        sb.append(upRightLon).append(' ').append(upRightLat).append(',');
        sb.append(lowLeftLon).append(' ').append(upRightLat).append(',');
        sb.append(lowLeftLon).append(' ').append(lowLeftLat);
        sb.append("))");
        return sb.toString();
    }

    /**
     * Checks if the given space separated coordinates string is a valid sequence of Lat Lon points forming a polygon/polyline, optionally
     * closed.
     * <p>
     * No check is performed to see if two points are repeated.The coordinates must be at least 6 (3 points) or 8 (4 points) for closed
     * polygons/polylines.
     * <p>
     * @param coordString the coordinates string
     * @param closed      true to also check the last point is equal to the first
     * @return a short description of what's wrong, null if all ok
     */
    public static String checkIsPolySeq(String coordString, boolean closed) {
        // split space separated coordinates
        String[] coords = coordString.split("\\s+");
        // check coordinates are even
        if (coords.length % 2 != 0) {
            return "Odd number of coordinates";
        }
        // check coords are at least 4 or 6
        if (coords.length < 4 || (closed && coords.length < 6)) {
            return "Too few coordinates";
        }
        // check all coordinates are valid double numbers
        for (String c : coords) {
            try {
                double d = Double.valueOf(c);
            } catch (NumberFormatException e) {
                return c + " is not a number";
            }
        }
        if (closed) {
            // check the first two numbers are nearly equal to the last two (uses distance between double numbers)
            try {
                double d0 = Double.valueOf(coords[0]);
                double d1 = Double.valueOf(coords[1]);
                double d2 = Double.valueOf(coords[coords.length - 2]);
                double d3 = Double.valueOf(coords[coords.length - 1]);
                if (Math.abs(d0 - d2) > Math.ulp(d0) || Math.abs(d1 - d3) > Math.ulp(d1)) {
                    return "First and last point differ";
                }
            } catch (NumberFormatException e) {
                // should have been catched above
            }
        }
        //TODO check self intersection? Look for Bentley Ottman algorithm implementations
        return null;
    }
}
