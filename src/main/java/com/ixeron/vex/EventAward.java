package com.ixeron.vex;

import java.util.ArrayList;

public class EventAward {
    private String name;
    ArrayList< String > teams = new ArrayList < String > ();
    ArrayList < Object > qualifies_for = new ArrayList < Object > ();

    // Getter Methods

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }
}
