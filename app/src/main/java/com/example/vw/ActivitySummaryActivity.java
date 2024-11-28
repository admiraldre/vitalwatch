package com.example.vw;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.adapters.ActivityLogAdapter;
import com.example.vw.models.ActivityLog;
import com.example.vw.models.HealthStats;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying the activity summary of a specific patient.
 */
public class ActivitySummaryActivity extends AppCompatActivity {

    private TextView patientNameTextView;
    private TextView heartRateSummaryTextView;
    private TextView bloodPressureSummaryTextView;
    private RecyclerView activityLogRecyclerView;
    private ActivityLogAdapter activityLogAdapter;

    private FirebaseFirestore db;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get the patient ID passed from the previous activity
        patientId = getIntent().getStringExtra("PATIENT_ID");

        // Initialize UI elements
        patientNameTextView = findViewById(R.id.patientNameTextView);
        heartRateSummaryTextView = findViewById(R.id.heartRateSummaryTextView);
        bloodPressureSummaryTextView = findViewById(R.id.bloodPressureSummaryTextView);
        activityLogRecyclerView = findViewById(R.id.activityLogRecyclerView);

        // Set up RecyclerView for activity logs
        activityLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityLogAdapter = new ActivityLogAdapter(new ArrayList<>());
        activityLogRecyclerView.setAdapter(activityLogAdapter);

        // Fetch and display the activity summary
        if (patientId != null) {
            fetchActivitySummary();
            fetchActivityLogs();
        } else {
            Toast.makeText(this, "Patient ID is missing", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Fetches the activity summary of a patient from Firestore using their ID.
     */
    private void fetchActivitySummary() {
        db.collection("patients")
                .document(patientId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Map the Firestore document to the HealthStats model
                        HealthStats healthStats = documentSnapshot.toObject(HealthStats.class);
                        if (healthStats != null) {
                            displayActivitySummary(healthStats);
                        }
                    } else {
                        Toast.makeText(this, "Patient not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Fetches the activity logs of a patient from Firestore using their ID.
     */
    private void fetchActivityLogs() {
        db.collection("patients")
                .document(patientId)
                .collection("activityLogs")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<ActivityLog> activityLogs = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        ActivityLog log = document.toObject(ActivityLog.class);
                        if (log != null) {
                            activityLogs.add(log);
                        }
                    }
                    activityLogAdapter.updateActivityLogs(activityLogs);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching activity logs: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Displays the patient's activity summary on the screen.
     */
    private void displayActivitySummary(HealthStats healthStats) {
        patientNameTextView.setText("Name: " + healthStats.getName());
        heartRateSummaryTextView.setText("Heart Rate (Avg/Min/Max): "
                + healthStats.getAvgHeartRate() + "/" + healthStats.getMinHeartRate() + "/" + healthStats.getMaxHeartRate());
        bloodPressureSummaryTextView.setText("Blood Pressure (Avg/Min/Max): "
                + healthStats.getAvgBloodPressure() + "/" + healthStats.getMinBloodPressure() + "/" + healthStats.getMaxBloodPressure());
    }
}