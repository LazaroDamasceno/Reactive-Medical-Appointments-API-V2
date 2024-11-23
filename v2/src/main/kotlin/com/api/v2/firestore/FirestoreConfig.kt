package com.api.v2.firestore

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirestoreConfig {

    @PostConstruct
    fun config() {
        val path = "src/main/resources/private_key"
        val serviceAccount = FileInputStream(path)
        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        FirebaseApp.initializeApp(options)
    }

}