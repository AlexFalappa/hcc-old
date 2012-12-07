package wwind;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;

public class Circle {
  private LatLon _center = new LatLon(Angle.ZERO, Angle.ZERO);
  private double _radius = 0;

  public Circle() {
  }

  public Circle(LatLon center, double radius) {
    _center = center;
    _radius = radius;
  }

  public LatLon getCenter() {
    return _center;
  }

  public void setCenter(LatLon _center) {
    this._center = _center;
  }

  public double getRadius() {
    return _radius;
  }

  public void setRadius(double _radius) {
    this._radius = _radius;
  }

}
