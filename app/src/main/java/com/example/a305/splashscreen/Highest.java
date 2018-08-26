
package com.example.a305.splashscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Highest {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Highest() {
    }

    /**
     * 
     * @param colour
     * @param _long
     * @param value
     * @param date
     * @param lat
     */
    public Highest(String date, String colour, String lat, String _long, String value) {
        super();
        this.date = date;
        this.colour = colour;
        this.lat = lat;
        this._long = _long;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Highest withDate(String date) {
        this.date = date;
        return this;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Highest withColour(String colour) {
        this.colour = colour;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Highest withLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public Highest withLong(String _long) {
        this._long = _long;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Highest withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("colour", colour).append("lat", lat).append("_long", _long).append("value", value).toString();
    }

}
