package com.example.vw;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseConnection {

    private static FirebaseConnection instance;
    private FirebaseFirestore db;

    // Private constructor to enforce singleton pattern
    private FirebaseConnection(Context context) {
        // Initialize Firebase App only once
        FirebaseApp.initializeApp(context);
        System.out.println("Firebase successfully initialized.");

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    // Singleton instance getter with Context parameter
    public static FirebaseConnection getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseConnection(context);
        }
        return instance;
    }

    // Getter for FirebaseFirestore
    public FirebaseFirestore getFirestore() {
        return db;
    }
}
