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

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.opengis.www.cat.csw._2_0_2.GetRecordsDocument;
import net.opengis.www.cat.csw._2_0_2.GetRecordsResponseDocument;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;
import net.opengis.www.cat.wrs._1_0.ServiceExceptionReportFault;
import net.opengis.www.ows.ExceptionType;

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
        int recs = 0;
        try {
            final GetRecordsResponseDocument resp = stub.getRecords(req);
            if (isResults) {
                publish("Processing response...");
                recs = mw.processResults(resp);
            } else {
                recs = mw.processHits(resp);
            }
            publish("Done");
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace(System.err);
        } catch (ServiceExceptionReportFault exRep) {
            final ExceptionType exc = exRep.getFaultMessage().getExceptionReport().getExceptionArray(0);
            System.err.println(exc.getExceptionCode());
            System.err.println(exc.getExceptionTextArray(0));
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }
        return recs;
    }

    @Override
    protected void process(List<String> chunks) {
        mw.lMexs.setText(chunks.get(chunks.size() - 1));
    }

    @Override
    protected void done() {
        mw.enableSearchButtons(true);
        try {
            final Integer records = this.get();
            if (isResults) {
                mw.lMexs.setText(String.format("Retrieved %d records", records));
            } else {
                JOptionPane.showMessageDialog(mw, String.format("Query will give %d records", records), "Hits", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException ex) {
        }
    }

}
