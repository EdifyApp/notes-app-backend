package com.notes.api.auth;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Qualifier("firebaseAuthImpl")
public class FirebaseAuthImpl implements Auth{

    Logger logger = LoggerFactory.getLogger(FirebaseAuthImpl.class);

    public FirebaseAuthImpl() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault()).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            FirebaseApp.initializeApp();
        }
    }

    @Override
    public AuthUserCredentials verifyToken(String token) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        try {
            FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(token, true);
            AuthUserCredentials authUserCredentials = new AuthUserCredentials();
            authUserCredentials.setName(verifiedToken.getName());
            authUserCredentials.setEmailAddress(verifiedToken.getEmail());
            authUserCredentials.setUid(verifiedToken.getUid());
            return authUserCredentials;
        } catch (FirebaseAuthException exception) {
            logger.info("something went wrong");
            logger.info(exception.getMessage());
            return null;
        }
    }
}
