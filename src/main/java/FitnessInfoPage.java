import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import java.util.concurrent.ExecutionException;

public class FitnessInfoPage extends JFrame {
    private JTextField ageField, weightField, heightField;
    private JComboBox<String> genderBox;
    private Firestore db;
    private String userId;  // The UID of the signed-up user

    public FitnessInfoPage(String userId) {
        this.db = FirebaseConnection.getInstance().getFirestore();  // Get Firestore instance
        this.userId = userId;

        // Set up the main frame
        setTitle("Enter Fitness Information");
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
        JLabel headerLabel = new JLabel("Fitness Information");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(33, 150, 243));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Form panel for input fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);

        // Age
        JLabel ageLabel = new JLabel("Age:");
        ageField = ComponentFactory.createTextField(20);

        // Weight
        JLabel weightLabel = new JLabel("Weight (kg):");
        weightField = ComponentFactory.createTextField(20);

        // Height
        JLabel heightLabel = new JLabel("Height (cm):");
        heightField = ComponentFactory.createTextField(20);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);

        // Add fields to the form panel
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(weightLabel);
        formPanel.add(weightField);
        formPanel.add(heightLabel);
        formPanel.add(heightField);
        formPanel.add(genderLabel);
        formPanel.add(genderBox);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 1, 10, 0));

        // Save button
        JButton saveButton = ComponentFactory.createButton("Save", new Color(33, 150, 243), Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFitnessInfo();
            }
        });

        buttonPanel.add(saveButton);

        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Save the user's fitness information to Firestore
    private void saveFitnessInfo() {
        String age = ageField.getText();
        String weight = weightField.getText();
        String height = heightField.getText();
        String gender = (String) genderBox.getSelectedItem();

        // Prepare data to save in Firestore
        Map<String, Object> fitnessData = new HashMap<>();
        fitnessData.put("age", age);
        fitnessData.put("weight", weight);
        fitnessData.put("height", height);
        fitnessData.put("gender", gender);

        // Store the data in the user's document in Firestore
        DocumentReference userDocRef = db.collection("users").document(userId);
        try {
            userDocRef.update(fitnessData).get();
            JOptionPane.showMessageDialog(this, "Fitness info saved successfully!");
            dispose();  // Close the window
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving fitness info: " + ex.getMessage());
        }
    }
}
