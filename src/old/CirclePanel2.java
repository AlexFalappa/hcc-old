package gui.panels.geo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CirclePanel2 extends JPanel {

    private JSpinner spCenterLon;
    private JSpinner spCenterLat;
    private JSpinner spRadius;

    /**
     * Create the panel.
     */
    public CirclePanel2() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        lblCenterLon = new JLabel("Center lon:");
        GridBagConstraints gbc_lblCenterLon = new GridBagConstraints();
        gbc_lblCenterLon.anchor = GridBagConstraints.EAST;
        gbc_lblCenterLon.insets = new Insets(0, 0, 5, 5);
        gbc_lblCenterLon.gridx = 0;
        gbc_lblCenterLon.gridy = 0;
        add(lblCenterLon, gbc_lblCenterLon);

        spCenterLon = new JSpinner();
        spCenterLon.setModel(new SpinnerNumberModel(0.0, -180.0, 180.0, 5.0));
        lblCenterLon.setLabelFor(spCenterLon);
        GridBagConstraints gbc_txCenterLon = new GridBagConstraints();
        gbc_txCenterLon.insets = new Insets(0, 0, 5, 0);
        gbc_txCenterLon.fill = GridBagConstraints.HORIZONTAL;
        gbc_txCenterLon.gridx = 1;
        gbc_txCenterLon.gridy = 0;
        add(spCenterLon, gbc_txCenterLon);

        lblCenterLat = new JLabel("Center lat:");
        GridBagConstraints gbc_lblCenterLat = new GridBagConstraints();
        gbc_lblCenterLat.anchor = GridBagConstraints.EAST;
        gbc_lblCenterLat.insets = new Insets(0, 0, 5, 5);
        gbc_lblCenterLat.gridx = 0;
        gbc_lblCenterLat.gridy = 1;
        add(lblCenterLat, gbc_lblCenterLat);

        spCenterLat = new JSpinner();
        spCenterLat.setModel(new SpinnerNumberModel(0.0, -90.0, 90.0, 5.0));
        lblCenterLat.setLabelFor(spCenterLat);
        GridBagConstraints gbc_txCenterLat = new GridBagConstraints();
        gbc_txCenterLat.insets = new Insets(0, 0, 5, 0);
        gbc_txCenterLat.fill = GridBagConstraints.HORIZONTAL;
        gbc_txCenterLat.gridx = 1;
        gbc_txCenterLat.gridy = 1;
        add(spCenterLat, gbc_txCenterLat);

        lblRadius = new JLabel("Radius:");
        GridBagConstraints gbc_lblRadius = new GridBagConstraints();
        gbc_lblRadius.anchor = GridBagConstraints.EAST;
        gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
        gbc_lblRadius.gridx = 0;
        gbc_lblRadius.gridy = 2;
        add(lblRadius, gbc_lblRadius);

        spRadius = new JSpinner();
        spRadius.setModel(new SpinnerNumberModel(new Double(1000), new Double(0), null, new Double(100)));
        lblRadius.setLabelFor(spRadius);
        GridBagConstraints gbc_txRadius = new GridBagConstraints();
        gbc_txRadius.insets = new Insets(0, 0, 5, 0);
        gbc_txRadius.fill = GridBagConstraints.HORIZONTAL;
        gbc_txRadius.gridx = 1;
        gbc_txRadius.gridy = 2;
        add(spRadius, gbc_txRadius);
        JButton btnGraphicalSelection = new JButton("Graphical selection");
        btnGraphicalSelection.setEnabled(false);
        btnGraphicalSelection.setToolTipText("To implement");
        GridBagConstraints gbc_btnGraphicalSelection = new GridBagConstraints();
        gbc_btnGraphicalSelection.gridwidth = 2;
        gbc_btnGraphicalSelection.insets = new Insets(20, 0, 0, 0);
        gbc_btnGraphicalSelection.gridx = 0;
        gbc_btnGraphicalSelection.gridy = 3;
        add(btnGraphicalSelection, gbc_btnGraphicalSelection);
    }
    private JLabel lblRadius;
    private JLabel lblCenterLat;
    private JLabel lblCenterLon;

    @Override
    public void setEnabled(boolean enabled) {
        lblCenterLat.setEnabled(enabled);
        spCenterLat.setEnabled(enabled);
        lblCenterLon.setEnabled(enabled);
        spCenterLon.setEnabled(enabled);
        lblRadius.setEnabled(enabled);
        spRadius.setEnabled(enabled);
    }

    public String getCenter() {
        return spCenterLat.getValue().toString().concat(" ").concat(spCenterLon.getValue().toString());
    }

    public String getRadius() {
        return spRadius.getValue().toString();
    }
}
