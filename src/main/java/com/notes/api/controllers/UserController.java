package com.notes.api.controllers;

import com.notes.api.controllers.responses.SignOnResponse;
import com.notes.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    // TODO: exclude this endpoint from auth
    @PostMapping("/signon")
    @ResponseBody
    public SignOnResponse signOn(@RequestHeader("Authorization") String token) {
        String userId = userService.signOnUser(token);

        if (userId.isEmpty()) {
            return new SignOnResponse(false, "something went wrong signing on", "");
        }
        return new SignOnResponse(true, "successfully signed on", userId);
    }

}
