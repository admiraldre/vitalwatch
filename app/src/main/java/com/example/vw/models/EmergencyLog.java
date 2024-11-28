package com.example.vw.models;

/**
 * Model class representing an emergency log for a patient.
 */
public class EmergencyLog {

    private String patientName;
    private String urgencyMessage;
    private String details;

    // No-argument constructor required for Firestore
    public EmergencyLog() {}

    public EmergencyLog(String patientName, String urgencyMessage, String details) {
        this.patientName = patientName;
        this.urgencyMessage = urgencyMessage;
        this.details = details;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getUrgencyMessage() {
        return urgencyMessage;
    }

    public void setUrgencyMessage(String urgencyMessage) {
        this.urgencyMessage = urgencyMessage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}