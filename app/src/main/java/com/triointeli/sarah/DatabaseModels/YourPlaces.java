package com.triointeli.sarah.DatabaseModels;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class YourPlaces extends RealmObject{
    String placeLAT,placeLNG;

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
