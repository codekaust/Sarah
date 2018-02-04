package com.triointeli.sarah.DatabaseModels;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class MultiplePlaceEvent extends RealmObject{

    String leaveFromPlaceLAT,leaveFromPlaceLNG, reachPlace;

    public String getLeaveFromPlaceLAT() {
        return leaveFromPlaceLAT;
    }

    public void setLeaveFromPlaceLAT(String leaveFromPlaceLAT) {
        this.leaveFromPlaceLAT = leaveFromPlaceLAT;
    }

    public String getLeaveFromPlaceLNG() {
        return leaveFromPlaceLNG;
    }

    public void setLeaveFromPlaceLNG(String leaveFromPlaceLNG) {
        this.leaveFromPlaceLNG = leaveFromPlaceLNG;
    }

    public String getReachPlace() {
        return reachPlace;
    }

    public void setReachPlace(String reachPlace) {
        this.reachPlace = reachPlace;
    }
}
