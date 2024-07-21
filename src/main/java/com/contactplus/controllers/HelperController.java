package com.contactplus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.contactplus.entities.User;
import com.contactplus.helpers.Helper;
import com.contactplus.services.UserService;

@ControllerAdvice
public class HelperController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getUserDetails(Model model, Authentication authentication) {

        if (authentication == null) {
            return;
        }

        String username = Helper.getAuthenticateUserName(authentication);
        User loggedUser = userService.getUserByEmail(username);
        System.out.println(loggedUser.getEmail());
        System.out.println(loggedUser.getUsername());
        model.addAttribute("loggedUser", loggedUser);

    }

}
