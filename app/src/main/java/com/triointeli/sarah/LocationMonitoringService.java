package com.triointeli.sarah;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ktubuntu on 6/2/18.
 */

public class LocationMonitoringService extends Service{


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
