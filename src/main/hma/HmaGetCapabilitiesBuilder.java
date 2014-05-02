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
package main.hma;

import java.io.IOException;
import net.opengis.www.cat.csw._2_0_2.GetCapabilitiesDocument;
import org.apache.xmlbeans.XmlException;

/**
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class HmaGetCapabilitiesBuilder {

    GetCapabilitiesDocument capDoc;

    public HmaGetCapabilitiesBuilder() {
        try {
            capDoc = GetCapabilitiesDocument.Factory.parse(getClass().getResourceAsStream("templates/capabilities-template.xml"));
        } catch (XmlException | IOException ex) {
            // ignored should always works
        }
    }

    public GetCapabilitiesDocument getRequest() {
        return capDoc;
    }
}
