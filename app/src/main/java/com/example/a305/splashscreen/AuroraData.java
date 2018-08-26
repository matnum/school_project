
package com.example.a305.splashscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AuroraData {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("ace")
    @Expose
    private Ace ace;
    @SerializedName("probability")
    @Expose
    private Probability probability;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuroraData() {
    }

    /**
     * 
     * @param probability
     * @param ace
     * @param date
     */
    public AuroraData(String date, Ace ace, Probability probability) {
        super();
        this.date = date;
        this.ace = ace;
        this.probability = probability;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AuroraData withDate(String date) {
        this.date = date;
        return this;
    }

    public Ace getAce() {
        return ace;
    }

    public void setAce(Ace ace) {
        this.ace = ace;
    }

    public AuroraData withAce(Ace ace) {
        this.ace = ace;
        return this;
    }

    public Probability getProbability() {
        return probability;
    }

    public void setProbability(Probability probability) {
        this.probability = probability;
    }

    public AuroraData withProbability(Probability probability) {
        this.probability = probability;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("ace", ace).append("probability", probability).toString();
    }

}
