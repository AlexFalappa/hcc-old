/*
 * Copyright 2014 afalappa.
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
package gui;

import gui.panels.CollectionsPanel;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import main.App;
import main.hma.HmaGetCapabilitiesBuilder;
import net.opengis.www.cat.wrs._1_0.CapabilitiesDocument;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

/**
 * SwingWorker to make the HMA GetCapabilities request.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class CapabilitiesWorker extends SwingWorker<CapabilitiesDocument, Void> {

    private static final String NS_RIM = "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0";

    private final HmaGetCapabilitiesBuilder builder = new HmaGetCapabilitiesBuilder();
    private final CatalogueStub stub;
    private final CollectionsPanel cpane;

    public CapabilitiesWorker(CatalogueStub stub, CollectionsPanel pane) {
        this.stub = stub;
        this.cpane = pane;
    }

    @Override
    public CapabilitiesDocument doInBackground() throws Exception {
        CapabilitiesDocument capDoc = stub.getCapabilities(builder.getRequest());
        return capDoc;
    }

    @Override
    public void done() {
        try {
            CapabilitiesDocument capDoc = get();
            if (capDoc != null) {
                ArrayList<String> collections = new ArrayList<>();
                XmlObject[] res = capDoc.selectPath("declare namespace rim='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' .//rim:Slot");
                if (res.length > 0) {
                    XmlCursor xc = res[0].newCursor();
                    xc.toChild(NS_RIM, "ValueList");
                    if (xc.toFirstChild()) {
                        // at first <Value> tag
                        collections.add(xc.getTextValue());
                        // iterate on sibling <Value> tags
                        while (xc.toNextSibling()) {
                            collections.add(xc.getTextValue());
                        }
                    }
                    xc.dispose();
                }
                cpane.setCollections(collections.toArray(new String[]{}));
            }
        } catch (InterruptedException | ExecutionException e) {
            App.frame.showErrorDialog("Error", "Collection discovery failed!", e);
        }
    }
}
