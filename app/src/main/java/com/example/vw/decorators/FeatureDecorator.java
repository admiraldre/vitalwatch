package com.example.vw.decorators;

public abstract class FeatureDecorator implements Feature {
    protected Feature feature;

    public FeatureDecorator(Feature feature) {
        this.feature = feature;
    }

    @Override
    public void displayFeature() {
        feature.displayFeature();
    }
}
