package com.example.vw.decorators;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vw.HomeActivity;
import com.example.vw.R;

import java.util.ArrayList;
import java.util.List;

public class RemindersFeature extends FeatureDecorator {
    private final Context context;
    private final List<String> reminders;

    public RemindersFeature(Feature homeFeature, Context context) {
        super(homeFeature);
        this.context = context;
        this.reminders = new ArrayList<>();
    }

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

    private void displayReminders() {
        if (reminders.isEmpty()) {
            Toast.makeText(context, "No reminders set.", Toast.LENGTH_SHORT).show();
        } else {
            for (String reminder : reminders) {
                Toast.makeText(context, "Reminder: " + reminder, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addReminder(String reminder) {
        reminders.add(reminder);
    }

    // New getter for reminders
    public List<String> getReminders() {
        return new ArrayList<>(reminders);
    }

}
