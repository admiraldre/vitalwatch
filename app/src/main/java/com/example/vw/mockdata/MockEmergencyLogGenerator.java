package com.example.vw.mockdata;

import com.example.vw.models.EmergencyLog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

/**
 * Class for generating mock emergency logs.
 */
public class MockEmergencyLogGenerator {

    public static void generateMockEmergencyLogs(FirebaseFirestore db) {
        List<EmergencyLog> emergencyLogs = Arrays.asList(
                new EmergencyLog("John Doe", "URGENT: Abnormal Heart Rate Activity",
                        "Details: Max heart rate of 200 bpm, Min heart rate of 40 bpm"),
                new EmergencyLog("Jane Smith", "URGENT: Irregular Blood Pressure Activity",
                        "Details: Max BP of 190/120, Min BP of 80/50")
        );

        for (int i = 0; i < emergencyLogs.size(); i++) {
            db.collection("patients")
                    .document("id" + (i + 1)) // Assuming patient IDs are id1, id2
                    .collection("emergencyLogs")
                    .add(emergencyLogs.get(i));
        }
    }
}