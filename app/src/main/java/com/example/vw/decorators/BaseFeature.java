package com.example.vw.decorators;

import android.content.Context;
import android.widget.Toast;

public class BaseFeature implements Feature {
    private final Context context;

    public BaseFeature(Context context) {
        this.context = context;
    }

    @Override
    public void displayFeature() {
        Toast.makeText(context, "Base Home Feature: Heart Rate and Steps", Toast.LENGTH_SHORT).show();
    }
}
