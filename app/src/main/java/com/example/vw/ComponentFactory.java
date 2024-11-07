// ComponentFactory.java
package com.example.vw;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;

public class ComponentFactory {

    public static Button createButton(Context context, String text, int backgroundColor, int textColor) {
        Button button = new Button(context);
        button.setText(text);
        button.setBackgroundColor(backgroundColor);
        button.setTextColor(textColor);
        button.setTypeface(null, Typeface.BOLD);
        button.setTextSize(16);
        return button;
    }

    public static EditText createTextField(Context context, String hint) {
        EditText editText = new EditText(context);
        editText.setHint(hint);
        editText.setPadding(16, 16, 16, 16);
        editText.setTextColor(Color.BLACK);
        editText.setHintTextColor(Color.GRAY);
        return editText;
    }

    public static EditText createPasswordField(Context context, String hint) {
        EditText passwordField = new EditText(context);
        passwordField.setHint(hint);
        passwordField.setPadding(16, 16, 16, 16);
        passwordField.setTextColor(Color.BLACK);
        passwordField.setHintTextColor(Color.GRAY);
        passwordField.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return passwordField;
    }
}
