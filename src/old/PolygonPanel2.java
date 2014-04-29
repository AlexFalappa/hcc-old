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
package old;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwindx.examples.LineBuilder;
import gui.wwind.AOILayer;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PolygonPanel2 extends JPanel {

    private JTextArea taCoords;
    private LineBuilder lineBuilder;
    private WorldWindow wwd;
    private JButton btnDrawStart;
    private JButton btnDrawPause;
    private JButton btnDrawFinish;
    private AOILayer aoi;
    private StringBuilder posList = new StringBuilder(200);
    private final JLabel lblGraphicalSelection;
    private final JLabel lblCoords;

    /**
     * Create the panel.
     */
    public PolygonPanel2() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        lblCoords = new JLabel("Coords:");
        GridBagConstraints gbc_lblCoords = new GridBagConstraints();
        gbc_lblCoords.anchor = GridBagConstraints.WEST;
        gbc_lblCoords.insets = new Insets(0, 0, 5, 0);
        gbc_lblCoords.gridx = 0;
        gbc_lblCoords.gridy = 0;
        add(lblCoords, gbc_lblCoords);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        add(scrollPane, gbc_scrollPane);

        taCoords = new JTextArea(2, 22);
        taCoords.setFont(new Font("Monospaced", taCoords.getFont().getStyle(), taCoords.getFont().getSize()));
        taCoords.setEditable(false);
        // taCoords.setLineWrap(true);
        // taCoords.setWrapStyleWord(true);
        scrollPane.setViewportView(taCoords);

        lblGraphicalSelection = new JLabel("Graphical selection");
        GridBagConstraints gbc_lblGraphicalSelection = new GridBagConstraints();
        gbc_lblGraphicalSelection.anchor = GridBagConstraints.WEST;
        gbc_lblGraphicalSelection.insets = new Insets(0, 0, 5, 0);
        gbc_lblGraphicalSelection.gridx = 0;
        gbc_lblGraphicalSelection.gridy = 2;
        add(lblGraphicalSelection, gbc_lblGraphicalSelection);

        JPanel pDrawBtns = new JPanel();
        pDrawBtns.setLayout(new BoxLayout(pDrawBtns, BoxLayout.X_AXIS));
        GridBagConstraints gbc_pDrawBtns = new GridBagConstraints();
        gbc_pDrawBtns.fill = GridBagConstraints.BOTH;
        gbc_pDrawBtns.gridx = 0;
        gbc_pDrawBtns.gridy = 3;
        add(pDrawBtns, gbc_pDrawBtns);

        btnDrawStart = new JButton("Start");
        btnDrawStart.setMargin(new Insets(2, 6, 2, 6));
        btnDrawStart.setEnabled(false);
        btnDrawStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lineBuilder.setArmed(true);
                btnDrawPause.setText("Pause");
                btnDrawPause.setEnabled(true);
                btnDrawFinish.setEnabled(true);
                btnDrawStart.setEnabled(false);
                ((Component) wwd).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        });
        pDrawBtns.add(btnDrawStart);

        btnDrawPause = new JButton("Pause");
        btnDrawPause.setMargin(new Insets(2, 6, 2, 6));
        btnDrawPause.setEnabled(false);
        btnDrawPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lineBuilder.setArmed(!lineBuilder.isArmed());
                btnDrawPause.setText(!lineBuilder.isArmed() ? "Resume" : "Pause");
                ((Component) wwd).setCursor(Cursor.getDefaultCursor());
            }
        });
        pDrawBtns.add(btnDrawPause);

        btnDrawFinish = new JButton("Close");
        btnDrawFinish.setMargin(new Insets(2, 6, 2, 6));
        btnDrawFinish.setEnabled(false);
        btnDrawFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lineBuilder.setArmed(false);
                btnDrawStart.setEnabled(true);
                btnDrawPause.setEnabled(false);
                btnDrawPause.setText("Pause");
                btnDrawFinish.setEnabled(false);
                ((Component) wwd).setCursor(Cursor.getDefaultCursor());
                // make a copy of linebuilder pos
                ArrayList<Position> perimeter = new ArrayList<>();
                for (Position pos : lineBuilder.getLine().getPositions()) {
                    perimeter.add(pos);
                }
                // clear line
                lineBuilder.clear();
                posList.setLength(0);
                // display coordinates
                Iterator<Position> it = perimeter.iterator();
                Position first = it.next();
                taCoords.setText("");
                String firstLat = String.format(Locale.ENGLISH, "%10.6f", first.getLatitude().getDegrees());
                posList.append(firstLat).append(' ');
                taCoords.append(firstLat);
                taCoords.append(" ");
                String firstLon = String.format(Locale.ENGLISH, "%11.6f", first.getLongitude().getDegrees());
                posList.append(firstLon).append(' ');
                taCoords.append(firstLon);
                taCoords.append("\n");
                while (it.hasNext()) {
                    Position pos = it.next();
                    StringBuilder sb = new StringBuilder();
                    String lat = String.format(Locale.ENGLISH, "%10.6f", pos.getLatitude().getDegrees());
                    sb.append(lat);
                    posList.append(lat).append(' ');
                    sb.append(' ');
                    String lon = String.format(Locale.ENGLISH, "%11.6f", pos.getLongitude().getDegrees());
                    sb.append(lon);
                    posList.append(lon).append(' ');
                    sb.append("\n");
                    taCoords.append(sb.toString());
                }
                posList.append(firstLat);
                taCoords.append(firstLat);
                taCoords.append(" ");
                posList.append(firstLon).append(' ');
                taCoords.append(firstLon);
                // add polygonal area of interest
                aoi.setSurfPoly(perimeter);
                wwd.redraw();
            }
        });
        pDrawBtns.add(btnDrawFinish);
    }

    public void linkTo(WorldWindow wwd, AOILayer aoi) {
        this.wwd = wwd;
        this.aoi = aoi;
        lineBuilder = new LineBuilder(wwd, aoi, null);
    }

    @Override
    public void setEnabled(boolean enabled) {
        lblCoords.setEnabled(enabled);
        taCoords.setEnabled(enabled);
        lblGraphicalSelection.setEnabled(enabled);
        btnDrawStart.setEnabled(enabled);
    }

    public String getPosList() {
        return posList.toString();
    }
}
