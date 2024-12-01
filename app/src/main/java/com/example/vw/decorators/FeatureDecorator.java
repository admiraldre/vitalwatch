package com.example.vw.decorators;

/**
 * Abstract class that serves as a base for decorating features.
 * Extends the {@link Feature} interface to allow additional functionalities
 * to be added to an existing feature dynamically.
 */
public abstract class FeatureDecorator implements Feature {
    /**
     * The feature being decorated.
     */
    protected Feature feature;

    /**
     * Constructor to initialize the decorator with the base feature.
     *
     * @param feature the base feature to be decorated
     */
    public FeatureDecorator(Feature feature) {
        this.feature = feature;
    }

    /**
     * Delegates the display functionality to the decorated feature.
     * Subclasses can override this method to add extra behavior.
     */
    @Override
    public void displayFeature() {
        feature.displayFeature();
    }
}
