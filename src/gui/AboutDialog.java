package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import main.App;

public class AboutDialog extends JDialog {

  /**
   * Create the dialog.
   */
  public AboutDialog() {
    setModalityType(ModalityType.APPLICATION_MODAL);
    setResizable(false);
    setModal(true);
    setTitle("About");

    JPanel pMessages = new JPanel();
    pMessages.setBorder(new EmptyBorder(6, 0, 6, 6));
    pMessages.setLayout(new BoxLayout(pMessages, BoxLayout.Y_AXIS));

    JLabel lblAppName = new JLabel("HMA Catalogue Client");
    lblAppName.setFont(new Font("Dialog", Font.BOLD, 18));
    pMessages.add(lblAppName);

    JLabel lblVersion = new JLabel("version " + App.VERSION);
    pMessages.add(lblVersion);

    Component verticalStrut = Box.createVerticalStrut(20);
    pMessages.add(verticalStrut);

    JLabel lblAuthor = new JLabel("By Alessandro Falappa");
    lblAuthor.setFont(lblAuthor.getFont().deriveFont(lblAuthor.getFont().getStyle() | Font.ITALIC));
    pMessages.add(lblAuthor);

    JPanel pButton = new JPanel();
    pButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
    pButton.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Button.darkShadow")),
        BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Button.highlight"))));

    JButton bOk = new JButton("OK");
    bOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutDialog.this.setVisible(false);
      }
    });
    bOk.setActionCommand("OK");
    pButton.add(bOk);

    JLabel lblLogo = new JLabel();
    lblLogo.setIcon(new ImageIcon(AboutDialog.class.getResource("res/icons/information.png")));
    lblLogo.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

    getRootPane().setDefaultButton(bOk);
    getRootPane().setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(lblLogo, BorderLayout.WEST);
    getContentPane().add(pMessages, BorderLayout.CENTER);
    getContentPane().add(pButton, BorderLayout.SOUTH);
    pack();
  }

}
