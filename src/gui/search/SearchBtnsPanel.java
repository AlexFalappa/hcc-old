package gui.search;

import gov.nasa.worldwind.geom.LatLon;
import gui.MainFrame;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import main.App;
import net.opengis.www.cat.csw._2_0_2.GetRecordsDocument;
import net.opengis.www.cat.csw._2_0_2.GetRecordsResponseDocument;
import net.opengis.www.cat.csw._2_0_2.GetRecordsType;
import net.opengis.www.cat.csw._2_0_2.ResultType;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class SearchBtnsPanel extends JPanel {
  private JButton bResults;
  private JButton bHits;
  private SearchPanel searchPanel;
  private JSpinner spinFromRecord;
  private JSpinner spinMaxRecord;

  /**
   * Create the panel.
   */
  public SearchBtnsPanel() {
    setBorder(new EmptyBorder(3, 3, 3, 3));
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
    setLayout(gridBagLayout);

    JLabel lblMax = new JLabel("Max records:");
    GridBagConstraints gbc_lblMax = new GridBagConstraints();
    gbc_lblMax.anchor = GridBagConstraints.EAST;
    gbc_lblMax.insets = new Insets(0, 0, 5, 5);
    gbc_lblMax.gridx = 0;
    gbc_lblMax.gridy = 0;
    add(lblMax, gbc_lblMax);

    spinMaxRecord = new JSpinner();
    spinMaxRecord.setEnabled(false);
    spinMaxRecord.setModel(new SpinnerNumberModel(10, 1, 100000, 5));
    GridBagConstraints gbc_spinMaxRecord = new GridBagConstraints();
    gbc_spinMaxRecord.insets = new Insets(0, 0, 5, 5);
    gbc_spinMaxRecord.gridx = 1;
    gbc_spinMaxRecord.gridy = 0;
    add(spinMaxRecord, gbc_spinMaxRecord);

    JLabel lblFrom = new JLabel("Start from:");
    GridBagConstraints gbc_lblFrom = new GridBagConstraints();
    gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
    gbc_lblFrom.gridx = 0;
    gbc_lblFrom.gridy = 1;
    add(lblFrom, gbc_lblFrom);

    spinFromRecord = new JSpinner();
    spinFromRecord.setEnabled(false);
    spinFromRecord.setModel(new SpinnerNumberModel(1, 0, 1000, 1));
    GridBagConstraints gbc_spinFromRecord = new GridBagConstraints();
    gbc_spinFromRecord.anchor = GridBagConstraints.WEST;
    gbc_spinFromRecord.insets = new Insets(0, 0, 5, 5);
    gbc_spinFromRecord.gridx = 1;
    gbc_spinFromRecord.gridy = 1;
    add(spinFromRecord, gbc_spinFromRecord);

    JPanel panel = new JPanel();
    GridBagConstraints gbc_panel = new GridBagConstraints();
    gbc_panel.gridwidth = 2;
    gbc_panel.fill = GridBagConstraints.BOTH;
    gbc_panel.gridx = 0;
    gbc_panel.gridy = 2;
    add(panel, gbc_panel);
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

    Component horizontalGlue_1 = Box.createHorizontalGlue();
    panel.add(horizontalGlue_1);

    bResults = new JButton("Results");
    bResults.setEnabled(false);
    bResults.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GetRecordsDocument reqDoc = searchPanel.extractFromPanel();
        if (reqDoc == null) {
          JOptionPane.showMessageDialog(App.frame, "No collection selected!", "Error",
              JOptionPane.ERROR_MESSAGE);
          return;
        }
        GetRecordsType root = reqDoc.getGetRecords();
        root.setResultType(ResultType.RESULTS);
        int max = (Integer) spinMaxRecord.getValue();
        root.setMaxRecords(BigInteger.valueOf(max));
        int start = (Integer) spinFromRecord.getValue();
        root.setStartPosition(BigInteger.valueOf(start));
        bResults.setEnabled(false);
        bHits.setEnabled(false);
        App.frame.setStatus("Sending GetRecords RESULTS request...");
        App.frame.setGlobalBusyCursor(true);
        ResultsWorker rw = new ResultsWorker(reqDoc);
        rw.execute();
      }
    });
    panel.add(bResults);

    Component horizontalStrut_1 = Box.createHorizontalStrut(6);
    panel.add(horizontalStrut_1);

    bHits = new JButton("Hits");
    bHits.setEnabled(false);
    bHits.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GetRecordsDocument reqDoc = searchPanel.extractFromPanel();
        if (reqDoc == null) {
          JOptionPane.showMessageDialog(App.frame, "No collection selected!", "Error",
              JOptionPane.ERROR_MESSAGE);
          return;
        }
        GetRecordsType root = reqDoc.getGetRecords();
        root.setResultType(ResultType.HITS);
        int max = (Integer) spinMaxRecord.getValue();
        root.setMaxRecords(BigInteger.valueOf(max));
        int start = (Integer) spinFromRecord.getValue();
        root.setStartPosition(BigInteger.valueOf(start));
        bHits.setEnabled(false);
        bResults.setEnabled(false);
        App.frame.setStatus("Sending GetRecords HITS request...");
        App.frame.setGlobalBusyCursor(true);
        HitsWorker hw = new HitsWorker(reqDoc);
        hw.execute();
      }
    });
    panel.add(bHits);

    Component horizontalGlue = Box.createHorizontalGlue();
    panel.add(horizontalGlue);
  }

  public void setEnabled(boolean enabl) {
    bHits.setEnabled(enabl);
    bResults.setEnabled(enabl);
    spinFromRecord.setEnabled(enabl);
    spinMaxRecord.setEnabled(enabl);
  }

  public void linkTo(SearchPanel searchPanel) {
    this.searchPanel = searchPanel;
  }

  private String dumpToTempFile(XmlObject xob, String tag) {
    try {
      // Create temp file.
      File temp = File.createTempFile(tag, ".xml");
      // Write to temp file
      xob.save(temp, new XmlOptions().setSavePrettyPrint());
      return temp.getAbsolutePath();
    } catch (IOException e) {
      MainFrame.logger.warning("Problem dumping soap message file!");
      MainFrame.logger.throwing(getClass().getName(), "dumpToTempFile", e);
    }
    return "no dump";
  }

  class HitsWorker extends SwingWorker<GetRecordsResponseDocument, Void> {
    final GetRecordsDocument _reqDoc;

    HitsWorker(GetRecordsDocument reqDoc) {
      _reqDoc = reqDoc;
    }

    @Override
    protected GetRecordsResponseDocument doInBackground() throws Exception {
      MainFrame.logger.info("Sending GetRecords HITS request");
      MainFrame.logger.finer("Request message:");
      MainFrame.logger.finer(_reqDoc.xmlText(new XmlOptions().setSavePrettyPrint()));
      CatalogueStub st = App.frame.getWebServStub();
      GetRecordsResponseDocument respDoc = st.getRecords(_reqDoc);
      MainFrame.logger.info("Received GetRecords HITS response");
      MainFrame.logger.fine("Response saved in:" + dumpToTempFile(respDoc, "hits-"));
      return respDoc;
    }

    @Override
    protected void done() {
      ResultsWorker rw = null;
      try {
        // extract hits
        GetRecordsResponseDocument respDoc = get();
        BigInteger hits = respDoc.getGetRecordsResponse().getSearchResults()
            .getNumberOfRecordsMatched();
        if (hits.intValue() > 0) {
          // present hits and ask if user wants to retrieve them
          int choice = JOptionPane.showConfirmDialog(App.frame,
              "Search would return " + String.valueOf(hits) + " products.\nRetrieve them?", "Hits",
              JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (choice == JOptionPane.YES_OPTION) {
            GetRecordsType root = _reqDoc.getGetRecords();
            root.setResultType(ResultType.RESULTS);
            spinMaxRecord.setValue(hits.intValue());
            root.setMaxRecords(hits);
            int start = (Integer) spinFromRecord.getValue();
            root.setStartPosition(BigInteger.valueOf(start));
            bResults.setEnabled(false);
            bHits.setEnabled(false);
            App.frame.setStatus("Sending GetRecords RESULTS request...");
            rw = new ResultsWorker(_reqDoc);
            rw.execute();
          }
        } else {
          // present a simple information dialog
          JOptionPane.showMessageDialog(App.frame, "Search would return no hits!", "Notice",
              JOptionPane.INFORMATION_MESSAGE);
        }
      } catch (InterruptedException | CancellationException e) {
        MainFrame.logger.fine("Operation interrupted or cancelled");
      } catch (ExecutionException e) {
        JOptionPane.showMessageDialog(App.frame, "Error:\n" + e.getMessage(), "Error!",
            JOptionPane.ERROR_MESSAGE);
      }
      if (rw == null) {
        App.frame.setStatus("Received response");
        App.frame.setGlobalBusyCursor(false);
        App.frame.redrawGlobe();
        bHits.setEnabled(true);
        bResults.setEnabled(true);
      }
    }
  }

  class ResultsWorker extends SwingWorker<GetRecordsResponseDocument, Void> {
    final GetRecordsDocument _reqDoc;

    ResultsWorker(GetRecordsDocument reqDoc) {
      _reqDoc = reqDoc;
    }

    @Override
    protected GetRecordsResponseDocument doInBackground() throws Exception {
      MainFrame.logger.info("Sending GetRecords RESULTS request");
      MainFrame.logger.finer("Request message:");
      MainFrame.logger.finer(_reqDoc.xmlText(new XmlOptions().setSavePrettyPrint()));
      CatalogueStub st = App.frame.getWebServStub();
      GetRecordsResponseDocument respDoc = st.getRecords(_reqDoc);
      MainFrame.logger.info("Received GetRecords RESULTS response");
      MainFrame.logger.fine("Response saved in:" + dumpToTempFile(respDoc, "results-"));
      return respDoc;
    }

    @Override
    protected void done() {
      try {
        // extract footprints
        GetRecordsResponseDocument respDoc = get();
        XmlObject[] res = respDoc
            .selectPath("declare namespace gml='http://www.opengis.net/gml' .//gml:posList");
        if (res.length > 0) {
          App.frame.footprints.removeAllRenderables();
        }
        for (XmlObject xo : res) {
          XmlCursor xc = xo.newCursor();
          String[] coords = xc.getTextValue().split("\\s");
          xc.dispose();
          List<LatLon> geopoints = new ArrayList<LatLon>();
          for (int i = 0; i < coords.length; i += 2) {
            double lat = Double.valueOf(coords[i]);
            double lon = Double.valueOf(coords[i + 1]);
            geopoints.add(LatLon.fromDegrees(lat, lon));
          }
          App.frame.footprints.addSurfPoly(geopoints);
        }
        String mex = String.format("Received %d footprints", res.length);
        MainFrame.logger.info(mex);
        App.frame.setStatus(mex);
        App.frame.setGlobalBusyCursor(false);
        App.frame.redrawGlobe();
      } catch (InterruptedException | CancellationException e) {
        MainFrame.logger.fine("Operation interrupted or cancelled");
      } catch (ExecutionException e) {
        JOptionPane.showMessageDialog(App.frame, "Error:\n" + e.getMessage(), "Error!",
            JOptionPane.ERROR_MESSAGE);
      }
      bHits.setEnabled(true);
      bResults.setEnabled(true);
    }
  }

}
