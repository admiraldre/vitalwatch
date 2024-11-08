public class Main {
    public static void main(String[] args) {
        HeartRateModel model = new HeartRateModel();
        HeartRateView view = new HeartRateView();
        HeartRateController controller = new HeartRateController(model, view);

        controller.start(); // Start the simulation and observer pattern
    }
}