package com.example.vw.decorators;

public class SleepTrackingFeature extends FeatureDecorator {

    public SleepTrackingFeature(Feature feature) {
        super(feature);
    }

    @Override
    public void displayFeature() {
        super.displayFeature();
        System.out.println("Sleep Tracking Feature enabled.");
    }
}
