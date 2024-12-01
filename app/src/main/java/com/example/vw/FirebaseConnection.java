package com.example.vw;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A singleton class for managing Firebase Firestore connection.
 */
public class FirebaseConnection {

    private static FirebaseConnection instance;
    private FirebaseFirestore db;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param context the application context used to initialize Firebase
     */
    private FirebaseConnection(Context context) {
        // Initialize Firebase App only once
        FirebaseApp.initializeApp(context);
        System.out.println("Firebase successfully initialized.");

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Retrieves the singleton instance of FirebaseConnection.
     * Initializes the instance if it has not been created yet.
     *
     * @param context the application context used to initialize Firebase if needed
     * @return the singleton instance of FirebaseConnection
     */
    public static FirebaseConnection getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseConnection(context);
        }
        return instance;
    }

    /**
     * Retrieves the Firestore database instance.
     *
     * @return the Firestore database instance
     */
    public FirebaseFirestore getFirestore() {
        return db;
    }
}
