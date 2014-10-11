/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.falappa.wwind.utils;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.render.Highlightable;
import gov.nasa.worldwindx.applications.worldwindow.util.Util;

/**
 * Copied from worldwindx to make highlight method public.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class HighlightControllerPub implements SelectListener {

    protected WorldWindow wwd;
    protected Object highlightEventType = SelectEvent.ROLLOVER;
    protected Highlightable lastHighlightObject;
    protected boolean enabled = true;

    /**
     * Creates a controller for a specified World Window.
     *
     * @param wwd the World Window to monitor.
     * @param highlightEventType the type of {@link SelectEvent} to highlight in
     * response to. The default is {@link SelectEvent#ROLLOVER}.
     */
    public HighlightControllerPub(WorldWindow wwd, Object highlightEventType) {
        this.wwd = wwd;
        this.highlightEventType = highlightEventType;
        this.wwd.addSelectListener(this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void dispose() {
        this.wwd.removeSelectListener(this);
    }

    @Override
    public void selected(SelectEvent event) {
        if (!enabled) {
            return;
        }
        try {
            if (this.highlightEventType != null && event.getEventAction().equals(this.highlightEventType)) {
                highlight(event.getTopObject());
            }
        } catch (Exception e) {
            // Wrap the handler in a try/catch to keep exceptions from bubbling up
            Util.getLogger().warning(e.getMessage() != null ? e.getMessage() : e.toString());
        }
    }

    public void highlight(Object o) {
        if (this.lastHighlightObject == o) {
            return; // same thing selected
        }
        // Turn off highlight if on.
        if (this.lastHighlightObject != null) {
            this.lastHighlightObject.setHighlighted(false);
            this.lastHighlightObject = null;
        }
        // Turn on highlight if object selected.
        if (o instanceof Highlightable) {
            this.lastHighlightObject = (Highlightable) o;
            this.lastHighlightObject.setHighlighted(true);
        }
    }
}
