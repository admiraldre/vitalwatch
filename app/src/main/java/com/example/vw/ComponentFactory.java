// ComponentFactory.java
package com.example.vw;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;

/**
 * A factory class for creating UI components such as buttons and text fields.
 */
public class ComponentFactory {

    /**
     * Creates a button with specified properties.
     *
     * @param context         the context in which the button is created
     * @param text            the text to display on the button
     * @param backgroundColor the background color of the button
     * @param textColor       the text color of the button
     * @return the created button
     */
    public static Button createButton(Context context, String text, int backgroundColor, int textColor) {
        Button button = new Button(context);
        button.setText(text);
        button.setBackgroundColor(backgroundColor);
        button.setTextColor(textColor);
        button.setTypeface(null, Typeface.BOLD);
        button.setTextSize(16);
        return button;
    }

    /**
     * Creates a text field with a specified hint.
     *
     * @param context the context in which the text field is created
     * @param hint    the hint text to display in the text field
     * @return the created text field
     */
    public static EditText createTextField(Context context, String hint) {
        EditText editText = new EditText(context);
        editText.setHint(hint);
        editText.setPadding(16, 16, 16, 16);
        editText.setTextColor(Color.BLACK);
        editText.setHintTextColor(Color.GRAY);
        return editText;
    }

    /**
     * Creates a password field with a specified hint.
     *
     * @param context the context in which the password field is created
     * @param hint    the hint text to display in the password field
     * @return the created password field
     */
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
