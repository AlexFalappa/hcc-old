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
package gui;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import net.opengis.www.cat.csw._2_0_2.GetRecordsDocument;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;

/**
 * SwingWorker to make the GetRecords request.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class GetRecordsWorker extends SwingWorker<Integer, String> {

    private final MainWindow mw;
    private final CatalogueStub stub;
    private final boolean isResults;

    public GetRecordsWorker(MainWindow mw, CatalogueStub stub, boolean isResults) {
        this.mw = mw;
        this.stub = stub;
        this.isResults = isResults;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        publish("Building request...");
        GetRecordsDocument req = mw.buildReq(isResults);
        publish("Sending request...");
        Thread.sleep(2000);
//        final GetRecordsResponseDocument resp = stub.getRecords(req);
        publish("Processing response...");
        Thread.sleep(1000);
//        int recs = mw.processResponse(resp);
        int recs = mw.processResponse(null);
        publish("Done");
        return recs;
    }

    @Override
    protected void process(List<String> chunks) {
        mw.lMexs.setText(chunks.get(0));
    }

    @Override
    protected void done() {
        mw.enableSearchButtons(true);
        try {
            mw.lMexs.setText(String.format("Retrieved %d records", this.get()));
        } catch (InterruptedException | ExecutionException ex) {
        }
    }

}
