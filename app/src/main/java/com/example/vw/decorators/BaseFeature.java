package com.example.vw.decorators;

import android.content.Context;
import android.widget.Toast;

/**
 * The base feature implementation for the application, providing default functionality.
 */
public class BaseFeature implements Feature {
    private final Context context;

    /**
     * Constructs a BaseFeature instance.
     *
     * @param context The context used for displaying the feature's functionality.
     */
    public BaseFeature(Context context) {
        this.context = context;
    }

    /**
     * Displays the base feature by showing a toast message with default functionality.
     */
    @Override
    public void displayFeature() {
        Toast.makeText(context, "Base Home Feature: Heart Rate and Steps", Toast.LENGTH_SHORT).show();
    }
}
