package com.triointeli.sarah;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.triointeli.sarah.DatabaseModels.YourPlaces;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ktubuntu on 6/2/18.
 */

public class GeofenceTransitionService extends IntentService {

    private static final String TAG = GeofenceTransitionService.class.getSimpleName();

    public static final int GEOFENCE_NOTIFICATION_ID = 0;

    public GeofenceTransitionService() {
        super(TAG);
    }

    Realm realm;

    @Override
    protected void onHandleIntent(Intent intent) {
        realm=Realm.getDefaultInstance();

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Handling errors
        if ( geofencingEvent.hasError() ) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;
        }

        int geoFenceTransition = geofencingEvent.getGeofenceTransition();

        // Check if the transition type is of interest
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofence that were triggered
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            //String geofenceTransitionDetails = getGeofenceTransitionDetails(geoFenceTransition, triggeringGeofences );

            // Send notification details as a String
        }

        if(geoFenceTransition==Geofence.GEOFENCE_TRANSITION_EXIT){

            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

        }
    }


//    private String getGeofenceTransitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
//        // get the ID of each geofence triggered
//        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
//        for ( Geofence geofence : triggeringGeofences ) {
//            triggeringGeofencesList.add( geofence.getRequestId() );
//        }
//
//        String status = null;
//        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER )
//            status = "Entering ";
//        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
//            status = "Exiting ";
//        return status + TextUtils.join( ", ", triggeringGeofencesList);
//    }

//    private void sendNotification( String msg ) {
//        Log.i(TAG, "sendNotification: " + msg );
//
//        // Intent to start the main Activity
//        Intent notificationIntent = MainActivity.makeNotificationIntent(
//                getApplicationContext(), msg
//        );
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(notificationIntent);
//        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//        // Creating and sending Notification
//        NotificationManager notificatioMng =
//                (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
//        notificatioMng.notify(
//                GEOFENCE_NOTIFICATION_ID,
//                createNotification(msg, notificationPendingIntent));
//
//    }
//
//    // Create notification
//    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder
//                .setSmallIcon(R.drawable.logo_sarah)
//                .setColor(Color.RED)
//                .setContentTitle(msg)
//                .setContentText("Geofence Notification!")
//                .setContentIntent(notificationPendingIntent)
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
//                .setAutoCancel(true);
//        return notificationBuilder.build();
//    }


    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }
}