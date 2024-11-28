package com.example.vw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * BroadcastReceiver to handle alarm events.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Called when the BroadcastReceiver receives an alarm event.
     *
     * @param context the application context
     * @param intent  the intent triggering the alarm
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // Display a toast message when the alarm is triggered
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_LONG).show();
    }
}
