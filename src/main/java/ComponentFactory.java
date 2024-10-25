import javax.swing.*;
import java.awt.*;

public class ComponentFactory {

    public static JButton createButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    public static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        return textField;
    }

    public static JPasswordField createPasswordField(int columns) {
        JPasswordField passwordField = new JPasswordField(columns);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        return passwordField;
    }
}
