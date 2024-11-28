package com.example.vw;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that displays detailed health information of a patient.
 * The data is passed from the PatientDetailsActivity and displayed in TextViews.
 */
public class PatientHealthInfoActivity extends AppCompatActivity {

    private TextView nameTextView, ageTextView, fitnessGoalTextView;

    /**
     * Called when the activity is starting. Initializes the UI components (TextViews),
     * retrieves the patient details passed from PatientDetailsActivity, and displays
     * the patient's health information.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_health_info);

        // Initialize the TextViews for displaying patient data
        nameTextView = findViewById(R.id.nameTextView);
        ageTextView = findViewById(R.id.ageTextView);
        fitnessGoalTextView = findViewById(R.id.fitnessGoalTextView);

        // Retrieve patient data passed from the previous activity
        String patientName = getIntent().getStringExtra("PATIENT_NAME");
        String patientAge = getIntent().getStringExtra("PATIENT_AGE");
        String fitnessGoal = getIntent().getStringExtra("PATIENT_FITNESS_GOAL");

        // Display the patient data in the corresponding TextViews
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