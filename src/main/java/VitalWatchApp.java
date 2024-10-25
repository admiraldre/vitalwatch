import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class VitalWatchApp extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private Firestore db;

    public VitalWatchApp() {
        db = FirebaseConnection.getInstance().getFirestore();  // Get Firestore instance

        // Set up the main frame
        setTitle("VitalWatch - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen
        setLayout(new BorderLayout());

        // Main panel for styling
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Header label
        JLabel headerLabel = new JLabel("Welcome to VitalWatch");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(33, 150, 243));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Form panel for input fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 10));
        formPanel.setOpaque(false);

        // Email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailField = ComponentFactory.createTextField(20);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = ComponentFactory.createPasswordField(20);

        // Add fields to the form panel
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));

        // Login button
        JButton loginButton = ComponentFactory.createButton("Login", new Color(33, 150, 243), Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                firebaseLogin(email, password);
            }
        });

        // Sign Up button
        JButton signUpButton = ComponentFactory.createButton("Sign Up", new Color(76, 175, 80), Color.WHITE);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                firebaseSignUp(email, password);
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Firebase Login Logic
    private void firebaseLogin(String email, String password) {
        try {
            // Query Firestore to find a user with the matching email
            QuerySnapshot querySnapshot = db.collection("users").whereEqualTo("email", email).get().get();

            if (querySnapshot.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No user found with this email.");
                return;
            }

            // Check if password matches (in a real system, hash the password and compare)
            for (QueryDocumentSnapshot document : querySnapshot) {
                String storedPassword = document.getString("password");
                if (storedPassword.equals(password)) {  // In production, compare hashed passwords
                    JOptionPane.showMessageDialog(this, "Logged in successfully!");
                    // Proceed to next step, like opening the main app window
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid password.");
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login failed: " + e.getMessage());
        }
    }

    // Firebase Sign Up (already implemented)
    private void firebaseSignUp(String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);

        try {
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            DocumentReference docRef = db.collection("users").document(userRecord.getUid());
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", email);
            userData.put("password", password);

            docRef.set(userData).get();
            JOptionPane.showMessageDialog(this, "Signed up successfully! UID: " + userRecord.getUid());

            // Redirect to Fitness Info Page
            new FitnessInfoPage(userRecord.getUid());
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sign-up failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VitalWatchApp());
    }
}
