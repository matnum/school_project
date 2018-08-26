
package com.example.a305.splashscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Probability {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("calculated")
    @Expose
    private Calculated calculated;
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
    @SerializedName("highest")
    @Expose
    private Highest highest;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Probability() {
    }

    /**
     * 
     * @param colour
     * @param _long
     * @param highest
     * @param value
     * @param date
     * @param lat
     * @param calculated
     */
    public Probability(String date, Calculated calculated, String colour, String lat, String _long, String value, Highest highest) {
        super();
        this.date = date;
        this.calculated = calculated;
        this.colour = colour;
        this.lat = lat;
        this._long = _long;
        this.value = value;
        this.highest = highest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Probability withDate(String date) {
        this.date = date;
        return this;
    }

    public Calculated getCalculated() {
        return calculated;
    }

    public void setCalculated(Calculated calculated) {
        this.calculated = calculated;
    }

    public Probability withCalculated(Calculated calculated) {
        this.calculated = calculated;
        return this;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Probability withColour(String colour) {
        this.colour = colour;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Probability withLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public Probability withLong(String _long) {
        this._long = _long;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Probability withValue(String value) {
        this.value = value;
        return this;
    }

    public Highest getHighest() {
        return highest;
    }

    public void setHighest(Highest highest) {
        this.highest = highest;
    }

    public Probability withHighest(Highest highest) {
        this.highest = highest;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("calculated", calculated).append("colour", colour).append("lat", lat).append("_long", _long).append("value", value).append("highest", highest).toString();
    }

}
