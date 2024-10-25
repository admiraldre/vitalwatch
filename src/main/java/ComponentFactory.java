import javax.swing.*;

public class ComponentFactory {
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        // Customize the button as necessary
        return button;
    }

    public static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        // Customize the text field as necessary
        return textField;
    }

    public static JPasswordField createPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        return passwordField;
    }
}
