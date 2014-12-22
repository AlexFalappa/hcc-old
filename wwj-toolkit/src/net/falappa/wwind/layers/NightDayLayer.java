package net.falappa.wwind.layers;

import gov.nasa.worldwind.layers.RenderableLayer;
import java.util.Date;
import name.gano.astro.bodies.Sun;
import name.gano.astro.time.Time;
import net.falappa.wwind.helpers.EarthNightSurfacePolygon;

/**
 * A layer containing a night and day overlay.
 * <p>
 * Manages sun positions computation and update of a {@link EarthNightSurfacePolygon}.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class NightDayLayer extends RenderableLayer {

    Time time = new Time();
    Sun sunBody = new Sun(time.getMJD());
    EarthNightSurfacePolygon nightPoly = new EarthNightSurfacePolygon(sunBody, this);

    public NightDayLayer() {
        setName("Night Day Layer");
        setPickEnabled(false);
        addRenderable(nightPoly);
    }

    public void setTime(long millis) {
        time.set(millis);
        internalUpdate();
    }

    public void setTime(Date date) {
        time.set(date.getTime());
        internalUpdate();
    }

    public void addTimeSeconds(double secs) {
        time.addSeconds(secs);
        internalUpdate();
    }

    private void internalUpdate() {
        sunBody.setCurrentMJD(time.getMJD());
        nightPoly.updateFromSun();
    }
}
