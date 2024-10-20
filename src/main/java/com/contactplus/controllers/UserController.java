package com.contactplus.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactplus/user")
public class UserController {

    @RequestMapping("/dashboard")
    public String userDashboard(Authentication authentication) {
        return "user/dashboard";
    }

}
