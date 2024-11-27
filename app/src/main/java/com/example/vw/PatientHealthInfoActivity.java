package com.example.vw;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientHealthInfoActivity extends AppCompatActivity {

    private TextView nameTextView, ageTextView, fitnessGoalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_health_info);

        // Initialize the TextViews
        nameTextView = findViewById(R.id.nameTextView);
        ageTextView = findViewById(R.id.ageTextView);
        fitnessGoalTextView = findViewById(R.id.fitnessGoalTextView);

        // Retrieve data passed from PatientDetailsActivity
        String patientName = getIntent().getStringExtra("PATIENT_NAME");
        String patientAge = getIntent().getStringExtra("PATIENT_AGE");
        String fitnessGoal = getIntent().getStringExtra("PATIENT_FITNESS_GOAL");

        // Display the data in the TextViews
        if (patientName != null) {
            nameTextView.setText("Name: " + patientName);
        }
        if (patientAge != null) {
            ageTextView.setText("Age: " + patientAge);
        }
        if (fitnessGoal != null) {
            fitnessGoalTextView.setText("Fitness Goal: " + fitnessGoal);
        }
    }
}