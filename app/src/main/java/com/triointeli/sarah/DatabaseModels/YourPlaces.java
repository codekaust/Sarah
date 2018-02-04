package com.triointeli.sarah.DatabaseModels;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class YourPlaces extends RealmObject {
    String placeLAT;
    String placeLNG;
    String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
