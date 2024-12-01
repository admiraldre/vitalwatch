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

/**
 * Manages user-specific features such as alarms and reminders.
 */
public class UserFeatureManager {

    private final Context context;
    private Feature homeFeature;

    /**
     * Constructor to initialize the UserFeatureManager.
     *
     * @param context the application context
     */
    public UserFeatureManager(Context context) {
        this.context = context;
        this.homeFeature = new BaseFeature(context);
    }

    /**
     * Loads user settings such as alarms and reminders from an intent.
     *
     * @param intent the intent containing user settings
     */
    public void loadUserSettings(Intent intent) {
        // Load alarms from the intent
        ArrayList<Calendar> alarmTimes = (ArrayList<Calendar>) intent.getSerializableExtra("alarmTimes");
        if (alarmTimes != null) {
            for (Calendar alarmTime : alarmTimes) {
                homeFeature = new AlarmsFeature(homeFeature, alarmTime, context);
            }
        }

        // Load reminders from the intent
        ArrayList<String> reminders = intent.getStringArrayListExtra("reminders");
        if (reminders != null && !reminders.isEmpty()) {
            RemindersFeature reminderFeature = new RemindersFeature(homeFeature, context);
            for (String reminder : reminders) {
                reminderFeature.addReminder(reminder);
            }
            homeFeature = reminderFeature;
        }
    }

    /**
     * Displays user features (e.g., alarms and reminders) in the provided UI container.
     *
     * @param additionalFeaturesContainer the container to display the features
     */
    public void displayFeatures(LinearLayout additionalFeaturesContainer) {
        homeFeature.displayFeature();
        // Optionally, add logic to customize UI representation for features
    }
}
