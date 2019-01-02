package com.ixeron.vex;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventAwardsList {
    /**
     * List of EventAwards.  Note each EventAwards object is a list of awards of an event.  This class is a list of
     * events, each with list of awards for that event.
     */
    @SerializedName("data")
    ArrayList< EventAwards > events = new ArrayList < EventAwards > ();


    // Getter Methods



    // Setter Methods

}
