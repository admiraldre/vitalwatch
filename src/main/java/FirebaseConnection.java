import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseConnection {

    private static FirebaseConnection instance;
    private Firestore db;
    private GoogleCredentials googleCredentials;

    private FirebaseConnection() {
        try {
            // Load the credentials once and store them
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("vitalwatch-app-firebase-adminsdk-v32z4-72358b04a3.json");
            if (serviceAccount == null) {
                throw new IOException("Firebase credentials file not found");
            }

            googleCredentials = GoogleCredentials.fromStream(serviceAccount);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .setDatabaseUrl("https://vitalwatch-app.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase successfully initialized.");

            // Initialize Firestore using the stored credentials
            db = FirestoreOptions.newBuilder()
                    .setProjectId("vitalwatch-app")
                    .setCredentials(googleCredentials)
                    .build()
                    .getService();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FirebaseConnection getInstance() {
        if (instance == null) {
            instance = new FirebaseConnection();
        }
        return instance;
    }

    public Firestore getFirestore() {
        return db;
    }
}
