package com.example.vw.mockdata;

import com.example.vw.models.HealthStats;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Class for generating hardcoded mock data for testing purposes.
 */
public class MockDataGenerator {

    /**
     * Generates hardcoded patient data and writes it to Firestore.
     *
     * @param db Firestore instance to write mock data.
     */
    public static void generateMockData(FirebaseFirestore db) {
        // List of hardcoded patients
        HealthStats[] hardcodedPatients = {
                new HealthStats("id1", "John Doe", "Male", "35", "75", "60", "68", "140/90", "120/80", "130/85", "180", "70"),
                new HealthStats("id2", "Jane Smith", "Female", "29", "85", "65", "75", "150/95", "130/85", "140/90", "165", "60"),
                new HealthStats("id3", "Michael Johnson", "Male", "40", "90", "70", "80", "145/90", "125/85", "135/88", "170", "75"),
                new HealthStats("id4", "Emily Davis", "Female", "25", "78", "62", "70", "138/88", "118/78", "128/83", "160", "55")
        };

        // Push each patient to Firestore
        for (HealthStats patient : hardcodedPatients) {
            db.collection("patients")
                    .document(patient.getId()) // Use the patient ID as the document ID
                    .set(patient)
                    .addOnSuccessListener(aVoid -> {
                        System.out.println("Mock data added for patient: " + patient.getName());
                    })
                    .addOnFailureListener(e -> {
                        System.err.println("Failed to add mock data for " + patient.getName() + ": " + e.getMessage());
                    });
        }
    }
}