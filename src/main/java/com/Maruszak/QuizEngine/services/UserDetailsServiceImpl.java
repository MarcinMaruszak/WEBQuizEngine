package com.Maruszak.QuizEngine.services;

import com.Maruszak.QuizEngine.model.User;
import com.Maruszak.QuizEngine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }else {
            throw new UsernameNotFoundException("User Doesn't Exist");
        }
    }

    @Override
    public User register(User tempUser) {
        Optional<User> userOptional = userRepository.findByEmail(tempUser.getEmail());
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setEmail(tempUser.getUsername());
            user.setPassword(passwordEncoder.encode(tempUser.getPassword()));
            user.setRole(new GrantedAuthority[]{new SimpleGrantedAuthority("USER")});
            userRepository.save(user);
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
    }
}
