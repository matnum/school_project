
package com.example.a305.splashscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Roadconditions {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("properties")
    @Expose
    private Properties properties;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Roadconditions() {
    }

    /**
     * 
     * @param properties
     * @param type
     * @param geometry
     */
    public Roadconditions(String type, Properties properties, Geometry geometry) {
        super();
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Roadconditions withType(String type) {
        this.type = type;
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Roadconditions withProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Roadconditions withGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("properties", properties).append("geometry", geometry).toString();
    }

}
