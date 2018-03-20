package com.example.ritasantiago.vetcare.db.entity;

import java.io.Serializable;

/**
 * Created by raquelramos on 20-03-2018.
 */

public class EventsCalendar implements Serializable{

    public String name;
    public String hour;

    public EventsCalendar(String name, String hour) {
        this.name = name;
        this.hour = hour;
    }
}
