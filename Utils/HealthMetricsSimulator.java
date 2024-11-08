package Utils;

import Model.HeartRateModel;
import java.util.Random;
public class HealthMetricsSimulator {
    private static final Random random = new Random();

    public static int generateHeartRate() {
        return 60 + random.nextInt(40); // Random heart rate between 60 and 100 bpm
    }

    public static void startSimulation(HeartRateModel model) {
        while (true) {
            int newHeartRate = generateHeartRate();
            model.updateHeartRate(newHeartRate);
            try {
                Thread.sleep(5000); // Wait for 10 seconds before updating again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}