package com.example.vw.models;

/**
 * Model class representing an activity log for a patient.
 */
public class ActivityLog {

    private String activityType;
    private int duration; // Duration as an integer (e.g., in minutes)
    private String date;

    /**
     * No-argument constructor required for Firestore deserialization.
     */
    public ActivityLog() {
    }

    /**
     * Constructor for ActivityLog.
     *
     * @param activityType Type of activity (e.g., "Running", "Cycling").
     * @param duration Duration of the activity in minutes.
     * @param date Date the activity occurred (in yyyy-MM-dd format).
     */
    public ActivityLog(String activityType, int duration, String date) {
        this.activityType = activityType;
        this.duration = duration;
        this.date = date;
    }

    // Getter and setter methods
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}