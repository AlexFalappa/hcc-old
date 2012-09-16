package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DetailDialog extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private JTable table;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    try {
      DetailDialog dialog = new DetailDialog();
      dialog.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Create the dialog.
   */
  public DetailDialog() {
    setSize(600, 230);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    GridBagLayout gbl_contentPanel = new GridBagLayout();
    gbl_contentPanel.columnWidths = new int[] { 0, 0 };
    gbl_contentPanel.rowHeights = new int[] { 0, 0, 0 };
    gbl_contentPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
    gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
    contentPanel.setLayout(gbl_contentPanel);
    {
      JLabel lblProductDetails = new JLabel("Product details:");
      GridBagConstraints gbc_lblProductDetails = new GridBagConstraints();
      gbc_lblProductDetails.anchor = GridBagConstraints.WEST;
      gbc_lblProductDetails.insets = new Insets(0, 0, 5, 0);
      gbc_lblProductDetails.gridx = 0;
      gbc_lblProductDetails.gridy = 0;
      contentPanel.add(lblProductDetails, gbc_lblProductDetails);
    }
    {
      JScrollPane scrollPane = new JScrollPane();
      GridBagConstraints gbc_scrollPane = new GridBagConstraints();
      gbc_scrollPane.fill = GridBagConstraints.BOTH;
      gbc_scrollPane.gridx = 0;
      gbc_scrollPane.gridy = 1;
      contentPanel.add(scrollPane, gbc_scrollPane);
      {
        table = new JTable();
        table.setShowVerticalLines(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(new DefaultTableModel(new Object[][] {
            { "12312312@asdfasd", "2012/01/01 13:23:12", "2012/01/01 16:23:12", "Satellite", "OPTO" },
            { "42465786@bcvbcbn", "...", "...", "Satellite2", null }, },
            new String[] { "Id", "Begin", "End", "Satellite", "Sensor" }) {
          Class[] columnTypes = new Class[] { String.class, Object.class, Object.class, String.class, String.class };

          public Class getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
          }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(140);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(143);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(2).setPreferredWidth(141);
        scrollPane.setViewportView(table);
      }
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            setVisible(false);
          }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
    }
  }

}
