package com.triointeli.sarah.DatabaseModels;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class MultiplePlaceEvent {

    LatLng leaveFromPlace, reachPlace;

    public LatLng getLeaveFromPlace() {
        return leaveFromPlace;
    }

    public LatLng getReachPlace() {
        return reachPlace;
    }

    public void setLeaveFromPlace(LatLng leaveFromPlace) {
        this.leaveFromPlace = leaveFromPlace;
    }

    public void setReachPlace(LatLng reachPlace) {
        this.reachPlace = reachPlace;
    }
}
