package com.triointeli.sarah.DatabaseModels;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class TimePlace {

    Calendar calendar;
    LatLng place;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setPlace(LatLng place) {
        this.place = place;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public LatLng getPlace() {
        return place;
    }
}
