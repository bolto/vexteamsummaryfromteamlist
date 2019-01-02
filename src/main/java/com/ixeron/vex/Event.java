package com.ixeron.vex;

public class Event {
    private String sku;
    private String startDate;
    private String seasonName;


    // Getter Methods

    public String getSku() {
        return sku;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getSeasonName() {
        return seasonName;
    }

    // Setter Methods

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
