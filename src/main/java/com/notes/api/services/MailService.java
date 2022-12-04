package com.notes.api.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import io.swagger.v3.core.util.Json;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    private static final String apiKey = "86b8522e70958d5e554e7a7357084c87-f2340574-a59691f5";
    private static final String domainName = "https://api.mailgun.net/v3" +
            "/sandbox7acf68a2b2fd45e79c2bdb9d2ec50b7e.mailgun.org/messages";

    public JsonNode sendSimpleMessage(String userEmail) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(domainName)
                .basicAuth("api", apiKey)
                .queryString("from", "Eruditio Review Session <USER@YOURDOMAIN.COM>")
                .queryString("to", userEmail)
                .queryString("subject", "Flashcard Review Session Due")
                .queryString("text", "Hi, Your Flashcard review session is due.")
                .asJson();

        return response.getBody();
    }
}
