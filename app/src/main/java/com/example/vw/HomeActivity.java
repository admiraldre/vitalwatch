package com.example.vw;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;

public class HomeActivity extends AppCompatActivity {

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;

    private TextView stepsTextView;
    private TextView heartRateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome to VitalWatch Home!");

        stepsTextView = findViewById(R.id.stepsTextView);
        heartRateTextView = findViewById(R.id.heartRateTextView);

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
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Failed to retrieve steps", e);
                    Toast.makeText(this, "Failed to retrieve steps", Toast.LENGTH_SHORT).show();
                });

        // Retrieve last heart rate data
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_HEART_RATE_BPM)
                .addOnSuccessListener(dataSet -> {
                    if (!dataSet.isEmpty()) {
                        float heartRate = dataSet.getDataPoints().get(0).getValue(Field.FIELD_BPM).asFloat();
                        heartRateTextView.setText("Heart Rate: " + heartRate + " bpm");
                    } else {
                        heartRateTextView.setText("Heart Rate: N/A");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleFit", "Failed to retrieve heart rate", e);
                    Toast.makeText(this, "Failed to retrieve heart rate", Toast.LENGTH_SHORT).show();
                });
    }

    // Handle the result of the Google Fit permission request
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                accessGoogleFitData(); // Permissions granted, access data
            } else {
                Toast.makeText(this, "Permission denied to access Google Fit data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
