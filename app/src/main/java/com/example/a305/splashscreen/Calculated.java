
package com.example.a305.splashscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Calculated {

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("colour")
    @Expose
    private String colour;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Calculated() {
    }

    /**
     * 
     * @param colour
     * @param _long
     * @param value
     * @param lat
     */
    public Calculated(String lat, String _long, String value, String colour) {
        super();
        this.lat = lat;
        this._long = _long;
        this.value = value;
        this.colour = colour;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Calculated withLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public Calculated withLong(String _long) {
        this._long = _long;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Calculated withValue(String value) {
        this.value = value;
        return this;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Calculated withColour(String colour) {
        this.colour = colour;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("lat", lat).append("_long", _long).append("value", value).append("colour", colour).toString();
    }

}
