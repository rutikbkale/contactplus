package com.contactplus.services;

import com.contactplus.entities.User;

public interface UserService {

    public User saveUser(User user);

    public User getUserByEmail(String email);

}
