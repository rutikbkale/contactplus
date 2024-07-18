package com.contactplus.services.impl;

import org.springframework.stereotype.Service;

import com.contactplus.entities.User;
import com.contactplus.repositories.UserRepository;
import com.contactplus.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
