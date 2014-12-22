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
package net.falappa.wwind.posparser;

import gov.nasa.worldwind.geom.Position;

/**
 * A {@link PositionParser} for space separated Lat Lon strings.
 * <p>
 * Lat and lon should be double numbers
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class LatLonParser implements PositionParser {

    @Override
    public Position parseString(String text) {
        Position ret = null;
        // split text by space
        String[] numbers = text.split("\\s");
        // if there are exactly two components...
        if (numbers.length == 2) {
            try {
                // ... parse them as doubles...
                double lat = Double.valueOf(numbers[0]);
                double lon = Double.valueOf(numbers[1]);
                // ... return the position from those numbers
                return Position.fromDegrees(lat, lon);
            } catch (NumberFormatException nfe) {
                // do nothing will return null
            }
        }
        return ret;
    }

}
