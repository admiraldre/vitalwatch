import java.util.ArrayList;
import java.util.List;

interface HealthDataObserver {
    void update(String data);
}

class HealthDataSubject {
    private List<HealthDataObserver> observers = new ArrayList<>();
    private String healthData;

    public void registerObserver(HealthDataObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(HealthDataObserver observer) {
        observers.remove(observer);
    }

    public void setHealthData(String healthData) {
        this.healthData = healthData;
        notifyObservers();
    }

    private void notifyObservers() {
        for (HealthDataObserver observer : observers) {
            observer.update(healthData);
        }
    }
}
