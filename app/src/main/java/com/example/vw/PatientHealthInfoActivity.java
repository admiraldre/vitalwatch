package com.example.vw;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Activity to display detailed health information for a specific patient.
 * Retrieves the patient's data from Firestore and displays it in the UI.
 */
public class PatientHealthInfoActivity extends AppCompatActivity {

    private TextView nameTextView, ageTextView, weightTextView, heightTextView, genderTextView;
    private FirebaseFirestore db;
    private String patientId;  // The ID of the patient or user

    /**
     * Called when the activity is created. Initializes the UI components and Firestore instance,
     * retrieves the patient ID from the Intent, and fetches the health data.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_health_info);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize the TextViews to display the health info
        nameTextView = findViewById(R.id.nameTextView);
        ageTextView = findViewById(R.id.ageTextView);
        weightTextView = findViewById(R.id.weightTextView);
        heightTextView = findViewById(R.id.heightTextView);
        genderTextView = findViewById(R.id.genderTextView);

        // Retrieve the patientId from the Intent
        patientId = getIntent().getStringExtra("PATIENT_ID");

        // Fetch and display the health info from Firestore
        fetchHealthInfo();
    }

    /**
     * Fetches the health information of the patient from Firestore and updates the UI with the data.
     * Displays a Toast message if the data retrieval is unsuccessful or if no data is found.
     */
    private void fetchHealthInfo() {
        // Fetch the patient's fitness data from Firestore
        DocumentReference patientRef = db.collection("patients").document(patientId);
        patientRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Retrieve the patient's health data from the Firestore document
                    String name = document.getString("name");
                    String age = document.getString("age");
                    String weight = document.getString("weight");
                    String height = document.getString("height");
                    String gender = document.getString("gender");

                    // Display the data in the TextViews
                    nameTextView.setText("Name: " + name);
                    ageTextView.setText("Age: " + age);
                    weightTextView.setText("Weight: " + weight);
                    heightTextView.setText("Height: " + height);
                    genderTextView.setText("Gender: " + gender);
                } else {
                    // If no data is found for the patient
                    Toast.makeText(PatientHealthInfoActivity.this, "No health data found", Toast.LENGTH_SHORT).show();
                }
            } else {
                // If data fetch fails
                Toast.makeText(PatientHealthInfoActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}