package com.example.vw.models;

/**
 * Model class representing a Patient entity.
 */
public class Patient {
    private String id;
    private String name;
    private String email;
    private String age;  // Keep age field, remove fitnessGoal field

    /**
     * Default constructor required for Firestore and other serialization frameworks.
     */
    public Patient() {}

    /**
     * Constructs a Patient object with the given details.
     *
     * @param id          The unique identifier of the patient.
     * @param name        The name of the patient.
     * @param email       The email address of the patient.
     * @param age         The age of the patient.
     */
    public Patient(String id, String name, String email, String age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    /**
     * Gets the unique identifier of the patient.
     *
     * @return The patient's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the patient.
     *
     * @param id The patient's ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the patient.
     *
     * @return The patient's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the patient.
     *
     * @param name The patient's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the patient.
     *
     * @return The patient's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param email The patient's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the age of the patient.
     *
     * @return The patient's age.
     */
    public String getAge() {
        return age;
    }

    /**
     * Sets the age of the patient.
     *
     * @param age The patient's age.
     */
    public void setAge(String age) {
        this.age = age;
    }
}