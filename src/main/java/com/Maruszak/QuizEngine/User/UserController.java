package com.Maruszak.QuizEngine.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController() {
    }

    @PostMapping(path = "/api/register")
    public User register(@RequestBody @Valid  User userTemp) {
        User user;
        user = userRepository.findByEmail(userTemp.getUsername());
        if (user == null) {
            user = new User();
            user.setEmail(userTemp.getUsername());
            user.setPassword(passwordEncoder.encode(userTemp.getPassword()));
            user.setRole(new GrantedAuthority[]{new SimpleGrantedAuthority("USER")});
            userRepository.save(user);
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

    }
}
