package com.knu_mobileapp1_team2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // This BroadcastReceiver will be called after boot
        Intent si = new Intent(context, StepCounterService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(si);
        } else {
            context.startService(si);
        }
    }
}
