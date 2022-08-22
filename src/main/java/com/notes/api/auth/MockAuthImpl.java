package com.notes.api.auth;

import org.springframework.stereotype.Component;

@Component
public class MockAuthImpl implements Auth {

    @Override
    public AuthUserCredentials verifyToken(String token) {
        AuthUserCredentials mockCredentials = new AuthUserCredentials();
        mockCredentials.setUid("abc12345");
        mockCredentials.setName("test-name");
        mockCredentials.setEmailAddress("testname@testdomain.com");
        return mockCredentials;
    }
}
