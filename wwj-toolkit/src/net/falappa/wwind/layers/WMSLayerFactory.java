package com.telespazio.wwind.layers;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.avlist.AVListImpl;
import gov.nasa.worldwind.util.WWXML;
import gov.nasa.worldwind.wms.WMSTiledImageLayer;

/**
 * A factory to create {@link WMSTiledImageLayer}s based on a definition template and some parameters.
 * <p>
 * It's a static utility class.
 *
 * @author Alessandro Falappa
 */
public final class WMSLayerFactory {

    // private to prevent instantiation
    private WMSLayerFactory() {
    }

    /**
     * Create a WorldWind {@link WMSTiledImageLayer} based on the internal template definition and the given parameters.
     * <p>
     * The template definition is in {@code wms-def-template.xml}.
     *
     * @param displayName WorldWind layer name
     * @param capabilitiesUrl URL for the WMS GetCapabilities operation
     * @param mapUrl URL for the WMS GetMap operation
     * @param serverLayerNames comma separated list of layer names on the WMS server
     * @param cacheFolder cache subfolder(s) in WorldWind cache dir. The final folder will be {@code <WWindCacheDir>/Earth/<cacheFolder>}.
     * @param numLevels number of pyramidal levels available from the WMS server
     * @return a WorldWind {@link WMSTiledImageLayer} object
     */
    public static WMSTiledImageLayer createWMSLayer(String displayName, String capabilitiesUrl, String mapUrl, String serverLayerNames, String cacheFolder, int numLevels) {
        // prepare attribute value pairs
        AVList params = new AVListImpl();
        params.setValue(AVKey.DISPLAY_NAME, displayName);
        params.setValue(AVKey.GET_CAPABILITIES_URL, capabilitiesUrl);
        params.setValue(AVKey.GET_MAP_URL, mapUrl);
        params.setValue(AVKey.LAYER_NAMES, serverLayerNames);
        params.setValue(AVKey.DATA_CACHE_NAME, cacheFolder);
        params.setValue(AVKey.NUM_LEVELS, numLevels);
        return new WMSTiledImageLayer(WWXML.openDocumentStream(WMSLayerFactory.class.getResourceAsStream(
                "wms-def-template.xml")), params);
    }

    /**
     * Shorter form to create a WorldWind {@link WMSTiledImageLayer} based on the internal template definition.
     * <p>
     * The GetCapabilities URL is assumed equal to the GetMap URL, the cache folder is set to the display name and 10 levels are assumed.
     *
     * @param displayName
     * @param mapUrl
     * @param serverLayerNames
     * @return
     * @see #createWMSLayer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
     */
    public static WMSTiledImageLayer createWMSLayer(String displayName, String mapUrl, String serverLayerNames) {
        return createWMSLayer(displayName, mapUrl, mapUrl, serverLayerNames, displayName, 10);
    }
}
