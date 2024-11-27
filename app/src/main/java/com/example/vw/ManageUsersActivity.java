package com.example.vw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vw.adapters.PatientsAdapter;
import com.example.vw.models.Patient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientsAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientsAdapter();
        recyclerView.setAdapter(adapter);

        // Set up the observer to navigate to HomeActivity on item click
        adapter.setOnItemClickListener(patient -> {
            Intent intent = new Intent(ManageUsersActivity.this, PatientDetailsActivity.class);
            intent.putExtra("PATIENT_ID", patient.getId()); // Pass patient ID to fetch data
            startActivity(intent);
        });

        // Attach Firestore listener
        observePatientsCollection();
    }

    private void observePatientsCollection() {
        CollectionReference patientsRef = db.collection("patients");

        patientsRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(this, "Error loading patients: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            List<Patient> patientList = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                Patient patient = doc.toObject(Patient.class);
                patient.setId(doc.getId());
                patientList.add(patient);
            }

            // Notify the RecyclerView Adapter (Observer)
            adapter.updatePatients(patientList);
        });
    }
}