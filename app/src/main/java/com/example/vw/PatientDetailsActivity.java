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

public class PatientDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientDetailsAdapter adapter;
    private FirebaseFirestore db;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        recyclerView = findViewById(R.id.recyclerViewPatientDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientDetailsAdapter(new PatientDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String option) {
                // Handle the item click based on the option selected
                if (option.equals("Patient Health Info")) {
                    // Pass patient data to the PatientHealthInfoActivity
                    Intent intent = new Intent(PatientDetailsActivity.this, PatientHealthInfoActivity.class);
                    intent.putExtra("PATIENT_ID", patientId);  // Pass the patientId

                    // Pass the actual patient data to display (fitness details)
                    intent.putExtra("PATIENT_NAME", adapter.getPatientName());
                    intent.putExtra("PATIENT_AGE", adapter.getPatientAge());
                    intent.putExtra("PATIENT_FITNESS_GOAL", adapter.getPatientFitnessGoal());

                    startActivity(intent);
                } else if (option.equals("View Home Activity")) {
                    // Navigate to the Home Activity
                    Intent intent = new Intent(PatientDetailsActivity.this, HomeActivity.class);
                    intent.putExtra("PATIENT_ID", patientId);  // Pass the patientId
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        patientId = getIntent().getStringExtra("PATIENT_ID");

        // Fetch the patient's data from Firestore
        fetchPatientDetails();
    }

    private void fetchPatientDetails() {
        DocumentReference patientRef = db.collection("patients").document(patientId);
        patientRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Deserialize the document to a Patient object
                    Patient patient = document.toObject(Patient.class);
                    List<String> options = new ArrayList<>();

                    // Add clickable options for the admin
                    options.add("Patient Health Info");
                    options.add("View Home Activity");

                    // Pass the patient details to the adapter for easy access
                    if (patient != null) {
                        adapter.setPatientDetails(patient.getName(), patient.getAge(), patient.getFitnessGoal());
                    }

                    // Update the RecyclerView adapter with the list of options
                    adapter.updateOptions(options);
                }
            } else {
                Toast.makeText(PatientDetailsActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}