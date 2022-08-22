package com.notes.api.services;

import com.notes.api.auth.Auth;
import com.notes.api.auth.AuthUserCredentials;
import com.notes.api.entities.User;
import com.notes.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    Auth authImpl;

    @Autowired
    UserRepository userRepository;

    public UserService(Auth authImplementation) {
        this.authImpl = authImplementation;
    }

    public String signOnUser(String token) {
        AuthUserCredentials authUserCredentials = authImpl.verifyToken(token);

        if (authUserCredentials != null && ! userRepository.existsById(authUserCredentials.getUid())) {
            User newUser = new User();
            newUser.setId(authUserCredentials.getUid());
            newUser.setName(authUserCredentials.getName());
            newUser.setEmailAddress(authUserCredentials.getEmailAddress());
            userRepository.save(newUser);
            return newUser.getId();
        }

        if (authUserCredentials != null) {
            return authUserCredentials.getUid();
        }

        return "";
    }
}
