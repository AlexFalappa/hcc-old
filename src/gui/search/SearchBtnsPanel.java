package gui.search;

import gov.nasa.worldwind.geom.LatLon;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import main.App;
import net.opengis.www.cat.csw._2_0_2.GetRecordsDocument;
import net.opengis.www.cat.csw._2_0_2.GetRecordsResponseDocument;
import net.opengis.www.cat.csw._2_0_2.GetRecordsType;
import net.opengis.www.cat.csw._2_0_2.ResultType;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;
import net.opengis.www.cat.wrs._1_0.ServiceExceptionReportFault;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class SearchBtnsPanel extends JPanel {
  final static Logger logger = Logger.getLogger(App.LOGGER_GUI);
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
        // TODO invocare getrecords results in swingworker
        doResults();
      }
    });
    panel.add(bResults);

    Component horizontalStrut_1 = Box.createHorizontalStrut(12);
    panel.add(horizontalStrut_1);

    bHits = new JButton("Hits");
    bHits.setEnabled(false);
    bHits.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // TODO invocare getrecords hits in swingworker
        doHits();
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

  private void doHits() {
    try {
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
      logger.info("Sending GetRecords HITS request");
      logger.finer("Request message:");
      logger.finer(reqDoc.xmlText(new XmlOptions().setSavePrettyPrint()));
      CatalogueStub st = App.frame.getWebServStub();
      GetRecordsResponseDocument respDoc = st.getRecords(reqDoc);
      logger.info("Received GetRecords HITS response");
      logger.fine("Response saved in:" + dumpToTempFile(respDoc, "hits-"));
      // extract hits
      BigInteger hits = respDoc.getGetRecordsResponse().getSearchResults()
          .getNumberOfRecordsMatched();
      if (hits.intValue() > 0) {
        // present hits and ask if user wants to retrieve them
        int choice = JOptionPane.showConfirmDialog(App.frame,
            "Search would return " + String.valueOf(hits) + " products.\nRetrieve them?", "Hits",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
          spinMaxRecord.setValue(hits.intValue());
          doResults();
        }
      } else {
        // present a simple information dialog
        JOptionPane.showMessageDialog(App.frame, "Search would return no hits!", "Hits",
            JOptionPane.INFORMATION_MESSAGE);
      }
    } catch (RemoteException e1) {
      e1.printStackTrace();
    } catch (ServiceExceptionReportFault e1) {
      JOptionPane.showMessageDialog(App.frame, e1.getFaultMessage().getExceptionReport()
          .getExceptionArray(0).getExceptionTextArray(0), "Error!", JOptionPane.WARNING_MESSAGE);
      logger.warning("Catalogue error message: " + e1.getFaultMessage());
    }
    // 20 random circles
    // for (int i = 0; i < 20; i++) {
    // double lon = -180.0 + r.nextDouble() * 360.0;
    // double lat = -90.0 + r.nextDouble() * 180.0;
    // double rad = 10000.0 + r.nextDouble() * 100000.0;
    // logger.info(String.valueOf(i) + " lat:" + String.valueOf(lat) +
    // " lon:" + String.valueOf(lon) + " rad:"
    // + String.valueOf(rad));
    // App.frame.footprints.addSurfCircle(lat, lon, rad);
    // }
    // path crossing the date line
    // List<Position> coords = new ArrayList<Position>(5);
    // coords.add(Position.fromDegrees(60, 175, 10000));
    // coords.add(Position.fromDegrees(60, -175, 10000));
    // coords.add(Position.fromDegrees(70, -175, 10000));
    // coords.add(Position.fromDegrees(70, 175, 10000));
    // coords.add(Position.fromDegrees(60, 175, 10000));
    // logger.info("Coords:" + coords.toString());
    // App.frame.footprints.addPath(coords, "Percorso");
    // App.frame.footprints.addSurfSect(-10, -170, 10, 170);
    // App.frame.footprints.addSurfSect(-10, -70, 10, 70);
    // App.frame.footprints.addSurfSect(-90, -180, 80, 180);
    // App.frame.footprints.addSurfSect(80, -180, 90, 180);
    App.frame.redrawGlobe();
  }

  private void doResults() {
    try {
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
      logger.info("Sending GetRecords RESULTS request");
      logger.finer("Request message:");
      logger.finer(reqDoc.xmlText(new XmlOptions().setSavePrettyPrint()));
      CatalogueStub st = App.frame.getWebServStub();
      GetRecordsResponseDocument respDoc = st.getRecords(reqDoc);
      logger.info("Received GetRecords RESULTS response");
      logger.fine("Response saved in:" + dumpToTempFile(respDoc, "results-"));
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
      logger.info(String.format("Received %d footprints", res.length));
      // polygon crossing the date line
      // List<Position> coords = new ArrayList<Position>(5);
      // coords.add(Position.fromDegrees(60, 175, 100000));
      // coords.add(Position.fromDegrees(60, -175, 100000));
      // coords.add(Position.fromDegrees(70, -175, 100000));
      // coords.add(Position.fromDegrees(70, 175, 100000));
      // coords.add(Position.fromDegrees(60, 175, 100000));
      // logger.info("Coords:" + coords.toString());
      // App.frame.footprints.addPoly(coords, "Poligono");

      // 20 random quads
      // for (int i = 0; i < 100; i++) {
      // double lon = -180.0 + r.nextDouble() * 360.0;
      // double lat = -90.0 + r.nextDouble() * 180.0;
      // double wid = 100000.0 + r.nextDouble() * 100000.0;
      // double hei = 100000.0 + r.nextDouble() * 100000.0;
      // String tip = String.format("%11.6f", lon) + ";" +
      // String.format("%10.6f", lat);
      // App.frame.footprints.addSurfQuad(lat, lon, wid, hei, tip);
      // }
      App.frame.redrawGlobe();
    } catch (RemoteException e1) {
      e1.printStackTrace();
    } catch (ServiceExceptionReportFault e1) {
      JOptionPane.showMessageDialog(App.frame, e1.getFaultMessage().getExceptionReport()
          .getExceptionArray(0).getExceptionTextArray(0), "Error!", JOptionPane.WARNING_MESSAGE);
      logger.warning("Catalogue error message: " + e1.getFaultMessage());
    }
  }

  private String dumpToTempFile(XmlObject xob, String tag) {
    try {
      // Create temp file.
      File temp = File.createTempFile(tag, ".xml");
      // Write to temp file
      xob.save(temp, new XmlOptions().setSavePrettyPrint());
      return temp.getAbsolutePath();
    } catch (IOException e) {
      logger.warning("Problem dumping soap message file!");
      logger.throwing(getClass().getName(), "SearchPanel.dumpToTempFile", e);
    }
    return "error";
  }

}
