package com.notes.api.controllers;

import com.notes.api.controllers.requests.AuthRequest;
import com.notes.api.controllers.responses.SignOnResponse;
import com.notes.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signon")
    @ResponseBody
    public SignOnResponse signOn(@RequestBody AuthRequest request) {
        String userId = userService.signOnUser(request.getToken());

        if (userId.isEmpty()) {
            return new SignOnResponse(false, "something went wrong signing on", "");
        }
        return new SignOnResponse(true, "successfully signed on", userId);
    }

}
