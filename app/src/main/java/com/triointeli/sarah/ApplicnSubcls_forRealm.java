package com.triointeli.sarah;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by ktubuntu on 4/2/18.
 */

public class ApplicnSubcls_forRealm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
