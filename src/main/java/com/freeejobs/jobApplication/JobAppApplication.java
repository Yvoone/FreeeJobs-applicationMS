package com.freeejobs.jobApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.freeejobs.jobApplication.model.FirebaseCredential;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class JobAppApplication {

	@Bean
	FirebaseMessaging firebaseMessaging() throws Exception {
		InputStream firebaseCredentialStream = createFirebaseCredential();

		GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream(firebaseCredentialStream);
		FirebaseOptions firebaseOptions = FirebaseOptions
				.builder()
				.setCredentials(googleCredentials)
				.build();
		FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "freeejobs-app");
		return FirebaseMessaging.getInstance(app);
	}

	public static void main(String[] args) {
		SpringApplication.run(JobAppApplication.class, args);
	}

	private InputStream createFirebaseCredential() throws Exception {
		//private key

		FirebaseCredential firebaseCredential = new FirebaseCredential();
		//serialization of the object to json string
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(firebaseCredential);

		//convert jsonString string to InputStream using Apache Commons
		return IOUtils.toInputStream(jsonString);
	}

}
