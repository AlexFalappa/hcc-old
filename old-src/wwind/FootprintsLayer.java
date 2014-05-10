package wwind;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.Polygon;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.render.SurfaceQuad;
import gov.nasa.worldwind.render.SurfaceSector;
import java.util.List;

public class FootprintsLayer extends RenderableLayer {
  private BasicShapeAttributes attr = new BasicShapeAttributes();
  private BasicShapeAttributes attrHigh = new BasicShapeAttributes();

  public FootprintsLayer() {
    // properties of layer
    setName("Footprints");
    setEnabled(true);
    // painting attributes for footprints
    attr.setOutlineMaterial(Material.ORANGE);
    attr.setOutlineWidth(1);
    attr.setInteriorMaterial(Material.ORANGE);
    attr.setInteriorOpacity(0.4f);
    // painting attributes for hihglighted footprints
    attrHigh.setOutlineMaterial(Material.BLACK);
    attrHigh.setOutlineWidth(2);
    attrHigh.setInteriorMaterial(Material.WHITE);
    attrHigh.setInteriorOpacity(0.7f);
  }

  public void addSurfCircle(double lat, double lon, double rad, String tooltip) {
    SurfaceCircle shape = new SurfaceCircle(attr, LatLon.fromDegrees(lat, lon), rad);
    shape.setValue(AVKey.HOVER_TEXT, tooltip);
    addRenderable(shape);
  }

  public void addSurfPoly(List<LatLon> coords) {
    SurfacePolygon shape = new SurfacePolygon(attr, coords);
    addRenderable(shape);
  }

  public void addSurfPoly(List<LatLon> coords, String tooltip) {
    SurfacePolygon shape = new SurfacePolygon(coords);
    shape.setAttributes(attr);
    shape.setHighlightAttributes(attrHigh);
    shape.setValue(AVKey.HOVER_TEXT, tooltip);
    addRenderable(shape);
  }

  public void addSurfQuad(double lat, double lon, double w, double h, String tooltip) {
    SurfaceQuad shape = new SurfaceQuad(attr, LatLon.fromDegrees(lat, lon), w, h);
    shape.setValue(AVKey.HOVER_TEXT, tooltip);
    addRenderable(shape);
  }

  public void addSurfSect(double minlat, double minlon, double maxlat, double maxlon) {
    SurfaceSector shape = new SurfaceSector(attr,
        Sector.fromDegrees(minlat, maxlat, minlon, maxlon));
    shape.setPathType(AVKey.LINEAR);
    addRenderable(shape);
  }

  public void addPath(List<Position> coords, String tooltip) {
    Path shape = new Path(coords);
    shape.setAttributes(attr);
    shape.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
    shape.setPathType(AVKey.GREAT_CIRCLE);
    shape.setFollowTerrain(true);
    shape.setTerrainConformance(40);
    if (tooltip != null) {
      shape.setValue(AVKey.HOVER_TEXT, "hover: " + tooltip);
      shape.setValue(AVKey.ROLLOVER_TEXT, "rollover: " + tooltip);
    }
    addRenderable(shape);
  }

  public void addPoly(List<Position> coords, String tooltip) {
    Polygon shape = new Polygon(coords);
    shape.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
    shape.setAttributes(attr);
    if (tooltip != null) {
      shape.setValue(AVKey.HOVER_TEXT, "hover: " + tooltip);
      shape.setValue(AVKey.ROLLOVER_TEXT, "rollover: " + tooltip);
    }
    addRenderable(shape);
  }
}
