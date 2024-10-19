package com.contactplus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.contactplus.entities.User;
import com.contactplus.repositories.UserRepository;
import com.contactplus.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // encoded the user password
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // getting user from email id
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
