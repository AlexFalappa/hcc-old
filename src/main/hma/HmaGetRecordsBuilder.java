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
package main.hma;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;

import net.opengis.www.cat.csw._2_0_2.GetRecordsDocument;
import net.opengis.www.cat.csw._2_0_2.QueryType;
import net.opengis.www.cat.csw._2_0_2.ResultType;

/**
 * Builder for HMA GetRecords XML requests.
 * <p>
 * Uses an internal XML template representing a GetRecords RESULTS request with
 * full detail, max 100 records starting from 1.
 * <p>
 * Follows the <i>Builder</i> Design pattern.
 * <p>
 * Typical usage is as follows:
 * <pre>
 * HmaGetRecordsBuilder builder=new HmaGetRecordsBuilder();
 * builder.setResults();
 * builder.setMaxRecords(100);
 * builder.setDetailFull();
 * builder.addCollection("Collection1");
 * builder.addTemporalOverlaps(date1, date2);
 * GetRecordsDocument req=builder.getRequest();
 * </pre>
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class HmaGetRecordsBuilder {

    private static final String NS_OGC = "http://www.opengis.net/ogc";
    private static final String NS_GML = "http://www.opengis.net/gml";
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private GetRecordsDocument reqDoc;
    private QueryType query;

    public HmaGetRecordsBuilder() {
        internalInit();
    }

    public void reset() {
        internalInit();
    }

    public void setHits() {
        reqDoc.getGetRecords().setResultType(ResultType.HITS);
    }

    public void setResults() {
        reqDoc.getGetRecords().setResultType(ResultType.RESULTS);
    }

    public void setStartPosition(int pos) {
        reqDoc.getGetRecords().setStartPosition(BigInteger.valueOf(pos));
    }

    public void setMaxRecords(int max) {
        reqDoc.getGetRecords().setMaxRecords(BigInteger.valueOf(max));
    }

    public void setDetailFull() {
        query.addNewElementSetName().setStringValue("full");
    }

    public void setDetailSummary() {
        query.addNewElementSetName().setStringValue("summary");
    }

    public void addCollection(String collection) {
        final XmlCursor xc = getGlobalAndCur();
        addParentIdXMLBlock(xc, collection);
        xc.dispose();
    }

    public void addCollections(String[] collections) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Or", NS_OGC);
        xc.toEndToken();
        for (String c : collections) {
            addParentIdXMLBlock(xc, c);
        }
        xc.dispose();
    }

    public void addTemporalContained(Date t1, Date t2) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("PropertyIsGreaterThanOrEqualTo", NS_OGC);
        xc.toEndToken();
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.START_SENSING.toString()));
        xc.toNextToken();
        xc.beginElement("Literal", NS_OGC);
        xc.insertChars(df.format(t1));
        xc.toNextToken();
        xc.toNextToken();
        xc.beginElement("PropertyIsLessThanOrEqualTo", NS_OGC);
        xc.toEndToken();
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.STOP_SENSING.toString()));
        xc.toNextToken();
        xc.beginElement("Literal", NS_OGC);
        xc.insertChars(df.format(t2));
        xc.toNextToken();
        xc.toNextToken();
        xc.dispose();
    }

    public void addTemporalOverlaps(Date t1, Date t2) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("PropertyIsGreaterThanOrEqualTo", NS_OGC);
        xc.toEndToken();
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.STOP_SENSING.toString()));
        xc.toNextToken();
        xc.beginElement("Literal", NS_OGC);
        xc.insertChars(df.format(t1));
        xc.toNextToken();
        xc.toNextToken();
        xc.beginElement("PropertyIsLessThanOrEqualTo", NS_OGC);
        xc.toEndToken();
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.START_SENSING.toString()));
        xc.toNextToken();
        xc.beginElement("Literal", NS_OGC);
        xc.insertChars(df.format(t2));
        xc.toNextToken();
        xc.toNextToken();
        xc.dispose();
    }

    public void addTemporalAfter(Date t1) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("PropertyIsGreaterThanOrEqualTo", NS_OGC);
        xc.toEndToken();
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.START_SENSING.toString()));
        xc.toNextToken();
        xc.beginElement("Literal", NS_OGC);
        xc.insertChars(df.format(t1));
        xc.toNextToken();
        xc.toNextToken();
        xc.dispose();
    }

    public void addTemporalBefore(Date t1) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("PropertyIsLessThanOrEqualTo", NS_OGC);
        xc.toEndToken();
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.STOP_SENSING.toString()));
        xc.toNextToken();
        xc.beginElement("Literal", NS_OGC);
        xc.insertChars(df.format(t1));
        xc.toNextToken();
        xc.toNextToken();
        xc.dispose();
    }

    public void addSpatialOverlapsPoint(double lat, double lon) {

    }

    public void addSpatialOverlapsPolygon(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Overlaps", NS_OGC);
        xc.toEndToken();
        insertPolygonBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialOverlapsPolyline(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Overlaps", NS_OGC);
        xc.toEndToken();
        insertLinestringBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialOverlapsRange(double minlat, double maxlat, double minlon, double maxlon) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Overlaps", NS_OGC);
        xc.toEndToken();
        insertEnvelopeBlock(xc, minlat, minlon, maxlat, maxlon);
        xc.dispose();
    }

    public void addSpatialOverlapsCircle(double lat, double lon, double radius) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Overlaps", NS_OGC);
        xc.toEndToken();
        insertCircleBlock(xc, lat, lon, radius);
        xc.dispose();
    }

    public void addSpatialIntersectsPoint(double lat, double lon) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Intersects", NS_OGC);
        xc.toEndToken();
        insertPointBlock(xc, lat, lon);
        xc.dispose();
    }

    public void addSpatialIntersectsPolygon(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Intersects", NS_OGC);
        xc.toEndToken();
        insertPolygonBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialIntersectsPolyline(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Intersects", NS_OGC);
        xc.toEndToken();
        insertLinestringBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialIntersectsRange(double minlat, double maxlat, double minlon, double maxlon) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Intersects", NS_OGC);
        xc.toEndToken();
        insertEnvelopeBlock(xc, minlat, minlon, maxlat, maxlon);
        xc.dispose();
    }

    public void addSpatialIntersectsCircle(double lat, double lon, double radius) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Intersects", NS_OGC);
        xc.toEndToken();
        insertCircleBlock(xc, lat, lon, radius);
        xc.dispose();
    }

    public void addSpatialContainsPoint(double lat, double lon) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Contains", NS_OGC);
        xc.toEndToken();
        insertPointBlock(xc, lat, lon);
        xc.dispose();
    }

    public void addSpatialContainsPolygon(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Contains", NS_OGC);
        xc.toEndToken();
        insertPolygonBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialContainsPolyline(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Contains", NS_OGC);
        xc.toEndToken();
        insertLinestringBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialContainsRange(double minlat, double maxlat, double minlon, double maxlon) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Contains", NS_OGC);
        xc.toEndToken();
        insertEnvelopeBlock(xc, minlat, minlon, maxlat, maxlon);
        xc.dispose();
    }

    public void addSpatialContainsCircle(double lat, double lon, double radius) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Contains", NS_OGC);
        xc.toEndToken();
        insertCircleBlock(xc, lat, lon, radius);
        xc.dispose();
    }

    public void addSpatialIsContainedPoint(double lat, double lon) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Within", NS_OGC);
        xc.toEndToken();
        insertPointBlock(xc, lat, lon);
        xc.dispose();
    }

    public void addSpatialIsContainedPolygon(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Within", NS_OGC);
        xc.toEndToken();
        insertPolygonBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialIsContainedPolyline(String coords) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Within", NS_OGC);
        xc.toEndToken();
        insertLinestringBlock(xc, coords);
        xc.dispose();
    }

    public void addSpatialIsContainedRange(double minlat, double maxlat, double minlon, double maxlon) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Within", NS_OGC);
        xc.toEndToken();
        insertEnvelopeBlock(xc, minlat, minlon, maxlat, maxlon);
        xc.dispose();
    }

    public void addSpatialIsContainedCircle(double lat, double lon, double radius) {
        XmlCursor xc = getGlobalAndCur();
        xc.beginElement("Within", NS_OGC);
        xc.toEndToken();
        insertCircleBlock(xc, lat, lon, radius);
        xc.dispose();
    }

    public GetRecordsDocument getRequest() {
        return reqDoc;
    }

    private XmlCursor getGlobalAndCur() {
        XmlCursor xc = query.getConstraint().getFilter().newCursor();
        xc.toFirstChild();
        xc.toEndToken();
        return xc;
    }

    private void internalInit() {
        try {
            reqDoc = GetRecordsDocument.Factory.parse(getClass().getResourceAsStream("templates/getrecords-template.xml"));
            query = (QueryType) reqDoc.getGetRecords().getAbstractQuery();
        } catch (XmlException | IOException ex) {
            // ignored should always works
        }
    }

    private void addParentIdXMLBlock(XmlCursor xc, String parentId) {
        xc.beginElement("PropertyIsEqualTo", NS_OGC);
        xc.toEndToken();
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.PARENT_IDENTIFIER.toString()));
        xc.toNextToken();
        xc.beginElement("Literal", NS_OGC);
        xc.insertChars(parentId);
        xc.toNextToken();
        xc.toNextToken();
    }

    private void insertPolygonBlock(XmlCursor xc, String coords) {
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.FOOTPRINT.toString()));
        xc.toNextToken();
        xc.beginElement("Polygon", NS_GML);
        xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
        xc.toEndToken();
        xc.beginElement("exterior", NS_GML);
        xc.toEndToken();
        xc.beginElement("LinearRing", NS_GML);
        xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
        xc.toEndToken();
        xc.beginElement("posList", NS_GML);
        xc.insertChars(coords);
        xc.toNextToken();
        xc.toNextToken();
        xc.toNextToken();
        xc.toNextToken();
    }

    private void insertLinestringBlock(XmlCursor xc, String coords) {
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.FOOTPRINT.toString()));
        xc.toNextToken();
        xc.beginElement("LineString", NS_GML);
        xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
        xc.toEndToken();
        xc.beginElement("posList", NS_GML);
        xc.insertChars(coords);
        xc.toNextToken();
        xc.toNextToken();
    }

    private void insertEnvelopeBlock(XmlCursor xc, double minlat, double minlon, double maxlat, double maxlon) {
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.FOOTPRINT.toString()));
        xc.toNextToken();
        xc.beginElement("Envelope", NS_GML);
        xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
        xc.toEndToken();
        xc.beginElement("lowerCorner", NS_GML);
        xc.insertChars(String.format("%f %f", minlat, minlon));
        xc.toNextToken();
        xc.beginElement("upperCorner", NS_GML);
        xc.insertChars(String.format("%f %f", maxlat, maxlon));
        xc.toNextToken();
    }

    private void insertPointBlock(XmlCursor xc, double lat, double lon) {
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.FOOTPRINT.toString()));
        xc.toNextToken();
        xc.beginElement("Point", NS_GML);
        xc.insertAttributeWithValue("srsName", "urn:ogc:def:crs:EPSG:6.3:4326");
        xc.toEndToken();
        xc.beginElement("pos", NS_GML);
        xc.insertChars(String.format("%f %f", lat, lon));
        xc.toNextToken();
    }

    private void insertCircleBlock(XmlCursor xc, double centerLat, double centerLon, double radius) {
        xc.beginElement("PropertyName", NS_OGC);
        xc.insertChars(Slots.meta2req.get(MetadataNames.FOOTPRINT.toString()));
        xc.toNextToken();
        xc.beginElement("CircleByCenterPoint", NS_GML);
        xc.insertAttributeWithValue("numArc", "1");
        xc.toEndToken();
        xc.beginElement("pos", NS_GML);
        xc.insertChars(String.format("%f %f", centerLat, centerLon));
        xc.toNextToken();
        xc.beginElement("radius", NS_GML);
        xc.insertChars(String.valueOf(radius));
        xc.toNextToken();
    }
}
