package com.example.vw;

import android.app.Application;
import com.google.firebase.FirebaseApp;

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
    }
}
