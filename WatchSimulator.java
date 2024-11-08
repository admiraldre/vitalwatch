import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class HeartRateSimulator {

    // Method to simulate heart rate generation
    private static int generateHeartRate() {
        Random random = new Random();
        // Simulate a random heart rate between 60 and 100 bpm
        return random.nextInt(41) + 60; // Random between 60 and 100
    }

    public static void main(String[] args) {
        // Create a Timer object to schedule tasks
        Timer timer = new Timer();

        // Create a TimerTask that will execute every 10 seconds
        TimerTask heartRateTask = new TimerTask() {
            @Override
            public void run() {
                // Generate and print the heart rate every 10 seconds
                int heartRate = generateHeartRate();
                System.out.println("Heart Rate: " + heartRate + " BPM");
            }
        };

        // Schedule the heart rate task to run every 10 seconds (10,000 milliseconds)
        timer.scheduleAtFixedRate(heartRateTask, 0, 5000); // Initial delay 0ms, period 10000ms
    }
}