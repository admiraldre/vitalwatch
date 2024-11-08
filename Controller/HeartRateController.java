package Controller;
import Utils.HealthMetricsSimulator;
public class HeartRateController {
    private HeartRateModel model;
    private HeartRateView view;

    public HeartRateController(HeartRateModel model, HeartRateView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        model.addObserver(view); // Add view as an observer of the model
        HealthMetricsSimulator.startSimulation(model); // Start the simulation
    }
}