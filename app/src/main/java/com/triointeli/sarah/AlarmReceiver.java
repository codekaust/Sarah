package com.triointeli.sarah;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by ritik on 07-02-2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager alarmNotificationManager;
    public static Ringtone ringtone;
    public static CountDownTimer countDownTimer;
    public static Vibrator v;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent myIntent = new Intent(AlarmReceiver.this, MainActivity.class);
        Log.i("point AR61", "entered receiver");
        v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        v.vibrate(40000);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                context).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("ring"))
                .setContentText("yeah");
        Notification notification = alamNotificationBuilder.build();
        notification.flags = Notification.FLAG_INSISTENT;
//
    }
}