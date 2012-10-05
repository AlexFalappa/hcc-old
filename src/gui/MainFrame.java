package gui;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.RenderingExceptionListener;
import gov.nasa.worldwind.exception.WWAbsentRequirementException;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;
import gov.nasa.worldwindx.examples.util.ToolTipController;
import gui.search.SearchPanel;
import gui.view.ViewPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import main.App;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;
import org.apache.axis2.AxisFault;
import wwind.AOILayer;
import wwind.FootprintsLayer;

public class MainFrame extends JFrame {
  private CatalogueStub catServiceStub = null;
  private JPanel contentPane;
  private SearchPanel searchPanel;
  private WorldWindowGLCanvas wwCanvas;
  private JLabel lStatus;
  private JComboBox<String> catalogueCombo;
  private JTabbedPane tabbedPane;
  private JScrollPane scroller;

  public final static Logger logger = Logger.getLogger("hcc.gui");
  public FootprintsLayer footprints;
  public AOILayer aois;

  /**
   * Create the frame.
   */
  public MainFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle(App.APP_FRAME_TITLE);
    setGlassPane(new BusyPane());

    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 5));
    setContentPane(contentPane);

    JPanel globePanel = new JPanel(new BorderLayout());
    globePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    wwCanvas = new WorldWindowGLCanvas();
    wwCanvas.setPreferredSize(new java.awt.Dimension(800, 400));
    setupWorldWind();
    globePanel.add(wwCanvas, java.awt.BorderLayout.CENTER);

    lStatus = new JLabel("Connect to a catalogue service...");
    lStatus.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Button.darkShadow")),
        BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Button.highlight"))));
    contentPane.add(lStatus, java.awt.BorderLayout.SOUTH);

    Box horizontalBox = Box.createHorizontalBox();
    contentPane.add(horizontalBox, BorderLayout.NORTH);

    JLabel lblCatalogue = new JLabel("Catalogue:");
    horizontalBox.add(lblCatalogue);

    Component horizontalStrut_1 = Box.createHorizontalStrut(5);
    horizontalBox.add(horizontalStrut_1);

    Preferences prefs = Preferences.userRoot().node(App.PREFS_ROOT);
    String[] services = prefs.get(App.PREFS_KEY_SERVICES,
        "http://<host>:<port>/path/to/service-endpoint").split(",");
    catalogueCombo = new JComboBox<String>(services);
    catalogueCombo.setMaximumRowCount(4);
    catalogueCombo.setEditable(true);
    catalogueCombo.setLightWeightPopupEnabled(false);
    horizontalBox.add(catalogueCombo);

    Component horizontalStrut = Box.createHorizontalStrut(5);
    horizontalBox.add(horizontalStrut);

    JButton btnConnect = new JButton("Connect...");
    btnConnect.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CapabilitiesDialog d = new CapabilitiesDialog();
        d.setLocationRelativeTo(MainFrame.this);
        d.connect(searchPanel);
      }
    });
    horizontalBox.add(btnConnect);

    Component horizontalGlue = Box.createHorizontalGlue();
    horizontalBox.add(horizontalGlue);

    JButton bInfo = new JButton(new ImageIcon(this.getClass().getResource(
        "res/icons/information.png")));
    bInfo.setMargin(new Insets(1, 1, 1, 1));
    bInfo.setToolTipText("Information");
    bInfo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutDialog dlg = new AboutDialog();
        dlg.setLocationRelativeTo(MainFrame.this);
        dlg.setVisible(true);
      }
    });
    horizontalBox.add(bInfo);

    tabbedPane = new JTabbedPane(JTabbedPane.TOP);

    searchPanel = new SearchPanel();
    searchPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    searchPanel.linkTo(wwCanvas, aois);

    scroller = new JScrollPane(searchPanel);
    scroller.setBorder(null);
    tabbedPane.addTab("Search", null, scroller, null);

    ViewPanel layerPanel = new ViewPanel();
    layerPanel.linkTo(wwCanvas);
    tabbedPane.addTab("View", null, layerPanel, null);

    JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, globePanel);
    splitter.setDividerSize(6);
    splitter.setOneTouchExpandable(false);
    splitter.setUI(new BasicSplitPaneUI() {
      public BasicSplitPaneDivider createDefaultDivider() {
        return new BasicSplitPaneDivider(this) {
          public void setBorder(Border b) {
          }
        };
      }
    });
    splitter.setBorder(null);
    contentPane.add(splitter, BorderLayout.CENTER);
  }

  private void setupWorldWind() {
    BasicModel model = new BasicModel();
    wwCanvas.setModel(model);
    ToolTipController ttController = new ToolTipController(wwCanvas);
    // Register a rendering exception listener that's notified when
    // exceptions occur during rendering.
    wwCanvas.addRenderingExceptionListener(new RenderingExceptionListener() {
      public void exceptionThrown(Throwable t) {
        if (t instanceof WWAbsentRequirementException) {
          StringBuilder message = new StringBuilder(
              "Computer does not meet minimum graphics requirements.\n");
          message.append("Please install up-to-date graphics driver and try again.\n");
          message.append("Reason: ").append(t.getMessage());
          message.append("\nThis program will end when you press OK.");
          JOptionPane.showMessageDialog(MainFrame.this, message, "Unable to Start Program",
              JOptionPane.ERROR_MESSAGE);
          System.exit(-1);
        } else {
          logger.warning("WorldWind library rendering problem!");
          logger.throwing(MainFrame.class.getName(), "constructor", t);
        }
      }
    });
    // get position of place names layer
    LayerList layers = model.getLayers();
    int position = 0;
    for (Layer l : layers) {
      if (l instanceof PlaceNameLayer)
        position = layers.indexOf(l);
    }
    // create footprints and AOI layers and add them before the place names
    // layer
    footprints = new FootprintsLayer();
    aois = new AOILayer();
    layers.add(position, aois);
    layers.add(position, footprints);
  }

  public void redrawGlobe() {
    wwCanvas.redraw();
  }

  public CatalogueStub getWebServStub() throws AxisFault {
    if (catServiceStub == null)
      catServiceStub = new CatalogueStub(catalogueCombo.getSelectedItem().toString());
    return catServiceStub;
  }

  public String getWebServEdp() {
    return catalogueCombo.getSelectedItem().toString();
  }

  public void setStatus(String message) {
    lStatus.setText(message);
  }

  public void setGlobalBusyCursor(boolean enabled) {
    getGlassPane().setVisible(enabled);
  }

  class BusyPane extends JComponent implements KeyListener {
    public BusyPane() {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      // Disable Mouse, Key and Focus events
      addMouseListener(new MouseAdapter() {
      });
      addMouseMotionListener(new MouseMotionAdapter() {
      });
      addKeyListener(this);
      setFocusTraversalKeysEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
      g.setColor(new Color(0, 0, 0, 166));
      g.fillRect(0, 0, getSize().width, getSize().height);
    }

    @Override
    public void keyTyped(KeyEvent e) {
      e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
      e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {
      e.consume();
    }
  }
}
