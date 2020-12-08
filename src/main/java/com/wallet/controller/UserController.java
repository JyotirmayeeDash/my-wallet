package com.wallet.controller;

import com.wallet.model.request.UserDetails;
import com.wallet.model.response.CreateUserResponse;
import com.wallet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * This controller class is used to create user(Sign-up).
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * End point to create a new user.
     *
     * @param user user
     * @return create user response
     */
    @PostMapping("/user")
    public CreateUserResponse createUser(@Valid @RequestBody UserDetails user) {

        log.info("Received request to create user.");
        return userService.createUser(user);
    }

}
