package com.notes.api.auth;

public interface Auth {
    AuthUserCredentials verifyToken(String token);
}
