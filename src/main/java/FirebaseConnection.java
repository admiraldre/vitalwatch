import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConnection {
    private static FirebaseConnection instance;

    private FirebaseConnection() {
        try {
            FileInputStream serviceAccount = new FileInputStream("/vitalwatch-app-firebase-adminsdk-v32z4-72358b04a3.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://vitalwatch-app.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
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
}
