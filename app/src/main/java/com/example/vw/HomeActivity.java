package com.example.vw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.vw.decorators.AlarmsFeature;
import com.example.vw.decorators.BaseFeature;
import com.example.vw.decorators.Feature;
import com.example.vw.decorators.RemindersFeature;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 2;

    private TextView stepsTextView;
    private TextView heartRateTextView;
    private LinearLayout additionalFeaturesContainer;

    private PieChart stepsPieChart;
    private LineChart heartRateLineChart;

    private Feature homeFeature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        initializeUI();

        // Check and request permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            requestActivityRecognitionPermission();
        } else {
            initializeGoogleFit();
        }

        // Load user settings and display features dynamically
        loadUserSettings();
    }

    private void initializeUI() {
        stepsTextView = findViewById(R.id.stepsTextView);
        heartRateTextView = findViewById(R.id.heartRateTextView);
        stepsPieChart = findViewById(R.id.stepsPieChart);
        heartRateLineChart = findViewById(R.id.heartRateLineChart);
        additionalFeaturesContainer = findViewById(R.id.additionalFeaturesContainer);

        findViewById(R.id.settingsButton).setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.logOutButton).setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void requestActivityRecognitionPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
    }

    private void initializeGoogleFit() {
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                .build();

        GoogleSignIn.requestPermissions(
                this,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                GoogleSignIn.getAccountForExtension(this, fitnessOptions),
                fitnessOptions);
    }

    private void accessGoogleFitData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataSet -> {
                    int totalSteps = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    stepsTextView.setText("Steps: " + totalSteps);
                    updateStepsPieChart(totalSteps, 6000);
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Failed to retrieve steps", e);
                    Toast.makeText(this, "Failed to retrieve steps", Toast.LENGTH_SHORT).show();
                });

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(new com.google.android.gms.fitness.request.DataReadRequest.Builder()
                        .read(DataType.TYPE_HEART_RATE_BPM)
                        .setTimeRange(1, System.currentTimeMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
                        .build())
                .addOnSuccessListener(dataReadResponse -> {
                    List<Entry> heartRateEntries = new ArrayList<>();
                    float latestHeartRate = -1;
                    int index = 0;

                    for (DataPoint dp : dataReadResponse.getDataSet(DataType.TYPE_HEART_RATE_BPM).getDataPoints()) {
                        float heartRate = dp.getValue(Field.FIELD_BPM).asFloat();
                        heartRateEntries.add(new Entry(index++, heartRate));
                        latestHeartRate = heartRate;
                    }

                    heartRateTextView.setText(latestHeartRate != -1 ? "Heart Rate: " + latestHeartRate + " bpm" : "Heart Rate: N/A");
                    updateHeartRateLineChart(heartRateEntries);
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Failed to retrieve heart rate", e);
                    Toast.makeText(this, "Failed to retrieve heart rate", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateStepsPieChart(int steps, int goal) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(steps, "Steps Taken"));
        entries.add(new PieEntry(goal - steps, "Remaining"));

        PieDataSet dataSet = new PieDataSet(entries, "Steps Goal");
        dataSet.setColors(new int[]{R.color.teal_700, R.color.teal_200}, this);

        PieData data = new PieData(dataSet);
        stepsPieChart.setData(data);
        stepsPieChart.invalidate();
    }

    private void updateHeartRateLineChart(List<Entry> heartRateEntries) {
        LineDataSet dataSet = new LineDataSet(heartRateEntries, "Heart Rate");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);

        LineData data = new LineData(dataSet);
        heartRateLineChart.setData(data);
        heartRateLineChart.invalidate();
    }

    private void loadUserSettings() {
        // Base feature
        homeFeature = new BaseFeature(this);

        // Add alarms if enabled
        Intent intent = getIntent();
        Calendar alarmTime = (Calendar) intent.getSerializableExtra("alarmTime");
        if (alarmTime != null) {
            homeFeature = new AlarmsFeature(homeFeature, alarmTime, this);
        }

        // Add reminders if enabled
        ArrayList<String> reminders = intent.getStringArrayListExtra("reminders");
        if (reminders != null && !reminders.isEmpty()) {
            RemindersFeature reminderFeature = new RemindersFeature(homeFeature, this);
            for (String reminder : reminders) {
                reminderFeature.addReminder(reminder);
            }
            homeFeature = reminderFeature;
        }

        // Display the features
        homeFeature.displayFeature();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE && resultCode == RESULT_OK) {
            accessGoogleFitData();
        } else if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            showPermissionDeniedDialog();
        }
    }

    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permissions Required")
                .setMessage("Google Fit permissions are required for the app to function fully.")
                .setPositiveButton("Retry", (dialog, which) -> initializeGoogleFit())
                .setNegativeButton("Cancel", (dialog, which) ->
                        Toast.makeText(this, "Permissions are required for full functionality.", Toast.LENGTH_SHORT).show())
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeGoogleFit();
            } else {
                Toast.makeText(this, "Permission denied for Activity Recognition", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
