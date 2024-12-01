package com.example.vw.models;

/**
 * Model class representing the activities of a patient.
 */
public class Activity {

    private String activityType; // e.g., "Running", "Cycling"
    private String duration; // Duration of the activity in minutes
    private String caloriesBurned; // Calories burned during the activity

    /**
     * Default constructor required for Firestore and other serialization frameworks.
     */
    public Activity() {}

    /**
     * Constructs an Activity object with the given details.
     *
     * @param activityType   The type of activity (e.g., "Running").
     * @param duration       The duration of the activity in minutes.
     * @param caloriesBurned The calories burned during the activity.
     */
    public Activity(String activityType, String duration, String caloriesBurned) {
        this.activityType = activityType;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    // Getter and setter methods for activity fields.

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(String caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
}