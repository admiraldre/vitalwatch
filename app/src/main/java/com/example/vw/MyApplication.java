package com.example.vw;

import android.app.Application;

import com.example.vw.mockdata.MockDataGenerator;
import com.example.vw.mockdata.MockActivityLogGenerator;
import com.example.vw.mockdata.MockEmergencyLogGenerator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Custom Application class for the app.
 * Used for application-wide initialization.
 */
public class MyApplication extends Application {

    /**
     * Called when the application is starting, before any activity, service, or receiver has been created.
     * This is where application-wide configurations or initializations should be done.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Generate and push mock data
        MockDataGenerator.generateMockData(db);
        MockActivityLogGenerator.generateMockActivityLogs(db);
        MockEmergencyLogGenerator.generateMockEmergencyLogs(db);
    }
}