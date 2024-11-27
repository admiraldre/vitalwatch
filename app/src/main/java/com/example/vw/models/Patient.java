package com.example.vw.models;

public class Patient {
    private String id;
    private String name;
    private String email;
    private String age;
    private String fitnessGoal;

    // Default constructor for Firestore
    public Patient() {}

    public Patient(String id, String name, String email, String age, String fitnessGoal) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.fitnessGoal = fitnessGoal;
    }

    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }
}