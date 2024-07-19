package com.contactplus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.contactplus.entities.User;
import com.contactplus.repositories.UserRepository;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // public CustomUserServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byEmail = userRepository.findByEmail(username);
        return byEmail;
    }

}
