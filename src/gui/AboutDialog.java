package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AboutDialog extends JDialog {

  private final JPanel contentPanel = new JPanel();

  /**
   * Create the dialog.
   */
  public AboutDialog() {
    setModalityType(ModalityType.APPLICATION_MODAL);
    setResizable(false);
    setModal(true);
    setTitle("About");
    setSize(300, 170);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    JLabel lblTheApplication = new JLabel("HMA Catalogue Client");
    lblTheApplication.setFont(new Font("Dialog", Font.BOLD, 18));
    lblTheApplication.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPanel.add(lblTheApplication);
    JLabel lblVersion = new JLabel("version 0.4.5");//TODO make a global constant
    lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPanel.add(lblVersion);
    Component verticalStrut = Box.createVerticalStrut(20);
    contentPanel.add(verticalStrut);
    JLabel lblByAlessandroFalappa = new JLabel("By Alessandro Falappa");
    lblByAlessandroFalappa.setFont(lblByAlessandroFalappa.getFont().deriveFont(
        lblByAlessandroFalappa.getFont().getStyle() | Font.ITALIC));
    lblByAlessandroFalappa.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPanel.add(lblByAlessandroFalappa);
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
    getContentPane().add(buttonPane, BorderLayout.SOUTH);
    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutDialog.this.setVisible(false);
      }
    });
    okButton.setActionCommand("OK");
    buttonPane.add(okButton);
    getRootPane().setDefaultButton(okButton);
  }

}
