package com.Maruszak.QuizEngine.controller;


import com.Maruszak.QuizEngine.model.User;
import com.Maruszak.QuizEngine.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserDetailsServiceImpl userService;


    public UserController() {
    }

    @PostMapping(path = "/api/register")
    public User register(@RequestBody @Valid User userTemp) {
        return userService.register(userTemp);
    }
}
