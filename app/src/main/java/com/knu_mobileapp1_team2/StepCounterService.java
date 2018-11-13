package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class StepCounterService extends Service implements SensorEventListener {
    private static final String NOTI_CHANNEL_ID = "com.knu_mobileapp1_team2.notificationchannel";

    BroadcastReceiver receiver;

    NotificationCompat.Builder nb;
    Notification n;

    Sensor stepCounter = null;
    int currentStep = 0;

    public StepCounterService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sp = getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);
        if (!sp.getBoolean("app_enabled", false)) {
            // service is not supposed to run
            stopSelf();
            return;
        }

        // we can safely assume that the screen is turned on when service is starting up
        registerSensor();

        // notification must be shown on foreground service
        updateNotification();

        // register service to receive screen on/off broadcast
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // screen on or off triggered
                intent.getAction();
            }
        };
        registerReceiver(receiver, filter);
    }

    private void registerSensor() {
        if (stepCounter != null) return;
        SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepCounter = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepCounter != null) sm.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL, 5000000);     // batch for 5sec.
    }

    private void unregisterSensor() {
        if (stepCounter == null) return;
        SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sm.unregisterListener(this);
    }

    private void updateNotification() {
        Intent ni = new Intent(this, SplashActivity.class);
        PendingIntent npi = PendingIntent.getActivity(this, 1, ni, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTI_CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_LOW);
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);
        }

        nb = new NotificationCompat.Builder(this, NOTI_CHANNEL_ID);
        nb.setContentTitle(getString(R.string.service_noti_title))
          .setContentText(String.format(getString(R.string.service_noti_content), currentStep))
          .setContentIntent(npi)
          .setSmallIcon(R.drawable.welcome_seed)
          .setNumber(0)
          .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE);

        n = nb.build();

        startForeground(1, n);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterSensor();
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // this service does not need to be bound
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO: implement proper currentStep update method
        currentStep = (int)event.values[0];
        updateNotification();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
