package com.example.vw.mockdata;

import com.example.vw.models.ActivityLog;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for generating mock activity logs for testing purposes.
 */
public class MockActivityLogGenerator {

    private static final Random random = new Random();

    // Modify this method to accept FirebaseFirestore as an argument
    public static void generateMockActivityLogs(FirebaseFirestore db) {
        List<ActivityLog> activityLogs = new ArrayList<>();

        // Generate mock activity logs for 4 patients
        for (int i = 0; i < 4; i++) {
            String activityType = getRandomActivityType();
            int duration = random.nextInt(60) + 20; // Random duration between 20 and 80 minutes
            String date = "2024-11-" + (random.nextInt(30) + 1); // Random date in November 2024

            activityLogs.add(new ActivityLog(activityType, duration, date));
        }

        // Assume we're adding these activity logs for a patient with ID "patient1"
        for (int i = 0; i < activityLogs.size(); i++) {
            db.collection("patients")
                    .document("patient" + (i + 1))  // Use dynamic patient IDs (e.g., patient1, patient2)
                    .collection("activityLogs")
                    .add(activityLogs.get(i))  // Push each activity log to Firestore
                    .addOnSuccessListener(documentReference -> {
                        // Successfully added activity logs
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                    });
        }
    }

    private static String getRandomActivityType() {
        String[] activities = {"Running", "Cycling", "Swimming", "Walking", "Hiking"};
        return activities[random.nextInt(activities.length)];
    }
}