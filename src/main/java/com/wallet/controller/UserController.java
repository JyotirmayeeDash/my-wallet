package com.wallet.controller;

import com.wallet.model.CreateUserResponse;
import com.wallet.model.UserDetails;
import com.wallet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public CreateUserResponse createUser(@Valid @RequestBody UserDetails user) {

        return userService.createUser(user);
    }

}
