package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.adapters.PatientDetailsAdapter;
import com.example.vw.models.Patient;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying the details of a specific patient.
 * Provides options for viewing the patient's health information or navigating to the home activity.
 */
public class PatientDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientDetailsAdapter adapter;
    private FirebaseFirestore db;
    private String patientId;

    /**
     * Called when the activity is starting. Sets up the RecyclerView and Firestore
     * listener to fetch and display patient details. It also handles item click events
     * for navigation to other activities.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerViewPatientDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientDetailsAdapter(new PatientDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String option) {
                // Handle click events for the selected option
                if (option.equals("Patient Health Info")) {
                    // Navigate to PatientHealthInfoActivity with patient details
                    Intent intent = new Intent(PatientDetailsActivity.this, PatientHealthInfoActivity.class);
                    intent.putExtra("PATIENT_ID", patientId); // Pass patient ID
                    intent.putExtra("PATIENT_NAME", adapter.getPatientName()); // Pass patient name
                    intent.putExtra("PATIENT_AGE", adapter.getPatientAge()); // Pass patient age
                    intent.putExtra("PATIENT_FITNESS_GOAL", adapter.getPatientFitnessGoal()); // Pass fitness goal
                    startActivity(intent);
                } else if (option.equals("View Home Activity")) {
                    // Navigate to HomeActivity with patient ID
                    Intent intent = new Intent(PatientDetailsActivity.this, HomeActivity.class);
                    intent.putExtra("PATIENT_ID", patientId); // Pass patient ID
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        // Initialize Firestore and retrieve the patient ID from the intent
        db = FirebaseFirestore.getInstance();
        patientId = getIntent().getStringExtra("PATIENT_ID");

        // Fetch the patient's details from Firestore
        fetchPatientDetails();
    }

    /**
     * Fetches the details of the patient from Firestore based on the patient ID.
     * If the data is successfully fetched, it updates the RecyclerView adapter
     * with options for the admin to interact with.
     */
    private void fetchPatientDetails() {
        DocumentReference patientRef = db.collection("patients").document(patientId);
        patientRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Deserialize the document into a Patient object
                    Patient patient = document.toObject(Patient.class);
                    List<String> options = new ArrayList<>();

                    // Add available options for the admin to select
                    options.add("Patient Health Info");
                    options.add("View Home Activity");

                    // Set patient details for use in the adapter
                    if (patient != null) {
                        adapter.setPatientDetails(patient.getName(), patient.getAge(), patient.getFitnessGoal());
                    }

                    // Update the RecyclerView with the available options
                    adapter.updateOptions(options);
                }
            } else {
                // Show error message if data fetch fails
                Toast.makeText(PatientDetailsActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}