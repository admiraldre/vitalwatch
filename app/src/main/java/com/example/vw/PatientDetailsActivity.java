package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.adapters.PatientDetailsAdapter;
import com.example.vw.models.Patient;
import com.example.vw.models.ActivityLog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
                    startActivity(intent);
                } else if (option.equals("View Activity Summary")) {
                    // Navigate to ActivitySummaryActivity with patient ID
                    Intent intent = new Intent(PatientDetailsActivity.this, ActivitySummaryActivity.class);
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
                    options.add("View Activity Summary");

                    // Set patient details for use in the adapter
                    if (patient != null) {
                        adapter.setPatientDetails(patient.getName(), patient.getAge());
                    }

                    // Fetch and add activity log history for the patient
                    fetchActivityLogs(patient);

                    // Update the RecyclerView with the available options
                    adapter.updateOptions(options);
                }
            } else {
                // Show error message if data fetch fails
                Toast.makeText(PatientDetailsActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Fetches activity logs for the patient from Firestore.
     *
     * @param patient The patient whose activity logs are to be fetched.
     */
    private void fetchActivityLogs(Patient patient) {
        db.collection("patients")
                .document(patientId)
                .collection("activityLogs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<ActivityLog> activityLogs = new ArrayList<>();

                        // Parse the Firestore documents into ActivityLog objects
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ActivityLog log = document.toObject(ActivityLog.class);
                            activityLogs.add(log);
                        }

                        // Handle if there are no activity logs
                        if (activityLogs.isEmpty()) {
                            Toast.makeText(PatientDetailsActivity.this, "No activity logs found", Toast.LENGTH_SHORT).show();
                        } else {
                            // You can decide how to present these logs; e.g., showing them in a separate activity
                            // Add logic to pass this data to another activity or update the UI accordingly.
                        }
                    } else {
                        Toast.makeText(PatientDetailsActivity.this, "Failed to fetch activity logs", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}