package com.example.vw;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeActivity extends AppCompatActivity {

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;

    private TextView stepsTextView;
    private TextView heartRateTextView;
    private TextView bloodOxygenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome to VitalWatch Home!");

        stepsTextView = findViewById(R.id.stepsTextView);
        heartRateTextView = findViewById(R.id.heartRateTextView);
        bloodOxygenTextView = findViewById(R.id.bloodOxygenTextView);

        // Initialize Google Fit options
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getAccountForExtension(this, fitnessOptions), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getAccountForExtension(this, fitnessOptions),
                    fitnessOptions);
        } else {
            accessGoogleFitData();
        }
    }

    private void accessGoogleFitData() {
        // Retrieve daily steps count
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataSet -> {
                    int totalSteps = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    stepsTextView.setText("Steps: " + totalSteps);
                })
                .addOnFailureListener(e -> {
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
                    Toast.makeText(this, "Failed to retrieve heart rate", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                accessGoogleFitData();
            } else {
                Toast.makeText(this, "Permission denied to access Google Fit data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
