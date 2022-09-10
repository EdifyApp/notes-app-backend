package com.notes.api.services;

import com.google.firebase.auth.FirebaseToken;
import com.notes.api.entities.User;
import com.notes.api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public String signOnUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FirebaseToken firebaseToken = (FirebaseToken) authentication.getCredentials();

        if (firebaseToken == null) {
            return "";
        }

        if (! userRepository.existsById(firebaseToken.getUid())) {
            logger.info("creating new user");
            createNewUser(firebaseToken.getName(), firebaseToken.getUid(), firebaseToken.getEmail());
        }
        return firebaseToken.getUid();
    }

    public User getUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public void createNewUser(String name, String id, String email){
        User newUser = new User();
        newUser.setEmailAddress(email);
        newUser.setId(id);
        newUser.setName(name);

        userRepository.save(newUser);
    }
}
