import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VitalWatchApp extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private FirebaseConnection firebase;

    public VitalWatchApp() {
        firebase = FirebaseConnection.getInstance(); // Get the Firebase instance

        // Setting up the JFrame
        setTitle("VitalWatch - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Create UI Components using Factory
        emailField = ComponentFactory.createTextField(20);
        passwordField = ComponentFactory.createPasswordField(20);
        JButton loginButton = ComponentFactory.createButton("Login");
        JButton signUpButton = ComponentFactory.createButton("Sign Up");

        // Adding Components to Frame
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(signUpButton);

        // Login Button ActionListener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Firebase login logic
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                firebaseLogin(email, password);
            }
        });

        // Sign Up Button ActionListener
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Firebase sign up logic
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                firebaseSignUp(email, password);
            }
        });

        setVisible(true);
    }

    // Simulate Firebase Login
    private void firebaseLogin(String email, String password) {
        // Firebase login code would go here
        JOptionPane.showMessageDialog(this, "Logged in with Firebase!");
    }

    // Simulate Firebase Sign Up
    private void firebaseSignUp(String email, String password) {
        // Firebase sign up code would go here
        JOptionPane.showMessageDialog(this, "Signed up with Firebase!");
    }

    public static void main(String[] args) {
        new VitalWatchApp();
    }
}
