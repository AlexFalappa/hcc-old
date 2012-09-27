package gui.search;

import gov.nasa.worldwind.WorldWindow;
import gui.search.params.CirclePanel;
import gui.search.params.DateTimePanel;
import gui.search.params.LonLatRangePanel;
import gui.search.params.PolygonPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import main.App;
import net.opengis.www.cat.csw._2_0_2.GetRecordsDocument;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import wwind.AOILayer;

public class SearchPanel extends JPanel implements ItemListener {
  final static Logger logger = Logger.getLogger(App.LOGGER_GUI);
  final String[] paramsPanels = new String[] { "Polygon", "Lat/Lon range", "Circle" };
  private CardLayout paramsLayout;
  private JPanel spatialParamsPanels;
  private JComboBox<String> timeOpCombo;
  private LonLatRangePanel lonLatRangePanel;
  private CirclePanel circlePanel;
  private PolygonPanel polygonPanel;
  private JList<String> lstCollections;
  private JComboBox<String> spatOpCombo;
  private DateTimePanel dtpFrom;
  private DateTimePanel dtpTo;
  private JCheckBox chckbxSpatialConstraints;
  private JCheckBox chckbxTimeConstraints;
  private SearchBtnsPanel pSearchButtons;

  /**
   * Create the panel.
   */
  public SearchPanel() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0 };
    gridBagLayout.columnWidths = new int[] { 20, 0, 0 };
    setLayout(gridBagLayout);

    chckbxTimeConstraints = new JCheckBox("Time constraints:");
    chckbxTimeConstraints.setEnabled(false);
    chckbxTimeConstraints.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        boolean enabled = (e.getStateChange() == ItemEvent.SELECTED);
        timeOpCombo.setEnabled(enabled);
        dtpFrom.setEnabled(enabled);
        dtpTo.setEnabled(enabled);
      }
    });
    GridBagConstraints gbc_chckbxTimeConstraints = new GridBagConstraints();
    gbc_chckbxTimeConstraints.anchor = GridBagConstraints.NORTHWEST;
    gbc_chckbxTimeConstraints.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxTimeConstraints.gridwidth = 3;
    gbc_chckbxTimeConstraints.gridx = 0;
    gbc_chckbxTimeConstraints.gridy = 2;
    add(chckbxTimeConstraints, gbc_chckbxTimeConstraints);

    JLabel lblCollections = new JLabel("Collections:");
    GridBagConstraints gbc_lblCollections = new GridBagConstraints();
    gbc_lblCollections.anchor = GridBagConstraints.NORTHWEST;
    gbc_lblCollections.insets = new Insets(0, 0, 5, 0);
    gbc_lblCollections.gridwidth = 3;
    gbc_lblCollections.gridx = 0;
    gbc_lblCollections.gridy = 0;
    add(lblCollections, gbc_lblCollections);

    lstCollections = new JList<String>(new String[] { "Connect to a catalogue" });
    lstCollections.setEnabled(false);
    lstCollections.setVisibleRowCount(5);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    scrollPane.setViewportView(lstCollections);
    scrollPane.setPreferredSize(new Dimension(100, 100));
    GridBagConstraints gbc_scrollPane = new GridBagConstraints();
    gbc_scrollPane.fill = GridBagConstraints.BOTH;
    gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
    gbc_scrollPane.gridwidth = 3;
    gbc_scrollPane.gridx = 0;
    gbc_scrollPane.gridy = 1;
    add(scrollPane, gbc_scrollPane);

    timeOpCombo = new JComboBox<String>();
    timeOpCombo.setModel(new DefaultComboBoxModel<String>(new String[] { "Contained in",
        "Overlaps", "After", "Before" }));
    timeOpCombo.setEnabled(false);
    GridBagConstraints gbc_timeOpCombo = new GridBagConstraints();
    gbc_timeOpCombo.anchor = GridBagConstraints.NORTHWEST;
    gbc_timeOpCombo.insets = new Insets(0, 0, 5, 0);
    gbc_timeOpCombo.gridwidth = 2;
    gbc_timeOpCombo.gridx = 1;
    gbc_timeOpCombo.gridy = 3;
    add(timeOpCombo, gbc_timeOpCombo);

    JLabel lblFrom = new JLabel("Start:");
    GridBagConstraints gbc_lblFrom = new GridBagConstraints();
    gbc_lblFrom.anchor = GridBagConstraints.NORTHEAST;
    gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
    gbc_lblFrom.gridx = 1;
    gbc_lblFrom.gridy = 4;
    add(lblFrom, gbc_lblFrom);

    chckbxSpatialConstraints = new JCheckBox("Spatial constraints:");
    chckbxSpatialConstraints.setEnabled(false);
    chckbxSpatialConstraints.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        boolean enabled = (e.getStateChange() == ItemEvent.SELECTED);
        spatOpCombo.setEnabled(enabled);
        lonLatRangePanel.setEnabled(enabled);
        circlePanel.setEnabled(enabled);
        polygonPanel.setEnabled(enabled);
      }
    });

    dtpFrom = new DateTimePanel();
    dtpFrom.setEnabled(false);
    GridBagConstraints gbc_dtpFrom = new GridBagConstraints();
    gbc_dtpFrom.fill = GridBagConstraints.HORIZONTAL;
    gbc_dtpFrom.anchor = GridBagConstraints.NORTH;
    gbc_dtpFrom.insets = new Insets(0, 0, 5, 0);
    gbc_dtpFrom.gridx = 2;
    gbc_dtpFrom.gridy = 4;
    add(dtpFrom, gbc_dtpFrom);

    JLabel lblTo = new JLabel("End:");
    GridBagConstraints gbc_lblTo = new GridBagConstraints();
    gbc_lblTo.anchor = GridBagConstraints.NORTHEAST;
    gbc_lblTo.insets = new Insets(0, 0, 5, 5);
    gbc_lblTo.gridx = 1;
    gbc_lblTo.gridy = 5;
    add(lblTo, gbc_lblTo);

    dtpTo = new DateTimePanel();
    dtpTo.setEnabled(false);
    GridBagConstraints gbc_dtpTo = new GridBagConstraints();
    gbc_dtpTo.fill = GridBagConstraints.HORIZONTAL;
    gbc_dtpTo.anchor = GridBagConstraints.NORTH;
    gbc_dtpTo.insets = new Insets(0, 0, 5, 0);
    gbc_dtpTo.gridx = 2;
    gbc_dtpTo.gridy = 5;
    add(dtpTo, gbc_dtpTo);
    GridBagConstraints gbc_chckbxSpatialConstraints = new GridBagConstraints();
    gbc_chckbxSpatialConstraints.anchor = GridBagConstraints.NORTHWEST;
    gbc_chckbxSpatialConstraints.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxSpatialConstraints.gridwidth = 3;
    gbc_chckbxSpatialConstraints.gridx = 0;
    gbc_chckbxSpatialConstraints.gridy = 6;
    add(chckbxSpatialConstraints, gbc_chckbxSpatialConstraints);

    spatOpCombo = new JComboBox<String>();
    spatOpCombo.setMaximumRowCount(3);
    spatOpCombo.setEnabled(false);
    spatOpCombo.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String pname = (String) cb.getSelectedItem();
        paramsLayout.show(spatialParamsPanels, pname);
      }
    });
    spatOpCombo.setModel(new DefaultComboBoxModel<String>(paramsPanels));
    GridBagConstraints gbc_spatOpCombo = new GridBagConstraints();
    gbc_spatOpCombo.anchor = GridBagConstraints.NORTHWEST;
    gbc_spatOpCombo.insets = new Insets(0, 0, 5, 0);
    gbc_spatOpCombo.gridwidth = 2;
    gbc_spatOpCombo.gridx = 1;
    gbc_spatOpCombo.gridy = 7;
    add(spatOpCombo, gbc_spatOpCombo);

    spatialParamsPanels = new JPanel();
    paramsLayout = new CardLayout(0, 0);
    spatialParamsPanels.setLayout(paramsLayout);
    spatialParamsPanels.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    GridBagConstraints gbc_spatialParamsPanels = new GridBagConstraints();
    gbc_spatialParamsPanels.fill = GridBagConstraints.BOTH;
    gbc_spatialParamsPanels.gridwidth = 2;
    gbc_spatialParamsPanels.insets = new Insets(0, 0, 5, 0);
    gbc_spatialParamsPanels.gridx = 1;
    gbc_spatialParamsPanels.gridy = 8;
    add(spatialParamsPanels, gbc_spatialParamsPanels);

    polygonPanel = new PolygonPanel();
    polygonPanel.setEnabled(false);
    spatialParamsPanels.add(polygonPanel, paramsPanels[0]);

    lonLatRangePanel = new LonLatRangePanel();
    lonLatRangePanel.setEnabled(false);
    spatialParamsPanels.add(lonLatRangePanel, paramsPanels[1]);

    circlePanel = new CirclePanel();
    circlePanel.setEnabled(false);
    spatialParamsPanels.add(circlePanel, paramsPanels[2]);

    JLabel lblSearch = new JLabel("Get:");
    GridBagConstraints gbc_lblSearch = new GridBagConstraints();
    gbc_lblSearch.gridwidth = 3;
    gbc_lblSearch.anchor = GridBagConstraints.NORTHWEST;
    gbc_lblSearch.insets = new Insets(0, 0, 5, 0);
    gbc_lblSearch.gridx = 0;
    gbc_lblSearch.gridy = 9;
    add(lblSearch, gbc_lblSearch);

    pSearchButtons = new SearchBtnsPanel();
    pSearchButtons.linkTo(this);
    GridBagConstraints gbc_pSearchButtons = new GridBagConstraints();
    gbc_pSearchButtons.fill = GridBagConstraints.BOTH;
    gbc_pSearchButtons.gridwidth = 2;
    gbc_pSearchButtons.gridx = 1;
    gbc_pSearchButtons.gridy = 10;
    add(pSearchButtons, gbc_pSearchButtons);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    JComboBox<String> cb = (JComboBox<String>) e.getSource();
    String pname = (String) cb.getSelectedItem();
    paramsLayout.show(spatialParamsPanels, pname);
  }

  public void setCollections(String[] coll) {
    lstCollections.setModel(new CollectionListModel(coll));
    lstCollections.setEnabled(true);
    chckbxTimeConstraints.setEnabled(true);
    chckbxSpatialConstraints.setEnabled(true);
    pSearchButtons.setEnabled(true);
  }

  public void linkTo(WorldWindow wwd, AOILayer aoi) {
    lonLatRangePanel.linkTo(wwd);
    polygonPanel.linkTo(wwd, aoi);
  }

  public GetRecordsDocument extractFromPanel() {
    GetRecordsDocument req = null;
    try {
      Object[] selectedValues = lstCollections.getSelectedValuesList().toArray();
      // short circuit if no collection selected
      if (selectedValues.length == 0)
        return null;
      // log selected collections
      StringBuilder sb = new StringBuilder("Selected collections: ");
      for (Object ob : selectedValues)
        sb.append(ob).append(',');
      logger.fine(sb.toString());
      if (selectedValues.length > 1) {
        // multicollection
        req = GetRecordsDocument.Factory.parse(this.getClass().getResourceAsStream(
            "xml/getrecords-multi-template.xml"));
        XmlObject[] res = req
            .selectPath("declare namespace ogc='http://www.opengis.net/ogc' .//ogc:Or");
        if (res.length > 0) {
          XmlCursor xc = res[0].newCursor();
          xc.toEndToken();
          for (Object obj : selectedValues) {
            String pid = (String) obj;
            addParentIdXMLBlock(xc, pid);
          }
          xc.dispose();
        }
      } else {
        // single collection
        req = GetRecordsDocument.Factory.parse(this.getClass().getResourceAsStream(
            "xml/getrecords-single-template.xml"));
        XmlObject[] res = req
            .selectPath("declare namespace ogc='http://www.opengis.net/ogc' .//ogc:PropertyIsEqualTo[contains(ogc:PropertyName[1],'parentIdentifier')]");
        if (res.length > 0) {
          XmlCursor xc = res[0].newCursor();
          xc.toChild("http://www.opengis.net/ogc", "Literal");
          xc.setTextValue((String) selectedValues[0]);
          xc.dispose();
        }
      }
      // place a cursor just before the </ogc:And> tag
      XmlObject[] res = req
          .selectPath("declare namespace ogc='http://www.opengis.net/ogc' .//ogc:And");
      XmlCursor xc = res[0].newCursor();
      xc.toEndToken();
      if (chckbxTimeConstraints.isSelected()) {
        Date from = dtpFrom.getDateTime();
        Date to = dtpTo.getDateTime();
        sb = new StringBuilder("Temporal constraints: from ");
        sb.append(dtpFrom).append(" to ").append(dtpTo);
        logger.finer(sb.toString());
        addTemporalConstraintsBlock(xc, (String) timeOpCombo.getSelectedItem(), from, to);
      }
      if (chckbxSpatialConstraints.isSelected()) {
        sb = new StringBuilder("Spatial constraints: ");
        sb.append(spatOpCombo.getSelectedItem()).append(" coords ");
        sb.append(polygonPanel.getPosList());
        logger.finer(sb.toString());
        addSpatialConstraintsBlock(xc, (String) spatOpCombo.getSelectedItem());
      }
      xc.dispose();
    } catch (XmlException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return req;
  }

  private void addSpatialConstraintsBlock(XmlCursor xc, String constraintType) {
    if (constraintType.equalsIgnoreCase(paramsPanels[0])) {
      // polygon
      xc.beginElement("Overlaps", "http://www.opengis.net/ogc");
      xc.toEndToken();
      xc.beginElement("PropertyName", "http://www.opengis.net/ogc");
      xc.insertChars("/rim:ExtrinsicObject/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf\"]/wrs:ValueList/wrs:AnyValue[1]");
      xc.toNextToken();
      xc.beginElement("Polygon", "http://www.opengis.net/gml");
      xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
      xc.toEndToken();
      xc.beginElement("exterior", "http://www.opengis.net/gml");
      xc.toEndToken();
      xc.beginElement("LinearRing", "http://www.opengis.net/gml");
      xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
      xc.toEndToken();
      xc.beginElement("posList", "http://www.opengis.net/gml");
      xc.insertChars(polygonPanel.getPosList());
      xc.toNextToken();
      xc.toNextToken();
      xc.toNextToken();
      xc.toNextToken();
      xc.toNextToken();
    } else if (constraintType.equalsIgnoreCase(paramsPanels[1])) {
      // BBOX
      xc.beginElement("BBOX", "http://www.opengis.net/ogc");
      xc.toEndToken();
      xc.beginElement("PropertyName", "http://www.opengis.net/ogc");
      xc.insertChars("/rim:ExtrinsicObject/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf\"]/wrs:ValueList/wrs:AnyValue[1]");
      xc.toNextToken();
      xc.beginElement("Envelope", "http://www.opengis.net/gml");
      xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
      xc.toEndToken();
      xc.beginElement("lowerCorner", "http://www.opengis.net/gml");
      xc.insertChars(lonLatRangePanel.getLowerCorner());
      xc.toNextToken();
      xc.beginElement("upperCorner", "http://www.opengis.net/gml");
      xc.insertChars(lonLatRangePanel.getUpperCorner());
      xc.toNextToken();
      xc.toNextToken();
      // <ogc:BBOX>
      // <ogc:PropertyName>/rim:ExtrinsicObject/rim:Slot[@name="urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf"]/wrs:ValueList/wrs:AnyValue[1]</ogc:PropertyName>
      // <Envelope xmlns="http://www.opengis.net/gml"
      // srsName="urn:ogc:def:crs:EPSG:6.3:4326">
      // <lowerCorner>39.0 -20.0</lowerCorner>
      // <upperCorner>61.0 16.0</upperCorner>
      // </Envelope>
      // </ogc:BBOX>
    } else if (constraintType.equalsIgnoreCase(paramsPanels[2])) {
      // Circle
      xc.beginElement("Overlaps", "http://www.opengis.net/ogc");
      xc.toEndToken();
      xc.beginElement("PropertyName", "http://www.opengis.net/ogc");
      xc.insertChars("/rim:ExtrinsicObject/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf\"]/wrs:ValueList/wrs:AnyValue[1]");
      xc.toNextToken();
      xc.beginElement("Curve", "http://www.opengis.net/gml");
      xc.toEndToken();
      xc.beginElement("segments", "http://www.opengis.net/gml");
      xc.toEndToken();
      xc.beginElement("CircleByCenterPoint", "http://www.opengis.net/gml");
      xc.insertAttributeWithValue("numArc", "1");
      xc.toEndToken();
      xc.beginElement("pos", "http://www.opengis.net/gml");
      xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
      xc.insertChars(circlePanel.getCenter());
      xc.toNextToken();
      xc.beginElement("radius", "http://www.opengis.net/gml");
      xc.insertAttributeWithValue("uom", "urn:EPSG:uom:9001");
      xc.insertChars(circlePanel.getRadius());
      xc.toNextToken();
      xc.toNextToken();
      xc.toNextToken();
      xc.toNextToken();
      xc.toNextToken();
      // <ogc:Overlaps>
      // <ogc:PropertyName>/rim:ExtrinsicObject/rim:Slot[@name="urn:ogc:def:ebRIM-Slot:OGC-06-131:multiExtentOf"]/wrs:ValueList/wrs:AnyValue[1]</ogc:PropertyName>
      // <gml:Curve>
      // <gml:segments>
      // <gml:CircleByCenterPoint numArc="1">
      // <gml:pos srsName=" urn:ogc:def:crs:EPSG:6.3:4326">42.5463
      // -73.2512</gml:pos>
      // <gml:radius uom="urn:EPSG:uom:9001">850.24</gml:radius>
      // </gml:CircleByCenterPoint>
      // </gml:segments>
      // </gml:Curve>
      // </ogc:Overlaps>
    }
  }

  private void addTemporalConstraintsBlock(XmlCursor xc, String constraintType, Date from, Date to) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    if (constraintType.equalsIgnoreCase("Overlaps")) {
      // TODO implementare
    } else if (constraintType.equalsIgnoreCase("Contained in")) {
      xc.beginElement("PropertyIsGreaterThanOrEqualTo", "http://www.opengis.net/ogc");
      xc.toEndToken();
      xc.beginElement("PropertyName", "http://www.opengis.net/ogc");
      xc.insertChars("/rim:ExtrinsicObject/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:beginPosition\"]/rim:ValueList/rim:Value[1]");
      xc.toNextToken();
      xc.beginElement("Literal", "http://www.opengis.net/ogc");
      xc.insertChars(df.format(from));
      xc.toNextToken();
      xc.toNextToken();
      xc.beginElement("PropertyIsLessThanOrEqualTo", "http://www.opengis.net/ogc");
      xc.toEndToken();
      xc.beginElement("PropertyName", "http://www.opengis.net/ogc");
      xc.insertChars("/rim:ExtrinsicObject/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:endPosition\"]/rim:ValueList/rim:Value[1]");
      xc.toNextToken();
      xc.beginElement("Literal", "http://www.opengis.net/ogc");
      xc.insertChars(df.format(to));
      xc.toNextToken();
      xc.toNextToken();
    } else if (constraintType.equalsIgnoreCase("After")) {
      // TODO implementare
    } else if (constraintType.equalsIgnoreCase("Before")) {
      // TODO implementare
    }
  }

  private void addParentIdXMLBlock(XmlCursor xc, String parentId) {
    xc.beginElement("PropertyIsEqualTo", "http://www.opengis.net/ogc");
    xc.toEndToken();
    xc.beginElement("PropertyName", "http://www.opengis.net/ogc");
    xc.insertChars("/rim:ExtrinsicObject/rim:Slot[@name=\"urn:ogc:def:ebRIM-Slot:OGC-06-131:parentIdentifier\"]/rim:ValueList/rim:Value[1]");
    xc.toNextToken();
    xc.beginElement("Literal", "http://www.opengis.net/ogc");
    xc.insertChars(parentId);
    xc.toNextToken();
    xc.toNextToken();
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          JFrame f = new JFrame("test");
          f.setContentPane(new SearchPanel());
          f.pack();
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}

class CollectionListModel extends AbstractListModel<String> {
  String[] values;

  public CollectionListModel(String[] values) {
    this.values = values;
  }

  public int getSize() {
    return values.length;
  }

  public String getElementAt(int index) {
    return values[index];
  }

}