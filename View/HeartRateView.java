package View;
import java.util.*;
import Model.HeartRateModel;

public class HeartRateView implements Observer {
    public void update(Observable o, Object arg) {
        if (o instanceof HeartRateModel) {
            System.out.println("Updated Heart Rate: " + ((HeartRateModel) o).getHeartRate() + " bpm");
        }
    }
}