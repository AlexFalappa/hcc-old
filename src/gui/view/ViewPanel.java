package gui.view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwindx.examples.util.StatusLayer;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewPanel extends JPanel {

  private JCheckBox chckbxStars;
  private JCheckBox chckbxAtmosphere;
  private JCheckBox chckbxBlueMarbleImage;
  private JCheckBox chckbxWorldMap;
  private JCheckBox chckbxCompass;
  private JCheckBox chckbxScale;
  private JCheckBox chckbxBlueMarbleWms;
  private JCheckBox chckbxMsVirtualEarth;
  private JCheckBox chckbxPoliticalBoundaries;
  private JCheckBox chckbxGraticule;
  private JCheckBox chckbxPlaceNames;
  private JCheckBox chckbxFootprints;
  private JCheckBox chckbxAreaOfInterest;
  private JCheckBox chckbxStatus;
  private Component verticalGlue;
  private JPanel pProjection;
  private JLabel lblMode;

  /**
   * Create the panel.
   */
  public ViewPanel() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.rowWeights = new double[] {
    		0.0, 0.0, 0.0, 0.0, 0.0,
    		0.0, 0.0, 0.0, 0.0, 0.0,
    		0.0, 0.0, 0.0, 0.0, 0.0,
    		0.0, 0.0, 0.0, 0.0, 1.0 };
    gridBagLayout.columnWidths = new int[] { 20, 0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 1.0 };
    setLayout(gridBagLayout);

    JLabel lblSearch = new JLabel("Search:");
    GridBagConstraints gbc_lblSearch = new GridBagConstraints();
    gbc_lblSearch.gridwidth = 2;
    gbc_lblSearch.anchor = GridBagConstraints.WEST;
    gbc_lblSearch.insets = new Insets(5, 0, 5, 0);
    gbc_lblSearch.gridx = 0;
    gbc_lblSearch.gridy = 0;
    add(lblSearch, gbc_lblSearch);

    chckbxFootprints = new JCheckBox("Footprints N/A");
    GridBagConstraints gbc_chckbxFootprints = new GridBagConstraints();
    gbc_chckbxFootprints.anchor = GridBagConstraints.WEST;
    gbc_chckbxFootprints.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxFootprints.gridx = 1;
    gbc_chckbxFootprints.gridy = 1;
    add(chckbxFootprints, gbc_chckbxFootprints);

    chckbxAreaOfInterest = new JCheckBox("Area of interest N/A");
    GridBagConstraints gbc_chckbxArea = new GridBagConstraints();
    gbc_chckbxArea.anchor = GridBagConstraints.WEST;
    gbc_chckbxArea.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxArea.gridx = 1;
    gbc_chckbxArea.gridy = 2;
    add(chckbxAreaOfInterest, gbc_chckbxArea);

    JLabel lblCarto = new JLabel("Cartography:");
    GridBagConstraints gbc_lblCarto = new GridBagConstraints();
    gbc_lblCarto.gridwidth = 2;
    gbc_lblCarto.anchor = GridBagConstraints.WEST;
    gbc_lblCarto.insets = new Insets(10, 0, 5, 0);
    gbc_lblCarto.gridx = 0;
    gbc_lblCarto.gridy = 3;
    add(lblCarto, gbc_lblCarto);

    chckbxStars = new JCheckBox("Stars N/A");
    GridBagConstraints gbc_chckbxStars = new GridBagConstraints();
    gbc_chckbxStars.anchor = GridBagConstraints.WEST;
    gbc_chckbxStars.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxStars.gridx = 1;
    gbc_chckbxStars.gridy = 4;
    add(chckbxStars, gbc_chckbxStars);

    chckbxAtmosphere = new JCheckBox("Atmosphere N/A");
    GridBagConstraints gbc_chckbxAtmosphere = new GridBagConstraints();
    gbc_chckbxAtmosphere.anchor = GridBagConstraints.WEST;
    gbc_chckbxAtmosphere.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxAtmosphere.gridx = 1;
    gbc_chckbxAtmosphere.gridy = 5;
    add(chckbxAtmosphere, gbc_chckbxAtmosphere);

    chckbxBlueMarbleImage = new JCheckBox("Blue Marble Image N/A");
    GridBagConstraints gbc_chckbxBlueMarbleImage = new GridBagConstraints();
    gbc_chckbxBlueMarbleImage.anchor = GridBagConstraints.WEST;
    gbc_chckbxBlueMarbleImage.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxBlueMarbleImage.gridx = 1;
    gbc_chckbxBlueMarbleImage.gridy = 6;
    add(chckbxBlueMarbleImage, gbc_chckbxBlueMarbleImage);

    chckbxBlueMarbleWms = new JCheckBox("Blue Marble 2004 (WMS) N/A");
    GridBagConstraints gbc_chckbxBlueMarbleWms = new GridBagConstraints();
    gbc_chckbxBlueMarbleWms.anchor = GridBagConstraints.WEST;
    gbc_chckbxBlueMarbleWms.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxBlueMarbleWms.gridx = 1;
    gbc_chckbxBlueMarbleWms.gridy = 7;
    add(chckbxBlueMarbleWms, gbc_chckbxBlueMarbleWms);

    chckbxMsVirtualEarth = new JCheckBox("MS Virtual Earth N/A");
    GridBagConstraints gbc_chckbxMsVirtualEarth = new GridBagConstraints();
    gbc_chckbxMsVirtualEarth.anchor = GridBagConstraints.WEST;
    gbc_chckbxMsVirtualEarth.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxMsVirtualEarth.gridx = 1;
    gbc_chckbxMsVirtualEarth.gridy = 8;
    add(chckbxMsVirtualEarth, gbc_chckbxMsVirtualEarth);

    chckbxPoliticalBoundaries = new JCheckBox("Political Boundaries N/A");
    GridBagConstraints gbc_chckbxPoliticalBoundaries = new GridBagConstraints();
    gbc_chckbxPoliticalBoundaries.anchor = GridBagConstraints.WEST;
    gbc_chckbxPoliticalBoundaries.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxPoliticalBoundaries.gridx = 1;
    gbc_chckbxPoliticalBoundaries.gridy = 9;
    add(chckbxPoliticalBoundaries, gbc_chckbxPoliticalBoundaries);

    chckbxGraticule = new JCheckBox("Graticule N/A");
    GridBagConstraints gbc_chckbxGraticule = new GridBagConstraints();
    gbc_chckbxGraticule.anchor = GridBagConstraints.WEST;
    gbc_chckbxGraticule.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxGraticule.gridx = 1;
    gbc_chckbxGraticule.gridy = 10;
    add(chckbxGraticule, gbc_chckbxGraticule);

    chckbxPlaceNames = new JCheckBox("Place Names N/A");
    GridBagConstraints gbc_chckbxPlaceNames = new GridBagConstraints();
    gbc_chckbxPlaceNames.anchor = GridBagConstraints.WEST;
    gbc_chckbxPlaceNames.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxPlaceNames.gridx = 1;
    gbc_chckbxPlaceNames.gridy = 11;
    add(chckbxPlaceNames, gbc_chckbxPlaceNames);

    JLabel lblVisual = new JLabel("Visual aids:");
    GridBagConstraints gbc_lblVisual = new GridBagConstraints();
    gbc_lblVisual.gridwidth = 2;
    gbc_lblVisual.anchor = GridBagConstraints.WEST;
    gbc_lblVisual.insets = new Insets(10, 0, 5, 0);
    gbc_lblVisual.gridx = 0;
    gbc_lblVisual.gridy = 12;
    add(lblVisual, gbc_lblVisual);

    chckbxWorldMap = new JCheckBox("World Map N/A");
    GridBagConstraints gbc_chckbxWorldMap = new GridBagConstraints();
    gbc_chckbxWorldMap.anchor = GridBagConstraints.WEST;
    gbc_chckbxWorldMap.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxWorldMap.gridx = 1;
    gbc_chckbxWorldMap.gridy = 13;
    add(chckbxWorldMap, gbc_chckbxWorldMap);

    chckbxCompass = new JCheckBox("Compass N/A");
    GridBagConstraints gbc_chckbxCompass = new GridBagConstraints();
    gbc_chckbxCompass.anchor = GridBagConstraints.WEST;
    gbc_chckbxCompass.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxCompass.gridx = 1;
    gbc_chckbxCompass.gridy = 14;
    add(chckbxCompass, gbc_chckbxCompass);

    chckbxScale = new JCheckBox("Scale N/A");
    GridBagConstraints gbc_chckbxScale = new GridBagConstraints();
    gbc_chckbxScale.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxScale.anchor = GridBagConstraints.WEST;
    gbc_chckbxScale.gridx = 1;
    gbc_chckbxScale.gridy = 15;
    add(chckbxScale, gbc_chckbxScale);

    chckbxStatus = new JCheckBox("Status N/A");
    GridBagConstraints gbc_chckbxStatus = new GridBagConstraints();
    gbc_chckbxStatus.insets = new Insets(0, 0, 5, 0);
    gbc_chckbxStatus.anchor = GridBagConstraints.WEST;
    gbc_chckbxStatus.gridx = 1;
    gbc_chckbxStatus.gridy = 16;
    add(chckbxStatus, gbc_chckbxStatus);

    lblMode = new JLabel("Mode:");
    GridBagConstraints gbc_lblMode = new GridBagConstraints();
    gbc_lblMode.insets = new Insets(0, 0, 5, 5);
    gbc_lblMode.gridx = 0;
    gbc_lblMode.gridy = 17;
    add(lblMode, gbc_lblMode);

    verticalGlue = Box.createVerticalGlue();
    GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
    gbc_verticalGlue.gridx = 1;
    gbc_verticalGlue.gridy = 19;
    add(verticalGlue, gbc_verticalGlue);
  }

  public void linkTo(WorldWindow wwd) {
    LayerList layers = wwd.getModel().getLayers();
    Layer layer = layers.getLayerByName("Footprints");
    link(chckbxFootprints, wwd, layer);
    layer = layers.getLayerByName("Area of interest");
    link(chckbxAreaOfInterest, wwd, layer);
    layer = layers.getLayerByName("Scale bar");
    link(chckbxScale, wwd, layer);
    layer = layers.getLayerByName("Compass");
    link(chckbxCompass, wwd, layer);
    layer = layers.getLayerByName("World Map");
    link(chckbxWorldMap, wwd, layer);
    layer = layers.getLayerByName("Lat-Lon Graticule");
    link(chckbxGraticule, wwd, layer);
    layer = layers.getLayerByName("Place Names");
    link(chckbxPlaceNames, wwd, layer);
    layer = layers.getLayerByName("Political Boundaries");
    link(chckbxPoliticalBoundaries, wwd, layer);
    layer = layers.getLayerByName("Stars");
    link(chckbxStars, wwd, layer);
    layer = layers.getLayerByName("Atmosphere");
    link(chckbxAtmosphere, wwd, layer);
    layer = layers.getLayerByName("NASA Blue Marble Image");
    link(chckbxBlueMarbleImage, wwd, layer);
    layer = layers.getLayerByName("Blue Marble (WMS) 2004");
    link(chckbxBlueMarbleWms, wwd, layer);
    layer = layers.getLayerByName("MS Virtual Earth Aerial");
    link(chckbxMsVirtualEarth, wwd, layer);

    StatusLayer slayer = new StatusLayer();
    slayer.setEventSource(wwd);
    wwd.getModel().getLayers().add(slayer);
    chckbxStatus.setSelected(slayer.isEnabled());
    chckbxStatus.setAction(new ToggleAction(slayer, wwd));

    pProjection = new WorldModePanel(wwd);
    GridBagConstraints gbc_pProjection = new GridBagConstraints();
    gbc_pProjection.gridwidth = 2;
    gbc_pProjection.insets = new Insets(0, 0, 5, 0);
    gbc_pProjection.fill = GridBagConstraints.NONE;
    gbc_pProjection.gridx = 0;
    gbc_pProjection.gridy = 18;
    add(pProjection, gbc_pProjection);
  }

  private void link(JCheckBox jcb, WorldWindow wwd, Layer layer) {
    if (layer != null) {
      jcb.setSelected(layer.isEnabled());
      jcb.setAction(new ToggleAction(layer, wwd));
    } else {
      jcb.setEnabled(false);
    }
  }

  protected static class ToggleAction extends AbstractAction {
    private WorldWindow wwd;
    private Layer layer;

    public ToggleAction(Layer layer, WorldWindow wwd) {
      super(layer.getName());
      this.putValue(SHORT_DESCRIPTION, "Toggle visibility of " + layer.getName());
      this.wwd = wwd;
      this.layer = layer;
    }

    public void actionPerformed(ActionEvent actionEvent) {
      boolean toggle = ((JCheckBox) actionEvent.getSource()).isSelected();
      this.layer.setEnabled(toggle);
      wwd.redraw();
    }
  }
}
