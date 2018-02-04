package com.triointeli.sarah.DatabaseModels;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class PlaceEvent {

    LatLng place;
    int enterOrLeave;

    public void setPlace(LatLng place) {
        this.place = place;
    }

    public void setEnterOrLeave(int enterOrLeave) {
        this.enterOrLeave = enterOrLeave;
    }

    public LatLng getPlace() {

        return place;
    }

    public int getEnterOrLeave() {
        return enterOrLeave;
    }
}
