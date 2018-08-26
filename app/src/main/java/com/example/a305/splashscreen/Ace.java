
package com.example.a305.splashscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Ace {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("bz")
    @Expose
    private String bz;
    @SerializedName("density")
    @Expose
    private String density;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("kp1hour")
    @Expose
    private String kp1hour;
    @SerializedName("kp4hour")
    @Expose
    private String kp4hour;
    @SerializedName("kp")
    @Expose
    private String kp;
    @SerializedName("colour")
    @Expose
    private Colour colour;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Ace() {
    }

    /**
     * 
     * @param colour
     * @param density
     * @param speed
     * @param bz
     * @param kp
     * @param kp4hour
     * @param kp1hour
     * @param date
     */
    public Ace(String date, String bz, String density, String speed, String kp1hour, String kp4hour, String kp, Colour colour) {
        super();
        this.date = date;
        this.bz = bz;
        this.density = density;
        this.speed = speed;
        this.kp1hour = kp1hour;
        this.kp4hour = kp4hour;
        this.kp = kp;
        this.colour = colour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Ace withDate(String date) {
        this.date = date;
        return this;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Ace withBz(String bz) {
        this.bz = bz;
        return this;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public Ace withDensity(String density) {
        this.density = density;
        return this;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Ace withSpeed(String speed) {
        this.speed = speed;
        return this;
    }

    public String getKp1hour() {
        return kp1hour;
    }

    public void setKp1hour(String kp1hour) {
        this.kp1hour = kp1hour;
    }

    public Ace withKp1hour(String kp1hour) {
        this.kp1hour = kp1hour;
        return this;
    }

    public String getKp4hour() {
        return kp4hour;
    }

    public void setKp4hour(String kp4hour) {
        this.kp4hour = kp4hour;
    }

    public Ace withKp4hour(String kp4hour) {
        this.kp4hour = kp4hour;
        return this;
    }

    public String getKp() {
        return kp;
    }

    public void setKp(String kp) {
        this.kp = kp;
    }

    public Ace withKp(String kp) {
        this.kp = kp;
        return this;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Ace withColour(Colour colour) {
        this.colour = colour;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("bz", bz).append("density", density).append("speed", speed).append("kp1hour", kp1hour).append("kp4hour", kp4hour).append("kp", kp).append("colour", colour).toString();
    }

}
