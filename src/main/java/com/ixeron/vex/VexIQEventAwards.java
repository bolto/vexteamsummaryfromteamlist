package com.ixeron.vex;

import java.util.List;

public class VexIQEventAwards {
    private final String eventName;
    private List<String> awards;

    public VexIQEventAwards(String eventName) {
        this.eventName = eventName;
    }
    public String getEventName() {
        return eventName;
    }
    public List<String> getAwards() {
        return awards;
    }
    public void addAward(String award){
        this.getAwards().add(award);
    }
    public void setAwards(List<String> awards) {
        this.awards = awards;
    }
}
