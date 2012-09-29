package gui;

import gui.search.SearchPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import main.App;
import net.opengis.www.cat.csw._2_0_2.GetCapabilitiesDocument;
import net.opengis.www.cat.wrs._1_0.CapabilitiesDocument;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;
import net.opengis.www.cat.wrs._1_0.ServiceExceptionReportFault;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

public class CapabilitiesDialog extends JDialog {

  private final JPanel contentPanel = new JPanel();
  private final JTextArea taCapabilities = new JTextArea(10, 60);
  private SearchPanel searchPane;
  private JButton btnAquire;
  private JButton btnCancel;
  private CapabilitiesDocument capDoc;

  /**
   * Create the dialog.
   */
  public CapabilitiesDialog() {
    setTitle("Connect to catalogue");
    setSize(400, 300);
    setModal(true);

    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    GridBagLayout gbl_contentPanel = new GridBagLayout();
    gbl_contentPanel.columnWeights = new double[] { 1.0 };
    gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0 };
    contentPanel.setLayout(gbl_contentPanel);

    JLabel lblCapabilities = new JLabel("Capabilities:");
    GridBagConstraints gbc_lblCapabilities = new GridBagConstraints();
    gbc_lblCapabilities.anchor = GridBagConstraints.WEST;
    gbc_lblCapabilities.insets = new Insets(0, 0, 5, 0);
    gbc_lblCapabilities.gridx = 0;
    gbc_lblCapabilities.gridy = 0;
    contentPanel.add(lblCapabilities, gbc_lblCapabilities);

    JScrollPane scrollPane = new JScrollPane();
    GridBagConstraints gbc_scrollPane = new GridBagConstraints();
    gbc_scrollPane.fill = GridBagConstraints.BOTH;
    gbc_scrollPane.gridx = 0;
    gbc_scrollPane.gridy = 1;
    contentPanel.add(scrollPane, gbc_scrollPane);

    taCapabilities.setEditable(false);
    taCapabilities.setEnabled(false);
    taCapabilities.setText("Retrieving capabilities...");
    scrollPane.setViewportView(taCapabilities);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    btnCancel = new JButton("Cancel connection");
    btnCancel.setEnabled(false);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
    buttonPanel.add(btnCancel);

    btnAquire = new JButton("Acquire collections");
    btnAquire.setEnabled(false);
    btnAquire.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        XmlObject[] res = capDoc
            .selectPath("declare namespace rim='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' .//rim:Slot[contains(@name,'parentIdentifier')]");
        ArrayList<String> collections = new ArrayList<String>();
        if (res.length > 0) {
          XmlCursor xc = res[0].newCursor();
          xc.toChild("urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", "ValueList");
          if (xc.toFirstChild()) {
            // at first <Value> tag
            collections.add(xc.getTextValue());
            // iterate on sibling <Value> tags
            while (xc.toNextSibling()) {
              collections.add(xc.getTextValue());
            }
          }
          xc.dispose();
        }
        searchPane.setCollections(collections.toArray(new String[] {}));
        setVisible(false);
        App.currServiceInPrefs();
      }
    });
    buttonPanel.add(btnAquire);

    getRootPane().setDefaultButton(btnAquire);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  public void connect(SearchPanel pane) {
    searchPane = pane;
    SwingWorker<CapabilitiesDocument, Void> worker = new SwingWorker<CapabilitiesDocument, Void>() {
      @Override
      public CapabilitiesDocument doInBackground() {
        CapabilitiesDocument capDoc = null;
        try {
          CatalogueStub stub = App.frame.getWebServStub();
          GetCapabilitiesDocument capReq = GetCapabilitiesDocument.Factory.parse(this.getClass().getResourceAsStream(
              "xml/capabilities.xml"));
          capDoc = stub.getCapabilities(capReq);
        } catch (RemoteException e) {
          e.printStackTrace();
        } catch (ServiceExceptionReportFault e) {
          e.printStackTrace();
        } catch (XmlException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return capDoc;
      }

      @Override
      public void done() {
        try {
          capDoc = get();
          if (capDoc != null) {
            taCapabilities.setText(capDoc.xmlText());
          }
          taCapabilities.setEnabled(true);
          btnAquire.setEnabled(true);
          btnCancel.setEnabled(true);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
    };
    worker.execute();
    setVisible(true);
  }
}
