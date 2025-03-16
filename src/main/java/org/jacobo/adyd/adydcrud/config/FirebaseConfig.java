package org.jacobo.adyd.adydcrud.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirebaseConfig {
	
	@Bean("firebasedb")
	public Firestore getFireStore() {
		try {
			final InputStream is = ClassLoader.getSystemResourceAsStream("clave_privada_firebase.json");
			final FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(is))
			.build();
			final FirebaseApp firebase =  FirebaseApp.initializeApp(options);
			return FirestoreClient.getFirestore(firebase);
		} catch (IOException e) {		
			e.printStackTrace();
		}
		return null;
		
	}

}
