package com.example.vw.decorators;

/**
 * Represents a feature that can be displayed in the application.
 * This interface is used as a base for implementing various features.
 */
public interface Feature {
    /**
     * Displays the specific feature.
     * Implementing classes should define the behavior for how the feature is displayed.
     */
    void displayFeature();
}
