/*
 * Copyright 2014 Alessandro Falappa <alex.falappa@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gui;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gui.dialogs.AboutDialog;
import gui.dialogs.CatDefinitionDialog;
import gui.wwind.AOILayer;
import gui.wwind.FootprintsLayer;
import gui.wwind.MOILayer;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import main.App;
import main.data.CatalogueDefinition;
import main.hma.HmaGetRecordsBuilder;
import net.opengis.www.cat.csw._2_0_2.GetRecordsDocument;
import net.opengis.www.cat.csw._2_0_2.GetRecordsResponseDocument;
import net.opengis.www.cat.wrs._1_0.CatalogueStub;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

/**
 * HCC main window.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class MainWindow extends javax.swing.JFrame {

    public FootprintsLayer footprints;
    public AOILayer aois;
    public MOILayer mois;
    private final DefaultComboBoxModel<CatalogueDefinition> dcmCatalogues = new DefaultComboBoxModel<>();
    private final HmaGetRecordsBuilder builder = new HmaGetRecordsBuilder();
    private CatalogueStub stub = null;

    public MainWindow() {
        initComponents();
        try {
            stub = new CatalogueStub();
            pCollections.setStub(stub);
        } catch (AxisFault ex) {
            ex.printStackTrace(System.err);
        }
        setupLayers();
        loadPrefs();
    }

    public CatalogueDefinition getCurrentCatalogue() {
        return (CatalogueDefinition) cbCatalogues.getSelectedItem();
    }

    public void execHits() {
        if (checkCanSubmit()) {
            pSearchButons.enableButtons(false);
            startWorker(false);
        }
    }

    public void execResults() {
        if (checkCanSubmit()) {
            pSearchButons.enableButtons(false);
            startWorker(true);
        }
    }

    public String getReqText() {
        if (checkCanSubmit()) {
            builder.reset();
            GetRecordsDocument req = buildReq(true);
            return req.xmlText(new XmlOptions().setSavePrettyPrint());
        }
        return null;
    }

    public void showErrorDialog(String title, String message) {
        showErrorDialog(title, message, null);
    }

    public void showErrorDialog(String title, String message, Exception ex) {
        StringBuilder sb = new StringBuilder(message);
        if (ex != null) {
            sb.append("\n\nException:\n").append(ex.getMessage());
        }
        JOptionPane.showMessageDialog(this, sb.toString(), title, JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void zoomToSector(Sector sector) {
        WorldWindowGLCanvas wwCanvas = wwindPane.getWWCanvas();
        double delta_x = sector.getDeltaLonRadians();
        double delta_y = sector.getDeltaLatRadians();
        double earthRadius = wwCanvas.getModel().getGlobe().getRadius();
        double horizDistance = earthRadius * delta_x;
        double vertDistance = earthRadius * delta_y;
        // Form a triangle consisting of the longest distance on the ground and the ray from the eye to the center point
        // The ray from the eye to the midpoint on the ground bisects the FOV
        double distance = Math.max(horizDistance, vertDistance) / 2;
        double altitude = distance / Math.tan(wwCanvas.getView().getFieldOfView().radians / 2);
        LatLon latlon = sector.getCentroid();
        Position pos = new Position(latlon, altitude);
        View view = wwCanvas.getView();
        view.setEyePosition(pos);
        view.firePropertyChange(AVKey.VIEW, null, view);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pQueryParams = new javax.swing.JPanel();
        pCollections = new gui.panels.CollectionsPanel();
        pTime = new gui.panels.TimeWindowPanel();
        pGeo = new gui.panels.GeoAreaPanel();
        pSearchButons = new gui.panels.SearchButtonsPanel();
        pToolBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbCatalogues = new javax.swing.JComboBox();
        bAddCat = new javax.swing.JButton();
        bDelCat = new javax.swing.JButton();
        lMexs = new javax.swing.JLabel();
        bEditCat = new javax.swing.JButton();
        bInfo = new javax.swing.JButton();
        pViewSettings = new gui.panels.ViewSettingsPanel();
        wwindPane = new net.falappa.widgets.wwind.WWindPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HMA Catalogue Client");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pQueryParams.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 6, 0, 0));
        pQueryParams.setLayout(new javax.swing.BoxLayout(pQueryParams, javax.swing.BoxLayout.PAGE_AXIS));

        pCollections.setButtonsVisible(false);
        pQueryParams.add(pCollections);
        pQueryParams.add(pTime);
        pQueryParams.add(pGeo);
        pQueryParams.add(pSearchButons);

        getContentPane().add(pQueryParams, java.awt.BorderLayout.WEST);

        jLabel1.setText("Catalogue");

        cbCatalogues.setModel(dcmCatalogues);
        cbCatalogues.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCataloguesItemStateChanged(evt);
            }
        });
        cbCatalogues.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbCataloguesMouseEntered(evt);
            }
        });

        bAddCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_190_circle_plus.png"))); // NOI18N
        bAddCat.setToolTipText("Create catalogue definition");
        bAddCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddCatActionPerformed(evt);
            }
        });

        bDelCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_191_circle_minus.png"))); // NOI18N
        bDelCat.setToolTipText("Remove current catalogue definition");
        bDelCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDelCatActionPerformed(evt);
            }
        });

        lMexs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        bEditCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_150_edit.png"))); // NOI18N
        bEditCat.setToolTipText("Edit current catalogue definition");
        bEditCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditCatActionPerformed(evt);
            }
        });

        bInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images_16x16/glyphicons_195_circle_info.png"))); // NOI18N
        bInfo.setToolTipText("About this application");
        bInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pToolBarLayout = new javax.swing.GroupLayout(pToolBar);
        pToolBar.setLayout(pToolBarLayout);
        pToolBarLayout.setHorizontalGroup(
            pToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pToolBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCatalogues, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bAddCat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bDelCat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bEditCat)
                .addGap(5, 5, 5)
                .addComponent(lMexs, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bInfo)
                .addContainerGap())
        );
        pToolBarLayout.setVerticalGroup(
            pToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pToolBarLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lMexs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bAddCat)
                        .addComponent(cbCatalogues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(bDelCat))
                    .addComponent(bEditCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        getContentPane().add(pToolBar, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(pViewSettings, java.awt.BorderLayout.LINE_END);

        wwindPane.setBottomBar(true);
        getContentPane().add(wwindPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAddCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddCatActionPerformed
        CatDefinitionDialog d = new CatDefinitionDialog(this);
        d.setStub(stub);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
        if (d.isOkPressed()) {
            cbCatalogues.addItem(d.getDefinedCatalogue());
        }
    }//GEN-LAST:event_bAddCatActionPerformed

    private void bDelCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDelCatActionPerformed
        if (cbCatalogues.getSelectedIndex() >= 0) {
            dcmCatalogues.removeElementAt(cbCatalogues.getSelectedIndex());
        }
    }//GEN-LAST:event_bDelCatActionPerformed

    private void bInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInfoActionPerformed
        AboutDialog ad = new AboutDialog(this);
        ad.setLocationRelativeTo(this);
        ad.setVisible(true);
    }//GEN-LAST:event_bInfoActionPerformed

    private void bEditCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditCatActionPerformed
        final int selIdx = cbCatalogues.getSelectedIndex();
        if (selIdx >= 0) {
            CatDefinitionDialog d = new CatDefinitionDialog(this, (CatalogueDefinition) cbCatalogues.getSelectedItem());
            d.setStub(stub);
            d.setLocationRelativeTo(this);
            d.setVisible(true);
            if (d.isOkPressed()) {
                dcmCatalogues.insertElementAt(d.getDefinedCatalogue(), selIdx + 1);
                dcmCatalogues.removeElementAt(selIdx);
            }
        }
    }//GEN-LAST:event_bEditCatActionPerformed

    private void cbCataloguesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbCataloguesMouseEntered
        cbCatalogues.setToolTipText(getCatalogueTooltip());
    }//GEN-LAST:event_cbCataloguesMouseEntered

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        storePrefs();
    }//GEN-LAST:event_formWindowClosing

    private void cbCataloguesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCataloguesItemStateChanged
        if (cbCatalogues.getSelectedIndex() >= 0) {
            final CatalogueDefinition selCatDef = getCurrentCatalogue();
            // rewrite collection list
            pCollections.setCollections(selCatDef.getCollections());
            if (selCatDef.isSoapV12()) {
                //set soap 1.2 in stub
                stub._getServiceClient().getOptions().setSoapVersionURI(Constants.URI_SOAP12_ENV);
            } else {
                //set soap 1.1 in stub
                stub._getServiceClient().getOptions().setSoapVersionURI(Constants.URI_SOAP11_ENV);
            }
            //set timeout in stub
            Integer to = selCatDef.getTimeoutMillis();
            stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, to);
            stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, to);
            // set endpoint url in stub
            stub._getServiceClient().setTargetEPR(new EndpointReference(selCatDef.getEndpoint()));
        }
    }//GEN-LAST:event_cbCataloguesItemStateChanged

    private void setupLayers() {
        WorldWindowGLCanvas wwCanvas = wwindPane.getWWCanvas();
        // create footprints and AOI layers and add them before the place names
        footprints = new FootprintsLayer();
        footprints.linkTo(wwCanvas);
        wwindPane.addLayer(footprints);
        aois = new AOILayer();
        wwindPane.addLayer(aois);
        mois = new MOILayer();
        wwindPane.addLayer(mois);
        // link panels to globe
        pGeo.linkTo(wwCanvas, aois, mois);
        // link view settings panel
        pViewSettings.linkTo(wwCanvas);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddCat;
    private javax.swing.JButton bDelCat;
    private javax.swing.JButton bEditCat;
    private javax.swing.JButton bInfo;
    private javax.swing.JComboBox cbCatalogues;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel lMexs;
    private gui.panels.CollectionsPanel pCollections;
    private gui.panels.GeoAreaPanel pGeo;
    private javax.swing.JPanel pQueryParams;
    private gui.panels.SearchButtonsPanel pSearchButons;
    private gui.panels.TimeWindowPanel pTime;
    private javax.swing.JPanel pToolBar;
    private gui.panels.ViewSettingsPanel pViewSettings;
    public net.falappa.widgets.wwind.WWindPanel wwindPane;
    // End of variables declaration//GEN-END:variables

    private boolean checkCanSubmit() {
        if (getCurrentCatalogue() == null) {
            showErrorDialog("Submission error", "Select a catalogue first!");
            return false;
        }
        if (!pCollections.isCollectionSelected()) {
            showErrorDialog("Submission error", "At least one collection must be selected");
            return false;
        }
        return true;
    }

    GetRecordsDocument buildReq(boolean isResults) {
        switch (pSearchButons.getDetail()) {
            case 0:
                //brief
                builder.setDetailBrief();
                break;
            case 1:
                //summary
                builder.setDetailSummary();
                break;
            case 2:
                //full
                builder.setDetailFull();
                break;
        }
        // request type
        if (isResults) {
            builder.setResults();
        } else {
            builder.setHits();
        }
        // max records and start position
        builder.setMaxRecords(pSearchButons.getMaxRecs());
        builder.setStartPosition(pSearchButons.getStartPos());
        //add collections
        String[] colls = pCollections.getSelectedCollections();
        if (colls != null) {
            if (colls.length == 1) {
                builder.addCollection(colls[0]);
            } else {
                builder.addCollections(colls);
            }
        }
        // add time constraints
        if (pTime.constraintsEnabled()) {
            switch (pTime.getOperator()) {
                case 0:
                    builder.addTemporalContained(pTime.getT1(), pTime.getT2());
                    break;
                case 1:
                    builder.addTemporalOverlaps(pTime.getT1(), pTime.getT2());
                    break;
                case 2:
                    builder.addTemporalAfter(pTime.getT1());
                    break;
                case 3:
                    builder.addTemporalBefore(pTime.getT1());
                    break;
            }
        }
        // add spatial constraints
        if (pGeo.constraintsEnabled()) {
            switch (pGeo.getOperator()) {
                case 0:
                    switch (pGeo.getPrimitive()) {
                        case 0:
                            builder.addSpatialOverlapsPolygon(pGeo.getPolygonCoords());
                            break;
                        case 1:
                            builder.addSpatialOverlapsCircle(pGeo.getCircleCenterLat(), pGeo.getCircleCenterLon(), pGeo.getCircleRadius());
                            break;
                        case 2:
                            builder.addSpatialOverlapsPolyline(pGeo.getPolylineCoords());
                            break;
                        case 3:
                            builder.addSpatialOverlapsPoint(pGeo.getPointLat(), pGeo.getPointLon());
                            break;
                        case 4:
                            builder.addSpatialOverlapsRange(pGeo.getRangeLatMin(), pGeo.getRangeLatMax(), pGeo.getRangeLonMin(), pGeo.getRangeLonMax());
                            break;
                    }
                    break;
                case 1:
                    switch (pGeo.getPrimitive()) {
                        case 0:
                            builder.addSpatialContainsPolygon(pGeo.getPolygonCoords());
                            break;
                        case 1:
                            builder.addSpatialContainsCircle(pGeo.getCircleCenterLat(), pGeo.getCircleCenterLon(), pGeo.getCircleRadius());
                            break;
                        case 2:
                            builder.addSpatialContainsPolyline(pGeo.getPolylineCoords());
                            break;
                        case 3:
                            builder.addSpatialContainsPoint(pGeo.getPointLat(), pGeo.getPointLon());
                            break;
                        case 4:
                            builder.addSpatialContainsRange(pGeo.getRangeLatMin(), pGeo.getRangeLatMax(), pGeo.getRangeLonMin(), pGeo.getRangeLonMax());
                            break;
                    }
                    break;
                case 2:
                    switch (pGeo.getPrimitive()) {
                        case 0:
                            builder.addSpatialIntersectsPolygon(pGeo.getPolygonCoords());
                            break;
                        case 1:
                            builder.addSpatialIntersectsCircle(pGeo.getCircleCenterLat(), pGeo.getCircleCenterLon(), pGeo.getCircleRadius());
                            break;
                        case 2:
                            builder.addSpatialIntersectsPolyline(pGeo.getPolylineCoords());
                            break;
                        case 3:
                            builder.addSpatialIntersectsPoint(pGeo.getPointLat(), pGeo.getPointLon());
                            break;
                        case 4:
                            builder.addSpatialIntersectsRange(pGeo.getRangeLatMin(), pGeo.getRangeLatMax(), pGeo.getRangeLonMin(), pGeo.getRangeLonMax());
                            break;
                    }
                    break;
                case 3:
                    switch (pGeo.getPrimitive()) {
                        case 0:
                            builder.addSpatialIsContainedPolygon(pGeo.getPolygonCoords());
                            break;
                        case 1:
                            builder.addSpatialIsContainedCircle(pGeo.getCircleCenterLat(), pGeo.getCircleCenterLon(), pGeo.getCircleRadius());
                            break;
                        case 2:
                            builder.addSpatialIsContainedPolyline(pGeo.getPolylineCoords());
                            break;
                        case 3:
                            builder.addSpatialIsContainedPoint(pGeo.getPointLat(), pGeo.getPointLon());
                            break;
                        case 4:
                            builder.addSpatialIsContainedRange(pGeo.getRangeLatMin(), pGeo.getRangeLatMax(), pGeo.getRangeLonMin(), pGeo.getRangeLonMax());
                            break;
                    }
                    break;
            }
        }
        final GetRecordsDocument request = builder.getRequest();
        System.out.println("********** Request ******************");
        System.out.println(request.xmlText(new XmlOptions().setSavePrettyPrint()));
        return request;
    }

    int processResults(GetRecordsResponseDocument resp) {
        // extract footprints
        XmlObject[] res = resp.selectPath("declare namespace rim='urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0' .//rim:RegistryPackage");
        if (res.length > 0) {
            App.frame.footprints.removeAllRenderables();
        }
        for (XmlObject xo : res) {
            // extract pid
            XmlCursor xc = xo.newCursor();
            String pid = xc.getAttributeText(new QName("id"));
            xc.dispose();
            // extract footprint coordinates
            XmlObject[] xpos = xo.selectPath("declare namespace gml='http://www.opengis.net/gml' .//gml:posList");
            if (xpos.length == 0) {
                continue;
            }
            xc = xpos[0].newCursor();
            String[] coords = xc.getTextValue().split("\\s");
            xc.dispose();
            List<LatLon> geopoints = new ArrayList<>();
            for (int i = 0; i < coords.length; i += 2) {
                double lat = Double.valueOf(coords[i]);
                double lon = Double.valueOf(coords[i + 1]);
                geopoints.add(LatLon.fromDegrees(lat, lon));
            }
            footprints.addSurfPoly(geopoints, pid);
            wwindPane.redraw();
        }
        return res.length;
    }

    int processHits(GetRecordsResponseDocument resp) {
        return resp.getGetRecordsResponse().getSearchResults().getNumberOfRecordsMatched().intValue();
    }

    void enableSearchButtons(boolean enabled) {
        pSearchButons.enableButtons(enabled);
    }

    private void startWorker(boolean isResults) {
        builder.reset();
        GetRecordsWorker grw = new GetRecordsWorker(this, stub, isResults);
        grw.execute();
    }

    private String getCatalogueTooltip() {
        CatalogueDefinition cat = getCurrentCatalogue();
        if (cat != null) {
            StringBuilder sb = new StringBuilder("<html>");
            sb.append("<b>").append(cat.getName()).append("</b><br>");
            sb.append("Endpoint: ").append(cat.getEndpoint()).append("<br><i>");
            sb.append(cat.isSoapV12() ? "SOAP v1.2" : "SOAP V1.1").append("</i></html>");
            return sb.toString();
        } else {
            return "No catalogue";
        }
    }

    private void storePrefs() {
        // get preferences API node
        Preferences prefs = Preferences.userRoot().node("alexfalappa.hcc-nb");
        // save view settings
        pViewSettings.storePrefs(prefs);
        // save catalogue definitions
        Preferences pCatalogs = prefs.node("catalogues");
        for (int i = 0; i < dcmCatalogues.getSize(); i++) {
            CatalogueDefinition catDef = dcmCatalogues.getElementAt(i);
            // create new pref child node with catalogue name
            Preferences catPref = pCatalogs.node(catDef.getName());
            // store endpoint, timeout and soap flag
            catPref.put("edp", catDef.getEndpoint());
            catPref.putBoolean("soapv12", catDef.isSoapV12());
            catPref.putInt("timeout", catDef.getTimeoutMillis());
            // store collections as space separated string
            StringBuilder sb = new StringBuilder();
            final int arrLen = catDef.getCollections().length;
            for (int j = 0; j < arrLen; j++) {
                sb.append(catDef.getCollections()[j]);
                if (j < arrLen - 1) {
                    sb.append(' ');
                }
            }
            catPref.put("collections", sb.toString());
        }
    }

    private void loadPrefs() {
        try {
            // get preferences API node
            Preferences prefs = Preferences.userRoot().node("alexfalappa.hcc-nb");
            // load view settings
            pViewSettings.loadPrefs(prefs);
            // load catalogue definitions
            Preferences pCatalogs = prefs.node("catalogues");
            final String[] nodes = pCatalogs.childrenNames();
            for (String nodeName : nodes) {
                Preferences catPref = pCatalogs.node(nodeName);
                CatalogueDefinition catDef = new CatalogueDefinition(nodeName, catPref.get("edp", "n/a"), catPref.getBoolean("soapv12", false), catPref.getInt("timeout", 20000));
                catDef.setCollections(catPref.get("collections", "").split("\\s"));
                dcmCatalogues.addElement(catDef);
            }
        } catch (BackingStoreException ex) {
            // no prefs, do nothing
        }
    }

    public void resetNavigation() {
        pViewSettings.reset();
    }
}
