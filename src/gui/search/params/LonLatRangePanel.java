package gui.search.params;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwindx.examples.util.SectorSelector;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import main.App;

public class LonLatRangePanel extends JPanel {
  private JSpinner spMinLon;
  private JSpinner spMaxLon;
  private JSpinner spMinLat;
  private JSpinner spMaxLat;
  private JButton btnGraphicalSelection;
  private boolean selecting = false;
  private SectorSelector selector;

  /**
   * Create the panel.
   */
  public LonLatRangePanel() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
    gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
    setLayout(gridBagLayout);

    JLabel lblMinLon = new JLabel("Min lon:");
    GridBagConstraints gbc_lblMinLon = new GridBagConstraints();
    gbc_lblMinLon.anchor = GridBagConstraints.EAST;
    gbc_lblMinLon.insets = new Insets(0, 0, 5, 5);
    gbc_lblMinLon.gridx = 0;
    gbc_lblMinLon.gridy = 0;
    add(lblMinLon, gbc_lblMinLon);

    spMinLon = new JSpinner();
    spMinLon.setModel(new SpinnerNumberModel(0.0, -180.0, 180.0, 5.0));
    GridBagConstraints gbc_txMinLon = new GridBagConstraints();
    gbc_txMinLon.insets = new Insets(0, 0, 5, 0);
    gbc_txMinLon.fill = GridBagConstraints.HORIZONTAL;
    gbc_txMinLon.gridx = 1;
    gbc_txMinLon.gridy = 0;
    add(spMinLon, gbc_txMinLon);

    JLabel lblMaxLon = new JLabel("Max lon:");
    GridBagConstraints gbc_lblMaxLon = new GridBagConstraints();
    gbc_lblMaxLon.anchor = GridBagConstraints.EAST;
    gbc_lblMaxLon.insets = new Insets(0, 0, 5, 5);
    gbc_lblMaxLon.gridx = 0;
    gbc_lblMaxLon.gridy = 1;
    add(lblMaxLon, gbc_lblMaxLon);

    spMaxLon = new JSpinner();
    spMaxLon.setModel(new SpinnerNumberModel(0.0, -180.0, 180.0, 5.0));
    GridBagConstraints gbc_txMaxLon = new GridBagConstraints();
    gbc_txMaxLon.insets = new Insets(0, 0, 5, 0);
    gbc_txMaxLon.fill = GridBagConstraints.HORIZONTAL;
    gbc_txMaxLon.gridx = 1;
    gbc_txMaxLon.gridy = 1;
    add(spMaxLon, gbc_txMaxLon);

    JLabel lblMinLat = new JLabel("Min lat:");
    GridBagConstraints gbc_lblMinLat = new GridBagConstraints();
    gbc_lblMinLat.anchor = GridBagConstraints.EAST;
    gbc_lblMinLat.insets = new Insets(0, 0, 5, 5);
    gbc_lblMinLat.gridx = 0;
    gbc_lblMinLat.gridy = 2;
    add(lblMinLat, gbc_lblMinLat);

    spMinLat = new JSpinner();
    spMinLat.setModel(new SpinnerNumberModel(0.0, -90.0, 90.0, 5.0));
    GridBagConstraints gbc_txMinLat = new GridBagConstraints();
    gbc_txMinLat.insets = new Insets(0, 0, 5, 0);
    gbc_txMinLat.fill = GridBagConstraints.HORIZONTAL;
    gbc_txMinLat.gridx = 1;
    gbc_txMinLat.gridy = 2;
    add(spMinLat, gbc_txMinLat);

    JLabel lblMaxLat = new JLabel("Max lat:");
    GridBagConstraints gbc_lblMaxLat = new GridBagConstraints();
    gbc_lblMaxLat.anchor = GridBagConstraints.EAST;
    gbc_lblMaxLat.insets = new Insets(0, 0, 5, 5);
    gbc_lblMaxLat.gridx = 0;
    gbc_lblMaxLat.gridy = 3;
    add(lblMaxLat, gbc_lblMaxLat);

    spMaxLat = new JSpinner();
    spMaxLat.setModel(new SpinnerNumberModel(0.0, -90.0, 90.0, 5.0));
    GridBagConstraints gbc_txMaxLat = new GridBagConstraints();
    gbc_txMaxLat.insets = new Insets(0, 0, 5, 0);
    gbc_txMaxLat.fill = GridBagConstraints.HORIZONTAL;
    gbc_txMaxLat.gridx = 1;
    gbc_txMaxLat.gridy = 3;
    add(spMaxLat, gbc_txMaxLat);

    btnGraphicalSelection = new JButton("Graphical selection");
    btnGraphicalSelection.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (selector == null)
          return;
        if (selecting) {
          btnGraphicalSelection.setText("Graphical selection");
          setSpinnersEnabled(true);
          Sector s = selector.getSector();
          updateRanges(s);
          App.frame.aois.setSurfSect(s);
          selector.disable();
          selecting = false;
        } else {
          btnGraphicalSelection.setText("Accept");
          setSpinnersEnabled(false);
          selector.enable();
          selecting = true;
        }
      }
    });
    GridBagConstraints gbc_btnStartGraphicalSelection = new GridBagConstraints();
    gbc_btnStartGraphicalSelection.insets = new Insets(20, 0, 0, 0);
    gbc_btnStartGraphicalSelection.gridwidth = 2;
    gbc_btnStartGraphicalSelection.gridx = 0;
    gbc_btnStartGraphicalSelection.gridy = 4;
    add(btnGraphicalSelection, gbc_btnStartGraphicalSelection);
  }

  @Override
  public void setEnabled(boolean enabled) {
    setSpinnersEnabled(enabled);
    btnGraphicalSelection.setEnabled(enabled);
  }

  private void setSpinnersEnabled(boolean enabled) {
    spMaxLat.setEnabled(enabled);
    spMaxLon.setEnabled(enabled);
    spMinLat.setEnabled(enabled);
    spMinLon.setEnabled(enabled);
  }

  private void updateRanges(Sector s) {
    if (s != null) {
      spMinLon.setValue(s.getMinLongitude().degrees);
      spMaxLon.setValue(s.getMaxLongitude().degrees);
      spMinLat.setValue(s.getMinLatitude().degrees);
      spMaxLat.setValue(s.getMaxLatitude().degrees);
    }
  }

  public void linkTo(WorldWindow wwd) {
    selector = new SectorSelector(wwd);
    selector.addPropertyChangeListener(SectorSelector.SECTOR_PROPERTY,
        new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent evt) {
            Sector sec = (Sector) evt.getNewValue();
            EventQueue.invokeLater(new RangeUpdater(sec));
          }
        });
  }

  class RangeUpdater implements Runnable {
    private Sector sec;

    public RangeUpdater(Sector sec) {
      this.sec = sec;
    }

    @Override
    public void run() {
      updateRanges(sec);
    }
  }

  public String getLowerCorner() {
    return spMinLat.getValue().toString().concat(" ").concat(spMinLon.getValue().toString());
  }

  public String getUpperCorner() {
    return spMaxLat.getValue().toString().concat(" ").concat(spMaxLon.getValue().toString());
  }
}
