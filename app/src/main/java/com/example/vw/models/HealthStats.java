package com.example.vw.models;

/**
 * Model class representing the health statistics of a patient.
 */
public class HealthStats {

    private String id;
    private String name;
    private String gender;
    private String age; // Added age field
    private String maxHeartRate;
    private String minHeartRate;
    private String avgHeartRate;
    private String maxBloodPressure;
    private String minBloodPressure;
    private String avgBloodPressure;
    private String height;
    private String weight;

    /**
     * Default constructor required for Firestore and other serialization frameworks.
     */
    public HealthStats() {}

    /**
     * Constructs a HealthStats object with all details.
     */
    public HealthStats(String id, String name, String gender, String age, String maxHeartRate, String minHeartRate,
                       String avgHeartRate, String maxBloodPressure, String minBloodPressure,
                       String avgBloodPressure, String height, String weight) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age; // Initialize age
        this.maxHeartRate = maxHeartRate;
        this.minHeartRate = minHeartRate;
        this.avgHeartRate = avgHeartRate;
        this.maxBloodPressure = maxBloodPressure;
        this.minBloodPressure = minBloodPressure;
        this.avgBloodPressure = avgBloodPressure;
        this.height = height;
        this.weight = weight;
    }

    // Getter and Setter for all fields (including age)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() { // New getter
        return age;
    }

    public void setAge(String age) { // New setter
        this.age = age;
    }

    public String getMaxHeartRate() {
        return maxHeartRate;
    }

    public void setMaxHeartRate(String maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }

    public String getMinHeartRate() {
        return minHeartRate;
    }

    public void setMinHeartRate(String minHeartRate) {
        this.minHeartRate = minHeartRate;
    }

    public String getAvgHeartRate() {
        return avgHeartRate;
    }

    public void setAvgHeartRate(String avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }

    public String getMaxBloodPressure() {
        return maxBloodPressure;
    }

    public void setMaxBloodPressure(String maxBloodPressure) {
        this.maxBloodPressure = maxBloodPressure;
    }

    public String getMinBloodPressure() {
        return minBloodPressure;
    }

    public void setMinBloodPressure(String minBloodPressure) {
        this.minBloodPressure = minBloodPressure;
    }

    public String getAvgBloodPressure() {
        return avgBloodPressure;
    }

    public void setAvgBloodPressure(String avgBloodPressure) {
        this.avgBloodPressure = avgBloodPressure;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}