import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConnection {

    private static FirebaseConnection instance;
    private Firestore db;
    private GoogleCredentials googleCredentials;

    private FirebaseConnection() {
        try {
            // Retrieve the path to the credentials file from the environment variable
            String firebaseConfigPath = System.getenv("FIREBASE_CONFIG_PATH");  // Use the correct variable name here

            // Check if the environment variable is set and has a valid path
            if (firebaseConfigPath == null || firebaseConfigPath.isEmpty()) {
                throw new IOException("FIREBASE_CONFIG_PATH environment variable is not set or is empty.");
            }

            // Load the Firebase credentials from the file path
            FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);

            // Initialize Firebase using the credentials
            googleCredentials = GoogleCredentials.fromStream(serviceAccount);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .setDatabaseUrl("https://vitalwatch-app.firebaseio.com")  // Use your Firebase Realtime Database or Firestore URL
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase successfully initialized.");

            // Initialize Firestore using the credentials
            db = FirestoreOptions.newBuilder()
                    .setProjectId("vitalwatch-app")  // Replace with your Firebase project ID
                    .setCredentials(googleCredentials)
                    .build()
                    .getService();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Singleton instance getter
    public static FirebaseConnection getInstance() {
        if (instance == null) {
            instance = new FirebaseConnection();
        }
        return instance;
    }

    // Getter for Firestore
    public Firestore getFirestore() {
        return db;
    }
}
