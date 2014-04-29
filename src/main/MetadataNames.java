package main;

/**
 * Enumeration of names of standard EO product metadata.
 *
 * The strings refers to the metadata names in HMA catalogue spec.
 *
 * @author Alessandro Falappa
 */
public enum MetadataNames {
    PRODUCT_IDENTIFIER("prodIdentifier"),
    PARENT_IDENTIFIER("parentIdentifier"),
    PRODUCT_TYPE("productType"),
    FOOTPRINT("footprint"),
    START_SENSING("startSensingTime"),
    STOP_SENSING("stopSensingTime"),
    ORBIT_NUMBER("orbitNumber"),
    ORBIT_DIRECTION("orbitDirection"),
    POLARISN_CHANNELS("polarisationChannels"),
    POLARISN_MODE("polarisationMode"),
    LOOK_SIDE("antennaLookDirection"),
    INCID_ANGLE("incidenceAngle"),
    MIN_INCID_ANGLE("minimumIncidenceAngle"),
    MAX_INCID_ANGLE("maximumIncidenceAngle"),
    DOPPLER_FREQ("dopplerFrequency"),
    INCID_ANGLE_VAR("incindenceAngleVariation"),
    STATUS("productStatus"),
    SCENE_CENTER("sceneCenter"),
    ACQ_STATION("acquisitionStation"),
    ACQ_DATE("acquisitionDate"),
    ASC_NODE_LON("ascendingNodeLongitude"),
    WRS_LON("wrsLongitudeGrid"),
    WRS_LAT("wrsLatitudeGrid"),
    MISSION_NAME("missionName"),
    SAT_NAME("platformShortName"),
    INST_NAME("instrumentShortName"),
    SAT_SERIAL("platformSerialIdentifier"),
    SENS_TYPE("sensorType"),
    SENS_OP_MODE("sensorOperationalMode"),
    SENS_RESOLUTION("sensorResolution"),
    SENS_SWATH("sensorSwathIdentifier"),
    ARCH_CENTER("archivingCenter"),
    ARCH_ID("archivingIdentifier"),
    ARCH_DATE("archivingDate"),
    PROC_LEVEL("processingLevel"),
    START_DOWNLINK("startDownlinkTime"),
    STOP_DOWNLINK("stopDownlinkTime"),
    ARCHIVE_PATH("archivePath");
    
    private final String name;

    private MetadataNames(final String n) {
        name = n;
    }

    @Override
    public String toString() {
        return name;
    }

    public static MetadataNames forValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Null MetadataNames enum value!");
        }
        for (MetadataNames v : values()) {
            if (value.equalsIgnoreCase(v.name)) {
                return v;
            }
        }
        throw new IllegalArgumentException(String.format(
                "Invalid MetadataNames enum value: %s", value));
    }
}
