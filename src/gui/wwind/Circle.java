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

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;

public class Circle {

    private LatLon _center = new LatLon(Angle.ZERO, Angle.ZERO);
    private double _radius = 0;

    public Circle() {
    }

    public Circle(LatLon center, double radius) {
        _center = center;
        _radius = radius;
    }

    public LatLon getCenter() {
        return _center;
    }

    public void setCenter(LatLon _center) {
        this._center = _center;
    }

    public double getRadius() {
        return _radius;
    }

    public void setRadius(double _radius) {
        this._radius = _radius;
    }

}
