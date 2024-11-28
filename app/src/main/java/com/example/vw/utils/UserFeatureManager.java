package com.example.vw.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import com.example.vw.decorators.AlarmsFeature;
import com.example.vw.decorators.BaseFeature;
import com.example.vw.decorators.Feature;
import com.example.vw.decorators.RemindersFeature;

import java.util.ArrayList;
import java.util.Calendar;

public class UserFeatureManager {

    private final Context context;
    private Feature homeFeature;

    public UserFeatureManager(Context context) {
        this.context = context;
        this.homeFeature = new BaseFeature(context);
    }

    public void loadUserSettings(Intent intent) {
        ArrayList<Calendar> alarmTimes = (ArrayList<Calendar>) intent.getSerializableExtra("alarmTimes");
        if (alarmTimes != null) {
            for (Calendar alarmTime : alarmTimes) {
                homeFeature = new AlarmsFeature(homeFeature, alarmTime, context);
            }
        }

        ArrayList<String> reminders = intent.getStringArrayListExtra("reminders");
        if (reminders != null && !reminders.isEmpty()) {
            RemindersFeature reminderFeature = new RemindersFeature(homeFeature, context);
            for (String reminder : reminders) {
                reminderFeature.addReminder(reminder);
            }
            homeFeature = reminderFeature;
        }
    }

    public void displayFeatures(LinearLayout additionalFeaturesContainer) {
        homeFeature.displayFeature();
        // Optionally, add logic to display features in the UI
    }
}
