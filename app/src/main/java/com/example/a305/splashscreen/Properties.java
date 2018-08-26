
package com.example.a305.splashscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Properties {

    @SerializedName("roadpoint")
    @Expose
    private boolean roadpoint;
    @SerializedName("deviceid")
    @Expose
    private int deviceid;
    @SerializedName("route_from")
    @Expose
    private String routeFrom;
    @SerializedName("route_to")
    @Expose
    private String routeTo;
    @SerializedName("timestamp_from")
    @Expose
    private String timestampFrom;
    @SerializedName("timestamp_to")
    @Expose
    private String timestampTo;
    @SerializedName("geometry_id")
    @Expose
    private String geometryId;
    @SerializedName("geometry_name")
    @Expose
    private String geometryName;
    @SerializedName("sensorvalue_range")
    @Expose
    private int sensorvalueRange;
    @SerializedName("sensor_type")
    @Expose
    private String sensorType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Properties() {
    }

    /**
     * 
     * @param sensorvalueRange
     * @param geometryId
     * @param routeTo
     * @param sensorType
     * @param roadpoint
     * @param timestampTo
     * @param routeFrom
     * @param geometryName
     * @param timestampFrom
     * @param deviceid
     */
    public Properties(boolean roadpoint, int deviceid, String routeFrom, String routeTo, String timestampFrom, String timestampTo, String geometryId, String geometryName, int sensorvalueRange, String sensorType) {
        super();
        this.roadpoint = roadpoint;
        this.deviceid = deviceid;
        this.routeFrom = routeFrom;
        this.routeTo = routeTo;
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.geometryId = geometryId;
        this.geometryName = geometryName;
        this.sensorvalueRange = sensorvalueRange;
        this.sensorType = sensorType;
    }

    public boolean isRoadpoint() {
        return roadpoint;
    }

    public void setRoadpoint(boolean roadpoint) {
        this.roadpoint = roadpoint;
    }

    public Properties withRoadpoint(boolean roadpoint) {
        this.roadpoint = roadpoint;
        return this;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public Properties withDeviceid(int deviceid) {
        this.deviceid = deviceid;
        return this;
    }

    public String getRouteFrom() {
        return routeFrom;
    }

    public void setRouteFrom(String routeFrom) {
        this.routeFrom = routeFrom;
    }

    public Properties withRouteFrom(String routeFrom) {
        this.routeFrom = routeFrom;
        return this;
    }

    public String getRouteTo() {
        return routeTo;
    }

    public void setRouteTo(String routeTo) {
        this.routeTo = routeTo;
    }

    public Properties withRouteTo(String routeTo) {
        this.routeTo = routeTo;
        return this;
    }

    public String getTimestampFrom() {
        return timestampFrom;
    }

    public void setTimestampFrom(String timestampFrom) {
        this.timestampFrom = timestampFrom;
    }

    public Properties withTimestampFrom(String timestampFrom) {
        this.timestampFrom = timestampFrom;
        return this;
    }

    public String getTimestampTo() {
        return timestampTo;
    }

    public void setTimestampTo(String timestampTo) {
        this.timestampTo = timestampTo;
    }

    public Properties withTimestampTo(String timestampTo) {
        this.timestampTo = timestampTo;
        return this;
    }

    public String getGeometryId() {
        return geometryId;
    }

    public void setGeometryId(String geometryId) {
        this.geometryId = geometryId;
    }

    public Properties withGeometryId(String geometryId) {
        this.geometryId = geometryId;
        return this;
    }

    public String getGeometryName() {
        return geometryName;
    }

    public void setGeometryName(String geometryName) {
        this.geometryName = geometryName;
    }

    public Properties withGeometryName(String geometryName) {
        this.geometryName = geometryName;
        return this;
    }

    public int getSensorvalueRange() {
        return sensorvalueRange;
    }

    public void setSensorvalueRange(int sensorvalueRange) {
        this.sensorvalueRange = sensorvalueRange;
    }

    public Properties withSensorvalueRange(int sensorvalueRange) {
        this.sensorvalueRange = sensorvalueRange;
        return this;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public Properties withSensorType(String sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("roadpoint", roadpoint).append("deviceid", deviceid).append("routeFrom", routeFrom).append("routeTo", routeTo).append("timestampFrom", timestampFrom).append("timestampTo", timestampTo).append("geometryId", geometryId).append("geometryName", geometryName).append("sensorvalueRange", sensorvalueRange).append("sensorType", sensorType).toString();
    }

}
