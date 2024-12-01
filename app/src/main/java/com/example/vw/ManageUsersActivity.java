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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for managing users. Displays a list of patients in a RecyclerView and allows
 * admins to click on a patient to view their details.
 */
public class ManageUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientsAdapter adapter;
    private FirebaseFirestore db;

    /**
     * Called when the activity is starting. Sets up the RecyclerView and Firestore listener
     * to observe changes in the patients collection.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        // Initialize Firestore and RecyclerView
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientsAdapter();
        recyclerView.setAdapter(adapter);

        // Set up the observer to navigate to PatientDetailsActivity on item click
        adapter.setOnItemClickListener(patient -> {
            // Pass patient ID to PatientDetailsActivity to fetch patient details
            Intent intent = new Intent(ManageUsersActivity.this, PatientDetailsActivity.class);
            intent.putExtra("PATIENT_ID", patient.getId());
            startActivity(intent);
        });

        // Attach Firestore listener to observe the patients collection
        observePatientsCollection();
    }

    /**
     * Observes changes in the patients collection in Firestore. Updates the RecyclerView's
     * adapter with the latest list of patients when data changes.
     */
    private void observePatientsCollection() {
        CollectionReference patientsRef = db.collection("patients");

        patientsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                if (error != null) {
                    // Show a Toast message if there is an error loading patient data
                    Toast.makeText(ManageUsersActivity.this, "Error loading patients: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Patient> patientList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    // Convert Firestore document to Patient object
                    Patient patient = doc.toObject(Patient.class);
                    patient.setId(doc.getId()); // Set the patient ID from the document ID
                    patientList.add(patient);
                }

                // Notify the RecyclerView Adapter with the updated list of patients
                adapter.updatePatients(patientList);
            }
        });
    }
}