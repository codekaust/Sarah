package com.triointeli.sarah.DatabaseModels;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import io.realm.RealmObject;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class TimePlace extends RealmObject {

    Long calendar;
    String placeLAT,placeLNG;

    public Long getCalendar() {
        return calendar;
    }

    public void setCalendar(Long calendar) {
        this.calendar = calendar;
    }

    public String getPlaceLAT() {
        return placeLAT;
    }

    public void setPlaceLAT(String placeLAT) {
        this.placeLAT = placeLAT;
    }

    public String getPlaceLNG() {
        return placeLNG;
    }

    public void setPlaceLNG(String placeLNG) {
        this.placeLNG = placeLNG;
    }
}
