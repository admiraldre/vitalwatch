package com.example.vw.decorators;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vw.AlarmReceiver;
import com.example.vw.HomeActivity;
import com.example.vw.R;

import java.util.Calendar;

public class AlarmsFeature extends FeatureDecorator {
    private final Context context;
    private Calendar alarmTime;

    public AlarmsFeature(Feature homeFeature, Calendar alarmTime, Context context) {
        super(homeFeature);
        this.context = context;
        this.alarmTime = alarmTime;
    }

    @Override
    public void displayFeature() {
        super.displayFeature();

        if (alarmTime != null) {
            // Display the alarm in the HomeActivity UI
            LinearLayout additionalFeaturesContainer = ((LinearLayout) ((HomeActivity) context).findViewById(R.id.additionalFeaturesContainer));

            TextView alarmView = new TextView(context);
            alarmView.setText("Alarm set for: " + alarmTime.getTime());
            alarmView.setPadding(10, 10, 10, 10);
            alarmView.setTextSize(16);
            additionalFeaturesContainer.addView(alarmView);

            // Set up the actual alarm
            setupAlarm();
        } else {
            Toast.makeText(context, "No alarm set.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupAlarm() {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
            Toast.makeText(context, "Alarm set for " + alarmTime.getTime(), Toast.LENGTH_SHORT).show();
        }
    }


    public void setAlarmTime(Calendar alarmTime) {
        this.alarmTime = alarmTime;
    }
}
