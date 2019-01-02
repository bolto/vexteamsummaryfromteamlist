package com.ixeron.vex;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventAwards {

    private float id;
    private String name;
    @SerializedName("event_code")
    private String eventCode;
    @SerializedName("start_at")
    private float startAt;
    @SerializedName("end_at")
    private float endAt;
    ArrayList< EventAward > awards = new ArrayList < EventAward > ();


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEventCode() {
        return eventCode;
    }

    public float getStartAt() {
        return startAt;
    }

    public float getEndAt() {
        return endAt;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public void setStartAt(float startAt) {
        this.startAt = startAt;
    }

    public void setEndAt(float endAt) {
        this.endAt = endAt;
    }
}
