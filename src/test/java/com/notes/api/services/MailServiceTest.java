package com.notes.api.services;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {


    private MailService mailService;

    @BeforeEach
    public void setup () {
        mailService = new MailService();
    }

    @Test
    public void givenUserEmail_mailSent() throws UnirestException {
        String userEmail = "eruditiotest@gmail.com";
        JsonNode mailResponse = mailService.sendSimpleMessage(userEmail);
        Assertions.assertNotNull(mailResponse);
        Assertions.assertEquals("Queued. Thank you.", mailResponse.getObject().get("message"));
    }
}
