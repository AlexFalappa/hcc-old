/*
 * EarthNightSurfacePolygon.java
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
package net.falappa.wwind.helpers;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.SurfacePolygon;
import java.util.ArrayList;
import name.gano.astro.AstroConst;
import name.gano.astro.GeoFunctions;
import name.gano.astro.MathUtils;
import name.gano.astro.bodies.Sun;

/**
 * A SurfacePolygon object representing the night earth area.
 * <p>
 * Derived from SunTerminatorPolyLineTimeDep from JSatTrack.
 *
 * @author Shawn Gano
 * @author Alessandro Falappa
 */
public class EarthNightSurfacePolygon extends SurfacePolygon {

    private static final int POLY_POINTS = 6;
    // angle offset to generate a sliglthly less than a great circle (to make the surface polygon interior be in the right night zone)
    private static final double GREAT_CIRCLE_OFFSET = 0.0001;
    private final Sun sun;
    private final RenderableLayer rl;
    private final ArrayList<LatLon> llVec = new ArrayList<>(POLY_POINTS);

    public EarthNightSurfacePolygon(Sun sun, RenderableLayer rl) {
        super();
        this.sun = sun;
        this.rl = rl;
        // default attributes
        BasicShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setEnableAntialiasing(true);
        attrs.setInteriorOpacity(0.5);
        attrs.setInteriorMaterial(Material.BLACK);
        attrs.setOutlineMaterial(Material.GRAY);
        attrs.setOutlineWidth(1.0);
        this.setAttributes(attrs);
        // create initial dummy positions
        for (int i = 0; i < POLY_POINTS; i++) {
            llVec.add(LatLon.ZERO);
        }
        // substitute calculated positions
        calcPositions(sun.getCurrentLLA()[0], sun.getCurrentLLA()[1], sun.getCurrentLLA()[2]);
        // set llVec as the polygon point storage
        setLocations(llVec);
    }

    /**
     * Recalculate the shape depending on the current sun position.
     */
    public void updateFromSun() {
        calcPositions(sun.getCurrentLLA()[0], sun.getCurrentLLA()[1], sun.getCurrentLLA()[2]);
        //fire points changed event
        onShapeChanged();
    }

// code from SunTerminatorPolyLineTimeDep kept in case lighting is added to WWindPanel
//    @Override
//    public void render(DrawContext dc) {
//        javax.media.opengl.GL gl = dc.getGL();
//        gl.glPushAttrib(javax.media.opengl.GL.GL_TEXTURE_BIT | javax.media.opengl.GL.GL_ENABLE_BIT | javax.media.opengl.GL.GL_CURRENT_BIT);
//        // Added so that the colors wouldn't depend on sun shading
//        gl.glDisable(GL.GL_TEXTURE_2D);
//        super.render(dc);
//        gl.glPopAttrib();
//    }
    /**
     * Recalculates the list of lat/lon (radians) for the polygon.
     * <p>
     * Note: assumes the Earth is a perfect sphere!
     *
     * @param lat sun latitude in radians
     * @param lon sun longitude in radians
     * @param alt sun altitude in meters
     */
    private void calcPositions(double lat, double lon, double alt) {
        double lambda0 = Math.acos(AstroConst.R_Earth / (AstroConst.R_Earth + alt));
        // TODO - convert first geodetic/geographic!!?
        double beta = (90 * Math.PI / 180.0 - lat); // latitude center (pitch)
        double gamma = -lon + 180.0 * Math.PI / 180.0; // longitude (yaw)
        // rotation matrix
        double[][] M = new double[][]{{Math.cos(beta) * Math.cos(gamma), Math.sin(gamma), -Math.sin(beta) * Math.cos(gamma)},
        {-Math.cos(beta) * Math.sin(gamma), Math.cos(gamma), Math.sin(beta) * Math.sin(gamma)},
        {Math.sin(beta), 0.0, Math.cos(beta)}};
        double theta = 0 + Math.PI / 2.0; // with extra offset of pi/2 so circle starts left of center going counter clockwise
        double phi = lambda0 + GREAT_CIRCLE_OFFSET;
        // position
        double[] pos = new double[3];
        pos[0] = AstroConst.R_Earth * Math.cos(theta) * Math.sin(phi);
        pos[1] = AstroConst.R_Earth * Math.sin(theta) * Math.sin(phi);
        pos[2] = AstroConst.R_Earth * Math.cos(phi);
        // rotate to center around satellite sub point
        pos = MathUtils.mult(M, pos);
        // calculate Lat Long of point (first time save it)
        double[] lla0 = GeoFunctions.ecef2lla_Fast(pos);
        // add to vector
        llVec.set(0, LatLon.fromRadians(lla0[0], lla0[1]));
        // other polygon points
        double[] lla; // prepare array
        double dt = 2.0 * Math.PI / (POLY_POINTS - 1.0);
        for (int j = 1; j < POLY_POINTS; j++) {
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
            // add to vector
            llVec.set(j, LatLon.fromRadians(lla[0], lla[1]));
        } // for each point around footprint
        // return
//        Collections.reverse(llVec);
    }

}
