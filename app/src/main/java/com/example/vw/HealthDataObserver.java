package com.example.vw;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface representing an observer in the Observer design pattern.
 * Observers are notified when the subject's state changes.
 */
interface HealthDataObserver {
    /**
     * Called when the subject's state changes.
     *
     * @param data The updated health data.
     */
    void update(String data);
}

/**
 * Subject class in the Observer design pattern.
 * Maintains a list of observers and notifies them of any changes to its state.
 */
class HealthDataSubject {
    private final List<HealthDataObserver> observers = new ArrayList<>();
    private String healthData;

    /**
     * Registers a new observer to be notified of state changes.
     *
     * @param observer The observer to be added.
     */
    public void registerObserver(HealthDataObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer so it no longer receives notifications.
     *
     * @param observer The observer to be removed.
     */
    public void removeObserver(HealthDataObserver observer) {
        observers.remove(observer);
    }

    /**
     * Updates the health data and notifies all registered observers.
     *
     * @param healthData The new health data.
     */
    public void setHealthData(String healthData) {
        this.healthData = healthData;
        notifyObservers();
    }

    /**
     * Notifies all registered observers of the current state.
     */
    private void notifyObservers() {
        for (HealthDataObserver observer : observers) {
            observer.update(healthData);
        }
    }
}
