/*
 * SunTerminatorPolyLineTimeDep.java
 *
 * =====================================================================
 *   This file is part of JSatTrak.
 *
 *   Copyright 2007-2013 Shawn E. Gano
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * =====================================================================
 * Created 20 June 2009
 */
package name.gano.worldwind.objects;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.Polyline;
import java.awt.Color;
import java.util.Vector;
import javax.media.opengl.GL;
import name.gano.astro.AstroConst;
import name.gano.astro.GeoFunctions;
import name.gano.astro.MathUtils;
import name.gano.astro.bodies.Sun;

/**
 * A polyline object that is timedependant, representing the Sun's terminator
 *
 * @author Shawn Gano
 */
public class SunTerminatorPolyLineTimeDep extends Polyline implements TimeDepRenderable {

    private Sun sun;
    private int numPoints = 4;

    public SunTerminatorPolyLineTimeDep(Sun sun) {
        this.sun = sun;
        this.setColor(Color.DARK_GRAY);
        this.setAntiAliasHint(ANTIALIAS_DONT_CARE);
        this.setPathType(GREAT_CIRCLE); // because this ends up being a big circle and this saves us on points needed
        this.setClosed(true);
        this.setLineWidth(2.0);
        this.setFollowTerrain(true);

        updateMJD(numPoints);
    }

    /**
     *
     * @param currentMJD
     */
    @Override
    public void updateMJD(double currentMJD) {
        // don't need to use currentMJD, just the fact the time has changed, sun object already has updated info

        // get new footprint points
        Vector<LatLon> llVec = getFootPrintLatLonList(sun.getCurrentLLA()[0], sun.getCurrentLLA()[1], sun.getCurrentLLA()[2], numPoints);

        // update polyline
        this.setPositions(llVec, 0.0);

    } // updateMJD

    @Override
    public void render(DrawContext dc) {
        javax.media.opengl.GL gl = dc.getGL();
// the following statement is not valid anymore in jogl 2.1.5, propbably worked in a previous version
//        gl.glPushAttrib(javax.media.opengl.GL.GL_TEXTURE_BIT | javax.media.opengl.GL.GL_ENABLE_BIT | javax.media.opengl.GL.GL_CURRENT_BIT);

        // Added so that the colors wouldn't depend on sun shading
        gl.glDisable(GL.GL_TEXTURE_2D);
        super.render(dc);

// the following statement is not valid anymore in jogl 2.1.5, propbably worked in a previous version
//        gl.glPopAttrib();
    }

    // ======================================================
    // Get footprint polygons - gets the shape(s) of the foot prints - similar to drawFootprint but no drawing
    // Assumes total width and total height match image size, doesn't take into account placement on page...
    //  the above assumption is for J2DEarthPanel!! because it on't draws on the image exactly
    /**
     * Returns a list of lat/lon (radians) for the footprint TODO: add elevation limiting constraint!
     * <p>
     * Assumes a Earth is a perfect sphere!
     *
     * @param lat radians
     * @param lon radians
     * @param alt meters
     * @param numPtsFootPrint
     * @return list of <lat,lon> vector (radians, radians)
     */
    public static Vector<LatLon> getFootPrintLatLonList(double lat, double lon, double alt, int numPtsFootPrint) {

        // vars: ===============================
        //
        Vector<LatLon> llVec = new Vector<LatLon>(numPtsFootPrint);

        //========================================
        //disconnectCount = 0; // reset disconnect count
        double lambda0 = Math.acos(AstroConst.R_Earth / (AstroConst.R_Earth + alt));

        // TODO - convert first geodetic/geographic!!?
        double beta = (90 * Math.PI / 180.0 - lat); // latitude center (pitch)
        double gamma = -lon + 180.0 * Math.PI / 180.0; // longitude (yaw)

        // rotation matrix
        double[][] M = new double[][]{{Math.cos(beta) * Math.cos(gamma), Math.sin(gamma), -Math.sin(beta) * Math.cos(gamma)},
        {-Math.cos(beta) * Math.sin(gamma), Math.cos(gamma), Math.sin(beta) * Math.sin(gamma)},
        {Math.sin(beta), 0.0, Math.cos(beta)}};
        double theta = 0 + Math.PI / 2.0; // with extra offset of pi/2 so circle starts left of center going counter clockwise
        double phi = lambda0;

        // position
        double[] pos = new double[3];
        pos[0] = AstroConst.R_Earth * Math.cos(theta) * Math.sin(phi);
        pos[1] = AstroConst.R_Earth * Math.sin(theta) * Math.sin(phi);
        pos[2] = AstroConst.R_Earth * Math.cos(phi);

        // rotate to center around satellite sub point
        pos = MathUtils.mult(M, pos);

        // calculate Lat Long of point (first time save it)
        double[] llaOld = GeoFunctions.ecef2lla_Fast(pos);
        //llaOld[1] = llaOld[1] - 90.0*Math.PI/180.0;
        double[] lla = new double[3]; // prepare array

        // copy of orginal point
        double[] lla0 = new double[3];
        lla0[0] = llaOld[0];
        lla0[1] = llaOld[1];
        lla0[2] = llaOld[2];

        // add to vector
        llVec.add(LatLon.fromRadians(lla0[0], lla0[1]));

        // footprint parameters
        double dt = 2.0 * Math.PI / (numPtsFootPrint - 1.0);

        for (int j = 1; j < numPtsFootPrint; j++) {
            theta = j * dt + Math.PI / 2.0; // +Math.PI/2.0 // offset so it starts at the side
            //phi = lambda0;

            // find position - unrotated about north pole
            pos[0] = AstroConst.R_Earth * Math.cos(theta) * Math.sin(phi);
            pos[1] = AstroConst.R_Earth * Math.sin(theta) * Math.sin(phi);
            pos[2] = AstroConst.R_Earth * Math.cos(phi);

            // rotate to center around satellite sub point
            pos = MathUtils.mult(M, pos);

            // find lla
            lla = GeoFunctions.ecef2lla_Fast(pos);
            //lla[1] = lla[1]-90.0*Math.PI/180.0;
            //System.out.println("ll=" +lla[0]*180.0/Math.PI + "," + (lla[1]*180.0/Math.PI));

            // add to vector
            llVec.add(LatLon.fromRadians(lla[0], lla[1]));

        } // for each point around footprint

        // return
        return llVec;

    } // getFootPrintPolygons

    // ===  get footprint polygons ===================================================
}
