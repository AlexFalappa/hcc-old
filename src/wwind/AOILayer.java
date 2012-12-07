package wwind;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.SurfaceCircle;
import gov.nasa.worldwind.render.SurfacePolygon;
import gov.nasa.worldwind.render.SurfaceSector;
import java.awt.Color;

public class AOILayer extends RenderableLayer {
  private BasicShapeAttributes attr = new BasicShapeAttributes();
  private Renderable current;

  public AOILayer() {
    // properties of layer
    setName("Area of interest");
    setEnabled(true);
    setPickEnabled(false);
    // painting attributes for footprints
    attr.setOutlineMaterial(new Material(new Color(0, 127, 0)));
    attr.setOutlineWidth(2);
    attr.setInteriorMaterial(new Material(new Color(127, 255, 127)));
    attr.setInteriorOpacity(0.2f);
  }

  public void setSurfCircle(Circle circ) {
    SurfaceCircle shape = new SurfaceCircle(attr, circ.getCenter(), circ.getRadius());
    addRenderable(shape);
  }

  public void setSurfPoly(Iterable<? extends LatLon> coords) {
    if (current != null)
      removeRenderable(current);
    SurfacePolygon shape = new SurfacePolygon(attr, coords);
    current = shape;
    addRenderable(current);
  }

  // public void addSurfQuad(double lat, double lon, double w, double h) {
  // SurfaceQuad shape = new SurfaceQuad(attr, LatLon.fromDegrees(lat, lon), w,
  // h);
  // addRenderable(shape);
  // }

  public void setSurfSect(double minlat, double minlon, double maxlat, double maxlon) {
    if (current != null)
      removeRenderable(current);
    SurfaceSector shape = new SurfaceSector(attr,
        Sector.fromDegrees(minlat, maxlat, minlon, maxlon));
    shape.setPathType(AVKey.LINEAR);
    current = shape;
    addRenderable(current);
  }

  public void setSurfSect(Sector sec) {
    if (current != null)
      removeRenderable(current);
    SurfaceSector shape = new SurfaceSector(attr, sec);
    shape.setPathType(AVKey.LINEAR);
    current = shape;
    addRenderable(current);
  }

  // public void addPath(List<Position> coords, String tooltip) {
  // Path shape = new Path(coords);
  // shape.setAttributes(attr);
  // shape.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
  // shape.setPathType(AVKey.GREAT_CIRCLE);
  // shape.setFollowTerrain(true);
  // shape.setTerrainConformance(40);
  // if (tooltip != null) {
  // shape.setValue(AVKey.HOVER_TEXT, "hover: " + tooltip);
  // shape.setValue(AVKey.ROLLOVER_TEXT, "rollover: " + tooltip);
  // }
  // addRenderable(shape);
  // }
  //
  // public void addPoly(List<Position> coords, String tooltip) {
  // Polygon shape = new Polygon(coords);
  // shape.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
  // shape.setAttributes(attr);
  // if (tooltip != null) {
  // shape.setValue(AVKey.HOVER_TEXT, "hover: " + tooltip);
  // shape.setValue(AVKey.ROLLOVER_TEXT, "rollover: " + tooltip);
  // }
  // addRenderable(shape);
  // }
}
