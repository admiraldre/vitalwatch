package com.example.vw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.DataPoint;

import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 2;

    private TextView stepsTextView;
    private TextView heartRateTextView;

    private PieChart stepsPieChart;
    private LineChart heartRateLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome to VitalWatch Home!");

        stepsTextView = findViewById(R.id.stepsTextView);
        heartRateTextView = findViewById(R.id.heartRateTextView);

        stepsPieChart = findViewById(R.id.stepsPieChart);
        heartRateLineChart = findViewById(R.id.heartRateLineChart);

        // Check and request ACTIVITY_RECOGNITION permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        } else {
            initializeGoogleFit();
        }
    }

    private void initializeGoogleFit() {
        // Configure Google Fit options with necessary permissions
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                .build();

        // Check for permissions and request if not already granted
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getAccountForExtension(this, fitnessOptions), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getAccountForExtension(this, fitnessOptions),
                    fitnessOptions);
        } else {
            // Permissions already granted, proceed to access Google Fit data
            accessGoogleFitData();
        }
    }

    // Function to access Google Fit data
    private void accessGoogleFitData() {
        // Retrieve daily step count
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataSet -> {
                    int totalSteps = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    stepsTextView.setText("Steps: " + totalSteps);
                    int stepsGoal = 6000;
                    updateStepsPieChart(totalSteps, stepsGoal);
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Failed to retrieve steps", e);
                    Toast.makeText(this, "Failed to retrieve steps", Toast.LENGTH_SHORT).show();
                });

        // Retrieve current heart rate (latest reading) and heart rate trend over the day
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
                        latestHeartRate = heartRate; // Keep updating to get the latest
                    }

                    // Update the UI with the latest heart rate reading
                    if (latestHeartRate != -1) {
                        heartRateTextView.setText("Heart Rate: " + latestHeartRate + " bpm");
                    } else {
                        heartRateTextView.setText("Heart Rate: N/A");
                    }

                    // Update the heart rate trend line chart
                    updateHeartRateLineChart(heartRateEntries);
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Failed to retrieve heart rate data", e);
                    Toast.makeText(this, "Failed to retrieve heart rate data", Toast.LENGTH_SHORT).show();
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
        stepsPieChart.invalidate(); // Refresh the chart
    }

    private void updateHeartRateLineChart(List<Entry> heartRateEntries) {
        LineDataSet dataSet = new LineDataSet(heartRateEntries, "Heart Rate Throughout the Day");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        heartRateLineChart.setData(lineData);
        heartRateLineChart.invalidate(); // Refresh the chart
    }

    // Handle the result of the Google Fit permission request
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                accessGoogleFitData(); // Permissions granted, access data
            } else {
                Log.e("GoogleFit", "User denied Google Fit permissions");
                Toast.makeText(this, "Permission denied to access Google Fit data", Toast.LENGTH_SHORT).show();
            }
        }
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
