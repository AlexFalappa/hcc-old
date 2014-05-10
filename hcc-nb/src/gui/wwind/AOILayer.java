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
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.render.SurfaceSector;
import java.awt.Color;
import java.util.List;

public class AOILayer extends RenderableLayer {

    private final BasicShapeAttributes attr = new BasicShapeAttributes();
    private Renderable current;

    public AOILayer() {
        // properties of layer
        setName("Area of interest");
        setEnabled(true);
        setPickEnabled(false);
        // painting attributes for footprints
        attr.setOutlineMaterial(Material.RED);
        attr.setOutlineWidth(2);
        attr.setInteriorMaterial(new Material(Color.red.brighter().brighter()));
        attr.setInteriorOpacity(0.1f);
    }

    public void setColor(Color col) {
        attr.setOutlineMaterial(new Material(col));
        attr.setInteriorMaterial(new Material(col.brighter().brighter()));
    }

    public void setSurfCircle(Circle circ) {
        removeOldAoI();
        SurfaceCircle shape = new SurfaceCircle(attr, circ.getCenter(), circ.getRadius());
        addRenderable(shape);
    }

    public void setSurfCircle(LatLon center, double radius) {
        removeOldAoI();
        current = new SurfaceCircle(attr, center, radius);
        addRenderable(current);
    }

    public void setSurfPoly(Iterable<? extends LatLon> coords) {
        removeOldAoI();
        current = new SurfacePolygon(attr, coords);
        addRenderable(current);
    }

    public void setSurfSect(double minlat, double minlon, double maxlat, double maxlon) {
        removeOldAoI();
        SurfaceSector shape = new SurfaceSector(attr,
                Sector.fromDegrees(minlat, maxlat, minlon, maxlon));
        shape.setPathType(AVKey.LINEAR);
        current = shape;
        addRenderable(current);
    }

    public void setSurfSect(Sector sec) {
        removeOldAoI();
        SurfaceSector shape = new SurfaceSector(attr, sec);
        shape.setPathType(AVKey.LINEAR);
        current = shape;
        addRenderable(current);
    }

    public void setSurfLine(List<Position> coords) {
        removeOldAoI();
        Path shape = new Path(coords);
        shape.setAttributes(attr);
        shape.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        shape.setPathType(AVKey.GREAT_CIRCLE);
        shape.setFollowTerrain(true);
        shape.setTerrainConformance(40);
        current = shape;
        addRenderable(current);
    }

    public void clear() {
        removeAllRenderables();
    }

    private void removeOldAoI() {
        if (current != null) {
            removeRenderable(current);
        }
    }

}
