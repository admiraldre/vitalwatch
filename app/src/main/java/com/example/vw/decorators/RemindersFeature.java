package com.example.vw.decorators;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vw.HomeActivity;
import com.example.vw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A decorator class that adds reminders functionality to the base feature.
 * Extends {@link FeatureDecorator} to enhance the behavior of the feature by adding reminder management.
 */
public class RemindersFeature extends FeatureDecorator {
    private final Context context;
    private final List<String> reminders;

    /**
     * Constructor to initialize the reminders feature.
     *
     * @param homeFeature the base feature to decorate
     * @param context     the application context
     */
    public RemindersFeature(Feature homeFeature, Context context) {
        super(homeFeature);
        this.context = context;
        this.reminders = new ArrayList<>();
    }

    /**
     * Displays the base feature and adds reminders to the UI.
     * Clears any previously displayed reminders and adds new ones.
     */
    @Override
    public void displayFeature() {
        super.displayFeature();

        if (!reminders.isEmpty()) {
            LinearLayout additionalFeaturesContainer = ((LinearLayout) ((HomeActivity) context).findViewById(R.id.additionalFeaturesContainer));

            // Clear previous views before adding new ones
            additionalFeaturesContainer.removeAllViews();

            for (String reminder : reminders) {
                TextView reminderView = new TextView(context);
                reminderView.setText("Reminder: " + reminder);
                additionalFeaturesContainer.addView(reminderView);
            }
        }
    }

    /**
     * Displays reminders as Toast messages if reminders exist; otherwise, shows a "No reminders set" message.
     */
    private void displayReminders() {
        if (reminders.isEmpty()) {
            Toast.makeText(context, "No reminders set.", Toast.LENGTH_SHORT).show();
        } else {
            for (String reminder : reminders) {
                Toast.makeText(context, "Reminder: " + reminder, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Adds a new reminder to the list.
     *
     * @param reminder the reminder text to add
     */
    public void addReminder(String reminder) {
        reminders.add(reminder);
    }

    /**
     * Retrieves the list of reminders.
     *
     * @return a copy of the list of reminders
     */
    public List<String> getReminders() {
        return new ArrayList<>(reminders);
    }
}
