package com.example.vw;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vw.decorators.BaseFeature;
import com.example.vw.decorators.RemindersFeature;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Activity for managing user settings such as alarms and reminders.
 */
public class SettingsActivity extends AppCompatActivity {

    private Calendar alarmTime;
    private RemindersFeature reminderFeature;

    /**
     * Called when the activity is starting. Sets up the UI and initializes event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the most recent data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button setAlarmButton = findViewById(R.id.setAlarmButton);
        Button addReminderButton = findViewById(R.id.addReminderButton);
        Button saveButton = findViewById(R.id.saveSettingsButton);
        EditText reminderInput = findViewById(R.id.reminderInput);

        // Initialize the reminder feature
        reminderFeature = new RemindersFeature(new BaseFeature(this), this);

        // Set an alarm when the "Set Alarm" button is clicked
        setAlarmButton.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (TimePicker view, int hourOfDay, int minute) -> {
                        alarmTime = Calendar.getInstance();
                        alarmTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        alarmTime.set(Calendar.MINUTE, minute);
                        Toast.makeText(this, "Alarm set for " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                    },
                    8,
                    0,
                    true
            );
            timePickerDialog.show();
        });

        // Add a reminder when the "Add Reminder" button is clicked
        addReminderButton.setOnClickListener(v -> {
            String reminder = reminderInput.getText().toString();
            if (!reminder.isEmpty()) {
                reminderFeature.addReminder(reminder);
                Toast.makeText(this, "Reminder added: " + reminder, Toast.LENGTH_SHORT).show();
                reminderInput.setText("");
            } else {
                Toast.makeText(this, "Please enter a reminder.", Toast.LENGTH_SHORT).show();
            }
        });

        // Save settings and navigate back to the HomeActivity
        saveButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("alarmTime", alarmTime);
            intent.putExtra("reminders", (ArrayList<String>) reminderFeature.getReminders());
            startActivity(intent);
            finish();
        });
    }
}
