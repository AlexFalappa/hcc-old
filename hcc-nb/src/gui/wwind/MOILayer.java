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

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;
import gov.nasa.worldwind.render.markers.Marker;
import java.awt.Color;
import java.util.ArrayList;

public class MOILayer extends MarkerLayer {

    private final BasicMarkerAttributes attr = new BasicMarkerAttributes();
    private final ArrayList<Marker> mrkrs = new ArrayList<>();

    public MOILayer() {
        // properties of layer
        setMarkers(mrkrs);
        setName("Marker of interest");
        setEnabled(true);
        setPickEnabled(false);
        // painting attributes for markers
        attr.setShapeType(BasicMarkerShape.SPHERE);
        attr.setMaterial(Material.RED);
        attr.setMarkerPixels(8);
        attr.setMinMarkerSize(3);
    }

    public void setColor(Color col) {
        attr.setMaterial(new Material(col));
    }

    public void setMarker(Position coords) {
        if (mrkrs.size() > 0) {
            mrkrs.clear();
        }
        Position newPos = new Position(coords, coords.elevation + 100d);
        Marker marker = new BasicMarker(newPos, attr);
        mrkrs.add(marker);
    }

    public void clear() {
        mrkrs.clear();
    }
}
