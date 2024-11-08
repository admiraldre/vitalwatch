package Model;
import java.util.*;

public class HeartRateModel extends Observable {
    private int heartRate;

    public void updateHeartRate(int heartRate) {
        this.heartRate = heartRate;
        setChanged();
        notifyObservers(); // Notify observers when the heart rate updates
    }

    public int getHeartRate() {
        return heartRate;
    }
}