/*
Copyright (C) 2001, 2008 United States Government
as represented by the Administrator of the
National Aeronautics and Space Administration.
All Rights Reserved.
 */
package gui.view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.globes.Earth;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.globes.FlatGlobe;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.SkyColorLayer;
import gov.nasa.worldwind.layers.SkyGradientLayer;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.FlatOrbitView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Panel to control a flat or round world projection. The panel includes a radio
 * button to switch between flat and round globes, and a list box of map
 * projections for the flat globe. The panel is attached to a WorldWindow, and
 * changes the WorldWindow to match the users globe selection.
 * 
 * @author Patrick Murris
 * @version $Id: WorldModePanel.java 1 2011-07-16 23:22:47Z dcollins $
 */

public class WorldModePanel extends JPanel {
  private WorldWindow wwd;
  private Globe roundGlobe;
  private FlatGlobe flatGlobe;
  private JComboBox<String> projectionCombo;

  public WorldModePanel(WorldWindow wwd) {
    super();
    this.wwd = wwd;
    if (isFlatGlobe()) {
      this.flatGlobe = (FlatGlobe) wwd.getModel().getGlobe();
      this.roundGlobe = new Earth();
    } else {
      this.flatGlobe = new EarthFlat();
      this.roundGlobe = wwd.getModel().getGlobe();
    }
    ButtonGroup group = new ButtonGroup();
    GridBagLayout gridBagLayout = new GridBagLayout();
    setLayout(gridBagLayout);

    JRadioButton roundRadioButton = new JRadioButton("Round");
    roundRadioButton.setSelected(!isFlatGlobe());
    roundRadioButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        projectionCombo.setEnabled(false);
        enableFlatGlobe(false);
      }
    });
    GridBagConstraints gbc_roundRadioButton = new GridBagConstraints();
    gbc_roundRadioButton.gridwidth = 2;
    gbc_roundRadioButton.anchor = GridBagConstraints.NORTHWEST;
    gbc_roundRadioButton.insets = new Insets(0, 0, 5, 0);
    gbc_roundRadioButton.gridx = 0;
    gbc_roundRadioButton.gridy = 0;
    add(roundRadioButton, gbc_roundRadioButton);
    group.add(roundRadioButton);

    JRadioButton flatRadioButton = new JRadioButton("Flat");
    flatRadioButton.setSelected(isFlatGlobe());
    flatRadioButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        projectionCombo.setEnabled(true);
        enableFlatGlobe(true);
      }
    });
    GridBagConstraints gbc_flatRadioButton = new GridBagConstraints();
    gbc_flatRadioButton.anchor = GridBagConstraints.NORTHWEST;
    gbc_flatRadioButton.insets = new Insets(0, 0, 0, 5);
    gbc_flatRadioButton.gridx = 0;
    gbc_flatRadioButton.gridy = 1;
    add(flatRadioButton, gbc_flatRadioButton);
    group.add(flatRadioButton);

    projectionCombo = new JComboBox<String>(new String[] { "Mercator", "Lat-Lon", "Modified Sin.", "Sinusoidal" });
    projectionCombo.setEnabled(isFlatGlobe());
    projectionCombo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        updateProjection();
      }
    });
    GridBagConstraints gbc_projectionCombo = new GridBagConstraints();
    gbc_projectionCombo.anchor = GridBagConstraints.NORTHWEST;
    gbc_projectionCombo.gridx = 1;
    gbc_projectionCombo.gridy = 1;
    add(this.projectionCombo, gbc_projectionCombo);
  }

  // Update flat globe projection
  private void updateProjection() {
    if (!isFlatGlobe())
      return;
    // Update flat globe projection
    this.flatGlobe.setProjection(this.getProjection());
    this.wwd.redraw();
  }

  private String getProjection() {
    String item = (String) projectionCombo.getSelectedItem();
    if (item.equals("Mercator"))
      return FlatGlobe.PROJECTION_MERCATOR;
    else if (item.equals("Sinusoidal"))
      return FlatGlobe.PROJECTION_SINUSOIDAL;
    else if (item.equals("Modified Sin."))
      return FlatGlobe.PROJECTION_MODIFIED_SINUSOIDAL;
    // Default to lat-lon
    return FlatGlobe.PROJECTION_LAT_LON;
  }

  public boolean isFlatGlobe() {
    return wwd.getModel().getGlobe() instanceof FlatGlobe;
  }

  public void enableFlatGlobe(boolean flat) {
    if (isFlatGlobe() == flat)
      return;

    if (!flat) {
      // Switch to round globe
      wwd.getModel().setGlobe(roundGlobe);
      // Switch to orbit view and update with current position
      FlatOrbitView flatOrbitView = (FlatOrbitView) wwd.getView();
      BasicOrbitView orbitView = new BasicOrbitView();
      orbitView.setCenterPosition(flatOrbitView.getCenterPosition());
      orbitView.setZoom(flatOrbitView.getZoom());
      orbitView.setHeading(flatOrbitView.getHeading());
      orbitView.setPitch(flatOrbitView.getPitch());
      wwd.setView(orbitView);
      // Change sky layer
      LayerList layers = wwd.getModel().getLayers();
      for (int i = 0; i < layers.size(); i++) {
        if (layers.get(i) instanceof SkyColorLayer)
          layers.set(i, new SkyGradientLayer());
      }
    } else {
      // Switch to flat globe
      wwd.getModel().setGlobe(flatGlobe);
      flatGlobe.setProjection(this.getProjection());
      // Switch to flat view and update with current position
      BasicOrbitView orbitView = (BasicOrbitView) wwd.getView();
      FlatOrbitView flatOrbitView = new FlatOrbitView();
      flatOrbitView.setCenterPosition(orbitView.getCenterPosition());
      flatOrbitView.setZoom(orbitView.getZoom());
      flatOrbitView.setHeading(orbitView.getHeading());
      flatOrbitView.setPitch(orbitView.getPitch());
      wwd.setView(flatOrbitView);
      // Change sky layer
      LayerList layers = wwd.getModel().getLayers();
      for (int i = 0; i < layers.size(); i++) {
        if (layers.get(i) instanceof SkyGradientLayer)
          layers.set(i, new SkyColorLayer());
      }
    }
    wwd.redraw();
  }

}
